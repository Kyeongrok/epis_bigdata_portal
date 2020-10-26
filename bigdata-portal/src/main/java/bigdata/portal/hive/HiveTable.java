package bigdata.portal.hive;

import java.io.Serializable;
import java.util.ArrayList;

public class HiveTable implements Serializable {
	private static final long serialVersionUID = 1L;
	
	String tableId;
	ArrayList<HiveColumn> columns;
	ArrayList<String> partitions;
	ArrayList<String> index;

	// EXTERNAL
	String tableLocation;

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public ArrayList<HiveColumn> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<HiveColumn> columns) {
		this.columns = columns;
	}

	public ArrayList<String> getPartitions() {
		return partitions;
	}

	public void setPartitions(ArrayList<String> partitions) {
		this.partitions = partitions;
	}

	public ArrayList<String> getIndex() {
		return index;
	}

	public void setIndex(ArrayList<String> index) {
		this.index = index;
	}

	public String getTableLocation() {
		return tableLocation;
	}

	public void setTableLocation(String tableLocation) {
		this.tableLocation = tableLocation;
	}
}
