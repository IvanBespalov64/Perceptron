package nn;

public class Neuron {
	double out = 0;
	
	public void calc_output(double val, double alpha) {
		out = f(val, alpha);
	}
	
	public static double f(double val, double alpha) {
		return 1 / (1 + Math.exp(-alpha * val));
	}
}
