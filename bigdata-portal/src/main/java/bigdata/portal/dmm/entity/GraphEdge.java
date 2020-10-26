package bigdata.portal.dmm.entity;

public class GraphEdge implements Comparable<GraphEdge>{

	private int to;
	private int from;

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	@Override
	public String toString() {
		return "GrapeEdge [to=" + to + ", from=" + from + "]";
	}
	

	@Override
	public int compareTo(GraphEdge o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
