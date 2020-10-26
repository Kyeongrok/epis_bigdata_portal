package bigdata.portal.dmm.entity;

public class MenuVO {
	private String type;
	private String code;
	private String category;
	private String menuName;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Override
	public String toString() {
		return "MenuVO [type=" + type + ", code=" + code + ", category=" + category + ", menuName=" + menuName + "]";
	}

}
