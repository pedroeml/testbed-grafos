package negocio;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ExecutionSteps<N, E> {
	private List<Graph<N, E>> list_of_steps;
	
	public ExecutionSteps() {
		this.list_of_steps = new LinkedList<>();
	}
	
	public void clearList() {
		this.list_of_steps.clear();
	}
	
	public boolean addStep(Graph<N, E> graph) {
		if (graph == null)
			return false;
		try {
			this.list_of_steps.add((Graph<N, E>) graph.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Iterator<Graph<N, E>> iterate() {
		return this.list_of_steps.iterator();
	}
	
	public Graph<N, E> getLast() {
		return ((LinkedList<Graph<N, E>>) this.list_of_steps).getLast();
	}
}
