package bigdata.portal.dmm.graph;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import bigdata.portal.dmm.entity.FdmMetainfoVO;
import bigdata.portal.dmm.entity.GraphEdge;
import bigdata.portal.dmm.entity.GraphNode;

public class OrgGraph {
	
	/**
	 * 그래프의 nodes 변수에 매핑 될 데이터 생성 (기관)
	 * @param fdmMetaInfo
	 * @return
	 */
	public List<GraphNode> getNodeDatas(List<FdmMetainfoVO> fdmMetaInfo) {
		LinkedHashSet<GraphNode> nodes = new LinkedHashSet<GraphNode>();
		
		for (FdmMetainfoVO data : fdmMetaInfo) {
			
			String fdmorgNm = data.getFdmorgNm();
			String fdmorgCode = data.getFdmorgCode();

			if(fdmorgNm != null) {
			
				// 현지 기관만 출력되도록 설정
				GraphNode fdmorgNmNode = new GraphNode(fdmorgNm, nodes.size(), 2);
				fdmorgNmNode.setFdmorgCode(fdmorgCode);
				fdmorgNmNode.setFdmorgNm(fdmorgNm);
				fdmorgNmNode.setOrgGroup();
				nodes.add(fdmorgNmNode);
			}
			
		}
		
		return new ArrayList<GraphNode>(nodes);
	}
	
	/**
	 * 그래프의 edges 변수에 매핑 될 데이터 생성
	 * @param nodes
	 * @param rootNodeName
	 * @return
	 */
	public List<GraphEdge> getEdgeDatas(List<GraphNode> nodes, String rootNodeName) {
		
		List<GraphEdge> edges = new ArrayList<GraphEdge>();
		GraphNode rootNode = getRootNode(nodes, rootNodeName);
		
		for(int i=0; i<nodes.size(); i++){
			
			if(nodes.get(i).getFdmorgNm().equals(rootNodeName)) {
				continue;
			}
			
			GraphEdge edge = new GraphEdge();
			
			edge.setTo(rootNode.getId());
			edge.setFrom(nodes.get(i).getId());
			edges.add(edge);
			
		}
		return edges;		
	}
	
	
	/** 
	 * 기관의 루트노드를 찾기 위한 메소드
	 * @param nodes
	 * @param rootNodeName
	 * @return
	 */
	private GraphNode getRootNode(List<GraphNode> nodes, String rootNodeName) {
		
		GraphNode rootNode = new GraphNode();
		
		for(int i=0; i<nodes.size(); i++) {
			
			if(nodes.get(i).getLabel().equals(rootNodeName)) {
				rootNode.setLabel(rootNodeName);
				rootNode.setId(nodes.get(i).getId());
				rootNode.setDbCode(nodes.get(i).getDbCode());
				rootNode.setFdmorgCode(nodes.get(i).getFdmorgCode());
				rootNode.setGroup(nodes.get(i).getGroup());
				rootNode.setOrgGroup();
				rootNode.setSysCode(nodes.get(i).getSysCode());
				
				break;
				
			}
		}
		
		return rootNode;
	}


	
}
