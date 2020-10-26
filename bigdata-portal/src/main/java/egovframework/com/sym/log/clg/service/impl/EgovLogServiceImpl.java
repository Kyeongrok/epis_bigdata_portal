package egovframework.com.sym.log.clg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.com.sym.log.clg.service.EgovLogService;
import egovframework.com.sym.log.clg.service.Log;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * @Class Name : EgovLoginLogServiceImpl.java
 * @Description : 접속로그 관리를 위한 서비스 구현 클래스
 * @Modification Information
 *
 *       수정일       수정자         수정내용
 *      -------        -------     -------------------
 *    2009. 3. 11.     이삼섭        최초생성
 *    2011. 7. 01.     이기하        패키지 분리(stm.log -> sym.log.clg)
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Service("egovLogService")
public class EgovLogServiceImpl extends EgovAbstractServiceImpl implements EgovLogService {

	@Resource(name="logDAO")
	private LogDAO logDAO;

    /** ID Generation */
	@Resource(name="egovLoginLogIdGnrService")
	private EgovIdGnrService egovLoginLogIdGnrService;

	/**
	 * 접속로그를 기록한다.
	 *
	 * @param LoginLog
	 */
	@Override
	public void insertLog(Log log) throws Exception {
		String logId = egovLoginLogIdGnrService.getNextStringId();
		log.setLogId(logId);
		logDAO.insertLog(log);

	}

	/**
	 * 접속로그를 조회한다.
	 *
	 * @param log
	 * @return log
	 * @throws Exception
	 */
	@Override
	public Log selectLog(Log log) throws Exception {

		return logDAO.selectLog(log);
	}

	/**
	 * 접속로그 목록을 조회한다.
	 *
	 * @param LoginLog
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<?, ?> selectLogInf(Log Log) throws Exception {
		List<?> _result = logDAO.selectLogInf(Log);
		int _cnt = logDAO.selectLogInfCnt(Log);

		Map<String, Object> _map = new HashMap();
		_map.put("resultList", _result);
		_map.put("resultCnt", Integer.toString(_cnt));

		return _map;
	}

}
