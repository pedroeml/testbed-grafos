package negocio;

import java.io.File;

import com.mxgraph.swing.mxGraphComponent;

public class Fachada {
	private Main m;
	
	public Fachada() {
		
	}
	
	public void importartGrafo(File file) {
		this.m = new Main(file);
	}
	
	public void dijkstra(String source, String target) {
		this.m.dijkstra(source, target);
	}
	
	public void mst() {
		this.m.mst();
	}
	
	public void ordenacao_topologica() {
		this.m.ordenacao_topologica();
	}
	
	public mxGraphComponent proximo() {
		return this.m.proximo();
	}
	
	public mxGraphComponent ultimo() {
		return this.m.ultimo();
	}
	
	public mxGraphComponent component() {
		return this.m.component();
	}
}
