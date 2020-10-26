/**
 * 
 */
package bigdata.portal.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import kr.co.ucsit.core.CsVO;

/**
 * @author hyunseongkil
 *
 */
@SuppressWarnings("serial")
public class CtvtArSttemnt extends CsVO {

	private String STTEMNT_AR;
	private String MNNM;
	private String  PRDLST_NM;
	private String  REGSTR_SE;
	private String  cllctdt;
	private String  LI_NM;
	private String  STTEMNT_YEAR;
	private String  LNBR;
//	private String  @timestamp;
	private String  PNU;
	private String  EMD_NM;
	private String  SIGNGU_NM;
	private String  PNU_CNVR_SE;
	private String  SLNO;
	private String  CTPRVN_NM;
	
	
	
	public String getSTTEMNT_AR() {
		return STTEMNT_AR;
	}
	public void setSTTEMNT_AR(String sTTEMNT_AR) {
		STTEMNT_AR = sTTEMNT_AR;
	}
	public String getMNNM() {
		return MNNM;
	}
	public void setMNNM(String mNNM) {
		MNNM = mNNM;
	}
	public String getPRDLST_NM() {
		return PRDLST_NM;
	}
	public void setPRDLST_NM(String pRDLST_NM) {
		PRDLST_NM = pRDLST_NM;
	}
	public String getREGSTR_SE() {
		return REGSTR_SE;
	}
	public void setREGSTR_SE(String rEGSTR_SE) {
		REGSTR_SE = rEGSTR_SE;
	}
	public String getCllctdt() {
		return cllctdt;
	}
	public void setCllctdt(String cllctdt) {
		this.cllctdt = cllctdt;
	}
	public String getLI_NM() {
		return LI_NM;
	}
	public void setLI_NM(String lI_NM) {
		LI_NM = lI_NM;
	}
	public String getSTTEMNT_YEAR() {
		return STTEMNT_YEAR;
	}
	public void setSTTEMNT_YEAR(String sTTEMNT_YEAR) {
		STTEMNT_YEAR = sTTEMNT_YEAR;
	}
	public String getLNBR() {
		return LNBR;
	}
	public void setLNBR(String lNBR) {
		LNBR = lNBR;
	}
	public String getPNU() {
		return PNU;
	}
	public void setPNU(String pNU) {
		PNU = pNU;
	}
	public String getEMD_NM() {
		return EMD_NM;
	}
	public void setEMD_NM(String eMD_NM) {
		EMD_NM = eMD_NM;
	}
	public String getSIGNGU_NM() {
		return SIGNGU_NM;
	}
	public void setSIGNGU_NM(String sIGNGU_NM) {
		SIGNGU_NM = sIGNGU_NM;
	}
	public String getPNU_CNVR_SE() {
		return PNU_CNVR_SE;
	}
	public void setPNU_CNVR_SE(String pNU_CNVR_SE) {
		PNU_CNVR_SE = pNU_CNVR_SE;
	}
	public String getSLNO() {
		return SLNO;
	}
	public void setSLNO(String sLNO) {
		SLNO = sLNO;
	}
	public String getCTPRVN_NM() {
		return CTPRVN_NM;
	}
	public void setCTPRVN_NM(String cTPRVN_NM) {
		CTPRVN_NM = cTPRVN_NM;
	}
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	
}
