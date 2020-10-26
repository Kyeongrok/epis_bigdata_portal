package bigdata.portal.mapper;

import java.util.HashMap;
import java.util.List;

import bigdata.portal.entity.EntityMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface DatasetMapper {
	
	public EntityMap selectDataset(HashMap<String, Object> param);

	public EntityMap selectDatasetDetail(HashMap<String, Object> param);
	
	/**
	 * 빅데이터셋 리스트를 리턴한다.
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectDatasetList(HashMap<String, Object> param);

	/**
	 * 빅데이터셋 갯수를 리턴한다.
	 * @param param
	 * @return
	 */
	public int getTotalCount(HashMap<String, Object> param);
	
	/**
	 * 데이터 종별, 데이터셋별 갯수를 리턴한다.
	 * @param param
	 * @return
	 */
	public EntityMap getTypeCount(HashMap<String, Object> param);

	/**
	 * 빅데이터셋목록 정보와 빅데이터셋 row 정보를 리턴한다.
	 * @param param
	 * @return
	 */
	public EntityMap selectBdpDataView(HashMap<String, Object> param);
	
	/**
	 * 빅데이터셋목록 조회수 업데이트
	 * @param param
	 * @return
	 */
	public int updateBdpDataViewHits(HashMap<String, Object> param);

	// sample rawdata
	public List<EntityMap> selectSampleMarketPriceRawData(HashMap<String, Object> param);
	
	/**
	 * CD_TP_SE 검색하여 OBJ_VAR_ID(분류코드) 종류를 리턴한다 
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectBdpStdCodeInfoObjVarIdList(HashMap<String, Object> param);

	public EntityMap selectBdpStdDimDtOvCount(HashMap<String, Object> param);
	
	public EntityMap selectBdpStdDimDtPreDe(HashMap<String, Object> param);
	
	public List<EntityMap> selectBdpStdDimDt(HashMap<String, Object> param);
	
	public List<EntityMap> selectBdpStdCodeInfoWhereItmId(HashMap<String, Object> param);
	
	public EntityMap selectBdpStdCodeInfoUpItmId(HashMap<String, Object> param);
	
	/**
	 * 통계데이터 차원수치 테이블에서 중복제거한 시점값을 리턴한다.
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectStaPrdDeList(HashMap<String, Object> param);
	
	/**
	 * 통계표 obj_var_id 값을 리턴한다
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectStaObjVarId(HashMap<String, Object> param);
	
	/**
	 * 통계표 상단 타이틀 JSON 호출
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectStaLeftTopNames(HashMap<String, Object> param);
	
	/**
	 * 통계표정보 및 주석 자동수집통계표 정보를 리턴한다
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectBdpStdStblInfo(HashMap<String, Object> param);
	
	/**
	 * 데이터셋 데이터 원천데이터 개방여부 업데이트
	 * @param param
	 * @return
	 */
	public int updateOpenRangeSource(HashMap<String, Object> param);
	
	/**
	 * 데이터셋 데이터 분석 개방여부 업데이트
	 * @param param
	 * @return
	 */
	public int updateOpenRangeAnalysis(HashMap<String, Object> param);
	
	/**
	 * 데이터셋 데이터 일반사용자에게 오픈 개방 여부 업데이트
	 * @param param
	 * @return
	 */
	public int updateOpenRangeDataOpen(HashMap<String, Object> param);
	
	/**
	 * 기관정보를 리턴
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectBdpOrganization(HashMap<String, Object> param);
	
	/**
	 * 데이터셋 목록 정보를 리턴
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectBdpDataList(HashMap<String, Object> param);
	
	/**
	 * 데이터 목록 행 정보를 리턴 
	 * @param param
	 * @return
	 */
	public EntityMap selectBdpDataListRow(HashMap<String, Object> param);
	
	/**
	 * 데이터셋 정보를 수정
	 * @param param
	 * @return
	 */
	public int updateBdpDatasetRow(HashMap<String, Object> param);
	
	/**
	 * 데이터셋 사용여부를 변경
	 * @param param
	 * @return
	 */
	public int updateDsUseAt(HashMap<String, Object> param);
	
	/**
	 * 데이터목록 갯수를 리턴
	 * @param param
	 * @return
	 */
	public int getTotalCountDataList(HashMap<String, Object> param);
	
	/**
	 * 데이터목록 사용여부를 변경
	 * @param param
	 * @return
	 */
	public int updateDlUseAt(HashMap<String, Object> param);
	
	/**
	 * 데이터목록 정보를 수정
	 * @param param
	 * @return
	 */
	public int updateBdpDataListRow(HashMap<String, Object> param);
	/**
	 * 데이터셋 정보를 리턴
	 * @param param
	 * @return
	 */
	public EntityMap selectBdpDataSetRow(HashMap<String, Object> param);
	
	
	/**
	 * 빅데이터셋 연관 데이터 리스트 리턴
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectAssociativeDataset(HashMap<String, Object> param);
	
	
	/**
	 * 빅데이터셋 데이터를 리턴
	 * @param tableNm
	 * @param columns
	 * @return
	 */
	public List<EntityMap> selectTableDatasetList(HashMap<String, Object> param);
	
	/**
	 * 빅데이터셋 DB 테이블 총 건수 리턴
	 * @param param
	 * @return
	 */
	public int getTableDatasetTotalCount(HashMap<String, Object> param);
	
}