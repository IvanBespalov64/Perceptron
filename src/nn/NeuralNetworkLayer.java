package nn;

public class NeuralNetworkLayer {

	public static final int S = 0;
	public static final int A = 1;
	public static final int R = 2;
	
	private int SIZE = 0;
	public int START_POS = 0;
	
	Neuron[] layer;

	double alpha = 1;
	
	public NeuralNetworkLayer(int arg0, int arg1, double arg2) {
		/** 
		 * arg0 - size of Layer
		 * arg1 - type of Layer
		 * arg2 - alpha
		 */
		
		layer = new Neuron[arg0];
		
		for(int i = 0; i < arg0; i++)
			layer[i] = new Neuron();
		
		alpha = arg2;
	
		SIZE = arg0;
	}
	
	protected void setStartPos(int arg0) {
		/**
		 * arg0 - id of first neuron
		 */
		START_POS = arg0;
	}
	
	protected void updateNeuron(int arg0, double arg1) {
		/**
		 * arg0 - id of neuron 
		 * arg1 - new value
		 */
		
		layer[arg0].calc_output(arg1, alpha);
	}
	
	protected Neuron get(int arg0) {
		/**
		 * arg0 - id of neuron in this layer
		 */
		
		return layer[arg0];
	}
	
	protected Neuron get_global(int arg0) {
		/**
		 * arg0 - id of neuron
		 */
		
		return layer[arg0 - START_POS];
	}
	
	protected int get_global_index(int arg0) {
		/**
		 * arg0 - id of neuron in this layer
		 */
		return arg0 + START_POS;
	}
	
	
	public int size() {
		return SIZE;
	}
}
