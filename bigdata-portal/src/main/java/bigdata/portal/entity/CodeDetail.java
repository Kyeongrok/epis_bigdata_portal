package bigdata.portal.entity;

import java.util.Date;

import kr.co.ucsit.core.CsVO;

@SuppressWarnings("serial")
public class CodeDetail extends CsVO {
	private String codeId;
	private String code;
	private String codeNm;
	private String codeDc;
	private String codeVal;
	private Integer codeSort;
	private String codeUseAt;
	private Date codeRegistDttm;
	private String codeRegistId;
	private Date codeUpdateDttm;
	private String codeUpdateId;

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeNm() {
		return codeNm;
	}

	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}

	public String getCodeDc() {
		return codeDc;
	}

	public void setCodeDc(String codeDc) {
		this.codeDc = codeDc;
	}

	public String getCodeVal() {
		return codeVal;
	}

	public void setCodeVal(String codeVal) {
		this.codeVal = codeVal;
	}

	public Integer getCodeSort() {
		return codeSort;
	}

	public void setCodeSort(Integer codeSort) {
		this.codeSort = codeSort;
	}

	public String getCodeUseAt() {
		return codeUseAt;
	}

	public void setCodeUseAt(String codeUseAt) {
		this.codeUseAt = codeUseAt;
	}

	public Date getCodeRegistDttm() {
		return codeRegistDttm;
	}

	public void setCodeRegistDttm(Date codeRegistDttm) {
		this.codeRegistDttm = codeRegistDttm;
	}

	public String getCodeRegistId() {
		return codeRegistId;
	}

	public void setCodeRegistId(String codeRegistId) {
		this.codeRegistId = codeRegistId;
	}

	public Date getCodeUpdateDttm() {
		return codeUpdateDttm;
	}

	public void setCodeUpdateDttm(Date codeUpdateDttm) {
		this.codeUpdateDttm = codeUpdateDttm;
	}

	public String getCodeUpdateId() {
		return codeUpdateId;
	}

	public void setCodeUpdateId(String codeUpdateId) {
		this.codeUpdateId = codeUpdateId;
	}

}
