package negocio;

import java.io.File;

import persistencia.IOpenGraphFileDAO;
import persistencia.OpenGraphFileDAOderby;

public class Assembler_fromGraphFile {
	private IOpenGraphFileDAO openGraphFile;
	private Graph<String, Double> graph;
	
	public Assembler_fromGraphFile(File file, ExecutionSteps<String, Double> steps) {
		this.openGraphFile = new OpenGraphFileDAOderby(file);
		this.graph = new Graph<>(steps);
		this.criarGrafo();
	}
	
	private void criarGrafo() {
		for (String[] line : this.openGraphFile.getGraphFileData()) {
			
			if (line[0].equalsIgnoreCase("Node"))
				this.graph.addNode(line[1]);
			else if (line[0].equalsIgnoreCase("Edge"))
				this.graph.addEdge(line[1], line[2], Double.parseDouble(line[3]));
		}
	}
	
	public Graph<String, Double> getGraph() {
		return this.graph;
	}
	
}
