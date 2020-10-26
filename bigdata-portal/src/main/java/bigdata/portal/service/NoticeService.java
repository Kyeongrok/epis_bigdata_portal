package bigdata.portal.service;

import java.util.List;
import java.util.Map;

import kr.co.ucsit.core.CsFileVO;
import kr.co.ucsit.core.CsTransferObject;

@SuppressWarnings("rawtypes")
public interface NoticeService {

	/**
	 * 목록 조회
	 * @param <T>
	 * @param paramMap
	 * @return
	 */
	<T extends Map> CsTransferObject gets(T paramMap);

	<T extends Map> CsTransferObject getsRply(T paramMap);

	<T extends Map> CsTransferObject get(T paramMap);

	<T extends Map> CsTransferObject regist(T paramMap);

	<T extends Map> CsTransferObject updt(T paramMap);

	<T extends Map> CsTransferObject delete(T paramMap);

	/**
	 * 등록
	 * @param bbsGbn 게시판 구분
	 * @param nttSj 제목
	 * @param nttCn 내용
	 * @param fileVos 첨부파일 목록
	 * @param ntcrId 등록자 아이디
	 * @param ntcrNm 등록자 명
	 */
	void regist(String bbsGbn, String nttSj, String nttCn, List<CsFileVO> fileVos, String ntcrId, String ntcrNm);

	void registRply(String nttId, String bbsGbn, String nttSj, String nttCn
			, Integer parntscttNo, Integer answerLc
			, List<CsFileVO> fileVos, String ntcrId, String ntcrNm
			, boolean adminChecked);


	/**
	 * 조회
	 * @param nttId 게시글 아이디
	 * @return
	 */
	CsTransferObject getByNttId(String nttId);

	/**
	 * 조회 by nttNo
	 * @param nttNo
	 * @param bbsGbn
	 * @return
	 */
	CsTransferObject getByNttNo(Integer nttNo, String bbsGbn);

	/**
	 * 삭제
	 * @param nttId 게시글 아이디
	 */
	void deleteByNttId(String nttId);

	/**
	 * 업데이트
	 * @param nttId 게시글 아이디
	 * @param nttSj 제목
	 * @param nttCn 내용
	 * @param deletedFileNos 삭제된 파일 번호 목록
	 * @param fileVos 업로드된 파일 정보 목록
	 * @param ntcrId 작성자 아이디
	 * @param ntcrNm 작성자 명
	 */
	void updt(String nttId, String nttSj, String nttCn, String[] deletedFileNos
			, List<CsFileVO> fileVos, String ntcrId, String ntcrNm);

}
