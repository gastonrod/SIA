% This function takes a series of patterns and checks how well the network does.
%
% Paramters:
%
% W is a cell array of matrices. W{m}(j, i) holds the weight of the
%   connection from unit i in the m-1 layer to unit j in the m layer
%
% patterns is a two dimentional cell array. patterns{i}{1} contains and input 
%   pattern; patterns{i}{2} holds the expected output
%
% g is a two dimentional cell array of function handles. g{m}{1} is the the 
%   activation function for layer m; g{m}{2} is the derivative of g{m}{1} in 
%   terms of g (for example, if g{m}{1}(x) = tanh(x), then g{m}{2}(x) = 1-x^2
%
%
% [OPTIONAL]
%
% eps is a scalar value that indicates how big the difference between expected 
%   output and actual output can be. It's default value is 0.01.
%
% print_ans is a boolean that says wether we print the results or not.

function wrong_pct = test(W, patterns, g, eps= 0.1, print_ans= true)
  err_count = 0;
  for k = [1:numel(patterns)]
    V = run_pattern(W, patterns{k}{1}, g);
    output = V{numel(V)};
    if abs(output - patterns{k}{2}) > eps
      err_count++;
    endif
  end
  wrong_pct = err_count / numel(patterns);
  if print_ans
    printf("%f%% of wrong answers\n",  wrong_pct * 100);
  end
end