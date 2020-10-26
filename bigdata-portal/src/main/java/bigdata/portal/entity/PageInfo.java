package bigdata.portal.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 전자정부 프레임워크 페이지정보에 파라미터 추가
 *
 * @author THEIMC JHY
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2018. 10. 25.     JHY          최초 생성
 *      </pre>
 * 
 * @since 2018. 10. 25.
 */
public class PageInfo extends PaginationInfo {
	String url = "?";
	int mobilePageSize = 5;

	public PageInfo() {
		super();
		url = getRequestUrl();
	}
	
	public int getArticleNo() {
		return getTotalRecordCount() - ((getCurrentPageNo() - 1) * getRecordCountPerPage());
	}	
	
	public int getArticleNoAsc() {
		return ((getCurrentPageNo() - 1) * getRecordCountPerPage()) + 1;
	}	
	
	/**
	 * 이전페이지 번호
	 * 
	 * @return
	 */
	public int getPrevPageNo() {
		int n = getCurrentPageNo() - 1;
		return n > 1 ? n : 1;
	}

	/**
	 * 다음페이지 번호
	 * 
	 * @return
	 */
	public int getNextPageNo() {
		int n = getCurrentPageNo() + 1;
		int total = getTotalPageCount();

		return n < total ? n : total;
	}

	/**
	 * 이전 페이지 블럭 번호
	 * 
	 * @return
	 */
	public int getPrevPageNoOnPageList() {
		int n = getFirstPageNoOnPageList() - 1;
		return n > 1 ? n : 1;
	}

	/**
	 * 다음 페이지 블럭 번호
	 * 
	 * @return
	 */
	public int getNextPageNoOnPageList() {
		int n = getLastPageNoOnPageList() + 1;
		int total = getTotalPageCount();

		return n < total ? n : total;
	}

	/**
	 * 페이지 블럭 리스트
	 * 
	 * @return
	 */
	public int[] getPageList() {
		int start = getFirstPageNoOnPageList();
		int end = getLastPageNoOnPageList();
		
		int pageCnt = end - start + 1;

		int pages[] = new int[end - start + 1];

		for (int i = 0; i < pageCnt; i++) {
			pages[i] = start + i;
		}

		return pages;
	}
	
	/**
	 * pageSize만큼 이전 페이지로 이동할 번호
	 * 
	 * @return
	 */
	public int getPrevPageBlockNo() {
		int pageSize = getPageSize();
		int n = getCurrentPageNo() - pageSize;
		return n > 1 ? n : 1;
	}

	/**
	 * pageSize만큼 다음 페이지로 이동할 번호
	 * 
	 * @return
	 */
	public int getNextPageBlockNo() {
		int pageSize = getPageSize();
		int n = getCurrentPageNo() + pageSize;
		int total = getTotalPageCount();

		return n < total ? n : total;
	}
	
	/**
	 * 페이지 블럭 리스트
	 * 
	 * @return
	 */
	public int[] getPageListMobile() {
		float current = getCurrentPageNo() * 1f;
		int total = getTotalPageCount();
		
		int start = ((int) Math.ceil(current / mobilePageSize) - 1) * mobilePageSize + 1;
		start = start > 1 ? start : 1;
		
		int end = (int) Math.ceil(current / mobilePageSize) * mobilePageSize;
		end = end < total ? end : total;
		
		int pageCnt = end - start + 1;
		int pages[] = new int[end - start + 1];

		for (int i = 0; i < pageCnt; i++) {
			pages[i] = start + i;
		}

		return pages;
	}
	
	/**
	 * pageSize만큼 이전 페이지로 이동할 번호(모바일용)
	 * 
	 * @return
	 */
	public int getPrevPageBlockNoMobile() {
		int n = getCurrentPageNo() - mobilePageSize;
		return n > 1 ? n : 1;
	}

	/**
	 * pageSize만큼 다음 페이지로 이동할 번호(모바일용)
	 * 
	 * @return
	 */
	public int getNextPageBlockNoMobile() {
		int n = getCurrentPageNo() + mobilePageSize;
		int total = getTotalPageCount();

		return n < total ? n : total;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	private String getRequestUrl() {
		ArrayList<String> exclude = new ArrayList<String>();
		exclude.add("perPage");
		exclude.add("page");
		exclude.add("pageIndex");
		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		String parameters = "?";
		try {
			String queryString = request.getQueryString();
			if (queryString != null && !queryString.equals("")) {
				queryString = URLDecoder.decode(queryString, "UTF-8");

				String[] pares = queryString.split("&");
				for (String pare : pares) {
					String[] nameAndValue = pare.split("=");
					String value = "";
					if (nameAndValue[0].trim().equals("") || exclude.contains(nameAndValue[0])) {
						continue;
					}
					if (nameAndValue.length > 1) {
						value = nameAndValue[1];
					}

					parameters += nameAndValue[0].trim() + "=" + URLEncoder.encode(value, "UTF-8") + "&";
				}
			}
		} catch (UnsupportedEncodingException e1) {}

		return request.getRequestURI() + parameters;
	}
}
