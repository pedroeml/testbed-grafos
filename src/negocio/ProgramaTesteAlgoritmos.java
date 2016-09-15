package negocio;


public class ProgramaTesteAlgoritmos {
	private static ExecutionSteps<String, Double> steps;
	private static Graph<String, Double> graph;
	
	public static void main(String[] args) {
		steps = new ExecutionSteps<String, Double>();
		setUpGraph_caminhamentos_dijkstra();
		
		System.out.println("CAMINHAMENTO EM LARGURA:");
		graph.caminhamentoLargura("A");
		graph.caminhamentoLargura("C");
		
		System.out.println("CAMINHAMENTO EM PROFUNDIDADE:\n");
		graph.caminhamentoProfundidade("A");
		graph.caminhamentoProfundidade("C");
		
		System.out.println("MENOR CAMINHO -- DIJKSTRA:\n");
		
		System.out.println("A C:\n"+graph.dijkstra("A", "C")+"\n\n");
		System.out.println("A D:\n"+graph.dijkstra("A", "D")+"\n\n");
		System.out.println("A E:\n"+graph.dijkstra("A", "E")+"\n\n");
		System.out.println("A F:\n"+graph.dijkstra("A", "F")+"\n\n");
		System.out.println("B F:\n"+graph.dijkstra("B", "F")+"\n\n");
		System.out.println("C E:\n"+graph.dijkstra("C", "E")+"\n\n");
		System.out.println("C F:\n"+graph.dijkstra("C", "F")+"\n\n");
	
		
		setUpGraph_ordenacao_topologica();
		System.out.println("\nANTES DA ORDENAÇÃO:\n");
		System.out.println(graph);
		
		Graph<String, Double> grafo = graph.ordenacao_topologica();
		
		System.out.println("APÓS A ORDENAÇÃO:\n");
		System.out.println(grafo);
		
		setUpGraph_MST_1();
		System.out.println("MST:\n");
		System.out.println(graph.mst());
	}
	
	private static void setUpGraph_caminhamentos_dijkstra() {
		graph = new Graph<>(steps);
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addNode("D");
		graph.addNode("E");
		graph.addNode("F");		
		graph.addEdge("A", "B", new Double(7));
		graph.addEdge("A", "C", new Double(2));
		graph.addEdge("B", "D", new Double(3));
		graph.addEdge("C", "B", new Double(1));
		graph.addEdge("C", "D", new Double(2));
		graph.addEdge("C", "E", new Double(8));
		graph.addEdge("D", "E", new Double(3));
		graph.addEdge("D", "F", new Double(5));
		graph.addEdge("E", "F", new Double(1));
	}
	
	private static void setUpGraph_ordenacao_topologica() {
		graph = new Graph<>(steps);
		graph.addNode("cueca");
		graph.addNode("meias");
		graph.addNode("sapato");
		graph.addNode("calça");
		graph.addNode("cinto");
		graph.addNode("camisa");
		graph.addNode("paletó");
		graph.addNode("gravata");
		graph.addEdge("cueca", "calça", new Double(0));
		graph.addEdge("cueca", "sapato", new Double(0));
		graph.addEdge("meias", "sapato", new Double(0));
		graph.addEdge("calça", "sapato", new Double(0));
		graph.addEdge("calça", "cinto", new Double(0));
		graph.addEdge("cinto", "paletó", new Double(0));
		graph.addEdge("camisa", "cinto", new Double(0));
		graph.addEdge("camisa", "gravata", new Double(0));
		graph.addEdge("gravata", "paletó", new Double(0));
	}
	
	private static void setUpGraph_MST_1() {
		graph = new Graph<>(steps);
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addNode("D");
		graph.addNode("E");
		graph.addNode("F");
		graph.addNode("G");
		graph.addNode("H");
		graph.addNode("I");
		// Como é um grafo não-direcional, é então adicionado são adicionadas duas arestas de direções opostas entre nodo n0 e n1
		graph.addEdge("A", "B", new Double(4));
		graph.addEdge("A", "H", new Double(8));
		graph.addEdge("B", "A", new Double(4));
		graph.addEdge("B", "H", new Double(11));
		graph.addEdge("B", "C", new Double(8));
		graph.addEdge("C", "B", new Double(8));
		graph.addEdge("C", "I", new Double(2));
		graph.addEdge("C", "D", new Double(7));
		graph.addEdge("C", "F", new Double(4));
		graph.addEdge("D", "C", new Double(7));
		graph.addEdge("D", "F", new Double(14));
		graph.addEdge("D", "E", new Double(9));
		graph.addEdge("E", "D", new Double(9));
		graph.addEdge("E", "F", new Double(10));
		graph.addEdge("F", "C", new Double(4));
		graph.addEdge("F", "G", new Double(2));
		graph.addEdge("F", "D", new Double(14));
		graph.addEdge("F", "E", new Double(10));
		graph.addEdge("G", "I", new Double(6));
		graph.addEdge("G", "F", new Double(2));
		graph.addEdge("G", "H", new Double(1));
		graph.addEdge("H", "A", new Double(8));
		graph.addEdge("H", "B", new Double(11));
		graph.addEdge("H", "I", new Double(7));
		graph.addEdge("H", "G", new Double(1));
		graph.addEdge("I", "H", new Double(7));
		graph.addEdge("I", "C", new Double(2));
		graph.addEdge("I", "G", new Double(6));
	}
	
	private static void setUpGraph_MST_2() {
		graph = new Graph<>(steps);
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addNode("D");
		graph.addNode("E");
		graph.addEdge("A", "B", new Double(3));
		graph.addEdge("A", "E", new Double(1));
		graph.addEdge("B", "A", new Double(3));
		graph.addEdge("B", "C", new Double(5));
		graph.addEdge("B", "E", new Double(4));
		graph.addEdge("C", "B", new Double(5));
		graph.addEdge("C", "E", new Double(6));
		graph.addEdge("C", "D", new Double(2));
		graph.addEdge("D", "C", new Double(2));
		graph.addEdge("D", "E", new Double(7));
		graph.addEdge("E", "A", new Double(1));
		graph.addEdge("E", "B", new Double(4));
		graph.addEdge("E", "C", new Double(6));
		graph.addEdge("E", "D", new Double(7));
	}
}
