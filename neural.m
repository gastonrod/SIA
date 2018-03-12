SIZE = 5

function res = learn(p, w_i)
  corrections = 0
  SIZE = 5
  lr = 0.5
  w = w_i
  correct = 0
  i = 1
  while correct != rows(p)
    expected = p(i,SIZE+1)
    extended_pattern = [-1 p(i,1:SIZE)]
    obtained = calculate(w, extended_pattern, @sgn)
    if (expected == obtained)
      correct = correct+1
    else
      correct = 0
      corrections = corrections + 1
      w = w + lr*(expected - obtained)*extended_pattern
    end
    i = mod(i, rows(p))+1
  end
  res = [w corrections]
end

function obtained = calculate(w, p, g)
  obtained = g(dot(w, p))
end

function res = sgn(x)
  if (x >= 0)
    res = 1
  else
    res = -1
  end
end

training_set = [
  [1 1 1 1 1]  1;
  [1 1 1 1 0] -1;
  [0 0 0 0 0] -1;
]

w_i = [0 0 0 0 0 0]

res = learn(training_set, w_i)
w = res(1:SIZE+1)
corrections = res(SIZE+2)