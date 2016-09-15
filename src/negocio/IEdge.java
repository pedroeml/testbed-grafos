package negocio;

public interface IEdge<N, E> {
	
	public INode<N, E> getSourceNode();
	public INode<N, E> getDestNode();
	public E getPeso();
}
