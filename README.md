# Artificial Intelligence Systems

## Objective

Implement a Multilayer Neural Network using supervized learning to approximate/simulate world terrains that look real.

## Usage

* Open Octave
* Load `multilayer.m` file.
* Run `study_terrain(terrain_path, epochs, n_samples)` where:
  * `terrain_path` is the relative path to a terrain data file.
  * `epochs` is the number of epochs to run.
  * `n_samples` is the amounts of data points to use from the data file.

Alternatively, you may run `study_terrain_custom` which offers richer configuration arguments:

* `study_terrain_custom(terrain_file, epochs, n_samples, learn_function, eta, momentum)` where:
  * `terrain_file, epochs, n_samples` are the same as above.
  * `learn_function` must be either `"incremental"` or `"batch"` and is used to set the learning function.
  * `eta` is the learning rate.
  * `momentum` is the momentum factor.

## Relevant files

* `multilayer.m` contains the functions necessary to make the neural network.
* `gen_arq.m` generates the eleven architectures we experimented with.
* `study_terrain` uses the already pre-set best parameters to learn a new terrain.
* `test_results.data` contains the results of running the 11th architecture with different epochs and sample sizes.
* `initial_W.data` contains the initial weights matrix utilized in the tests.
* `/terrains` contains all the terrains provided.
* `/images` contains the different graphs we gathered, such as different error rates for different architectures and many other things
