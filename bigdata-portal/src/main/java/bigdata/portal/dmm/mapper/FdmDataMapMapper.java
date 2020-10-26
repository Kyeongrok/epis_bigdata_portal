package bigdata.portal.dmm.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bigdata.portal.dmm.entity.DbMetaData;
import bigdata.portal.dmm.entity.ErdImage;
import bigdata.portal.dmm.entity.FdmMetainfoVO;
import bigdata.portal.dmm.entity.FdmorgMap;
import bigdata.portal.dmm.entity.SearchDbInfo;
import bigdata.portal.dmm.entity.SearchSysInfo;
import bigdata.portal.dmm.entity.SysMetaData;
import bigdata.portal.dmm.entity.TableMetaData;
import bigdata.portal.dmm.entity.TreeMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface FdmDataMapMapper {

	public List<FdmMetainfoVO> selectGraphNodeData(HashMap<String, String> param);

	public List<FdmMetainfoVO> selectFdmorgNodeData(HashMap<String, String> param);

	public List<FdmMetainfoVO> selectGraphChildNodeData(HashMap<String, String> param);

	public SysMetaData selectSysMetadataInfo(HashMap<String, String> param);

	public DbMetaData selectDbMetadataInfo(HashMap<String, String> param);

	public ArrayList<SearchSysInfo> selectSearchSysInfo(HashMap<String, Object> param);

	public ArrayList<SearchDbInfo> selectSearchDbInfo(HashMap<String, String> param);

	public int selectSearchDbInfoCount(HashMap<String, Object> param) throws Exception;

	public int selectSearchSysInfoCount(HashMap<String, Object> param) throws Exception;

	public List<FdmorgMap> selectFdmorgMapData(HashMap<String, String> param);

	public List<DbMetaData> selectDbdataInfo(HashMap<String, String> param);

	public String selectLogicERDImgPath(HashMap<String, String> erdConf);

	public String selectPhysicalERDImgPath(HashMap<String, String> erdConf);

	public List<TableMetaData> selectTabledataInfo(HashMap<String, String> param);

	public TableMetaData selectTableMetadataInfo(HashMap<String, String> param);

	public List<TableMetaData> selectSearchTableInfo(HashMap<String, Object> param);

	public ArrayList<ErdImage> selectERDImgInfo(HashMap<String, String> dataCode);

	public List<TreeMap> selectTreeMapTable(HashMap<String, String> param);

	public List<TreeMap> selectTreeMapDatabase(HashMap<String, String> param);

	public List<TreeMap> selectTreeMapSysInfo(HashMap<String, String> param);

	public List<TreeMap> selectTreeMapFdmorg();

	public List<FdmMetainfoVO> selectFdmorgList();

}
