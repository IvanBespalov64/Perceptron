package nn;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		double alpha = 1;
		NeuralNetwork nn = new NeuralNetwork();
		nn.addLayer(new NeuralNetworkLayer(2, NeuralNetworkLayer.S, alpha));
		//nn.addLayer(new NeuralNetworkLayer(4, NeuralNetworkLayer.A, alpha));
		//nn.addLayer(new NeuralNetworkLayer(4, NeuralNetworkLayer.A, alpha));
    	//nn.addLayer(new NeuralNetworkLayer(4, NeuralNetworkLayer.A, alpha));
		nn.addLayer(new NeuralNetworkLayer(1, NeuralNetworkLayer.R, alpha));
		ArrayList<Integer[]> input = new ArrayList<Integer[]>();
		input.add(new Integer[] {0, 0});
		input.add(new Integer[] {0, 1});
		input.add(new Integer[] {1, 0});
		input.add(new Integer[] {1, 1});
		Integer[] output = {0, 0, 0, 1};
		nn.train(input, output, 10000000, 0.01, alpha);
		System.out.println(nn.predict(new Integer[] {0, 0}));
		System.out.println(nn.predict(new Integer[] {0, 1}));
		System.out.println(nn.predict(new Integer[] {1, 0}));
		System.out.println(nn.predict(new Integer[] {1, 1}));
		for(int i = 0; i < nn.SIZE; i++)
			System.out.println(nn.out(i));
		for(int i = 0; i < nn.SIZE; i++) {
			for(int j = 0; j < nn.SIZE; j++)
				System.out.print(nn.synapse[i][j] + " ");
			System.out.println();
		}
	}

}