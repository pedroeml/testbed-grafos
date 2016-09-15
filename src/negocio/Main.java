package negocio;

import java.io.File;
import java.util.Iterator;


import com.mxgraph.swing.mxGraphComponent;

public class Main {
	private ExecutionSteps<String, Double> steps;
	private Graph<String, Double> graph;
	private Iterator<Graph<String, Double>> it;
	private JGraphT displaying_JGraphT;
	
	
	public Main(File file) {
		this.steps = new ExecutionSteps<>();
		this.loadGraphFile(file);
		this.makeJGraphT(graph);
	}
	
	public void loadGraphFile(File file) {
		this.graph = (new Assembler_fromGraphFile(file, this.steps)).getGraph();
	}
	
	public void dijkstra(String source, String target) {
		this.makeJGraphT(this.graph.dijkstra(source, target));
		this.it = this.steps.iterate();
	}
	
	public void mst() {
		this.graph.mst();
		this.it = this.steps.iterate();
		this.proximo();
	}
	
	public void ordenacao_topologica() {
		this.graph.ordenacao_topologica();
		this.it = this.steps.iterate();
		this.proximo();
	}
	
	public mxGraphComponent proximo() {
		if (it.hasNext()) {
			this.makeJGraphT(this.it.next());
			
		}
		return this.component();
	}
	
	public mxGraphComponent ultimo() {
		this.makeJGraphT(this.steps.getLast());
		return this.component();
	}
	
	private void makeJGraphT(Graph<String, Double> graph) {
		JGraphT jGraphT = new JGraphT();
		Iterator<INode<String, Double>> it_n = graph.getNodesIterator();
		while(it_n.hasNext()) {
			INode<String, Double> n = it_n.next();
			
			Iterator<IEdge<String, Double>> it_e = n.getEdgesIterator();
			while (it_e.hasNext()) {
				IEdge<String, Double> e = it_e.next();
				
				jGraphT.addVertex(e.getSourceNode().getItem());
				jGraphT.addVertex(e.getDestNode().getItem());
				jGraphT.addEdge(e.getSourceNode().getItem(), e.getDestNode().getItem(), e.getPeso().doubleValue());
			}			
		}
		
		this.displaying_JGraphT = jGraphT;
	}
	
	public mxGraphComponent component() {
		return this.displaying_JGraphT.component();
	}
}
