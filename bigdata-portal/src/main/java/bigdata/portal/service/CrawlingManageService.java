package bigdata.portal.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.mapper.CodeMapper;
import bigdata.portal.mapper.CrawlingManageMapper;

/**
 * 데이터 분석 서비스 클래스 
 *
 * @author THEIMC JHY
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2018. 10. 8.     JHY          최초 생성
 *      </pre>
 * 
 * @since 2018. 10. 8.
 */
@Service
public class CrawlingManageService {
	@Autowired private CrawlingManageMapper crawlingManageMapper;	
	
	
	/**
	 * 스케줄러 리스트를 리턴한다.
	 * 
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectSchedulerList() {
		List<EntityMap> dataList = crawlingManageMapper.selectSchedulerList();

		return dataList;
	}
	
	/**
	 * 스케줄러 별 수집 히스토리 총 리스트를 리턴한다.(통계데이터)
	 * 
	 * @param param
	 * @return
	 */
	public int getStaCrawlerTotalcount() {
		int totalCount = crawlingManageMapper.getStaCrawlerTotalcount();
		return totalCount;
	}
	
	/**
	 * 스케줄러 별 수집 히스토리 페이지 리스트를 리턴한다.(통계데이터)
	 * 
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectStaCrawlerHistoryList(HashMap<String, Object> param) {
		List<EntityMap> dataList = crawlingManageMapper.selectStaCrawlerHistoryList(param);

		return dataList;
	}
	
	/**
	 * 스케줄러 별 수집 히스토리 총 리스트를 리턴한다.(공공데이터)
	 * 
	 * @param param
	 * @return
	 */
	public int getPdlCrawlerTotalcount() {
		int totalCount = crawlingManageMapper.getPdlCrawlerTotalcount();
		return totalCount;
	}
	
	/**
	 * 스케줄러 별 수집 히스토리 페이지 리스트를 리턴한다.(공공데이터)
	 * 
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectPdlCrawlerHistoryList(HashMap<String, Object> param) {
		List<EntityMap> dataList = crawlingManageMapper.selectPdlCrawlerHistoryList(param);

		return dataList;
	}
	
	/**
	 * 스케줄러 별 수집 히스토리 총 리스트를 리턴한다.(빅데이터셋)
	 * 
	 * @param param
	 * @return
	 */
	public int getBdsCrawlerTotalcount() {
		int totalCount = crawlingManageMapper.getBdsCrawlerTotalcount();
		return totalCount;
	}
	
	/**
	 * 스케줄러 별 수집 히스토리 페이지 리스트를 리턴한다.(빅데이터셋)
	 * 
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectBdsCrawlerHistoryList(HashMap<String, Object> param) {
		List<EntityMap> dataList = crawlingManageMapper.selectBdsCrawlerHistoryList(param);

		return dataList;
	}
	
	/**
	 * 변경된 설정 값 업데이트(수집 주기 / 동작여부)
	 * 
	 * @param param
	 * @return
	 */
	public int updateSchedulerData(HashMap<String, Object> param) {
		int res = crawlingManageMapper.updateSchedulerData(param);

		return res;
	}
}
