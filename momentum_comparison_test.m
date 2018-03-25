function out = momentum_comparison_test(arqW, patterns, eta, epochs)
  arq_num = numel(arqW);
  out = cell(arq_num, 1);
  won_low = 0;
  won_high = 0;
  for w = [1:arq_num]
    M = numel(arqW{w});
    g = cell(M, 1);
    for k = [1:M-1]
      g{k} = {@tanh, @dtanh};
    endfor
    g{M} = {@(x) x, @(x) 1};
    WE_low = incremental_learn(arqW{w}, patterns, g, eta, epochs, false, 0.5, [], true);
    WE_high = incremental_learn(arqW{w}, patterns, g, eta, epochs, false, 0.9, [], true);
    err_low = WE_low{2}(epochs);
    err_high = WE_high{2}(epochs);
    out{w} = {WE_low{2}, WE_high{2}};
    if (err_low > err_high)
      won_high++;
    else
      if (err_low < err_high)
        won_low++;
      else
        won_low += 0.5;
        won_high += 0.5;
      endif
    endif
  endfor
  printf('low won: %f\nhigh won: %f\n', won_low, won_high);
endfunction