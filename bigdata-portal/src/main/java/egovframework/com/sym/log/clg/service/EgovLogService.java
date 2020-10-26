package egovframework.com.sym.log.clg.service;

import java.util.Map;

public interface EgovLogService {

	/**
	 * 접속로그를 기록한다.
	 *
	 * @param LoginLog
	 */
	public void insertLog(Log log) throws Exception;

	/**
	 * 접속로그를 조회한다.
	 *
	 * @param loginLog
	 * @return loginLog
	 * @throws Exception
	 */
	public Log selectLog(Log log) throws Exception;

	/**
	 * 접속로그 목록을 조회한다.
	 *
	 * @param LoginLog
	 */
	public Map<?, ?> selectLogInf(Log log) throws Exception;
	
}
