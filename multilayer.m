1; % Not a function file

function out = learn(W, patterns, g, eta)
  n = numel(patterns);
  M = numel(W);
  for k = [1:100]
    p = mod(k,n)+1;
    printf("Running pattern: ");
    patterns{p}
    dw = run_pattern(W, patterns{p}{1}, g, patterns{p}{2}, eta);
    for j = [1:M]
      W{j} = W{j} + dw{j};
    endfor
    dw;
  endfor
  out = W;
endfunction

% Parameters:
%
% W is a cell array of matrices. W{m}(j, i) holds the weight of the
% connection from unit i in the m-1 layer to unit j in the m layer
%
% E is a column vector. E(i) holds the input for unit i
%
% g is a two dimentional cell array of function handles. g{m}{1} is the the 
% activation function for layer m; g{m}{2} is the derivative of g{m}{1} in 
% terms of g
%
% S is the expected output as a column vector
%
% eta is the learning rate

% Return value:
%
% dw is a cell array of matrices. dw{m} contains the updates to be added to the 
% weight matrix at layer m

function dw = run_pattern(W, E, g, S, eta)
  % M is the number of layers
  M = numel(W);
  % V is a cell array of column vectors. V{m}(i) hols the output of unit i at
  % layer m-1 
  V = cell(M+1, 1);
  V{1} = E;
  for k = [1:M]
    V{k} = [-1; V{k}];
    V{k+1} = arrayfun(g{k}{1}, W{k}*V{k});
  endfor
  
  % ders is a cell array. ders{m}(i) contains the derivative of the mth layer 
  % activation function evaluated at the ith unit's h
  ders = V;
  for k = [2:M]
    ders{k} = arrayfun(g{k-1}{2}, V{k}(2:end));
  endfor
  ders{M+1} = arrayfun(g{k-1}{2}, V{k});
  
  % delta is a cell array.
  delta = cell(M, 1);
  delta{M} = direct_product(S - V{M+1}, ders{M+1});
  for k = [M-1:-1:1]
    delta{k} = direct_product((delta{k+1}*W{k+1})(2:end), ders{k+2});
  endfor
  
  % dw is a cell array of matrices; dw{m} contains the weight update matrix for 
  % layer m
  dw = cell(M, 1);
  for k = [1:M]
    dw{k} = eta*(V{k}*delta{k})';
  endfor
  
endfunction

function out = direct_product(v, w)
  out = [];
  for k = [1:numel(v)]
    out = [out v(k)*w(k)];
  endfor
endfunction
