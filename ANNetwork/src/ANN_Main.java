import java.util.*;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ANN_Main extends JPanel {

	private static int x = 0, y = 0, width = 125, height = 35;
	static double[][] inputs = { {-1, -1, 1, -1}};
	static double[][] outputs = { {1} };
	private static JFrame ANNFrame;
	private static JPanel ANNPanel;
	private static JButton Button1;
	private static JButton Button2;
	private static JButton Button3;
	private static JButton Button4;
	private static List<Double> out;
	private static Double g[];
	public static void main(String[] args) {
		
		int a;
		ANNFrame = new JFrame ("ANN"); //Result Frame
		ANNFrame.setSize(400,400);
		
		ANNPanel = new JPanel ();
		ANNPanel.setLayout(new GridLayout(2,2));
		ANNFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ANNFrame.add(ANNPanel);
		
		Button1 = new JButton(" "); //Buttons init. displaying contents from newstr array
		Button2 = new JButton(" ");
		Button3 = new JButton(" ");
		Button4 = new JButton(" ");
		
		ANNetwork testArch = new ANNetwork(2, 25, 1);
		testArch.general(inputs, outputs, 4);
		
		double[][] duplinputs = { {0, 0}, {1, 0}, {0, 1}, {1, 1} };
		g = new Double[20];
		
		for (double newArr[]: duplinputs) {
		
				out = testArch.predictArch(newArr);
				
				//out.add(newArr[0]);
				//out.add(newArr[1]);
				System.out.println(out.toString());
			
				//if (out.get(0) > out.get(1)) {
					
					Button1.setBackground(Color.WHITE);
					Button1.setOpaque(true);
					Button1.setBorderPainted(false);
					Button2.setBackground(Color.BLACK);
					Button2.setOpaque(true);
					Button2.setBorderPainted(false);
					Button3.setBackground(Color.BLACK);
					Button3.setOpaque(true);
					Button3.setBorderPainted(false);
					Button4.setBackground(Color.WHITE);
					Button4.setOpaque(true);
					Button4.setBorderPainted(false);
					
				//} else if (out.get(0) < out.get(1)) {
					
					//Button1.setBackground(Color.BLACK); 
					//Button2.setBackground(Color.WHITE);
					//Button3.setBackground(Color.BLACK);
					//Button4.setBackground(Color.WHITE);
					
				//}
		}
		 
		
		
			ANNPanel.add(Button1);
			ANNPanel.add(Button2);
			ANNPanel.add(Button3);
			ANNPanel.add(Button4);
			
		ANNFrame.setVisible(true);
		
		
	}
}
