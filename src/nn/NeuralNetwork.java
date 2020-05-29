package nn;

import java.util.ArrayList;

public class NeuralNetwork {
	
	public int SIZE = 0;
	public int NUM_OF_LAYERS = 0;
	public int OUT = 0;
	
	ArrayList<NeuralNetworkLayer> layers;
	ArrayList<Integer> linkToNeuron; // We have number of layer of i neuron in i position 
	
	double[][] synapse, adduct;
	double[] gamma;
	
	public NeuralNetwork() {
		layers = new ArrayList<NeuralNetworkLayer>();
		linkToNeuron = new ArrayList<Integer>();
	}
	
	public void addLayer(NeuralNetworkLayer arg0) {
		/**
		 * arg0 - current Neural Network layer
		 */

		arg0.setStartPos(SIZE);
		
		for(int i = 0; i < arg0.size(); i++)
			linkToNeuron.add(NUM_OF_LAYERS);
		
		layers.add(arg0);

		NUM_OF_LAYERS++;
		
		SIZE += arg0.size();
	}
	
	private Neuron getNeuron(int arg0) {
		/**
		 * arg0 - id of current neuron
		 */
		return layers.get(linkToNeuron.get(arg0)).get_global(arg0);
	}
	
	public double out(int arg0) {
		/**
		 * arg0 - id of current neuron
		 */
		return getNeuron(arg0).out;
	}
	
	private void init() {
		synapse = new double[SIZE][SIZE];
		adduct = new double[SIZE][SIZE];
		
		gamma = new double[SIZE];
		
		OUT = SIZE - 1;
		
		for(int i = 0; i < SIZE; i++) {
			gamma[i] = 0;
			for(int j = 0; j < SIZE; j++) {
				synapse[i][j] = 1;
				adduct[i][j] = 0;
			}
		}
	}
	
	private void push() {
		for(int k = 1; k < NUM_OF_LAYERS; k++) {
			for(int j = 0; j < layers.get(k).size(); j++) {
				double sum = 0;
				for(int i = 0; i < layers.get(k - 1).size(); i++)
					sum += layers.get(k - 1).get(i).out * synapse[layers.get(k - 1).get_global_index(i)][layers.get(k).get_global_index(j)];
				layers.get(k).updateNeuron(j, sum);
			}
		}
	}
	
	public void train(ArrayList<Integer[]> train_input, Integer[] train_output, int num_of_steps, double lambda, double alpha) {
		init();
		for(int step = 0; step < num_of_steps; step++) {
			double t = train_output[step % train_output.length];
			for(int i = 0; i < SIZE; i++) 
				for(int j = 0; j < SIZE; j++)
					adduct[i][j] = 0;
			for(int i = 0; i < layers.get(0).size(); i++) {
				layers.get(0).layer[i].out = train_input.get(step % train_input.size())[i];
				//layers.get(0).layer[i].calc_output(train_input.get(step % train_input.size())[i], alpha);
			}
			push();
			gamma[OUT] = 2 * alpha * (out(OUT) - t) * out(OUT) * (1 - out(OUT));
			for(int k = NUM_OF_LAYERS - 2; k > 0; k--) {
				for(int i = 0; i < layers.get(k).size(); i++) {
					double sum = 0;
					for(int j = 0; j < layers.get(k + 1).size(); j++)
						sum += gamma[layers.get(k + 1).get_global_index(j)];
					gamma[layers.get(k).get_global_index(i)] = alpha * sum * layers.get(k).get(i).out * (1 - layers.get(k).get(i).out);
				}
			}
			for(int k = NUM_OF_LAYERS - 2; k >= 0; k--) 
				for(int i = 0; i < layers.get(k).size(); i++) 
					for(int j = 0; j < layers.get(k + 1).size(); j++)
						adduct[layers.get(k).get_global_index(i)][layers.get(k + 1).get_global_index(j)] += 
								-lambda * gamma[layers.get(k + 1).get_global_index(j)] * layers.get(k).get(i).out;
			for(int i = 0; i < SIZE; i++)
				for(int j = 0; j < SIZE; j++)
					synapse[i][j] += adduct[i][j];
						
		}
	}
	
	public double predict(Integer[] input) {
		for(int i = 0; i < layers.get(0).size(); i++)
			layers.get(0).layer[i].out = input[i];
			//layers.get(0).updateNeuron(i, input[i]);
		push();
		return out(OUT);
	}
}
