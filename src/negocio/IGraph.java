package negocio;

public interface IGraph<N, E> {
	public boolean addNode(N item);
	public boolean addEdge(N origem, N destino, E peso);
}
