package negocio;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.lang.Double;


// Grafo direcional rotulado de mapeamento de adjacências rotulado

public class Graph<N, E> implements IGraph<N, E> {
	private Map<N, INode<N, E>> mapNodes; // Key: N do nodo a ser procurado, Value: Node que possui N
	private ExecutionSteps<N, E> steps;
	
	private final class Edge implements IEdge<N, E> {
		private INode<N, E> source, dest;
		private E peso;
		
		public Edge(INode<N, E> source, INode<N, E> dest, E peso) {
			this.source = source;
			this.dest = dest;
			this.peso = peso;
		}
		
		@Override
		public INode<N, E> getSourceNode() {
			return source;
		}
		
		@Override
		public INode<N, E> getDestNode() {
			return this.dest;
		}
		
		@Override
		public E getPeso(){
			return this.peso;
		}
		
		@Override
		public boolean equals(Object obj) {
			@SuppressWarnings("unchecked")
			Edge e = (Edge) obj;
			return this.getDestNode().equals(e.getDestNode()) && this.peso.equals(e.getPeso());
		}
	}

	private final class Node implements INode<N, E> {
		private N item;
		private Map<N, IEdge<N, E>> mapEdges; // Key: N que pertence ao Node-destino, Value: Edge que possui o Node-destino que contém N
		private boolean tagged;
		
		public Node(N item) {
			this(item, new HashMap<>());
		}
		
		public Node(N item, Map<N, IEdge<N, E>> mapEdges) {
			this.item = item;
			this.mapEdges = mapEdges;
			this.tagged = false;
		}
		
		@Override
		public N getItem() {
			return this.item;
		}
		
		public boolean isTagged() {
			return this.tagged;
		}
		
		public void setTagged(boolean tag) {
			this.tagged = tag;
		}
		
		public Collection<IEdge<N, E>> getEdgesCollection() {
			return this.mapEdges.values();
		}
		
		@Override
		public Iterator<IEdge<N, E>> getEdgesIterator() {
			return this.getEdgesCollection().iterator();
		}
		
		/**
		 * @param destino	nodo cujo queremos encontrar na coleção de adjacências que este (this) nodo já aponta
		 * @return Edge		se a aresta já estiver na coleção de adjacências
		 * @return null  	se a aresta não estiver presente na coleção de adjacências
		 */
		@Override
		public IEdge<N, E> findEdge(INode<N, E> destino) {
			return mapEdges.get(destino.getItem());
		}
		
		/**
		 * @param destino	nodo cujo queremos que este (this) nodo tenha uma aresta os ligando
		 * @return true		se a aresta for adicionada
		 * @return false  	se a aresta já estiver presente na coleção
		 */
		@Override
		public boolean addEdge(INode<N, E> destino, E peso) {
			if (findEdge(destino) != null)
				return false;
			Edge edge = (Edge) new Edge(this, destino, peso);
			this.mapEdges.put(destino.getItem(), edge);
			return true;
		}
		
		/**
		 * @param destino	nodo cujo queremos que este (this) nodo tenha a aresta removida
		 * @return true		se a aresta for removida com sucesso
		 * @return false  	se a aresta não for possível realizar a operação
		 * @throws NoSuchElementException	se a aresta não for encontrada na coleção
		 */
		@Override
		public boolean removeEdge(INode<N, E> destino) {
			IEdge<N, E> e = this.findEdge(destino);
			if (e == null)
				throw new NoSuchElementException(String.format("O arco requisitado não existe. Nodo destino: %s", destino.getItem()));
			return this.mapEdges.remove(e.getDestNode().getItem(), e);
		}
		
		@Override
		public boolean equals(Object obj) {
			@SuppressWarnings("unchecked")
			Node n = (Node) obj;	
			return this.getItem().equals(n.getItem());
		}
		
	}
	
	public Graph(ExecutionSteps<N, E> steps) {
		this(new HashMap<>(), steps);
	}
	
	public Graph(Map<N, INode<N, E>> mapNodes, ExecutionSteps<N, E> steps) {
		this.mapNodes = mapNodes;
		this.steps = steps;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Map<N, INode<N, E>> mapNodes = new HashMap<>();	// cria um novo Map de Nodes
		Collection<INode<N, E>> colecao_nodos = this.mapNodes.values();
		Collection<IEdge<N, E>> colecao_edges = new LinkedList<>();
		
		for (INode<N, E> n : colecao_nodos)	{ // para cada Node da coleção	
			mapNodes.put(n.getItem(), new Node(n.getItem()));	// no novo Map de Nodes adiciona o mapeamento de N para Node clonado
			colecao_edges.addAll(((Node) n).getEdgesCollection());
		}
		
		Graph<N, E> graph = new Graph<>(mapNodes, this.steps);
		
		for (IEdge<N, E> e : colecao_edges)
			graph.addEdge(e.getSourceNode().getItem(), e.getDestNode().getItem(), e.getPeso());
		
		return graph;
	}
	
	/**
	 * @param item 	item genérico a ser procurado
	 * @return Node	se o item procurado na coleção estiver nela
	 * @return null se o item procurado não estiver na coleção
	 */
	public INode<N, E> findNode(N item) {
		return this.mapNodes.get(item);
	}
	
	/**
	 * @param item	 item genérico a ser adicionado
	 * @return true  se o item a ser adicionado na coleção for adicionado com sucesso
	 * @return false se o item a ser adicionado já estiver na coleção
	 */
	@Override
	public boolean addNode(N item) {
		if (this.findNode(item) == null) { // se o item a ser adicionado não estiver na coleção 
			this.mapNodes.put(item, new Node(item)); // o adiciono
			return true;
		}
		return false;
	}
	
	/**
	 * @param origem 	item genérico a ser procurado na coleção
	 * @param destino	item genérico a ser procurado na coleção e referenciado pela aresta do origem
	 * @param peso		peso a ser adicionado na aresta
	 * @return true 	se a ligação da aresta de nodo a nodo for realizada com sucesso
	 * @return false 	se não for possível realizar a operação
	 * @throws NoSuchElementException	se um dos elementos a ser procurados na coleção de nodos não existir
	 */
	@Override
	public boolean addEdge(N origem, N destino, E peso) {
		Node nOrigem = (Node) this.mapNodes.get(origem);
		Node nDestino = (Node) this.mapNodes.get(destino);
		
		if (nOrigem == null || nDestino == null) // se ocorrer de um deles não for encontrado na coleção
			throw new NoSuchElementException(String.format("Um ou ambos os nodos não existe(m). Origem: %s, Destino: %s", origem, destino));
			//return false; // então retorna false
		
		nOrigem.addEdge(nDestino, peso); // caso o contrário, adicionamos uma aresta do nodo de origem para o nodo destino
		
		return true;
	}
	
	protected Collection<INode<N, E>> getNodesCollection() {
		return this.mapNodes.values();
	}
	
	public Iterator<INode<N, E>> getNodesIterator() {
		return this.getNodesCollection().iterator();
	}
	
	/*
	 * 1º verificar se o nodo pesquisado existe no map de nodos
	 * 2º se existir, procurar em todos os nodos se possui uma aresta tendo o nodo procurado como destino
	 * 3º se existir, remove todas as ocorrencias desta aresta
	 * 4º por fim, remove o nodo do map de nodos
	 */
	/**
	 * @param item	 item genérico a ser removido
	 * @return true  se o item a ser removido na coleção for removido com sucesso
	 * @return false se o item a operação não puder ser realizada
	 * @throws NoSuchElementException	se o nodo a ser removido não existir na coleção
	 */
	public boolean removeNode(N item) {
		Node n0 = (Graph<N, E>.Node) this.findNode(item), n1;
		if (n0 == null)
			throw new NoSuchElementException(String.format("O nodo que deseja ser removido não existe. Nodo: %s", item));
		Iterator<INode<N, E>> it = this.getNodesIterator();
		while (it.hasNext()) {
			n1 = (Node) it.next();
			n1.removeEdge(n0);
		}
		return this.mapNodes.remove(item, n0);
	}
	
	public int size() {
		return this.mapNodes.size();
	}
	
	/*
	 * Desmarca todos os nodos para as operações de caminhamento.
	 */
	private void untagAllNodes() {
		Iterator<INode<N, E>> it = this.getNodesIterator();
		while (it.hasNext()) {
			Node n = (Graph<N, E>.Node) it.next();
			n.setTagged(false);
		}
	}
	
	/* NOTA: se quiser que retorne os nodos, ao invés de uma String com o nome de cada nodo, basta 
	 * só adicionar os nodos à uma pilha (como fiz na classe Dijkstra). No entanto, usando pilha irá
	 * perder os nodos que -- não estiverem marcados -- estiverem na mesma largura. Contudo, Uma 
	 * solução para isso seria usar árvores genéricas. Optei por usar String apenas para visualizar o
	 * resultado, já que não vou (no momento) aplicar nenhuma operação sobre o caminho que seria retornado.
	 */
	public List<Node> caminhamentoLargura(N item) {
		this.untagAllNodes();
		Node n = (Node) this.findNode(item);
		if (n == null)
			throw new NoSuchElementException(String.format("O nodo que deseja inciar o caminhamento em largura não existe. Nodo: %s", item));
		return this.caminhamentoLargura(n);
	}
	
	/* ALGORITMO:
	 * 1. Visite um nodo arbitrário
	 * 2. Marque o nodo e coloque-o em uma fila Q
	 * 3. Enquanto a fila Q não estiver vazia
	 *		Retire um elemento N de Q
	 *		Para cada nodo M (não marcado) adjacente a N
	 *			Visite M
	 *			Coloque M na fila Q
	 *			Marque M 
	 */
	private List<Node> caminhamentoLargura(Node arbitrario) {
		StringBuilder str = new StringBuilder();
		List<Node> caminho = new LinkedList<>();
		arbitrario.setTagged(true);
		Queue<Node> fila = new ArrayDeque<>();
		fila.add(arbitrario);
		Iterator<IEdge<N, E>> it;
		while(!fila.isEmpty()) {
			Node n0 = fila.poll();
			caminho.add(n0);
			str.append("\n");
			str.append(n0.getItem());
			str.append(" -> ");
			it = n0.getEdgesIterator();
			while(it.hasNext()) {
				IEdge<N, E> e = it.next();
				Node n1 = (Node) e.getDestNode();
				if (!n1.isTagged()) {
					fila.add(n1);
					caminho.add(n1);
					str.append(n1.getItem());
					str.append(", ");
					n1.setTagged(true);
				}
			}
		}
		str.append("\n");
		System.out.println(str.toString());
		return caminho;
	}
	
	
	/* NOTA: se quiser que retorne os nodos, ao invés de uma String com o nome de cada nodo, basta 
	 * só adicionar os nodos à uma pilha (como fiz na classe Dijkstra). No entanto, usando pilha irá
	 * perder os nodos que -- não estiverem marcados -- estiverem na mesma largura. Contudo, Uma 
	 * solução para isso seria usar árvores genéricas. Optei por usar String apenas para visualizar o
	 * resultado, já que não vou (no momento) aplicar nenhuma operação sobre o caminho que seria retornado.
	 */
	public List<Node> caminhamentoProfundidade(N item) {
		this.untagAllNodes();
		Node n = (Graph<N, E>.Node) this.findNode(item);
		if (n == null)
			throw new NoSuchElementException(String.format("O nodo que deseja inciar o caminhamento em profundidade não existe. Nodo: %s", item));
		StringBuilder str = new StringBuilder();
		List<Node> caminho = new LinkedList<>();
		this.caminhamentoProfundidade(n, str, caminho);
		str.append("\n");
		System.out.println(str.toString());
		return caminho;
	}
	
	/* ALGORITMO:
	 * 1. Procedure CaminhaEmProfundidade(Nodo N)
	 * 2. Visite o nodo arbitrário (parâmetro) N
	 * 3. Marque o nodo
	 * 4. Para cada nodo M (não marcado) adjacente a N
	 *		exec. recursiv. CaminhaEmProfundidade(M)
	 */
	private void caminhamentoProfundidade(Node arbitrario, StringBuilder str, List<Node> caminho) {
		arbitrario.setTagged(true);
		caminho.add(arbitrario);
		str.append(arbitrario.getItem());
		str.append(" -> ");
		Iterator<IEdge<N, E>> it = arbitrario.getEdgesIterator();
		
		while (it.hasNext()) {
			IEdge<N, E> e = it.next();
			Node n = (Graph<N, E>.Node) e.getDestNode();
			if (!n.isTagged()) {
				this.caminhamentoProfundidade(n, str, caminho);
				str.append("\\");	// Se aparecer alguma letra ao lado na impressão, indica que há algum nodo não marcado que estava sendo apontado pelo mesmo 'n' barra [\] vezes antes do nodo mais profundo impresso
			}
		}
	}
	
	public Graph<N, E> dijkstra(N source, N target) {
		@SuppressWarnings("unchecked")
		Dijkstra dijkstra = new Dijkstra((Graph<N, Double>) this);
		steps.clearList();
		return (Graph<N, E>) dijkstra.dijkstra(source, target);
	}
	
	
	@SuppressWarnings("unchecked")
	public Graph<N, E> ordenacao_topologica() {
		this.steps.clearList();
		Graph<N, E> graph = null;
		try {
			graph = (Graph<N, E>) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			System.out.println("Objeto Graph não clonável!");
			return null;
		}
		
		LinkedList<Node> l = new LinkedList<>();
		Stack<Node> s = nodos_sem_aresta_de_entrada(graph);	// mais fácil de adicionar e remover com pilha -- poderia ser fila também...
		
		while (!s.isEmpty()) {
			Node n = s.pop();
			l.add(n);
			
			Iterator<IEdge<N, E>> it = n.getEdgesIterator();
			
			while (it.hasNext()) {
				IEdge<N, E> edge = it.next();
				Node m = (Node) edge.getDestNode();
				it.remove();
				
				if (!this.nodo_tem_aresta_de_entrada(m))
					s.push(m);
			}
			
			this.steps.addStep(graph);
		}
		
		return graph;
	}
	
	private Graph<N, E> makeGraphfromList(List<Node> l) {
		Graph<N, E> graph = new Graph<>(this.steps);
		
		for (Node n : l) {
			graph.addNode(n.getItem());
			
			for (IEdge<N, E> e : ((Node) this.findNode(n.getItem())).getEdgesCollection()) {
				boolean contains = false;
				
				for (Node nod : l)
					if (nod.getItem().equals(e.getDestNode().getItem()))
						contains =  true;
				
				if (contains) {
					System.out.println("CONTEM");
					graph.addNode(e.getDestNode().getItem());
					graph.addEdge(e.getSourceNode().getItem(), e.getDestNode().getItem(), e.getPeso());
				}
			}
		}
		
		return graph;
	}
	
	// Retorna uma pilha com todos os nodos sem aresta de entrada do grafo enviado por parâmetro.
	private Stack<Node> nodos_sem_aresta_de_entrada(Graph<N, E> grafo) {
		Stack<Node> s = new Stack<>();
		
		Collection<INode<N, E>> colecao_nodos = this.getNodesCollection();
		for (INode<N, E> n : colecao_nodos) {
			if (!this.nodo_tem_aresta_de_entrada((Node) n)) // se o nodo 'n' não tiver aresta de entrada...
				s.push((Node) n);	// então, adiciona o nodo 'n' na pilha de nodos sem aresta de entrada.
		}
		
		return s;
	}
	
	// Retorna true se o nodo enviado por parâmetro possui uma aresta de entrada, false caso o contrário.
	private boolean nodo_tem_aresta_de_entrada(Node nodo) {
		if (nodo == null)
			throw new IllegalArgumentException("O Node passado por parâmetro é null!");
		Collection<INode<N, E>> colecao_nodos = this.getNodesCollection();
		for (INode<N, E> n : colecao_nodos) {
			if (n.equals(nodo))	// se o nodo 'n' for o mesmo que o passado por parâmetro...
				continue;	// então, não é necessário verificar se ele tem uma aresta apontando para ele mesmo.
			Collection<IEdge<N, E>> colecao_arestas = ((Node) n).getEdgesCollection();
			for (IEdge<N, E> e : colecao_arestas) {
				if (((Edge) e).getDestNode().equals(nodo))	// se esta aresta de 'n' tiver como destino 'nodo'...
					return true;	// então, retorna true  por que há uma aresta de entrada em 'nodo'.
			}
		}
		return false; // 'nodo' não tem nenhuma aresta de entrada. 
	}
	
	@SuppressWarnings("unchecked")
	public Graph<N, E> mst() {
		MST_Prim mst = new MST_Prim((Graph<N, Double>) this);
		this.steps.clearList();
		return (Graph<N, E>) mst.mst_prim();
	}
	
	// ===============[ MST-PRIM ]======================================================
	
	private final class MST_Prim {
		private Graph<N, Double> graph;
		
		private final class Node_Key implements Comparable<Node_Key>, Comparator<Node_Key> {
			public Graph<N, Double>.Node node, parent;
			public Double key;
			
			public Node_Key() {
				this(null, null, null);
			}
			
			public Node_Key(Graph<N, Double>.Node node, Graph<N, Double>.Node parent, Double key) {
				this.node = node;
				this.parent = parent;
				this.key = key;
			}
			
			public Graph<N, Double>.Node getNode() {
				return node;
			}
			
			public Graph<N, Double>.Node getParent() {
				return parent;
			}

			public void setParent(Graph<N, Double>.Node parent) {
				this.parent = parent;
			}
			
			public Double getKey() {
				return key;
			}
			
			public void setKey(Double key) {
				this.key = key;
			}
			
			@Override
			public boolean equals(Object arg0) {
				@SuppressWarnings("unchecked")
				Node_Key node = (Node_Key) arg0;
				return this.node.equals(node.getNode());// && this.parent.equals(node.getParent());
			}
			
			@Override
			public int compareTo(Node_Key o) {
				return this.key.compareTo(o.key);
			}

			@Override
			public int compare(Node_Key arg0, Node_Key arg1) {
				return Double.compare(arg0.getKey(), arg1.getKey());
			}
		}
		
		public MST_Prim(Graph<N, Double> graph) {
			this.graph = graph;
		}
		
		/*MST-PRIM (G, w, r)
	        for each key ∈ G.V
	            u.key = ∞
	            u.parent = NIL
	
	        r.key = 0
	        Q = G.V
	        
	        while (Q ≠ ø)
	            u = Extract-Min(Q)
	            for each v ∈ G.Adj[u]
	                if (v ∈ Q) and w(u,v) < v.key
	                    v.parent = u
	                    v.key = w(u,v)
		 */
		
		public Graph<N, Double> mst_prim() {
			boolean first_vertex = true;
			Map<Graph<N, Double>.Node, Node_Key> map_node_to_nodeKey = new HashMap<>();
			
			for (INode<N, Double> u : this.graph.getNodesCollection()) {
				if (first_vertex) {
					map_node_to_nodeKey.put((Graph<N, Double>.Node) u, new Node_Key((Graph<N, Double>.Node) u, null, new Double(0)));
					first_vertex = !first_vertex;
					continue;
				}	
				map_node_to_nodeKey.put((Graph<N, Double>.Node) u, new Node_Key((Graph<N, Double>.Node) u, null, Double.MAX_VALUE));
			}
			
			Collection<Node_Key> collection = map_node_to_nodeKey.values();
			Comparator<Node_Key> comparator = new Node_Key();
			
			// TENTEI USAR PRIORITY QUEUE MAS EM MOMENTO ALGUM A REORDENAÇÃO DO HEAP É FEITA
			//PriorityQueue<Node_Key> queue = new PriorityQueue<>(collection.size(), comparator);
			//queue.addAll(collection);
			// SÓ FUNCIONOU CORRETAMENTE USANDO ORDENAÇÕES SUCESSIVAS EM LISTA
			ArrayList<Node_Key> list = new ArrayList<>(collection);
			list.sort(comparator);
			
			while (!list.isEmpty()/*!queue.isEmpty()*/) {
				list.sort(comparator);
				Node_Key u = list.remove(0);
				//Node_Key u = queue.remove();
				
				for (IEdge<N, Double> edge : u.getNode().getEdgesCollection()) { // para cada aresta (u, v)
					Node_Key v = map_node_to_nodeKey.get(edge.getDestNode());
					
					if (list.contains(v)/*queue.contains(v)*/ && edge.getPeso() < v.getKey()) {	// se o peso da aresta (u, v) for menor que a chave de v
						v.setParent(u.getNode());
						v.setKey(edge.getPeso());
					}
				}
				
				steps.addStep((Graph<N, E>) make_MST_graph(map_node_to_nodeKey.values()));
			}
			
			return this.make_MST_graph(map_node_to_nodeKey.values());
		}
		
		private Graph<N, Double> make_MST_graph(Collection<Node_Key> collection) {
			Graph<N, Double> mst_graph = new Graph<>((ExecutionSteps<N, Double>) steps);
			
			for (Node_Key n : collection) {
				mst_graph.addNode(n.getNode().getItem());
				
				if (n.getParent() != null) {
					mst_graph.addNode(n.getParent().getItem());
					mst_graph.addEdge(n.getNode().getItem(), n.getParent().getItem(), n.getKey());
					mst_graph.addEdge(n.getParent().getItem(), n.getNode().getItem(), n.getKey());
				}
			}
			
			
			return mst_graph;
		}
		
	}
	
	// ===============[ DIJKSTRA ]======================================================
	
	private final class Dijkstra {
		private Graph<N, Double> graph;
		private Stack<Graph<N, Double>.Node> stack;
		
		private final class Node_Distance implements Comparable<Node_Distance>, Comparator<Node_Distance>  {
			public Graph<N, Double>.Node node;
			public Double distance;
			
			public Node_Distance(Graph<N, Double>.Node node, Double distance) {
				this.node = node;
				this.distance = distance;
			}

			@Override
			public int compareTo(Node_Distance o) {
				return this.distance.compareTo(o.distance);
			}

			@Override
			public int compare(Node_Distance o1, Node_Distance o2) {
				return Double.compare(o1.distance, o2.distance);
			}
			
		}
			
		public Dijkstra(Graph<N, Double> graph) {
			
			this.graph = graph;
			stack = new Stack<>();
		}
		
		public Graph<N, Double> dijkstra(N source, N target) {
			Graph<N, Double>.Node src = (Graph<N, Double>.Node) graph.findNode(source);
			Graph<N, Double>.Node trgt = (Graph<N, Double>.Node) graph.findNode(target);
			if (src == null || trgt == null)
				throw new NoSuchElementException(String.format("Um ou ambos os nodos não existe(m). Origem: %s, Destino: %s", src, trgt));
			stack.clear();
			return dijkstra(src, trgt);
		}
		
		private Graph<N, Double> dijkstra(Graph<N, Double>.Node source, Graph<N, Double>.Node target) {
			ArrayList<INode<N, Double>> listaNodos = new ArrayList<>(graph.getNodesCollection());
			ArrayList<Node_Distance> distance = new ArrayList<>(graph.size());
			ArrayList<Node_Distance> previous = new ArrayList<>(graph.size());		
			
			PriorityQueue<Node_Distance> queue = new PriorityQueue<>();
			
			int index = 0;
			for (INode<N, Double> node : listaNodos) {
				Node_Distance n;
				
				if (!node.equals(source))
					n = new Node_Distance((Graph<N, Double>.Node) node, Double.MAX_VALUE);
				else
					n = new Node_Distance(source, new Double(0));
				
				distance.add(n);
				previous.add(null);
				queue.add(n);
				index++;
			}
			
			while (!queue.isEmpty()) {
				Node_Distance u = queue.remove();
				int indexOfU = listaNodos.indexOf(u.node);
				
				if (u.node.equals(target)) {
					while (!u.node.equals(source)) {
						stack.push(u.node);
						u = previous.get(listaNodos.indexOf(u.node));
					}
					stack.push(u.node);
					break;
				}
				
				for (IEdge<N, Double> edge : u.node.getEdgesCollection()) {
					Double alt = distance.get(indexOfU).distance + edge.getPeso();	// preciso que seja Double para realizar operações aritméticas
					int indexOfV =  listaNodos.indexOf(edge.getDestNode());
					
					if (alt < distance.get(indexOfV).distance) {
						distance.get(indexOfV).distance = alt;
						previous.set(indexOfV, u);
						queue.add(distance.get(indexOfV));
					}
				}
				// POR ALGUM MOTIVO ISSO NÃO ESTÁ ARMAZENANDO O GRAFO A CADA PASSO DO ALGORITMO
				steps.addStep((Graph<N, E>) this.make_Dijkstra_graph());
			}
			
			return this.make_Dijkstra_graph();
		}
		
		private Graph<N, Double> make_Dijkstra_graph() {
			Graph<N, Double> dijkstra_graph = new Graph<>((ExecutionSteps<N, Double>) steps);
			Stack<Graph<N, Double>.Node> stack = (Stack<Graph<N, Double>.Node>) this.stack.clone();
			
			while (!stack.isEmpty()) {
				Graph<N, Double>.Node n = stack.pop();
				dijkstra_graph.addNode(n.getItem());
				
				if (!stack.isEmpty()) {
					Graph<N, Double>.Node next = stack.peek();
					dijkstra_graph.addNode(next.getItem());
					
					dijkstra_graph.addEdge(n.getItem(), next.getItem(), n.findEdge(next).getPeso());		
				}
			}
			
			return dijkstra_graph;
		}
		
		@Override
		public String toString() {
			@SuppressWarnings("unchecked")
			Stack<Graph<N, Double>.Node> stack = (Stack<Graph<N, Double>.Node>) this.stack.clone();
			StringBuilder str = new StringBuilder();
			while (!stack.isEmpty()) {
				str.append(stack.pop().getItem());
				try {
					if (stack.peek() != null)
						str.append(" -> ");
				}
				catch (EmptyStackException e) {
					continue;
				}
			}
			return str.toString();
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		for (INode<N, E> n : this.mapNodes.values()) {
			str.append(String.format("%s -> ", n.getItem()));
			for (IEdge<N, E> e : ((Node) n).getEdgesCollection())
				str.append(String.format("%s, ", e.getDestNode().getItem()));
			str.append("\n");
		}
		
		return str.toString();
	}
}
 