patterns = {
 [{[0;0;0;0;0]} 0]
 [{[1;0;0;0;0]} 1]
 [{[0;1;0;0;0]} 1]
 [{[1;1;0;0;0]} 0]
 [{[0;0;1;0;0]} 1]
 [{[1;0;1;0;0]} 0]
 [{[1;1;1;0;0]} 1]
};

g = {
 [{@tanh} {@dtanh}]
 [{@tanh} {@dtanh}]
};

init_weights = {
 [rand(5,6)/10]
 [[0 0 0 0 0 0]]
};

eta = 0.5;
epoch = 1000000;

W = incremental_learn(init_weights, patterns, g, eta, epoch);