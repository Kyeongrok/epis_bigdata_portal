package bigdata.portal.hive;

import java.io.Serializable;

public class HiveData implements Serializable {
	private static final long serialVersionUID = 1L;

	final String extensions = ".hive";

	String tableId;
	String partition;
	String fileName;
	boolean overwrite;
	String data;

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getPartition() {
		return partition;
	}

	public void setPartition(String partition) {
		this.partition = partition;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isOverwrite() {
		return overwrite;
	}

	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getExtensions() {
		return extensions;
	}
}
