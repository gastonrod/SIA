% patterns{k} : Input - Expected output pair
function test(patterns)
  eps = 1e-05;
  err_count = 0;
  for k = [1:numel(patterns)]
    % run : Give the input to the neural network and get an answer.
    ans = run(patterns{k}{1});
    if abs(ans- patterns{k}{2}) > eps
      err_count++;
    endif
  end
  %print error percentage
  printf("%f%% of wrong answers\n", err_count / numel(patterns) * 100);
end