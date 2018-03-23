% This function takes a series of patterns and plots what the neural network 
%   thinks the answers are.
%
% Parameters:
%
% W is a cell array of matrices. W{m}(j, i) holds the weight of the
%   connection from unit i in the m-1 layer to unit j in the m layer
%
% g is a two dimentional cell array of function handles. g{m}{1} is the the 
%   activation function for layer m; g{m}{2} is the derivative of g{m}{1} in 
%   terms of g (for example, if g{m}{1}(x) = tanh(x), then g{m}{2}(x) = 1-x^2
%
% patterns is a two dimentional cell array. patterns{i}{1} contains and input 
%   pattern.

function plot_nn(W, patterns, g)
  x = [];
  y = [];
  z = [];
  for k = [1:numel(patterns)]
    x(k) = patterns{k}{1}(1);
    y(k) = patterns{k}{1}(2);
    V = run_pattern(W, [x(k);y(k)], g);
    z(k) = V{numel(V)};
  end
  title ("Neural network's interpretation");
  plot3(x, y, z, "o");
end