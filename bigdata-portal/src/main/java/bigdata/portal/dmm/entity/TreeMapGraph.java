package bigdata.portal.dmm.entity;

import java.util.ArrayList;
import java.util.List;

public class TreeMapGraph {


	public static List<GraphEdge> makeGrapeEdges(List<GraphNode> nodes, List<GraphNode> anoNodes, String mode) {
		List<GraphEdge> edges = new ArrayList<GraphEdge>();

		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < anoNodes.size(); j++) {
				GraphEdge edge = compare(nodes.get(i), anoNodes.get(j), mode);
				
				if (edge != null) {
					edges.add(edge);
				}
			}
		}

		return edges;
	}

	private static GraphEdge compare(GraphNode node, GraphNode anoNode, String mode) {
		if (mode.equals("SYSTEMINFO")) {
			if (node.getFdmorgCode().equals(anoNode.getFdmorgCode())) {
				return makeEdge(node.getId(), anoNode.getId());
			}
		} else if (mode.equals("DATABASE")) {
			if (node.getSysCode().equals(anoNode.getSysCode())) {
				return makeEdge(node.getId(), anoNode.getId());
			}
		} else {
			if (node.getDbCode().equals(anoNode.getDbCode())) {
				return makeEdge(node.getId(), anoNode.getId());
			}
		}

		return null;
	}

	private static GraphEdge makeEdge(int nodeId, int anoNodeId) {
		GraphEdge edge = new GraphEdge();
		edge.setTo(nodeId);
		edge.setFrom(anoNodeId);

		return edge;
	}

}
