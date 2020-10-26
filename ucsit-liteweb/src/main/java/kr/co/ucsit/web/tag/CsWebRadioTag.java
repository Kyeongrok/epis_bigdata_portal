/**
 * 
 */
package kr.co.ucsit.web.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import kr.co.ucsit.core.CsUtil;

/**
 * <input type="radio"> 커스텀 태그
 * @author cs1492
 *
 */
public class CsWebRadioTag extends CsWebTag {

	@Override
	public void doTag() throws IOException {
		
		StringBuffer sb = new StringBuffer();
		
		//
		appendHeaderText(sb);
		
		//
		List<Map<String,Object>> datas = getDatas();
		
		if(CsUtil.isEmpty(datas)) {
			getJspContext().getOut().println(sb.toString());
			return;
		}

		//
		sb = createRadios(datas, sb);

		getJspContext().getOut().println(sb.toString());
	}
	

	protected StringBuffer createRadios(List<Map<String, Object>> datas, StringBuffer sb) {
		for(Map<String,Object> map : datas) {
			sb.append("<label style='margin-right:10px;'>");
			
			sb.append("<input type='radio' ");
			
			//
			appendAttribute(sb, ""+map.get(_T), ""+map.get(_V));
			
			sb.append(">");
			
			sb.append(map.get(_T));
			
			sb.append("</label>");
		}
		
		return sb;
	}
	
	
	/**
	 * attribute 추가
	 * @date : 2018. 2. 8.
	 * @param sb
	 * @param t
	 * @param v
	 */
	protected void appendAttribute(StringBuffer sb, String t, String v) {
		if(!CsUtil.isEmpty(t)) {
			sb.append(" title='"+t+"'");
		}
		
		if(!CsUtil.isEmpty(getName())){
			sb.append(" name='"+getName()+"'");
		}
		
		if(!CsUtil.isEmpty(v)) {
			sb.append(" value='"+v+"'");
		}
		
		if(!CsUtil.isEmpty(getCss())){
			sb.append(" class='"+getCss()+"'");
		}			
		
		if(!CsUtil.isEmpty(getValid())){
			sb.append(" data-v='"+getValid()+"'");
		}
		
		if(!CsUtil.isEmpty(v)) {
			if(v.equals(getSelectedValue())){
				sb.append(" checked='checked'");
			}
		}
	}
	

	/**
	 * headerText 추가
	 * @date : 2018. 2. 8.
	 * @param sb
	 */
	protected void appendHeaderText(StringBuffer sb) {
		if(null == getHeaderText()) {
			return;
		}

		sb.append("<label style='margin-left:5px;'>");
		
		sb.append("<input type='radio' ");
		
		//
		appendAttribute(sb, getHeaderText(), getHeaderValue());
		
		sb.append(">");
		
		sb.append(getHeaderText());
		
		sb.append("</label>");
	
	}


}
