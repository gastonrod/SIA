function test(x,y,z)
  eps = 1e-05;
  err_count = 0;
  for k = [1:numel(x)]
    % run : Give the input to the neural network and get an answer.
    ans = run(x(k),y(k));
    if abs(ans- z(k)) > eps
      err_count++;
    endif
  end
  %print error percentage
  printf("%f%% of wrong answers\n", err_count / numel(x));
end