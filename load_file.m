function info = load_file(fn)
  data = load('-ascii', fn);
  length = length(data / 3);
  x = data(1:length);
  y = data(length+1:length*2);
  z = data(length*2+1:length*3);
  info = {};
  for k = [1:length]
    info{k}{1}(1) = x(k);
    info{k}{1}(2) = y(k);
    info{k}{2} = z(k);
  end
end