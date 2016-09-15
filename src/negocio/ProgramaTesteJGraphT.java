package negocio;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class ProgramaTesteJGraphT {
	
	private static void createAndShowGui() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 1024, 633);
		frame.setVisible(true);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
        JGraphT j = new JGraphT();
    	
        j.addVertex("camisa");
        j.addVertex("cueca");
        j.addVertex("sapato");
        
        j.addEdge("camisa", "cueca", 2);
        j.addEdge("sapato", "camisa", 0);
        
        Component c = j.component();
        c.setBounds(10, 10, 600, 500);
        contentPane.add(c);
        
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }

}
