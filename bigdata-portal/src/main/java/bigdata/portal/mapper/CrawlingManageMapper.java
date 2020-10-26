package bigdata.portal.mapper;

import java.util.HashMap;
import java.util.List;

import bigdata.portal.entity.Code;
import bigdata.portal.entity.CodeDetail;
import bigdata.portal.entity.EntityMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface CrawlingManageMapper {
	/**
	 * 스케줄러 리스트를 리턴한다.
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectSchedulerList();
	
	/**
	 * 스케줄러 별 수집 히스토리 총 리스트를 리턴한다.(통계데이터)
	 * 
	 * @param param
	 * @return
	 */
	public int getStaCrawlerTotalcount();
	
	/**
	 * 스케줄러 별 수집 히스토리 페이지 리스트를 리턴한다 (통계데이터)
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectStaCrawlerHistoryList(HashMap<String, Object> param);
	
	/**
	 * 스케줄러 별 수집 히스토리 총 리스트를 리턴한다.(공공데이터)
	 * 
	 * @param param
	 * @return
	 */
	public int getPdlCrawlerTotalcount();
	
	/**
	 * 스케줄러 별 수집 히스토리 페이지 리스트를 리턴한다 (공공데이터)
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectPdlCrawlerHistoryList(HashMap<String, Object> param);
	
	/**
	 * 스케줄러 별 수집 히스토리 총 리스트를 리턴한다.(빅데이터셋)
	 * 
	 * @param param
	 * @return
	 */
	public int getBdsCrawlerTotalcount();
	
	/**
	 * 스케줄러 별 수집 히스토리 페이지 리스트를 리턴한다 (빅데이터셋)
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectBdsCrawlerHistoryList(HashMap<String, Object> param);
	
	/**
	 * 변경된 설정 값 업데이트(수집 주기 / 동작여부)
	 * @param param
	 * @return
	 */
	public int updateSchedulerData(HashMap<String, Object> param);
}
