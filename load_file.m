function [x y z] = load_file(fn)
  data = load('-ascii', fn)
  length = length(data / 3)
  x = data(1:length)
  y = data(length+1:length*2)
  z = data(length*2+1:length*3)
end