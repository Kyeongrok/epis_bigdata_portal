package bigdata.portal.dmm.entity;

public class SearchSysInfo {
	
	private String sysCode;
	private String metaSysNM;
	private String metaSysMemo;	
	private String metaDBMSName;
	private String metaDBVol;
	private String metaDBRecord;
	private String metaManagerTel;
	private String fdmorgCode;
	private String fdmorgNM;
	private String metaSubject;
	private String publicDataLink;
	
	
	public String getPublicDataLink() {
		return publicDataLink;
	}
	public void setPublicDataLink(String publicDataLink) {
		this.publicDataLink = publicDataLink;
	}
	public String getMetaSubject() {
		return metaSubject;
	}
	public void setMetaSubject(String metaSubject) {
		this.metaSubject = metaSubject;
	}
	public String getFdmorgCode() {
		return fdmorgCode;
	}
	public void setFdmorgCode(String fdmorgCode) {
		this.fdmorgCode = fdmorgCode;
	}
	public String getFdmorgNM() {
		return fdmorgNM;
	}
	public void setFdmorgNM(String fdmorgNM) {
		this.fdmorgNM = fdmorgNM;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public String getMetaDBMSName() {
		return metaDBMSName;
	}
	public void setMetaDBMSName(String metaDBMSName) {
		this.metaDBMSName = metaDBMSName;
	}
	public String getMetaDBVol() {
		return metaDBVol;
	}
	public void setMetaDBVol(String metaDBVol) {
		this.metaDBVol = metaDBVol;
	}
	public String getMetaDBRecord() {
		return metaDBRecord;
	}
	public void setMetaDBRecord(String metaDBRecord) {
		this.metaDBRecord = metaDBRecord;
	}
	public String getMetaManagerTel() {
		return metaManagerTel;
	}
	public void setMetaManagerTel(String metaManagerTel) {
		this.metaManagerTel = metaManagerTel;
	}
	public String getMetaSysNM() {
		return metaSysNM;
	}
	public void setMetaSysNM(String metaSysNM) {
		this.metaSysNM = metaSysNM;
	}
	public String getMetaSysMemo() {
		return metaSysMemo;
	}
	public void setMetaSysMemo(String metaSysMemo) {
		this.metaSysMemo = metaSysMemo;
	}
	
	@Override
	public String toString() {
		return "SearchSysInfo [metaSysNM=" + metaSysNM + ", metaSysMemo=" + metaSysMemo + ", metaDBMSName="
				+ metaDBMSName + ", metaDBVol=" + metaDBVol + ", metaDBRecord=" + metaDBRecord + ", metaManagerTel="
				+ metaManagerTel + "]";
	}
	
	
}
