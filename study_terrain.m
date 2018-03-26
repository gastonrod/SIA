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
% Output:
%
% out is a two dimentional cell-array. out{1} holds the weight matrices of 
% the trained neural network; out{2}(i) is the global error of the network 
% after epoch i

function out = study_terrain(terrain_file, epochs, n_samples)
  g = {{@tanh, @dtanh},{@tanh, @dtanh},{@(x) x, @(x) 1}};
  W = random_weights([2 10 10 1]);
  full_patterns = load_file(terrain_file);
  test_patterns = n_random_patterns(full_patterns, n_samples);
  out = incremental_learn(W, test_patterns, g, eta=0.05, epochs, random_pass = true, momentum = 0.9, aep = [], with_error = true);
  plot_nn(out{1}, full_patterns, g);  
endfunction 