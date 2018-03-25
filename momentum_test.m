function out = momentum_test(arqW, patterns, eta, epochs)
  arq_num = numel(arqW);
  out = cell(arq_num, 1);
  won_simple = 0;
  won_momentum = 0;
  for w = [1:arq_num]
    M = numel(arqW{w});
    g = cell(M, 1);
    for k = [1:M-1]
      g{k} = {@tanh, @dtanh};
    endfor
    g{M} = {@(x) x, @(x) 1};
    WE_simple = incremental_learn(arqW{w}, patterns, g, eta, epochs, false, 0, [], true);
    WE_momentum = incremental_learn(arqW{w}, patterns, g, eta, epochs, false, 0.9, [], true);
    err_simple = WE_simple{2}(epochs);
    err_momentum = WE_momentum{2}(epochs);
    out{w} = {WE_simple{2}, WE_momentum{2}};
    if (err_simple > err_momentum)
      won_momentum++;
    else
      if (err_simple < err_momentum)
        won_simple++;
      else
        won_simple += 0.5;
        won_momentum += 0.5;
      endif
    endif
  endfor
  printf('simple won: %f\nmomentum won: %f\n', won_simple, won_momentum);
endfunction