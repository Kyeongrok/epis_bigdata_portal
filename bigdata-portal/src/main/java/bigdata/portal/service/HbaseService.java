package bigdata.portal.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.mapper.CodeMapper;
import bigdata.portal.mapper.DatasetMapper;
import bigdata.portal.mapper.PublicDatasetMapper;

/**
 * Hbase 데이터 조회 지원을 위한 서비스 클래스
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
public class HbaseService {
	public EntityMap selectRawData(HashMap<String, Object> param) {
		return null;
	}
}
