package bigdata.portal.dmm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author TheIMC 빅데이터R팀 허현범
 * @version 1.0
 * @Class Name : FdmMetainfoVO.java
 * @Description : 메타데이터 정보를 저장하는 VO 클래스
 * @Modification Information
 *               <p>
 *               수정일 수정자 수정내용 ------- ------- ------------------- 2017.08.24.
 *               허현범 2017.10.10. DHKim VO클래스 get/set 추가
 * @see
 * @since 2017. 8. 24.
 */

@SuppressWarnings("serial")
public class FdmMetainfoVO implements Serializable {

	private String txnm = "";
	private String txnmCode = "";
	private String fdmorgNm = "";
	private String sysNm = "";
	private String dbNm = "";
	private String sysCode = "";
	private String fdmorgCode = "";
	private int nodeValue;
	private String dbCode = "";
	private String dbType;
	private String publicDataLink;

	public String getTxnm() {
		return txnm;
	}

	public String getPublicDataLink() {
		return publicDataLink;
	}

	public void setPublicDataLink(String publicDataLink) {
		this.publicDataLink = publicDataLink;
	}

	public String getDbCode() {
		return dbCode;
	}

	public void setDbCode(String dbCode) {
		this.dbCode = dbCode;
	}

	public String getDbNm() {
		return dbNm;
	}

	public void setDbNm(String dbNm) {
		this.dbNm = dbNm;
	}

	public String getTxnmCode() {
		return txnmCode;
	}

	public void setTxnmCode(String txnmCode) {
		this.txnmCode = txnmCode;
	}

	public void setTxnm(String txnm) {
		this.txnm = txnm;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public int getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(int nodeValue) {
		this.nodeValue = nodeValue;
	}

	public String getFdmorgNm() {
		return fdmorgNm;
	}

	public void setFdmorgNm(String fdmorgNm) {
		this.fdmorgNm = fdmorgNm;
	}

	public String getSysNm() {
		return sysNm;
	}

	public void setSysNm(String sysNm) {
		this.sysNm = sysNm;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getFdmorgCode() {
		return fdmorgCode;
	}

	public void setFdmorgCode(String fdmorgCode) {
		this.fdmorgCode = fdmorgCode;
	}

	@Override
	public String toString() {
		return "FdmMetainfoVO [txnm=" + txnm + ", txnmCode=" + txnmCode + ", fdmorgNm=" + fdmorgNm + ", sysNm=" + sysNm
				+ ", sysCode=" + sysCode + ", fdmorgCode=" + fdmorgCode + ", nodeValue=" + nodeValue + ", dbType="
				+ dbType + "]";
	}

}
