package bigdata.portal.dmm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.dmm.entity.DbMetaData;
import bigdata.portal.dmm.entity.ErdImage;
import bigdata.portal.dmm.entity.FdmMetainfoVO;
import bigdata.portal.dmm.entity.FdmorgMap;
import bigdata.portal.dmm.entity.GraphEdge;
import bigdata.portal.dmm.entity.GraphNode;
import bigdata.portal.dmm.entity.SearchDbInfo;
import bigdata.portal.dmm.entity.SearchSysInfo;
import bigdata.portal.dmm.entity.SysMetaData;
import bigdata.portal.dmm.entity.TableMetaData;
import bigdata.portal.dmm.entity.TreeMap;
import bigdata.portal.dmm.graph.OrgGraph;
import bigdata.portal.dmm.graph.SysGraph;
import bigdata.portal.dmm.mapper.FdmDataMapMapper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class FdmDataMapService extends EgovAbstractServiceImpl{

	@Autowired
    FdmDataMapMapper dataMapMapper;

	/**
	 * 정보시스템 메타데이터 Load
	 */
	public SysMetaData selectSysMetadataInfo(HashMap<String, String> param) {
		//System.out.println(param.toString());
		return dataMapMapper.selectSysMetadataInfo(param);
	}

	/**
	 * 정보시스템 하위 데이터베이스 메타데이터 Load
	 */
	public DbMetaData selectDbMetadataInfo(HashMap<String, String> param) {

		return dataMapMapper.selectDbMetadataInfo(param);
	}

	/**
	 * 정보시스템 검색 정보 결과
	 */
	public ArrayList<SearchSysInfo> selectSearchSysInfo(HashMap<String, Object> param) {
		return dataMapMapper.selectSearchSysInfo(param);
	}


	/**
	 * DB 내 테이블 검색 정보 결과
	 */
	public ArrayList<SearchDbInfo> selectSearchDbInfo(HashMap<String, String> param) {
		return dataMapMapper.selectSearchDbInfo(param);
	}

	/**
	 * 기관 노드 데이터 출력 용도 (최종수정)
	 */
	public HashMap<String, Object> getGraphData(HashMap<String, String> code) throws Exception {

		OrgGraph orgGraph = new OrgGraph();
		HashMap<String, Object> data = new HashMap<String, Object>();

		List<FdmMetainfoVO> fdmMetaInfo = dataMapMapper.selectFdmorgList();
		List<GraphNode> nodes = orgGraph.getNodeDatas(fdmMetaInfo);
		List<GraphEdge> edges = orgGraph.getEdgeDatas(nodes, code.get("rootNodeName"));
		
		for(int i=0; i<nodes.size(); i++) {
			nodes.get(i).setLabel("");
		}

		data.put("nodes", nodes);
		data.put("edges", edges);

		return data;
	}


	/**
	 * 정보시스템 노드 데이터 출력 용도 (최종수정)
	 */
	public HashMap<String, Object> getGraphChildData(HashMap<String, String> code) throws Exception {

		SysGraph sysGraph = new SysGraph();
		HashMap<String, Object> data = new HashMap<String, Object>();
		List<FdmMetainfoVO> fdmMetaInfo = null;
		List<DbMetaData> dbMetaData = null;
		List<TableMetaData> tableMetaData = null;
		List<GraphNode> nodes = null;

		int lastID = Integer.parseInt(code.get("lastID"));
		int rootID = Integer.parseInt(code.get("rootID"));

		if(code.get("codeType").equals("FDMORG")) {
			fdmMetaInfo = dataMapMapper.selectGraphNodeData(code);
			nodes = sysGraph.getNodeDatas(fdmMetaInfo, lastID);
		} else if(code.get("codeType").equals("SYSTEM")){
			dbMetaData = dataMapMapper.selectDbdataInfo(code);
			nodes = sysGraph.getNodeDBDatas(dbMetaData, lastID);
		} else {
			tableMetaData = dataMapMapper.selectTabledataInfo(code);
			nodes = sysGraph.getNodeTableDatas(tableMetaData, lastID);
		}

		List<GraphEdge> edges = sysGraph.getEdgeDatas(nodes, rootID);

		data.put("nodes", nodes);
		data.put("edges", edges);

		return data;
	}



	/**
	 * 테이블 데이터 수
	 */
	public int getSearchDbInfoCount(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return dataMapMapper.selectSearchDbInfoCount(param);

	}


	/**
	 * 정보시스템 데이터 수
	 */
	public int getSearchSysInfoCount(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return dataMapMapper.selectSearchSysInfoCount(param);
	}

	public HashMap<String, Object> getFDMORGGraphData(HashMap<String, String> code) throws Exception {

		SysGraph sysGraph = new SysGraph();
		HashMap<String, Object> data = new HashMap<String, Object>();

		addKeywordToCode(code);

		List<FdmMetainfoVO> fdmMetaInfo = dataMapMapper.selectFdmorgNodeData(code);
		List<FdmorgMap> fdmorgMap = dataMapMapper.selectFdmorgMapData(code);

		List<GraphNode> nodes = sysGraph.getNodeDatas(fdmMetaInfo);
		List<GraphEdge> edges = sysGraph.getEdgeDatas(nodes, fdmorgMap);

		data.put("nodes", nodes);
		data.put("edges", edges);

		return data;
	}

	/**
	 * 분류체계의 데이터 검색을 위한 키워드 설정
	 * @param code
	 */
	public void addKeywordToCode(HashMap<String, String> code) {
		if(code.get("code").equals("01")) {
			code.put("keyword", "농산");
		} else if (code.get("code").equals("02")) {
			code.put("keyword", "축산");
		} else if (code.get("code").equals("03")) {
			code.put("keyword", "산림");
		} else {
			code.put("keyword", "식품");
		}
	}

	public String getLogicERDImgPath(HashMap<String, String> erdConf) {
		return dataMapMapper.selectLogicERDImgPath(erdConf);
	}

	public String getPhysicalERDImgPath(HashMap<String, String> erdConf) {
		return dataMapMapper.selectPhysicalERDImgPath(erdConf);
	}

	public TableMetaData selectTableMetadataInfo(HashMap<String, String> param) {
		return dataMapMapper.selectTableMetadataInfo(param);
	}

	public List<TableMetaData> selectSearchTableInfo(HashMap<String, Object> param) {
		return dataMapMapper.selectSearchTableInfo(param);
	}

	public ArrayList<ErdImage> getErdImgInfo(HashMap<String, String> dataCode) {
		ArrayList<ErdImage> erdImgs = dataMapMapper.selectERDImgInfo(dataCode);
		ArrayList<ErdImage> returnList = new ArrayList<ErdImage>();

		for (ErdImage erd : erdImgs) {
			String dbList = erd.getDblist();
			String erdImgLogic = erd.getErdImgLogic();
			String erdImgPhysical = erd.getErdImgPhysical();
			String metaSysNm = erd.getMetaSysNm();
			String sysLst = erd.getSyslst();


			if(dataCode.get("modelType").equals("l")) {
				if(erdImgLogic != null) {
					String[] token = erdImgLogic.split(",");
					if(token.length > 0) {
						for(int i=0; i<token.length; i++) {
							ErdImage eImg = new ErdImage();
							eImg.setDblist(dbList);
							eImg.setErdImgLogic(token[i]);
							eImg.setMetaSysNm(metaSysNm);
							eImg.setSyslst(sysLst);
							eImg.setMetaSysNm(metaSysNm);
							returnList.add(eImg);
						}
					}
				}
			} else {
				if(erdImgPhysical != null) {
					String[] token = erdImgPhysical.split(",");
					if(token.length > 0) {
						for(int i=0; i<token.length; i++) {
							ErdImage eImg = new ErdImage();
							eImg.setDblist(dbList);
							eImg.setErdImgPhysical((token[i]));
							eImg.setMetaSysNm(metaSysNm);
							eImg.setSyslst(sysLst);
							eImg.setMetaSysNm(metaSysNm);
							returnList.add(eImg);
						}
					}
				}
			}
		}


		return returnList;
	}

	public List<TreeMap> getTreeMapTable(HashMap<String, String> param) {
		// TODO Auto-generated method stub
		return dataMapMapper.selectTreeMapTable(param);
	}

	public List<TreeMap> getTreeMapDatabase(HashMap<String, String> param) {
		// TODO Auto-generated method stub
		return dataMapMapper.selectTreeMapDatabase(param);
	}

	public List<TreeMap> getTreeMapSysInfo(HashMap<String, String> param) {
		// TODO Auto-generated method stub
		return dataMapMapper.selectTreeMapSysInfo(param);
	}

	public List<TreeMap> getTreeMapFdmorg() {
		// TODO Auto-generated method stub

		List<TreeMap> fdmorg = dataMapMapper.selectTreeMapFdmorg();

		for(int i=0; i<fdmorg.size(); i++) {
			if(fdmorg.get(i).getName().equals("농림축산식품부")) {
				fdmorg.get(i).setParentId(fdmorg.get(i).getName());
				fdmorg.get(i).setItemId("root");
			}
		}

		return fdmorg;
	}

	public List<GraphNode> getSysGraphData(HashMap<String, String> code) throws Exception {

		SysGraph sysGraph = new SysGraph();
		List<FdmMetainfoVO> fdmMetaInfo = null;
		List<GraphNode> nodes = null;

		int lastID = Integer.parseInt(code.get("lastID"));

		fdmMetaInfo = dataMapMapper.selectGraphNodeData(code);
		nodes = sysGraph.getNodeDatas(fdmMetaInfo, lastID);

		return nodes;

	}

	public List<GraphNode> getDbGraphData(HashMap<String, String> code) throws Exception {
		SysGraph sysGraph = new SysGraph();
		List<DbMetaData> dbMetaData = null;
		List<GraphNode> nodes = null;

		int lastID = Integer.parseInt(code.get("lastID"));

		dbMetaData = dataMapMapper.selectDbdataInfo(code);
		nodes = sysGraph.getNodeDBDatas(dbMetaData, lastID);

		return nodes;
	}

	public List<GraphNode> getTableGraphData(HashMap<String, String> code) throws Exception {
		SysGraph sysGraph = new SysGraph();
		List<TableMetaData> tableMetaData = null;
		List<GraphNode> nodes = null;

		int lastID = Integer.parseInt(code.get("lastID"));

		tableMetaData = dataMapMapper.selectTabledataInfo(code);
		nodes = sysGraph.getNodeTableDatas(tableMetaData, lastID);

		return nodes;
	}


}
