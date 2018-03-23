1; % Not a function file

% This function trains a neural network on a training set using back-propagation
% and batch learning
%
% Parameters:
%
% W is a cell array of matrices. W{m}(j, i) holds the weight of the
% connection from unit i in the m-1 layer to unit j in the m layer
%
% patterns is a two dimentional cell array. patterns{i}{1} contains and input 
% pattern; patterns{i}{2} holds the expected output
%
% g is a two dimentional cell array of function handles. g{m}{1} is the the 
% activation function for layer m; g{m}{2} is the derivative of g{m}{1} in 
% terms of g (for example, if g{m}{1}(x) = tanh(x), then g{m}{2}(x) = 1-x^2
%
% eta is the learning rate
%
% epochs is the number if epochs to run the training for
%
% Return value:
%
% ansW holds the weight matrices of the trained neural network
function ansW = batch_learn(W, patterns, g, eta, epochs)
  ansW = learn(W, patterns, g, eta, epochs, true);
endfunction

% This function trains a neural network on a training set using back-propagation
% and incremental learning
%
% Parameters:
%
% W is a cell array of matrices. W{m}(j, i) holds the weight of the
% connection from unit i in the m-1 layer to unit j in the m layer
%
% patterns is a two dimentional cell array. patterns{i}{1} contains and input 
% pattern; patterns{i}{2} holds the expected output
%
% g is a two dimentional cell array of function handles. g{m}{1} is the the 
% activation function for layer m; g{m}{2} is the derivative of g{m}{1} in 
% terms of g (for example, if g{m}{1}(x) = tanh(x), then g{m}{2}(x) = 1-x^2
%
% eta is the learning rate
%
% epochs is the number if epochs to run the training for
%
% Return value:
%
% ansW holds the weight matrices of the trained neural network
function ansW = incremental_learn(W, patterns, g, eta, epochs)
  ansW = learn(W, patterns, g, eta, epochs, false);
endfunction

% This function trains a neural network on a training set using back-propagation
%
% Parameters:
%
% W is a cell array of matrices. W{m}(j, i) holds the weight of the
% connection from unit i in the m-1 layer to unit j in the m layer
%
% patterns is a two dimentional cell array. patterns{i}{1} contains and input 
% pattern; patterns{i}{2} holds the expected output
%
% g is a two dimentional cell array of function handles. g{m}{1} is the the 
% activation function for layer m; g{m}{2} is the derivative of g{m}{1} in 
% terms of g (for example, if g{m}{1}(x) = tanh(x), then g{m}{2}(x) = 1-x^2
%
% eta is the learning rate
%
% epochs is the number if epochs to run the training for
%
% is_batch is a boolean value specifying if the training should be batch or 
% incremental
%
% Return value:
%
% ansW holds the weight matrices of the trained neural network
function ansW = learn(W, patterns, g, eta, epochs, is_batch)
  n = numel(patterns);
  M = numel(W);
  batch_dw = cell(M, 1);
  
  for k = [1:epochs]
    
    if (is_batch)
      for i = [1:M]
        batch_dw{i} = zeros(rows(W{i}), columns(W{i}));
      endfor
    endif
    
    for p = [1:n]
      dw = run_and_correct(W, patterns{p}{1}, g, patterns{p}{2}, eta);
      if (is_batch)
        for i = [1:M]
          batch_dw{i} += dw{i};
        endfor
      else
        for i = [1:M]
          W{i} += dw{i};
        endfor
      endif
    endfor

    if (is_batch)
      for j = [1:M]
        W{j} += batch_dw{j};
      endfor
    endif

  endfor
  
  ansW = W;
endfunction

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% This function takes a neural network, a training pattern and a learning rate
% and calculates the weight updates according to the back-propagation rule
%
% Parameters:
%
% W is a cell array of matrices. W{m}(j, i) holds the weight of the
% connection from unit i in the m-1 layer to unit j in the m layer
%
% E is a column vector. E(i) holds the input for unit i
%
% g is a two dimentional cell array of function handles. g{m}{1} is the the 
% activation function for layer m; g{m}{2} is the derivative of g{m}{1} in 
% terms of g (for example, if g{m}{1}(x) = tanh(x), then g{m}{2}(x) = 1-x^2
%
% S is the expected output as a column vector
%
% eta is the learning rate
%
% Return value:
%
% dw is a cell array of matrices. dw{m} contains the updates to be added to the 
% weight matrix at layer m
function dw = run_and_correct(W, E, g, S, eta)
  % M is the number of layers
  M = numel(W);
  V = run_pattern(W, E, g);
  
  % ders is a cell array. ders{m}(i) contains the derivative of the mth layer 
  % activation function evaluated at the ith unit's h
  ders = V;
  for k = [2:M]
    ders{k} = arrayfun(g{k-1}{2}, V{k}(2:end));
  endfor
  ders{M+1} = arrayfun(g{M}{2}, V{M+1});
  
  % delta is a cell array.
  delta = cell(M, 1);
  delta{M} = direct_product(S - V{M+1}, ders{M+1});
  for k = [M-1:-1:1]
    delta{k} = direct_product((delta{k+1}*W{k+1})(2:end), ders{k+1});
  endfor
  % dw is a cell array of matrices; dw{m} contains the weight update matrix for 
  % layer m
  dw = cell(M, 1);
  for k = [1:M]
    dw{k} = eta*(V{k}*delta{k})';
  endfor
  
endfunction

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% This function takes a neural network and an input pattern and returns the 
% output at every layer
%
% Parameters:
%
% W is a cell array of matrices. W{m}(j, i) holds the weight of the
% connection from unit i in the m-1 layer to unit j in the m layer
%
% E is a column vector. E(i) holds the input for unit i
%
% g is a two dimentional cell array of function handles. g{m}{1} is the the 
% activation function for layer m
%
% Return value:
%
% V is a cell array of column vectors. V{m}(i) holds the output of unit i at
% layer m-1
function V = run_pattern(W, E, g)
  M = numel(W);
  V = cell(M+1, 1);
  V{1} = E;
  for k = [1:M]
    V{k} = [-1; V{k}];
    V{k+1} = arrayfun(g{k}{1}, W{k}*V{k});
  endfor
endfunction

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% This fucntion takes two vectors v and w with the same length and returns a row
% vector out, where out(i) = v(i)*w(i) (one to one product)
function out = direct_product(v, w)
  out = [];
  for k = [1:numel(v)]
    out = [out v(k)*w(k)];
  endfor
endfunction