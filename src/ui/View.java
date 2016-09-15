package ui;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mxgraph.swing.mxGraphComponent;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollBar;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class View extends JFrame implements Observer {

	private JPanel contentPane;
	private JButton btnImportarGrafo;
	private JButton btnOrdenacaoTopologica;
	private JButton btnDijkstra;
	private JButton btnMST;
	private JButton btnProximo;
	private JButton btnUltimo;
	private JTextField textField_NodoInicial;
	private JTextField textField_NodoFinal;
	private javax.swing.filechooser.FileFilter filter;
	private JLabel lblNodoInicial;
	private JLabel lblNodoFinal;
	private JButton btnExecutar;
	private mxGraphComponent comp;
	

	/**
	 * Create the frame.
	 */
	public View() {
		filter = new javax.swing.filechooser.FileFilter() {
			
			@Override
			public boolean accept(File arg0) {
				if (arg0.getName().endsWith(".csv"))
					return true;
				return false;
			}

			@Override
			public String getDescription() {
				return "Arquivo texto CSV formatado com separador ';'";
			}
			
		};
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 672, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnImportarGrafo = new JButton("Importar Grafo");
		btnImportarGrafo.setBackground(new Color(204, 204, 204));
		btnImportarGrafo.setBounds(10, 11, 124, 23);
		contentPane.add(btnImportarGrafo);
		
		btnOrdenacaoTopologica = new JButton("Orden. Topológica");
		btnOrdenacaoTopologica.setBackground(new Color(204, 204, 204));
		btnOrdenacaoTopologica.setBounds(312, 11, 160, 23);
		contentPane.add(btnOrdenacaoTopologica);
		
		btnDijkstra = new JButton("Dijkstra");
		btnDijkstra.setBackground(new Color(204, 204, 204));
		btnDijkstra.setBounds(482, 11, 86, 23);
		contentPane.add(btnDijkstra);
		
		btnMST = new JButton("MST");
		btnMST.setBackground(new Color(204, 204, 204));
		btnMST.setBounds(578, 11, 68, 23);
		contentPane.add(btnMST);
		
		btnProximo = new JButton("Próximo");
		btnProximo.setBackground(new Color(204, 204, 204));
		btnProximo.setBounds(10, 405, 89, 23);
		contentPane.add(btnProximo);
		
		btnUltimo = new JButton("Último");
		btnUltimo.setBackground(new Color(204, 204, 204));
		btnUltimo.setBounds(109, 405, 89, 23);
		contentPane.add(btnUltimo);
		
		lblNodoInicial = new JLabel("Nodo inicial:");
		lblNodoInicial.setBounds(464, 73, 78, 14);
		contentPane.add(lblNodoInicial);
		
		lblNodoFinal = new JLabel("Nodo final:");
		lblNodoFinal.setBounds(464, 101, 78, 14);
		contentPane.add(lblNodoFinal);
		
		textField_NodoInicial = new JTextField();
		textField_NodoInicial.setBounds(560, 70, 86, 20);
		contentPane.add(textField_NodoInicial);
		textField_NodoInicial.setColumns(10);
		
		textField_NodoFinal = new JTextField();
		textField_NodoFinal.setBounds(560, 98, 86, 20);
		contentPane.add(textField_NodoFinal);
		textField_NodoFinal.setColumns(10);
		
		btnExecutar = new JButton("Executar");
		btnExecutar.setBackground(new Color(51, 102, 255));
		btnExecutar.setBounds(496, 123, 89, 23);
		contentPane.add(btnExecutar);
		
		this.exibirBotoes_Algoritimos(false);
		this.exibirBotoes_ProximoUltimo(false);
		this.exibirComponentes_Dijkstra(false);
	}
	
	public void associaController_btnImportarGrafo(ActionListener c) {
		btnImportarGrafo.addActionListener(c);
	}
	
	public void associaController_btnOrdenacaoTopologica(ActionListener c) {
		btnOrdenacaoTopologica.addActionListener(c);
	}
	
	public void associaController_btnDijkstra(ActionListener c) {
		btnDijkstra.addActionListener(c);
	}
	
	public void associaController_btnMST(ActionListener c) {
		btnMST.addActionListener(c);
	}
	
	public void associaController_btnProximo(ActionListener c) {
		btnProximo.addActionListener(c);
	}
	
	public void associaController_btnFinal(ActionListener c) {
		btnUltimo.addActionListener(c);
	}
	
	public void associaController_btnExecutar(ActionListener c) {
		btnExecutar.addActionListener(c);
	}
	
	public File fileChooserDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(filter);
		fileChooser.setDialogTitle("Abra um arquivo texto em forma de grafo");
		fileChooser.showOpenDialog(this);
		return fileChooser.getSelectedFile();
	}
	
	public void exibirBotoes_ProximoUltimo(boolean b) {
		this.btnProximo.setVisible(b);
		this.btnUltimo.setVisible(b);
	}
	
	public void exibirBotoes_Algoritimos(boolean b) {
		this.btnDijkstra.setVisible(b);
		this.btnMST.setVisible(b);
		this.btnOrdenacaoTopologica.setVisible(b);
	}
	
	public void exibirComponentes_Dijkstra(boolean b) {
		this.lblNodoFinal.setVisible(b);
		this.lblNodoInicial.setVisible(b);
		this.textField_NodoFinal.setVisible(b);
		this.textField_NodoInicial.setVisible(b);
		this.btnExecutar.setVisible(b);
	}
	
	public String getText_NodeInicial() {
		return this.textField_NodoInicial.getText();
	}
	
	public String getText_NodeFinal() {
		return this.textField_NodoFinal.getText();
	}
	

	public void updateComponent(mxGraphComponent comp) {
		if (this.comp != null)
			contentPane.remove(this.comp);
		comp.setBounds(20, 65, 400, 300);
		comp.setVisible(true);
		this.comp = comp;
		contentPane.add(this.comp);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		Model model = (Model) arg0;
		this.updateComponent(model.getGrafoComponent());
		contentPane.updateUI();
	}
}
