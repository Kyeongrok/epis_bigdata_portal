package bigdata.portal.dmm.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import bigdata.portal.dmm.entity.DbMetaData;
import bigdata.portal.dmm.entity.FdmMetainfoVO;
import bigdata.portal.dmm.entity.FdmorgMap;
import bigdata.portal.dmm.entity.GraphEdge;
import bigdata.portal.dmm.entity.GraphNode;
import bigdata.portal.dmm.entity.TableMetaData;

public class SysGraph {

	private HashSet<String> group = null;

	/**
	 * 그래프의 nodes 변수에 매핑 될 데이터 생성 (기관)
	 * 
	 * @param fdmMetaInfo
	 * @param lastID
	 * @return
	 */
	public List<GraphNode> getNodeDatas(List<FdmMetainfoVO> fdmMetaInfo, int lastID) {
		int lastIDIte = lastID;
		LinkedHashSet<GraphNode> nodes = new LinkedHashSet<GraphNode>();

		for (FdmMetainfoVO data : fdmMetaInfo) {
			String txnmCode = data.getTxnmCode();
			String fdmorgCode = data.getFdmorgCode();
			String sysCode = data.getSysCode();
			String sysNm = data.getSysNm();			
			int nodeValue = data.getNodeValue();
			String dbType = data.getDbType();

			if (sysNm != null) {
				if(sysNm.length() > 0 && sysCode.length() > 0) {
					lastIDIte += 1;
	
					// 현지 기관만 출력되도록 설정
					GraphNode sysNmNode = new GraphNode(sysNm, lastIDIte, 3);
					sysNmNode.setFdmorgCode(fdmorgCode);
					sysNmNode.setTxnmCode(txnmCode);
					sysNmNode.setSysCode(sysCode);
					sysNmNode.setDbType(dbType);
					sysNmNode.setSize(nodeValue);
					
					nodes.add(sysNmNode);
				}
			}
		}

		return new ArrayList<GraphNode>(nodes);
	}
	
	public List<GraphNode> getNodeDBDatas(List<DbMetaData> fdmMetaInfo, int lastId) {
		int lastIDIte = lastId;
		ArrayList<GraphNode> nodes = new ArrayList<GraphNode>();

		for (DbMetaData data : fdmMetaInfo) {
			String dbName = data.getMetaDbNm();
			String dbCode = data.getDblistCode();
			String sysCode = data.getSyslstCode();

			if (dbName != null) {

				lastIDIte += 1;

				// 현지 기관만 출력되도록 설정
				GraphNode sysNmNode = new GraphNode(dbName, lastIDIte, 4);
				sysNmNode.setDbCode(dbCode);
				sysNmNode.setSysCode(sysCode);

				nodes.add(sysNmNode);
			}
		}

		return nodes;
		
	}
	
	public List<GraphNode> getNodeTableDatas(List<TableMetaData> fdmMetaInfo, int lastID) {
		int lastIDIte = lastID;
		ArrayList<GraphNode> nodes = new ArrayList<GraphNode>();

		for (TableMetaData data : fdmMetaInfo) {
			String tableName = data.getFdmsTableName();
			String tableCode = data.getFdmsTableIdx();
			String dbCode = data.getFdmsDblistCode();
			String tableDesc = data.getFdmsTableDesc();
			
			if (tableName != null) {

				lastIDIte += 1;

				// 현지 기관만 출력되도록 설정
				GraphNode sysNmNode = new GraphNode(tableName, lastIDIte, 5);
				sysNmNode.setDbCode(dbCode);
				sysNmNode.setTableCode(tableCode);
				sysNmNode.setTableDesc(tableDesc);
				nodes.add(sysNmNode);
			}
		}

		return nodes;
	}

	public List<GraphNode> getNodeDatas(List<FdmMetainfoVO> fdmMetaInfo) {
		LinkedHashSet<GraphNode> nodes = new LinkedHashSet<GraphNode>();
		this.group = new HashSet<String>();

		for (FdmMetainfoVO data : fdmMetaInfo) {
			String txnm = data.getTxnm();
			String fdmorgCode = data.getFdmorgCode();
			String sysCode = data.getSysCode();
			String sysNm = data.getSysNm();
			String txnmCode = data.getTxnmCode();			
			int nodeValue = data.getNodeValue();
			String dbType = data.getDbType();

			if (sysNm != null) {

				// 현지 기관만 출력되도록 설정
				GraphNode sysNmNode = new GraphNode(sysNm, nodes.size(), 3);
				sysNmNode.setFdmorgCode(fdmorgCode);
				sysNmNode.setSysCode(sysCode);
				sysNmNode.setTxnm(txnm);
				sysNmNode.setTxnmCode(txnmCode);
				sysNmNode.setDbType(dbType);
				
				sysNmNode.setSize(nodeValue);
				
				group.add(txnm);

				nodes.add(sysNmNode);
			}
		}

		return new ArrayList<GraphNode>(nodes);
	}

	public List<GraphEdge> getEdgeDatas(List<GraphNode> nodes, int rootId) {

		List<GraphEdge> edges = new ArrayList<GraphEdge>();

		for (int i = 0; i < nodes.size(); i++) {
			GraphEdge edge = new GraphEdge();

			edge.setTo(rootId);
			edge.setFrom(nodes.get(i).getId());
			edges.add(edge);

		}
		return edges;
	}
	
	public List<GraphEdge> linkEdgeDatas(List<GraphNode> nodes) {
		
		return null;
	}
	
	public List<GraphEdge> getEdgeDatas(List<GraphNode> nodes, List<FdmorgMap> fdmorgMap) {
		
		List<GraphEdge> edges = new ArrayList<GraphEdge>();
		int[] group = new int[]{-1, -1, -1, -1};
		int[] startGroupNum = new int[]{-1, -1, -1, -1};
		int idx = 0;
		
		for(int i = 0; i<nodes.size(); i++) {
			String txnmCode = nodes.get(i).getTxnmCode();
			int groupCode = Integer.parseInt(txnmCode.substring(txnmCode.length()-1));
			int nodeIId = nodes.get(i).getId();
			String nodeITxnm = nodes.get(i).getTxnm();
			
			if(group[groupCode-1] == -1) {
				startGroupNum[groupCode-1] = nodes.get(i).getId();
				group[groupCode-1] = nodes.get(i).getId();
				
			} else {					
				GraphEdge edge = new GraphEdge();
				edge.setTo(group[groupCode-1]);
				edge.setFrom(nodes.get(i).getId());
				
				group[groupCode-1] = nodes.get(i).getId();
				
				edges.add(edge);
			}
			
			for (int j = i + 1; j < nodes.size(); j++) {
				int nodeJId = nodes.get(j).getId();
				String nodeJTxnm = nodes.get(j).getTxnm();

				if (nodeIId == nodeJId) {
					continue;
				}
				
				
				if (idx < fdmorgMap.size() && fdmorgMap.get(idx).getSysTo().equals(nodes.get(i).getSysCode())) {
					if (fdmorgMap.get(idx).getSysFrom().equals(nodes.get(j).getSysCode())) {
						GraphEdge fdmorgMapEdge = new GraphEdge();
						fdmorgMapEdge.setTo(nodes.get(i).getId());
						fdmorgMapEdge.setFrom(nodes.get(j).getId());
						edges.add(fdmorgMapEdge);
						idx++;
					}
				}

			}		
		}
		
		/*
		for(int i=0; i<group.length; i++) {
			if(group[i] != startGroupNum[i]) {
				GrapeEdge edge = new GrapeEdge();
				edge.setTo(group[i]);
				edge.setFrom(startGroupNum[i]);
				
				edges.add(edge);
			}
		}
		*/
				 
		return edges;
		
	}

	public List<GraphEdge> getEdgeDatas(List<GraphNode> nodes, List<FdmorgMap> fdmorgMap, boolean flag) {

		List<GraphEdge> edges = new ArrayList<GraphEdge>();
		int idx = 0;

		for (int i = 0; i < nodes.size() - 1; i++) {
			String nodeITxnm = nodes.get(i).getTxnm();
			int nodeIId = nodes.get(i).getId();

			for (int j = i + 1; j < nodes.size(); j++) {
				String nodeJTxnm = nodes.get(j).getTxnm();
				int nodeJId = nodes.get(j).getId();

				if (nodeIId == nodeJId) {
					continue;
				}

				if (nodeITxnm.equals(nodeJTxnm)) {
					GraphEdge edge = new GraphEdge();
					edge.setTo(nodes.get(i).getId());
					edge.setFrom(nodes.get(j).getId());
					edges.add(edge);
				}

				if (idx < fdmorgMap.size() && fdmorgMap.get(idx).getSysTo().equals(nodes.get(i).getSysCode())) {
					if (fdmorgMap.get(idx).getSysFrom().equals(nodes.get(j).getSysCode())) {
						GraphEdge fdmorgMapEdge = new GraphEdge();
						fdmorgMapEdge.setTo(nodes.get(i).getId());
						fdmorgMapEdge.setFrom(nodes.get(j).getId());
						edges.add(fdmorgMapEdge);
						idx++;
					}
				}

			}
		}

		return edges;
	}

}
