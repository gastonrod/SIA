%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% This function studies a given terrain and plots and outputs a generalization.
%
% Parameters:
%
% terrain_file is a string containing the name of the file which contains
% the desired terrain.
%
% epochs is the number if epochs to run the training for.
%
% n_samples is an integer representing the number of samples from the terrain
% that will be used to train the neural network.
%
% learn_function must be either "batch" or "incremental", it sets the learning function.
%
% eta is the learning rate.
%
% momentum is the momentum factor
%
% Output:
%
% out is a two dimentional cell-array. out{1} holds the weight matrices of
% the trained neural network; out{2}(i) is the global error of the network
% after epoch i

function out = study_terrain_custom(terrain_file, epochs, n_samples, learn_function, eta, momentum)
  g = {{@tanh, @dtanh},{@tanh, @dtanh},{@(x) x, @(x) 1}};
  W = random_weights([2 10 10 1]);
  full_patterns = load_file(terrain_file);
  test_patterns = n_random_patterns(full_patterns, n_samples);
  if (strcmp(learn_function, "batch") != 0)
    out = batch_learn(W, test_patterns, g, eta, epochs, momentum, aep = [], true);
  endif
  if (strcmp(learn_function, "incremental") != 0)
    out = incremental_learn(W, test_patterns, g, eta, epochs, random_pass = true, momentum, aep = [], with_error = true);
  endif

  plot_nn(out{1}, full_patterns, g);
endfunction
