package negocio;

import java.util.Iterator;

public interface INode<N, E> {
	
	public N getItem();
	public Iterator<IEdge<N, E>> getEdgesIterator();
	public IEdge<N, E> findEdge(INode<N, E> destino);
	public boolean addEdge(INode<N, E> destino, E peso);
	public boolean removeEdge(INode<N, E> destino);
}
