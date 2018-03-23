% All possible patterns
all_patterns = {
 [{[0;0;0;0;0]} 0]
 [{[0;0;0;0;1]} 1]
 [{[0;0;0;1;0]} 1]
 [{[0;0;0;1;1]} 0]
 [{[0;0;1;0;0]} 1]
 [{[0;0;1;0;1]} 0]
 [{[0;0;1;1;0]} 0]
 [{[0;0;1;1;1]} 1]
 [{[0;1;0;0;0]} 1]
 [{[0;1;0;0;1]} 0]
 [{[0;1;0;1;0]} 0]
 [{[0;1;0;1;1]} 1]
 [{[0;1;1;0;0]} 0]
 [{[0;1;1;0;1]} 1]
 [{[0;1;1;1;0]} 1]
 [{[0;1;1;1;1]} 0]
 [{[0;0;0;0;0]} 0]
 [{[1;0;0;0;1]} 0]
 [{[1;0;0;1;0]} 0]
 [{[1;0;0;1;1]} 1]
 [{[1;0;1;0;0]} 0]
 [{[1;0;1;0;1]} 1]
 [{[1;0;1;1;0]} 1]
 [{[1;0;1;1;1]} 0]
 [{[1;1;0;0;0]} 0]
 [{[1;1;0;0;1]} 1]
 [{[1;1;0;1;0]} 1]
 [{[1;1;0;1;1]} 0]
 [{[1;1;1;0;0]} 1]
 [{[1;1;1;0;1]} 0]
 [{[1;1;1;1;0]} 0]
 [{[1;1;1;1;1]} 1]
};

% Learning patterns to be used in the learning and testing process
learning_patterns = {
 [{[0;0;0;0;0]} 0]
 [{[0;0;0;0;1]} 1]
 [{[0;0;0;1;0]} 1]
 [{[0;0;0;1;1]} 0]
 [{[0;0;1;0;0]} 1]
 [{[0;0;1;0;1]} 0]
 [{[0;0;1;1;0]} 0]
 [{[0;0;1;1;1]} 1]
 [{[0;1;0;0;0]} 1]
};

% Activation functions and their derivatives
g = {
 [{@tanh} {@dtanh}]
 [{@tanh} {@dtanh}]
};

% Initial matrix of weights. First layer initialized with random small numbers.
init_weights = {
 [rand(5,6)/10]
 [[0 0 0 0 0 0]]
};

% Learning rate. 0.3 seems to be doing fine
eta = 0.3;

% Amount of times to loop through the patterns
epoch = 1000;

W = incremental_learn(init_weights, learning_patterns, g, eta, epoch);

test(W, all_patterns, g);