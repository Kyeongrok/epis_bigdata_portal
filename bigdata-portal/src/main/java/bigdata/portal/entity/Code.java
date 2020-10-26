package bigdata.portal.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

public class Code {
	private String codeId;
	private String codeIdNm;
	private String codeIdDc;
	private String codeIdUseAt;
	private Date codeIdRegistDttm;
	private String codeIdRegistId;
	private Date codeIdUpdateDttm;
	private String codeIdUpdateId;

	private Map<String, CodeDetail> codeDetail = new HashMap<String, CodeDetail>();

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeIdNm() {
		return codeIdNm;
	}

	public void setCodeIdNm(String codeIdNm) {
		this.codeIdNm = codeIdNm;
	}

	public String getCodeIdDc() {
		return codeIdDc;
	}

	public void setCodeIdDc(String codeIdDc) {
		this.codeIdDc = codeIdDc;
	}

	public String getCodeIdUseAt() {
		return codeIdUseAt;
	}

	public void setCodeIdUseAt(String codeIdUseAt) {
		this.codeIdUseAt = codeIdUseAt;
	}

	public Date getCodeIdRegistDttm() {
		return codeIdRegistDttm;
	}

	public void setCodeIdRegistDttm(Date codeIdRegistDttm) {
		this.codeIdRegistDttm = codeIdRegistDttm;
	}

	public String getCodeIdRegistId() {
		return codeIdRegistId;
	}

	public void setCodeIdRegistId(String codeIdRegistId) {
		this.codeIdRegistId = codeIdRegistId;
	}

	public Date getCodeIdUpdateDttm() {
		return codeIdUpdateDttm;
	}

	public void setCodeIdUpdateDttm(Date codeIdUpdateDttm) {
		this.codeIdUpdateDttm = codeIdUpdateDttm;
	}

	public String getCodeIdUpdateId() {
		return codeIdUpdateId;
	}

	public void setCodeIdUpdateId(String codeIdUpdateId) {
		this.codeIdUpdateId = codeIdUpdateId;
	}

	public Map<String, CodeDetail> getCodeDetail() {
		return codeDetail;
	}

	public void setCodeDetail(Map<String, CodeDetail> codeDetail) {
		this.codeDetail = codeDetail;
	}

	public CodeDetail getCodeDetail(String key) {
		return this.codeDetail.get(key);
	}

	public void putCodeDetail(CodeDetail codeDetail) {
		this.codeDetail.put(codeDetail.getCode(), codeDetail);
	}
}
