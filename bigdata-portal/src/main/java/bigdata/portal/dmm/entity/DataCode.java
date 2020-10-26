package bigdata.portal.dmm.entity;

public class DataCode {

	private String codeType = null;
	private String code = null;

	public DataCode(String codeType, String code) {
		this.codeType = codeType;
		this.code = code;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeName) {
		this.codeType = codeName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "DataCode [codeType=" + codeType + ", code=" + code + "]";
	}

}
