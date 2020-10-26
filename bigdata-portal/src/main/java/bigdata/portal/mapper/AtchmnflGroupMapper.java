/**
 * 
 */
package bigdata.portal.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 첨부파일 그룹
 * @author hyunseongkil
 *
 */
@Mapper
public interface AtchmnflGroupMapper {

	@Select({"SELECT	ATCH_FILE_ID AS atchFileId"
			+ "			,FRST_REGIST_PNTTM AS frstRegistPnttm"
			+ "			,FRST_REGISTER_ID AS frstRegisterId"
			+ "	FROM	BDP_ATCHMNFL_GROUP"
			+ "	WHERE	ATCH_FILE_ID = #{atchFileId}"})
	Map<String,Object> get(String atchFileId);
	
	@Insert({"INSERT INTO BDP_ATCHMNFL_GROUP ("
			+ "	ATCH_FILE_ID"
			+ "	,FRST_REGIST_PNTTM"
			+ "	,FRST_REGISTER_ID"
			+ ") VALUES("
			+ "	#{atchFileId}"
			+ "	,NOW()"
			+ "	,#{frstRegisterId}"
			+ ")"})
	void regist(@Param("atchFileId") String atchFileId, @Param("frstRegisterId") String frstRegisterId);
}
