package bigdata.portal.dmm.entity;

public class SearchDbInfo {
	
	private String txnmCodeNm;
	private String txnmCode;
	private String fdmorgCodeNm;
	private String fdmorgCode;
	private String syslstCodeNm;
	private String syslstCode;
	private String dbTypeCodeNm;
	
	private String dbListCode;
	private String metaDbNM;
	private String metaJSON;
	private String metaErdFileID;
	private String frstRegistPnttm;
	private String frstRegisterID;
	private String lastUpdtPnttm;
	private String lastUpdustID;
	

	
	
	public String getDbTypeCodeNm() {
		return dbTypeCodeNm;
	}

	public void setDbTypeCodeNm(String dbTypeCodeNm) {
		this.dbTypeCodeNm = dbTypeCodeNm;
	}

	public String getTxnmCodeNm() {
		return txnmCodeNm;
	}

	public void setTxnmCodeNm(String txnmCodeNm) {
		this.txnmCodeNm = txnmCodeNm;
	}

	public String getTxnmCode() {
		return txnmCode;
	}

	public void setTxnmCode(String txnmCode) {
		this.txnmCode = txnmCode;
	}

	public String getFdmorgCodeNm() {
		return fdmorgCodeNm;
	}

	public void setFdmorgCodeNm(String fdmorgCodeNm) {
		this.fdmorgCodeNm = fdmorgCodeNm;
	}

	public String getFdmorgCode() {
		return fdmorgCode;
	}

	public void setFdmorgCode(String fdmorgCode) {
		this.fdmorgCode = fdmorgCode;
	}

	public String getSyslstCodeNm() {
		return syslstCodeNm;
	}

	public void setSyslstCodeNm(String syslstCodeNm) {
		this.syslstCodeNm = syslstCodeNm;
	}

	public String getSyslstCode() {
		return syslstCode;
	}

	public void setSyslstCode(String syslstCode) {
		this.syslstCode = syslstCode;
	}

	public String getDbListCode() {
		return dbListCode;
	}

	public void setDbListCode(String dbListCode) {
		this.dbListCode = dbListCode;
	}

	public String getMetaDbNM() {
		return metaDbNM;
	}

	public void setMetaDbNM(String metaDbNM) {
		this.metaDbNM = metaDbNM;
	}

	public String getMetaJSON() {
		return metaJSON;
	}

	public void setMetaJSON(String metaJSON) {
		this.metaJSON = metaJSON;
	}

	public String getMetaErdFileID() {
		return metaErdFileID;
	}

	public void setMetaErdFileID(String metaErdFileID) {
		this.metaErdFileID = metaErdFileID;
	}

	public String getFrstRegistPnttm() {
		return frstRegistPnttm;
	}

	public void setFrstRegistPnttm(String frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}

	public String getFrstRegisterID() {
		return frstRegisterID;
	}

	public void setFrstRegisterID(String frstRegisterID) {
		this.frstRegisterID = frstRegisterID;
	}

	public String getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}

	public void setLastUpdtPnttm(String lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}

	public String getLastUpdustID() {
		return lastUpdustID;
	}

	public void setLastUpdustID(String lastUpdustID) {
		this.lastUpdustID = lastUpdustID;
	}

	@Override
	public String toString() {
		return "SearchDbInfo [dbListCode=" + dbListCode + ", metaDbNM=" + metaDbNM + ", metaJSON=" + metaJSON
				+ ", metaErdFileID=" + metaErdFileID + ", frstRegistPnttm=" + frstRegistPnttm + ", frstRegisterID="
				+ frstRegisterID + ", lastUpdtPnttm=" + lastUpdtPnttm + ", lastUpdustID=" + lastUpdustID + "]";
	}

}
