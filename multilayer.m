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
% terms of g (for example, if g{m}{1}(x) = tanh(x), then g{m}{2}(x) = 1-x^2)
%
% eta is the learning rate
%
% epochs is the number if epochs to run the training for
%
% momentum is the momentum parameter. It defaults to cero (plain back-propagation)
%
% aep is the adaptative eta parameters values. aep(1) is the increasing factor;
% aep(2) is the decreasing factor; aep(3) is the number of successful epochs 
% before update. If aep is empty, no adaptative eta ocurrs (default)
%
% Return value:
%
% ansW holds the weight matrices of the trained neural network
function ansW = batch_learn(W, patterns, g, eta, epochs, momentum = 0, aep = [])
  ansW = learn(W, patterns, g, eta, epochs, true, false, momentum, aep);
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
% terms of g (for example, if g{m}{1}(x) = tanh(x), then g{m}{2}(x) = 1-x^2)
%
% eta is the learning rate
%
% epochs is the number if epochs to run the training for
%
% random_pass is a boolean value specifying whether the order of patterns 
% during an epoch should be chosen randomly or not. By default it's set to
% false
%
% momentum is the momentum parameter. It defaults to cero (plain back-propagation)
%
% aep is the adaptative eta parameters values. aep(1) is the increasing factor;
% aep(2) is the decreasing factor; aep(3) is the number of successful epochs 
% before update. If aep is empty, no adaptative eta ocurrs (default)
%
% Return value:
%
% ansW holds the weight matrices of the trained neural network
function ansW = incremental_learn(W, patterns, g, eta, epochs, random_pass = false, momentum = 0, aep = [])
  ansW = learn(W, patterns, g, eta, epochs, false, random_pass, momentum, aep);
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
% terms of g (for example, if g{m}{1}(x) = tanh(x), then g{m}{2}(x) = 1-x^2)
%
% eta is the learning rate
%
% epochs is the number if epochs to run the training for
%
% is_batch is a boolean value specifying whether the training should be batch 
% or incremental
%
% random_pass is a boolean value specifying whether the order of patterns 
% during an epoch should be chosen randomly or not
%
% momentum is the momentum parameter
%
% aep is the adaptative eta parameters values. aep(1) is the increasing factor;
% aep(2) is the decreasing factor; aep(3) is the number of successful epochs 
% before update. If aep is empty, no adaptative eta ocurrs
%
% Return value:
%
% ansW holds the weight matrices of the trained neural network
function ansW = learn(W, patterns, g, eta, epochs, is_batch, random_pass, momentum, aep)
  n = numel(patterns);
  % M is the number of layers (without counting the input layer)
  M = numel(W);
  batch_dw = cell(M, 1);
  % last_dw will hold the weight updates for the previous epoch (to implement momentum)
  last_dw = cell(M,1);
  % last_err will hold the error of the last epoch (to implement adaptative eta)
  last_err = Inf;
  % consecutive_success will hold the the consecutive number of epochs during which the
  % learning has been successful
  consecutive_success = 0;
  for i = [1:M]
    last_dw{i} = zeros(rows(W{i}), columns(W{i}));
  endfor
  
  for k = [1:epochs]
    
    % Permute the patterns array uniformly if requested
    if (random_pass)
      for i = [n:-1:2]
        j = floor((unifrnd(1, n+1)-1)*0.99999+1);
        temp = patterns{i};
        patterns{i} = patterns{j};
        patterns{j} = temp;
      endfor
    endif

    % Initialize batch_dw, which will accumulate the weight changes for one whole epoch
    if (is_batch)
      for i = [1:M]
        batch_dw{i} = zeros(rows(W{i}), columns(W{i}));
      endfor
    endif

    % Run each pattern once
    for p = [1:n]
      dw = run_and_correct(W, patterns{p}{1}, g, patterns{p}{2}, eta);
      if (is_batch)
        for i = [1:M]
          batch_dw{i} += dw{i};
        endfor
      else
        for i = [1:M]
          W{i} += dw{i} + momentum*last_dw{i};
        endfor
        last_dw = dw;
      endif
    endfor

    if (is_batch)
      for j = [1:M]
        W{j} += batch_dw{j} + momentum*last_dw{i};
      endfor
      last_dw = batch_dw;
    endif

    if (aep)
      err = calculate_error(W, patterns, g);
      if (err < last_err)
        consecutive_success++;
        if (consecutive_success == aep(3))
          eta += aep(1);
          consecutive_success = 0;
        endif
      else
        consecutive_success = 0;
        if (err > last_err)
          eta -= aep(2)*eta;
        endif
      endif
      last_err = err;
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
% This function takes a neural network and a series of patterns and returns the 
% overall error
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
% activation function for layer m
%
% Return value:
%
% V is a cell array of column vectors. V{m}(i) holds the output of unit i at
% layer m-1
function err = calculate_error(W, patterns, g)
  M = numel(W);
  outsize = rows(W{M});
  P = numel(patterns);
  err = 0;
  for p = [1:P]
    res = run_pattern(W, patterns{p}{1}, g){M+1};
    for o = [1:outsize]
      err += (patterns{p}{2}(o) - res(o))^2;
    endfor
  endfor
  err /= 2;
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

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% This function takes a vector of integers and return a random cell-array of 
% matrices representing a neural network. v(m) is the number of units at layer 
% m-1
function W = random_weights(v)
  M = numel(v)-1;
  W = cell(M, 1);
  for m = [1:M]
    high = 2/sqrt(v(m));
    low = high/4;
    W(m) = rand(v(m+1), (v(m)+1))*(high - low) + low;
  endfor
endfunction