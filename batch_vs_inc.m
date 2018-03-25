%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% This function takes a series of neural networks and compares the results of 
% training them with batch learning vs incremental learning
%
% Parameters:
%
% arqW is a cell array of architectures, where every element contains a cell 
% array of weight matrices
%
% patterns is the training set
%
% eta is the learning rate
%
% epochs is the number of epochs to train the network for
%
% Return value:
%
% The functon returns a cell array with as many elements as architectures
% out{i} is a cell array corresponding to arqW{i}. out{i}{1} contains a 
% vector with the global errors epoch by epoch with incremental learning; out{i}{2}
% is analogous but with batch learning
function out = batch_vs_inc(arqW, patterns, eta, epochs)
  arq_num = numel(arqW);
  out = cell(arq_num, 1);
  won_batch = 0;
  won_inc = 0;
  for w = [1:arq_num]
    M = numel(arqW{w});
    g = cell(M, 1);
    for k = [1:M-1]
      g{k} = {@tanh, @dtanh};
    endfor
    g{M} = {@(x) x, @(x) 1};
    WE_inc = incremental_learn(arqW{w}, patterns, g, eta, epochs, false, 0, [], true);
    WE_batch = batch_learn(arqW{w}, patterns, g, eta, epochs, 0, [], true);
    err_inc = WE_inc{2}(epochs);
    err_batch = WE_batch{2}(epochs);
    out{w} = {WE_inc{2}, WE_batch{2}};
    if (err_inc > err_batch)
      won_batch++;
    else
      if (err_inc < err_batch)
        won_inc++;
      else
        won_inc += 0.5;
        won_batch += 0.5;
      endif
    endif
  endfor
  printf('Incremental won: %f\nBatch won: %f\n', won_inc, won_batch);
endfunction