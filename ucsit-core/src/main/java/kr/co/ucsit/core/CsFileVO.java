/**
 * 
 */
package kr.co.ucsit.core;

import java.nio.file.Path;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * File vo
 * @author cs1492
 * @date   2018. 2. 1.
 *
 */
public class CsFileVO extends CsVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 크기
	 */
	private String size;
	/**
	 * 원본 파일 명
	 */
	private String originFileName;
	/**
	 * 저장 파일 명
	 */
	private String streFileName;
	/**
	 * content type
	 */
	private String contentType;
	
	/**
	 * 저장 파일의 중간 경로 
	 */
	private String subPath="";
	
	/**
	 * 저장 경로 명
	 */
	private String strePathName;
	
	/**
	 * 전체 경로. 경로 + 파일명
	 */
	private Path fullPath;
	
	private byte[] fileData;
	
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getOriginFileName() {
		return originFileName;
	}
	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}
	public String getStreFileName() {
		return streFileName;
	}
	public void setStreFileName(String streFileName) {
		this.streFileName = streFileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getSubPath() {
		return subPath;
	}
	public void setSubPath(String subPath) {
		this.subPath = subPath;
	}
	public byte[] getFileData() {
		return fileData;
	}
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	public Path getFullPath() {
		return fullPath;
	}
	public void setFullPath(Path fullPath) {
		this.fullPath = fullPath;
	}
	public String getStrePathName() {
		return strePathName;
	}
	public void setStrePathName(String strePathName) {
		this.strePathName = strePathName;
	}
	
	
}
