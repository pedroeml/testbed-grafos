package negocio;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;

public class JGraphT {
	private ListenableDirectedWeightedGraph<String, MyEdge> listenableGraph;
	
	public static class MyEdge extends DefaultWeightedEdge {
		public MyEdge() {
			super();
		}
		
		@Override
		public String toString() {
			return String.valueOf(getWeight());
		}
	}
	
	public JGraphT() {
		this.listenableGraph = new ListenableDirectedWeightedGraph<String, MyEdge>(MyEdge.class);
	}
	
	public boolean addVertex(String vertex) {
		return this.listenableGraph.addVertex(vertex);
	}
	
	public boolean addEdge(String source, String target, double weight) {
		MyEdge e = this.listenableGraph.addEdge(source, target);
		if (e != null) {
			this.listenableGraph.setEdgeWeight(e, weight);
			return true;
		}
		return false;
	}
	
	public ListenableGraph<String, MyEdge> getListenableGraph() {
		return listenableGraph;
	}
	
	public JGraphXAdapter<String, MyEdge> graphAdapter() {
		return new JGraphXAdapter<String, MyEdge>(this.getListenableGraph());
	}
	
	public mxGraphComponent component() {
		JGraphXAdapter<String, MyEdge> graphAdapter = this.graphAdapter();
		mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
		layout.execute(graphAdapter.getDefaultParent());
		return new mxGraphComponent(graphAdapter);
	}
}
