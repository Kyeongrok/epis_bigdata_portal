package bigdata.portal.dmm.entity;

public class ErdImage {

	private String syslst;
	private String dblist;
	private String erdImgLogic;
	private String erdImgPhysical;
	private String metaSysNm;
	
	

	public String getMetaSysNm() {
		return metaSysNm;
	}

	public void setMetaSysNm(String metaSysNm) {
		this.metaSysNm = metaSysNm;
	}

	public String getSyslst() {
		return syslst;
	}

	public void setSyslst(String syslst) {
		this.syslst = syslst;
	}

	public String getDblist() {
		return dblist;
	}

	public void setDblist(String dblist) {
		this.dblist = dblist;
	}

	public String getErdImgLogic() {
		return erdImgLogic;
	}

	public void setErdImgLogic(String erdImgLogic) {
		this.erdImgLogic = erdImgLogic;
	}

	public String getErdImgPhysical() {
		return erdImgPhysical;
	}

	public void setErdImgPhysical(String erdImgPhysical) {
		this.erdImgPhysical = erdImgPhysical;
	}

	@Override
	public String toString() {
		return "ErdImage [syslst=" + syslst + ", dblist=" + dblist + ", erdImgLogic=" + erdImgLogic
				+ ", erdImgPhysical=" + erdImgPhysical + ", metaSysNm=" + metaSysNm + "]";
	}

	
	

}
