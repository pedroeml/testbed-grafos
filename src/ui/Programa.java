package ui;

import javax.swing.SwingUtilities;

public class Programa {
	
	private static void criarGUI() {
		Model m = new Model();
		View v = new View();
		m.addObserver(v);
		new Controller(m, v);
		v.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					criarGUI();					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
