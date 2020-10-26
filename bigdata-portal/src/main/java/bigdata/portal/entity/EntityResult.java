package bigdata.portal.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings({ "rawtypes" })
@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntityResult {
	long totalCnt = 0;
	long startRow = 0;
	long endRow = 0;
	String desc = "";
	Result result = new Result();
	EntityList items = null;

	public long getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(long totalCnt) {
		this.totalCnt = totalCnt;
	}

	public long getStartRow() {
		return startRow;
	}

	public void setStartRow(long startRow) {
		this.startRow = startRow;
	}

	public long getEndRow() {
		return endRow;
	}

	public void setEndRow(long endRow) {
		this.endRow = endRow;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public EntityList getItems() {
		return items;
	}

	public void setItems(EntityList items) {
		this.items = items;
	}
}

class Result {
	String message = "정상 처리되었습니다.";
	String code = "INFO-000";
}
