package bigdata.portal.dmm.entity;

public class FdmorgMap {

	private String txnm;
	private String sysTo;
	private String sysFrom;

	public String getTxnm() {
		return txnm;
	}

	public void setTxnm(String txnm) {
		this.txnm = txnm;
	}

	public String getSysTo() {
		return sysTo;
	}

	public void setSysTo(String sysTo) {
		this.sysTo = sysTo;
	}

	public String getSysFrom() {
		return sysFrom;
	}

	public void setSysFrom(String sysFrom) {
		this.sysFrom = sysFrom;
	}

	@Override
	public String toString() {
		return "FdmorgMap [txnm=" + txnm + ", sysTo=" + sysTo + ", sysFrom=" + sysFrom + "]";
	}

}
