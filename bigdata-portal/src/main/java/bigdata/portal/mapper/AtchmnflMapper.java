/**
 * 
 */
package bigdata.portal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 첨부파일
 * @author hyunseongkil
 *
 */
@Mapper
public interface AtchmnflMapper {

	@Select({"SELECT	ATCH_FILE_ID AS atchFileId"
			+ "			,FILE_NO AS fileNo"
			+ "			,ORIGIN_FILE_NAME AS originFileName"
			+ "			,STRE_FILE_NAME AS streFileName"
			+ "			,CONTENT_TYPE AS contentType"
			+ "			,STRE_PATH_NAME AS strePathName"
			+ "			,FILE_SIZE AS fileSize"
			+ "			,FRST_REGIST_PNTTM AS frstRegistPnttm"
			+ "			,FRST_REGISTER_ID AS frstRegisterId"
			+ "	FROM	BDP_ATCHMNFL"
			+ "	WHERE	ATCH_FILE_ID = #{atchFileId}"})
	List<Map<String,Object>> getsByAtchFileId(String atchFileId);
	
	@Insert({"INSERT INTO BDP_ATCHMNFL ("
			+ "		ATCH_FILE_ID"
			+ "		,FILE_NO"
			+ "		,ORIGIN_FILE_NAME"
			+ "		,STRE_FILE_NAME"
			+ "		,STRE_PATH_NAME"
			+ "		,CONTENT_TYPE"
			+ "		,FILE_SIZE"
			+ "		,FRST_REGIST_PNTTM"
			+ "		,FRST_REGISTER_ID"
			+ "		,LAST_UPDT_PNTTM"
			+ "		,LAST_UPDUSR_ID"
			+ ") VALUES("
			+ "		#{atchFileId}"
			+ "		,#{fileNo}"
			+ "		,#{originFileName}"
			+ "		,#{streFileName}"
			+ "		,#{strePathName}"
			+ "		,#{contentType}"
			+ "		,#{fileSize}"
			+ "		,NOW()"
			+ "		,#{frstRegisterId}"
			+ "		,NOW()"
			+ "		,#{frstRegisterId}"
			+ ")"})
	void regist(@Param("atchFileId") String atchFileId, @Param("fileNo") String fileNo, @Param("originFileName") String originFileName, @Param("streFileName") String streFileName, @Param("strePathName") String strePathName, @Param("contentType") String contentType, @Param("fileSize") String fileSize, @Param("frstRegisterId") String frstRegisterId);
	
	
	@Delete({"DELETE FROM BDP_ATCHMNFL"
			+ "	WHERE	ATCH_FILE_ID = #{atchFileId}"
			+ "	AND	FILE_NO = #{fileNo}"})
	void delete(@Param("atchFileId") String atchFileId, @Param("fileNo") String fileNo);


}
