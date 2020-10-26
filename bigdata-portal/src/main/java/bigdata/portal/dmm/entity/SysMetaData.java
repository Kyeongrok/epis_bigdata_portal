package bigdata.portal.dmm.entity;

import java.io.Serializable;
import java.util.Date;

public class SysMetaData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String syslstCode;
	private String metaSysNM;
	private String metaSysMemo;
	private String metaBuildYear;
	private String metaBuildCost;

	private String metaIntroMethod;
	private String metaAdvanceYear;
	private String metaOperStatus;
	private String metaMonthVisitors;
	private String metaMonthPageViews;
	private String metaUtilNation;
	private String metaUtilComp;
	private String metaUtilInst;
	private String metaUtilInInst;
	private String metaManagerDept;
	private String metaManagerName;
	private String metaManagerTel;
	private String metaManagerEmail;
	private String metaMaintenComp;
	private String metaMaintenName;
	private String metaMaintenTel;
	private String metaMaintenEmail;
	private String metaDBAccess;
	private String metaDBVol;
	private String metaDBRecord;
	private String metaDBGenType;
	private String metaDBMSName;
	private String metaQualityDiag;
	private String metaQualityIssue;
	private String metaQualityOrgan;
	private Date frstRegistPnttm;
	private String frstRegisterId;
	private Date lastUpdtPnttm;
	private String lastUpdusrId;
	
	private String metaKeyword;
	private String metaSubject;

	private String fdmorgName;
	private String fdmorg;
	
	private String publicDataLink;
	
	
	public String getPublicDataLink() {
		return publicDataLink;
	}

	public void setPublicDataLink(String publicDataLink) {
		this.publicDataLink = publicDataLink;
	}

	public String getMetaKeyword() {
		return metaKeyword;
	}

	public void setMetaKeyword(String metaKeyword) {
		this.metaKeyword = metaKeyword;
	}

	public String getMetaSubject() {
		return metaSubject;
	}

	public void setMetaSubject(String metaSubject) {
		this.metaSubject = metaSubject;
	}

	public String getFdmorgName() {
		return fdmorgName;
	}

	public void setFdmorgName(String fdmorgName) {
		this.fdmorgName = fdmorgName;
	}

	public String getFdmorg() {
		return fdmorg;
	}

	public void setFdmorg(String fdmorg) {
		this.fdmorg = fdmorg;
	}

	public String getSyslstCode() {
		return syslstCode;
	}

	public void setSyslstCode(String syslstCode) {
		this.syslstCode = syslstCode;
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

	public String getMetaBuildYear() {
		return metaBuildYear;
	}

	public void setMetaBuildYear(String metaBuildYear) {
		this.metaBuildYear = metaBuildYear;
	}

	public String getMetaBuildCost() {
		return metaBuildCost;
	}

	public void setMetaBuildCost(String metaBuildCost) {
		this.metaBuildCost = metaBuildCost;
	}

	public String getMetaIntroMethod() {
		return metaIntroMethod;
	}

	public void setMetaIntroMethod(String metaIntroMethod) {
		this.metaIntroMethod = metaIntroMethod;
	}

	public String getMetaAdvanceYear() {
		return metaAdvanceYear;
	}

	public void setMetaAdvanceYear(String metaAdvanceYear) {
		this.metaAdvanceYear = metaAdvanceYear;
	}

	public String getMetaOperStatus() {
		return metaOperStatus;
	}

	public void setMetaOperStatus(String metaOperStatus) {
		this.metaOperStatus = metaOperStatus;
	}

	public String getMetaMonthVisitors() {
		return metaMonthVisitors;
	}

	public void setMetaMonthVisitors(String metaMonthVisitors) {
		this.metaMonthVisitors = metaMonthVisitors;
	}

	public String getMetaMonthPageViews() {
		return metaMonthPageViews;
	}

	public void setMetaMonthPageViews(String metaMonthPageViews) {
		this.metaMonthPageViews = metaMonthPageViews;
	}

	public String getMetaUtilNation() {
		return metaUtilNation;
	}

	public void setMetaUtilNation(String metaUtilNation) {
		this.metaUtilNation = metaUtilNation;
	}

	public String getMetaUtilComp() {
		return metaUtilComp;
	}

	public void setMetaUtilComp(String metaUtilComp) {
		this.metaUtilComp = metaUtilComp;
	}

	public String getMetaUtilInst() {
		return metaUtilInst;
	}

	public void setMetaUtilInst(String metaUtilInst) {
		this.metaUtilInst = metaUtilInst;
	}

	public String getMetaUtilInInst() {
		return metaUtilInInst;
	}

	public void setMetaUtilInInst(String metaUtilInInst) {
		this.metaUtilInInst = metaUtilInInst;
	}

	public String getMetaManagerDept() {
		return metaManagerDept;
	}

	public void setMetaManagerDept(String metaManagerDept) {
		this.metaManagerDept = metaManagerDept;
	}

	public String getMetaManagerName() {
		return metaManagerName;
	}

	public void setMetaManagerName(String metaManagerName) {
		this.metaManagerName = metaManagerName;
	}

	public String getMetaManagerTel() {
		return metaManagerTel;
	}

	public void setMetaManagerTel(String metaManagerTel) {
		this.metaManagerTel = metaManagerTel;
	}

	public String getMetaManagerEmail() {
		return metaManagerEmail;
	}

	public void setMetaManagerEmail(String metaManagerEmail) {
		this.metaManagerEmail = metaManagerEmail;
	}

	public String getMetaMaintenComp() {
		return metaMaintenComp;
	}

	public void setMetaMaintenComp(String metaMaintenComp) {
		this.metaMaintenComp = metaMaintenComp;
	}

	public String getMetaMaintenName() {
		return metaMaintenName;
	}

	public void setMetaMaintenName(String metaMaintenName) {
		this.metaMaintenName = metaMaintenName;
	}

	public String getMetaMaintenTel() {
		return metaMaintenTel;
	}

	public void setMetaMaintenTel(String metaMaintenTel) {
		this.metaMaintenTel = metaMaintenTel;
	}

	public String getMetaMaintenEmail() {
		return metaMaintenEmail;
	}

	public void setMetaMaintenEmail(String metaMaintenEmail) {
		this.metaMaintenEmail = metaMaintenEmail;
	}

	public String getMetaDBAccess() {
		return metaDBAccess;
	}

	public void setMetaDBAccess(String metaDBAccess) {
		this.metaDBAccess = metaDBAccess;
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

	public String getMetaDBGenType() {
		return metaDBGenType;
	}

	public void setMetaDBGenType(String metaDBGenType) {
		this.metaDBGenType = metaDBGenType;
	}

	public String getMetaDBMSName() {
		return metaDBMSName;
	}

	public void setMetaDBMSName(String metaDBMSName) {
		this.metaDBMSName = metaDBMSName;
	}

	public String getMetaQualityDiag() {
		return metaQualityDiag;
	}

	public void setMetaQualityDiag(String metaQualityDiag) {
		this.metaQualityDiag = metaQualityDiag;
	}

	public String getMetaQualityIssue() {
		return metaQualityIssue;
	}

	public void setMetaQualityIssue(String metaQualityIssue) {
		this.metaQualityIssue = metaQualityIssue;
	}

	public String getMetaQualityOrgan() {
		return metaQualityOrgan;
	}

	public void setMetaQualityOrgan(String metaQualityOrgan) {
		this.metaQualityOrgan = metaQualityOrgan;
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

	public String getLastUpdusrId() {
		return lastUpdusrId;
	}

	public void setLastUpdusrId(String lastUpdusrId) {
		this.lastUpdusrId = lastUpdusrId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SysMetaData [syslstCode=" + syslstCode + ", metaSysNM=" + metaSysNM + ", metaSysMemo=" + metaSysMemo
				+ ", metaBuildYear=" + metaBuildYear + ", metaBuildCost=" + metaBuildCost + ", metaIntroMethod="
				+ metaIntroMethod + ", metaAdvanceYear=" + metaAdvanceYear + ", metaOperStatus=" + metaOperStatus
				+ ", metaMonthVisitors=" + metaMonthVisitors + ", metaMonthPageViews=" + metaMonthPageViews
				+ ", metaUtilNation=" + metaUtilNation + ", metaUtilComp=" + metaUtilComp + ", metaUtilInst="
				+ metaUtilInst + ", metaUtilInInst=" + metaUtilInInst + ", metaManagerDept=" + metaManagerDept
				+ ", metaManagerName=" + metaManagerName + ", metaManagerTel=" + metaManagerTel + ", metaManagerEmail="
				+ metaManagerEmail + ", metaMaintenComp=" + metaMaintenComp + ", metaMaintenName=" + metaMaintenName
				+ ", metaMaintenTel=" + metaMaintenTel + ", metaMaintenEmail=" + metaMaintenEmail + ", metaDBAccess="
				+ metaDBAccess + ", metaDBVol=" + metaDBVol + ", metaDBRecord=" + metaDBRecord + ", metaDBGenType="
				+ metaDBGenType + ", metaDBMSName=" + metaDBMSName + ", metaQualityDiag=" + metaQualityDiag
				+ ", metaQualityIssue=" + metaQualityIssue + ", metaQualityOrgan=" + metaQualityOrgan
				+ ", frstRegistPnttm=" + frstRegistPnttm + ", frstRegisterId=" + frstRegisterId + ", lastUpdtPnttm="
				+ lastUpdtPnttm + ", lastUpdusrId=" + lastUpdusrId + ", metaKeyword=" + metaKeyword + ", metaSubject="
				+ metaSubject + ", fdmorgName=" + fdmorgName + ", fdmorg=" + fdmorg + ", publicDataLink="
				+ publicDataLink + "]";
	}



}
