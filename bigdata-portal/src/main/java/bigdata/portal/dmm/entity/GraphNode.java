package bigdata.portal.dmm.entity;

import java.util.HashMap;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

public class GraphNode implements Comparable<GraphNode> {

	private int id;
	private String label;
	private String group;
	private String fdmorgCode = null;
	private String fdmorgNm = null;
	private String sysCode = null;
	private String dbCode = null;
	private String txnm = null;
	private String txnmCode = null;
	private int size;
	private String dbType = null;
	private String tableCode = null;
	private String publicDataLink = null;
	private String tableDesc = null;
	private int x = 300;
	private int y = 0;

	public GraphNode() {

	};
	
	
	public int getX() {
		return x;
	}



	public void setX(int x) {
		this.x = x;
	}




	public int getY() {
		return y;
	}




	public void setY(int y) {
		this.y = y;
	}




	public String getTableDesc() {
		return tableDesc;
	}



	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}



	public String getPublicDataLink() {
		return publicDataLink;
	}

	public void setPublicDataLink(String publicDataLink) {
		this.publicDataLink = publicDataLink;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public int getSize() {
		return size;
	}

	public String getTxnmCode() {
		return txnmCode;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String sysGroupType() {

		return null;

	}

	public void setSize(int value) {
		this.size = value;

		if (value == 4) {
			size = 105;
		} else if (value == 3) {
			size = 90;
		} else if (value == 2) {
			size = 75;
		} else {
			size = 60;
		}

		this.group = "system" + this.txnmCode + this.dbType;

	}

	public String getFdmorgNm() {
		return fdmorgNm;
	}

	public void setFdmorgNm(String fdmorgNm) {
		this.fdmorgNm = fdmorgNm;
	}

	public String getTxnm() {
		return txnm;
	}

	public void setTxnm(String txnm) {
		this.txnm = txnm;
	}

	public GraphNode(String label, int id, int group) {
		super();		
		init(label, id, group);
	}
	
	/**
	 * 초기화 함수
	 * @param label
	 * @param id
	 * @param group
	 */
	private void init(String label, int id, int group) {
		this.label = label;
		this.id = id;
		this.setGroup(group);
	}

	public String getDbCode() {
		return dbCode;
	}

	public void setDbCode(String dbCode) {
		this.dbCode = dbCode;
	}

	public String getFdmorgCode() {
		return fdmorgCode;
	}

	public void setFdmorgCode(String fdmorgCode) {
		this.fdmorgCode = fdmorgCode;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getGroup() {
		return group;
	}

	public void setOrgGroup() {
		group = "org" + this.fdmorgCode;
		size = 33;
	}

	public void setGroup(int groupFlag) {
		switch (groupFlag) {
		case 5:
			group = "table";
			size = 40;
			break;
		case 4:
			group = "database";
			size = 40;
			break;
		case 3:
			group = "system";
			break;
		case 2:
			group = "org";
			break;
		case 1:
			group = "source";
			break;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(label).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		GraphNode node = (GraphNode) obj;
		if (obj instanceof GraphNode) {
			return new EqualsBuilder().append(label, node.getLabel()).isEquals();
		}
		return false;
	}

	@Override
	public int compareTo(GraphNode o) {
		// TODO Auto-generated method stub
		// return label.compareTo(o.getLabel());

		return id - o.getId();

	}

	public void setTxnmCode(String txnmCode) {
		this.txnmCode = txnmCode;

	}

	@Override
	public String toString() {
		return "GrapeNode [id=" + id + ", label=" + label + ", group=" + group + ", fdmorgCode=" + fdmorgCode
				+ ", fdmorgNm=" + fdmorgNm + ", sysCode=" + sysCode + ", dbCode=" + dbCode + ", txnm=" + txnm
				+ ", txnmCode=" + txnmCode + ", size=" + size + ", dbType=" + dbType + ", tableCode=" + tableCode
				+ ", publicDataLink=" + publicDataLink + "]";
	}

}
