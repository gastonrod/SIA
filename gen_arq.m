function arqW = gen_arq()
  arqV = {[2 2 1], [2 5 1], [2 10 1], [2 20 1], [2 2 2 1], [2 5 2 1], [2 2 5 1], [2 5 5 1], [2 10 5 1], [2 5 10 1], [2 10 10 1]};
  arqW = cell(numel(arqV),1);
  for k = [1:numel(arqV)]
  	arqW{k} = random_weights(arqV{k});
  endfor
endfunction