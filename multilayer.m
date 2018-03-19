1; % Not a function file

% Parameters:
%
% W is a cell array of matrices. W{m}(j, i) holds the weight of the
% connection from unit i in the m-1 layer to unit j in the m layer
%
% E is a column vector. E(i) holds the input for unit i
%
% g is a cell array of function handles. g{m} is the the activation function for
% layer m

% Return value:
%
% V is a cell array of column vectors. V{m}(i) hols the output of unit i at
% layer m-1

function V = run_pattern(W, E, g)
  V = cell(numel(W)+1, 1);
  V{1} = E;
  for k = [1:numel(W)]
    V{k} = [-1; V{k}];
    V{k+1} = arrayfun(g{k}, W{k}*V{k});
  endfor
endfunction