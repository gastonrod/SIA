% This function takes a series of patterns and tries out different architectures
%   to see which is the most fit.
%
% Parameters:
%
% init_weights is a cell array of matrices. init_weights{m}(j, i) holds the 
%   weight of the connection from unit i in the m-1 layer to unit j in the m 
%   layer
%
% g is a two dimentional cell array of function handles. g{m}{1} is the the 
% activation function for layer m; g{m}{2} is the derivative of g{m}{1} in 
% terms of g (for example, if g{m}{1}(x) = tanh(x), then g{m}{2}(x) = 1-x^2
%
%
% patterns is a two dimentional cell array. patterns{i}{1} contains and input 
%   pattern; patterns{i}{2} holds the expected output
%
%
% [OPTIONAL]
%
% divisions is an integer that says in how many parts do you want to divide the
%   patterns set to use as learning patterns
%
% eps is a scalar value that indicates how big the difference between expected 
%   output and actual output can be. It's default value is 0.01.
%
% eta is the beginning learning rate 
% eta_eps is how much you want to increase eta per run
%
% epochs is the number of times to run the patterns
% epoch_eps is how much you want to increase epoch per run
% epoch_max is the maximum number epoch will be able to reach

function [best_percentage best_eta best_epoch best_weights] = ...
                         automated_testing( init_weights, g, patterns, ...
                         divisions = 4, eps= 0.1, eta= 0.1, eta_eps= 0.1, ...
                         epoch= 100,epoch_eps= 100, epoch_max= 1000)
  best_epoch = -1;
  best_eta   = -1;
  best_percentage = 1;
  
  % I'm assuming 1 is the max possible value for eta
  eta_iterations = (1- eta)/eta_eps
  epoch_iterations = (epoch_max - epoch)/epoch_eps
  patterns_size = numel(patterns);
  learning_patterns = {patterns{1:patterns_size/divisions}};
  for j = [1:divisions-1]
    for k = [0:eta_iterations]
      for i = [0:epoch_iterations]
        W = incremental_learn(init_weights, learning_patterns, g, ...
                              eta+(k*eta_eps), epoch+(i*epoch_eps));
        pct = test(W, patterns, g, eps, false);
        if best_percentage > pct
          best_percentage= pct;
          best_eta = eta+(k*eta_eps);
          best_epoch = epoch+(i*epoch_eps);
          best_weights = W;
        end
      end
    end
    learning_patterns = {patterns{patterns_size/(divisions/j): ...
                         patterns_size*((j+1)/divisions)}};
  end
  
end