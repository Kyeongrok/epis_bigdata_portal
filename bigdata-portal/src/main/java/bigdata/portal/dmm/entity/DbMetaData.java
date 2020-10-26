package bigdata.portal.dmm.entity;

import java.util.Date;

public class DbMetaData {
	
	private String dblistCode;
	private String metaDbNm;
	private String metaJson;
	private String metaErdFileId;
	private Date frstRegistPnttm;
	private String frstRegisterId;
	private Date lastUpdtPnttm;
	private String lastUpdustId;
	private String syslstCode;
	
	public String getSyslstCode() {
		return syslstCode;
	}
	public void setSyslstCode(String syslstCode) {
		this.syslstCode = syslstCode;
	}
	public String getDblistCode() {
		return dblistCode;
	}
	public void setDblistCode(String dblistCode) {
		this.dblistCode = dblistCode;
	}
	public String getMetaDbNm() {
		return metaDbNm;
	}
	public void setMetaDbNm(String metaDbNm) {
		this.metaDbNm = metaDbNm;
	}
	public String getMetaJson() {
		return metaJson;
	}
	public void setMetaJson(String metaJson) {
		this.metaJson = metaJson;
	}
	public String getMetaErdFileId() {
		return metaErdFileId;
	}
	public void setMetaErdFileId(String metaErdFileId) {
		this.metaErdFileId = metaErdFileId;
	}
	public Date getFrstRegistPnttm() {
		return frstRegistPnttm;
	}
	public void setFrstRegistPnttm(Date frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}
	public String getFrstRegisterId() {
		return frstRegisterId;
	}
	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}
	public Date getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}
	public void setLastUpdtPnttm(Date lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}
	public String getLastUpdustId() {
		return lastUpdustId;
	}
	public void setLastUpdustId(String lastUpdustId) {
		this.lastUpdustId = lastUpdustId;
	}
	@Override
	public String toString() {
		return "DbMetaData [dblistCode=" + dblistCode + ", metaDbNm=" + metaDbNm + ", metaJson=" + metaJson
				+ ", metaErdFileId=" + metaErdFileId + ", frstRegistPnttm=" + frstRegistPnttm + ", frstRegisterId="
				+ frstRegisterId + ", lastUpdtPnttm=" + lastUpdtPnttm + ", lastUpdustId=" + lastUpdustId
				+ ", syslstCode=" + syslstCode + "]";
	}

	
	
	
	
	

}
