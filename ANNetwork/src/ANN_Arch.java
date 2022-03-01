import java.util.*;  

public class ANN_Arch {
	
	private double[][] contents; 
	
	private static int Rows;
	private static int Columns;
	
	/*
	 * ANN Architecture(Arch -> Neron) is my framework/Backgorund architecture,
	 * so I initialized a constructor, 
	 * which we will use as a data type
	 * to set up many other 2D arrays and methods
	*/
	public ANN_Arch(int Rows, int Columns) { 
		
		//Initializing the 2D content array 
		contents = new double[Rows][Columns];  
		this.Rows = Rows;
		this.Columns = Columns;
		
		//Traverses our 2D array assigning a random integer between -1 and 1 
		for (int a = 0; a < Rows; a++) {
			
			for (int b = 0; b < Columns; b++) {
				
				contents[a][b] = (Math.random() * 2) - 1;
				
			}
		}
	}
	
	/*
	 * OutOfShape method tells us 
	 * if our comparison Arch's Rows and Columns
	 * match our original Arch's Rows and Columns
	*/
	public boolean archOutOfShape(ANN_Arch comArch) {
		
		boolean noMatch;
		
		if (Rows != comArch.Rows || Columns != comArch.Columns) {
			
			noMatch = true;
			return noMatch;
			
		} else {
		
			noMatch = false;
			return noMatch;
			
		}
	}

	
	/*
	 * Add arch methods to add the contents of two Archs and 
	 * add a scalar value to the contents as well
	*/
	public void addArch(ANN_Arch archOne) {
		
		//We must make sure the 
		if (archOne.archOutOfShape(archOne) == true) {
			
			return ;
	
		}
		
		for (int a = 0; a < Rows; a++) {
			
			for (int b = 0; b < Columns; b++) {
				
				this.contents[a][b] += archOne.contents[a][b];
				
			}
		}
	}
	
	//Scales our Arch for the addition method
	public void addArch(double scaleNum){
	  	for (int a = 0; a < Rows; a++) {
	 
	 		for (int b = 0; b < Columns; b++) {
	 
	  			this.contents[a][b] += scaleNum;
	  			
	 		}
	 	}
	}
	
	//Subtract arch method to subtract the contents of two Archs
	public static ANN_Arch subArch(ANN_Arch archOne, ANN_Arch archTwo) {
		
		/*
		 * Assigning a temporary to allows us to store 
		 * the difference in the two arch's value;
		*/
		ANN_Arch newArch = new ANN_Arch(archOne.Rows, archOne.Columns);
		
		for (int a = 0; a < archOne.Rows; a++) {
			
			for (int b = 0; b < archOne.Columns; b++) {
				
				newArch.contents[a][b] = archOne.contents[a][b] - archTwo.contents[a][b];
				
			}
		}
		
		return newArch;
	}
	
	//Reverse Arch method swaps the Row and Column content of our Arch
	public static ANN_Arch revArch(ANN_Arch archOne) {
		
		//Initializing temporary Arch
		ANN_Arch newArch = new ANN_Arch(archOne.Rows, archOne.Columns);
		
		for (int a = 0; a < archOne.Rows; a++) {
			
			for (int b = 0; b < archOne.Columns; b++) {
				
				newArch.contents[b][a] = archOne.contents[a][b];
				
			}
		}
		
		return newArch;
	}
	
	/*
	 * Multiply Arch methods to do dot product of two Archs and 
	 * multiply their contents by by a scalar value w/ overloading parameters
	*/
	public static ANN_Arch multArch(ANN_Arch archOne, ANN_Arch archTwo) {
		
		//Initializing temporary Arch
		ANN_Arch newArch = new ANN_Arch(archOne.Rows, archOne.Columns);
		double productSum = 0; int a, b, c;//Sum of our product, and the Instance Variables
		
		for (a = 0; a < newArch.Rows; a++) {
			
			for (b = 0; b < newArch.Columns; b++) {
				
				for (c = 0; c < archOne.Columns; c++) {
					
					productSum += archTwo.contents[c][b] * archOne.contents[a][c]; 
				}
				
				newArch.contents[a][b] = productSum;
			}
		}
			
		return newArch;
		
	}
	
	//Multiplies the contents of our Archs similarly to the add method
	public void multArch(ANN_Arch archOne) {
		
		int a, b; //Instance Variables
		
		for (a = 0; a < archOne.Rows; a++) {
			
			for (b = 0; b < archOne.Columns; b++) {
				
				this.contents[a][b] *= archOne.contents[a][b];
			}
		}
	}
	
	//Multiplies our arch by a scalar value of type double
	public void multArch(double scaleNum) {
		
		int a, b;
		
		for (a = 0; a < Rows; a++) {
			
			for (b = 0; b < Columns; b++) {
				
				this.contents[a][b] *= scaleNum;
			}
		}
		
	}
	
	/*
	 * Activation method for the activation for ANN, 
	 * which implemented using the sigmoid function
	*/
	public void activteArch() {
		
		int a, b, c = 1; 
		
		for (a = 0; a < Rows; a++) {
			
			for (b = 0; b < Columns; b++) {
				
				this.contents[a][b] = c / c + Math.exp(- this.contents[a][b]);
			}
		}
	}
	
	/*
	 * The calcGradArch method is the derivative of the sigmoid function,
	 * which will calculate our gradient used for the backpropagation
	 */
	public ANN_Arch calcGradArch() {
		
		ANN_Arch newArch = new ANN_Arch(Rows, Columns);
		int a, b, c = 1; 
		
		for (a = 0; a < Rows; a++) {
			
			for (b = 0; b < Columns; b++) {
				
				newArch.contents[a][b] = this.contents[a][b] * (c - this.contents[a][b]);
				
			}
		}
		
		return newArch;
	}
	
	/*
	 * Converts from Arch to Array
	 */
	public List<Double> archToArr() {
		
		int a, b;
		List<Double> newArch = new ArrayList<Double>();
		
		for (a = 0; a < Rows; a++) {
			
			for (b = 0; b < Columns; b++) {
				
				newArch.add(contents[a][b]);
			}
		}
		
		return newArch;
	}
	
	/*
	 * Converts from Array to Arch 
	 */
	public static ANN_Arch arrToArch(double[] archArr) {
		
		int a = 1, b, c = 0;
		ANN_Arch newArch = new ANN_Arch(archArr.length, a);
		
			for (b = 0; b < archArr.length; b++) {
				
				newArch.contents[b][c] = archArr[b];
			}
			
		return newArch; 
	}
	
}
