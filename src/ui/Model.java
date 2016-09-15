package ui;

import java.io.File;
import java.util.Observable;

import com.mxgraph.swing.mxGraphComponent;

import negocio.Fachada;

public class Model extends Observable {
	private Fachada fachada;
	private mxGraphComponent grafoComponent;
	
	public Model() {
		this.fachada = new Fachada();
	}
	
	public mxGraphComponent getGrafoComponent() {
		return grafoComponent;
	}
	
	public void importartGrafo(File file) {
		this.fachada.importartGrafo(file);
		this.grafoComponent = this.fachada.component();
		setChanged();
		notifyObservers();
	}
	
	public void dijkstra(String source, String target) {
		this.fachada.dijkstra(source, target);
		this.grafoComponent = this.fachada.component();
		setChanged();
		notifyObservers();
	}
	
	public void mst() {
		this.fachada.mst();
		this.grafoComponent = this.fachada.component();
		setChanged();
		notifyObservers();
	}
	
	public void ordenacao_topologica() {
		this.fachada.ordenacao_topologica();
		this.grafoComponent = this.fachada.component();
		setChanged();
		notifyObservers();
	}
	
	public void proximo() {
		this.grafoComponent = this.fachada.proximo();
		setChanged();
		notifyObservers();
	}
	
	public void ultimo() {
		this.grafoComponent = this.fachada.ultimo();
		setChanged();
		notifyObservers();
	}
}
