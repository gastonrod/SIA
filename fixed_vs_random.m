function out = fixed_vs_random(arqW, patterns, eta, epochs)
  arq_num = numel(arqW);
  out = cell(arq_num, 1);
  won_fixed = 0;
  won_random = 0;
  for w = [1:arq_num]
    M = numel(arqW{w});
    g = cell(M, 1);
    for k = [1:M-1]
      g{k} = {@tanh, @dtanh};
    endfor
    g{M} = {@(x) x, @(x) 1};
    WE_fixed = incremental_learn(arqW{w}, patterns, g, eta, epochs, false, 0, [], true);
    WE_random = incremental_learn(arqW{w}, patterns, g, eta, epochs, true, 0, [], true);
    err_fixed = WE_fixed{2}(epochs);
    err_random = WE_random{2}(epochs);
    out{w} = {WE_fixed{2}, WE_random{2}};
    if (err_fixed > err_random)
      won_random++;
    else
      if (err_fixed < err_random)
        won_fixed++;
      else
        won_fixed += 0.5;
        won_random += 0.5;
      endif
    endif
  endfor
  printf('fixed won: %f\nrandom won: %f\n', won_fixed, won_random);
endfunction