package bigdata.portal.dmm.entity;

public class SubCategory {

	private int code;
	private String name;

	public SubCategory(String name, int code) {
		this.name = name;
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SubCategory [code=" + code + ", name=" + name + "]";
	}

}