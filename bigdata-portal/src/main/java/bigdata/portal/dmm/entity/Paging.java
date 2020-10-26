package bigdata.portal.dmm.entity;

import java.util.ArrayList;

public class Paging {
	
	private int limitPrintNum = 0;  
	private int total = 0;
	
	public Paging(int limitPrintNum, int total) {
		this.limitPrintNum = limitPrintNum;
		this.total = total;
	}
	
	// 79
	public String makePaging(int offset, String pageType, int page) {
		StringBuffer aTag = new StringBuffer();
		
		int idx = ((page-1) / limitPrintNum) * limitPrintNum;
		int prevPage = idx;
		int nextPage = idx + limitPrintNum + 1;
		double max = (double)this.total / (double)limitPrintNum;
		boolean hidePrev = false;
		boolean hideNext = false;
		
		int end = idx + limitPrintNum;

		if(prevPage <= 0) {
			prevPage = 1;
			
		}
		
		if(nextPage > (int)Math.ceil(max)) {
			nextPage = (int)Math.ceil(max);
			
		}
		
		if(end > (int)Math.ceil(max)) {
			end = (int)Math.ceil(max);
		}
		
		if(!hidePrev) {
			String prev = "<a class=\"pre\" href=\"#\" onClick=\"paging('"+prevPage+"', '"+pageType+"')\"></a>";
			aTag.append(prev);
		}
		aTag.append("<span class=\"num\">");
		for (int i = idx; i < end; i++) {
			
			
			if(page-1 == i) {				
				aTag.append("<strong>"+(i+1)+"</strong>");
				
			} else {
				aTag.append("<a href=\"#\" onClick=\"paging('"+(i+1)+"', '"+pageType+"')\">"+(i+1)+"</a>");
			}
			
		}
		aTag.append("</span>");
		
		if(!hideNext) {
			String next = "<a class=\"next\" href=\"#\" onClick=\"paging('"+nextPage+"', '"+pageType+"')\"></a>";		
			aTag.append(next);
		}

		return aTag.toString();

	}
	
}
