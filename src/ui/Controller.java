package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Controller {
	private Model model;
	private View view;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		view.associaController_btnDijkstra(new Action_Dijkstra());
		view.associaController_btnFinal(new Action_Ultimo());
		view.associaController_btnImportarGrafo(new Action_ImportarGrafo());
		view.associaController_btnMST(new Action_MST());
		view.associaController_btnOrdenacaoTopologica(new Action_OrdenacaoTopologica());
		view.associaController_btnProximo(new Action_Proximo());
		view.associaController_btnExecutar(new Action_Executar());
		
	}
	
	private class Action_ImportarGrafo implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			File file = view.fileChooserDialog();
			
			if (file == null)
				return;
			
			model.importartGrafo(file);
			view.exibirBotoes_Algoritimos(true);
			view.exibirComponentes_Dijkstra(false);
			view.exibirBotoes_ProximoUltimo(false);
		}
		
	}
	
	private class Action_OrdenacaoTopologica implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.ordenacao_topologica();
			view.exibirBotoes_ProximoUltimo(false);
			view.exibirComponentes_Dijkstra(false);
			view.exibirBotoes_Algoritimos(false);
		}
		
	}
	
	private class Action_Dijkstra implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//model.dijkstra(source, target);
			
			
			view.exibirBotoes_ProximoUltimo(false);
			view.exibirComponentes_Dijkstra(true);
			view.exibirBotoes_Algoritimos(false);
		}
		
	}
	
	private class Action_MST implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.mst();
			view.exibirBotoes_ProximoUltimo(true);
			view.exibirComponentes_Dijkstra(false);
			view.exibirBotoes_Algoritimos(false);
		}
		
	}
	
	private class Action_Proximo implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.proximo();
		}
		
	}
	
	private class Action_Ultimo implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.ultimo();
		}
		
	}
	
	private class Action_Executar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.dijkstra(view.getText_NodeInicial(), view.getText_NodeFinal());
			
		}
		
	}
	
	
}
