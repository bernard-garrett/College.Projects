import java.util.*;  

public class ANNetwork {

	/*
	 * Initializing my Arch weights for our hidden + inputs,
	 * weights for my hidden + outputs, 
	 * learning rate, and my hidden and output-bias Archs
	 * && Remember Arch -> Neuron
	 */
	ANN_Arch hiddB_Arch, outB_Arch;
	ANN_Arch inputW, outputW;
	private static double learnRate; 
	
	/*
	 * We used a constructor as we did 
	 * in ANN_Arch to actually declare our base 
	 * archs && bias Archs
	 * to be modified or displayed 
	 */
	public ANNetwork(int numIn, int numHidd, int numOut) {
		
		learnRate = 0.01;
		hiddB_Arch = new ANN_Arch(numHidd, 1);
		outB_Arch = new ANN_Arch(numOut, 1);
		
		inputW = new ANN_Arch(numHidd, numIn);
		outputW = new ANN_Arch(numOut, numHidd);
		
	}
	
	//The predict method is used for the Forward Propagation on ANN
	public List<Double> predictArch(double[] inputs) {
		
		//Initializing the input and hidden arch
		ANN_Arch inArch = ANN_Arch.arrToArch(inputs);
		ANN_Arch hiddArch = ANN_Arch.multArch(inputW, inArch);
		
		//Adds the hidden-bias arch and activates the hidden arch 
		hiddArch.addArch(hiddB_Arch);
		hiddArch.activteArch();
		
		//Initializing the output arch
		ANN_Arch outArch = ANN_Arch.multArch(outputW, hiddArch);
		
		//Adds the output-bias arch and activates the bias arch 
		outArch.addArch(outB_Arch);
		outArch.activteArch();
		
		return outArch.archToArr();
		
	}
	
	//In the Train methods 
	public void evaluate(double[] inputs, double[] outputs) {
		
		ANN_Arch inArch = ANN_Arch.arrToArch(inputs);
		ANN_Arch goalArch = ANN_Arch.arrToArch(outputs);
	
		ANN_Arch hiddArch = ANN_Arch.multArch(inputW, inArch);
		hiddArch.addArch(hiddB_Arch);
		hiddArch.activteArch();
		
		ANN_Arch outArch = ANN_Arch.multArch(outputW, hiddArch);
		outArch.addArch(outB_Arch);
		outArch.activteArch();
		
		ANN_Arch errArch = ANN_Arch.subArch(goalArch, outArch);
		ANN_Arch gradArch = outArch.calcGradArch();
		gradArch.multArch(errArch);
		gradArch.multArch(learnRate);
		
		ANN_Arch r_hiddArch = ANN_Arch.revArch(hiddArch);
		ANN_Arch d_outWArch = ANN_Arch.multArch(gradArch, r_hiddArch);
		ANN_Arch r_outWArch = ANN_Arch.revArch(outputW);
		ANN_Arch h_errArch = ANN_Arch.multArch(r_outWArch, errArch);
		ANN_Arch h_gradArch = hiddArch.calcGradArch();
		h_gradArch.multArch(h_errArch);
		h_gradArch.multArch(learnRate);
		
		ANN_Arch r_inArch = ANN_Arch.revArch(inArch);
		ANN_Arch d_inWArch = ANN_Arch.multArch(h_gradArch, r_inArch);
		
		inputW.addArch(d_inWArch);
		
		hiddB_Arch.addArch(h_gradArch);
		
	}
	
	public void general(double[][] inputs, double[][] outputs, int epochs) {
		
		int a;
		for (a = 0; a < epochs; a++) {
			
				int tmpNum = (int)(Math.random() * inputs.length);
				this.evaluate(inputs[tmpNum], outputs[tmpNum]);
			
		}
	}
	
	static double[][] outputs = { {0}, {1}, {1}, {0}};
}
