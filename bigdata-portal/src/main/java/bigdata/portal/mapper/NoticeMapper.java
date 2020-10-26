package bigdata.portal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @author hyunseongkil
 *
 */
@Mapper
public interface NoticeMapper {

	List<Map<String,Object>> gets(Map<String,Object> paramMap);

	List<Map<String,Object>> getsRply(Map<String,Object> paramMap);

	Integer getsCount(Map<String,Object> paramMap);

	Integer getNttNo(String bbsGbn);

	Integer getAnswerLc(int nttNo);

	/**
	 * @param bbsGbn
	 * @param nttSj
	 * @param nttCn
	 * @param atchFileId
	 * @param ntcrId
	 * @param ntcrNm
	 */
	void regist(@Param("bbsGbn") String bbsGbn, @Param("nttNo") Integer nttNo, @Param("nttSj") String nttSj, @Param("nttCn") String nttCn, @Param("atchFileId") String atchFileId, @Param("ntcrId") String ntcrId, @Param("ntcrNm") String ntcrNm);

	/**
	 *
	 * @param bbsGbn
	 * @param nttNo
	 * @param nttSj
	 * @param nttCn
	 * @param parntscttNo
	 * @param answerLc
	 * @param atchFileId
	 * @param ntcrId
	 * @param ntcrNm
	 */
	void registRply(@Param("bbsGbn") String bbsGbn, @Param("nttNo") Integer nttNo, @Param("nttSj") String nttSj, @Param("nttCn") String nttCn
			, @Param("parntscttNo") Integer parntscttNo, @Param("answerLc") Integer answerLc
			, @Param("atchFileId") String atchFileId, @Param("ntcrId") String ntcrId, @Param("ntcrNm") String ntcrNm);

	/**
	 *
	 * @param bbsGbn
	 * @param nttNo
	 * @param nttSj
	 * @param nttCn
	 * @param ntcrId
	 * @param ntcrNm
	 */
	void regist(@Param("bbsGbn") String bbsGbn, @Param("nttNo") Integer nttNo, @Param("nttSj") String nttSj
			, @Param("nttCn") String nttCn, @Param("ntcrId") String ntcrId
			, @Param("ntcrNm") String ntcrNm);

	public Map<String, Object> getByNttId(String nttId);

	public Map<String, Object> getByNttNo(Map<String,Object> paramMap);

	public void updateRdcntByNttId(String nttId);

	public void updateAnswerByNttId(String nttId);

	public void deleteByNttId(String nttId);

	public void update(@Param("nttId") String nttId, @Param("nttSj") String nttSj
			, @Param("nttCn") String nttCn, @Param("atchFileId") String atchFileId
			, @Param("ntcrId") String ntcrId, @Param("ntcrNm") String ntcrNm);

	public List<Map<String,Object>> getsLately3();
}
