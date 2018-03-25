function out = arch_comparison(arqW, patterns, eta, epochs)
  arq_num = numel(arqW);
  out = cell(arq_num, 1);
  for w = [1:arq_num]
    printf('Running achitecture number %d...\n', w);
    fflush(stdout);
    M = numel(arqW{w});
    g = cell(M, 1);
    for k = [1:M-1]
      g{k} = {@tanh, @dtanh};
    endfor
    g{M} = {@(x) x, @(x) 1};
    out{w} = incremental_learn(arqW{w}, patterns, g, eta, epochs, true, 0.9, [], true);
  endfor

  best = Inf;
  winner = 0;
  for k = [1:arq_num]
    if (out{k}{2}(epochs) < best)
      winner = k;
      best = out{k}{2}(epochs);
    endif
  endfor
  printf('The winner is number %d\n', winner);
endfunction