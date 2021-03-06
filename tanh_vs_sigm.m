%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% This function takes a series of neural networks and compares the results of 
% using hyperbolic tangent vs sigmoid as activation functions
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
% vector with the global errors epoch by epoch with hyperbolic tangent;
% out{i}{2} is analogous but with sigmoid learning
function out = tanh_vs_sigm(arqW, patterns, eta, epochs)
  arq_num = numel(arqW);
  out = cell(arq_num, 1);
  won_tanh = 0;
  won_sigm = 0;
  for w = [1:arq_num]
    M = numel(arqW{w});
    g_tanh = cell(M, 1);
    g_sigm = cell(M, 1);
    for k = [1:M-1]
      g_tanh{k} = {@tanh, @dtanh};
      g_sigm{k} = {@sigm, @dsigm};
    endfor
    g_tanh{M} = {@(x) x, @(x) 1};
    g_sigm{M} = {@(x) x, @(x) 1};
    WE_tanh = incremental_learn(arqW{w}, patterns, g_tanh, eta, epochs, false, 0, [], true);
    WE_sigm = incremental_learn(arqW{w}, patterns, g_sigm, eta, epochs, false, 0, [], true);
    err_tanh = WE_tanh{2}(epochs);
    err_sigm = WE_sigm{2}(epochs);
    out{w} = {WE_tanh{2}, WE_sigm{2}};    
    if (err_tanh > err_sigm)
      won_sigm++;
    else
      if (err_tanh < err_sigm)
        won_tanh++;
      else
        won_tanh += 0.5;
        won_sigm += 0.5;
      endif
    endif
  endfor
  printf('tanh won: %d\nsigm won: %d\n', won_tanh, won_sigm);
endfunction