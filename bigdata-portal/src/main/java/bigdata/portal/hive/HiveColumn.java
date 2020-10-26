package bigdata.portal.hive;

public class HiveColumn {

	String name;

	// TINYINT, SMALLINT, INT/INTEGER, BIGINT, FLOAT, DOUBLE
	// DECIMAL default (10,0)
	// VARCHAR, STRING, CHAR(10)
	// TIMESTAMP, DATE
	String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
