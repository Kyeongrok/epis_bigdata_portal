/**
 * 
 */
package kr.co.ucsit.web.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.ucsit.core.CsUtil;

/**
 * <select> 커스텀 태그
 * @author cs1492
 *
 */
public class CsWebSelectTag extends CsWebTag {
	static final Logger LOGGER = LoggerFactory.getLogger(CsWebSelectTag.class);

	@Override
	public void doTag() throws IOException {
		LOGGER.debug("{}", ToStringBuilder.reflectionToString(this, ToStringStyle.NO_FIELD_NAMES_STYLE));
		
		StringBuffer sb = new StringBuffer();
		
		//
		appendOpenSelect(sb);
		
		
		//
		List<Map<String,Object>> datas = getDatas();
		
		//
		if(CsUtil.isEmpty(datas)) {
			appendCloseSelect(sb);
			
			getJspContext().getOut().println(sb.toString());
			return;
		}
		
		//
		appendHeaderText(sb);
		
		//
		appendOption(sb, datas);
		
		//
		appendCloseSelect(sb);
		
		
		getJspContext().getOut().println(sb.toString());		
	}
	

	/**
	 * select의 닫는 태그 추가
	 * @date : 2018. 2. 7.
	 * @param sb
	 */
	protected void appendCloseSelect(StringBuffer sb) {
		sb.append("</select>");
	}


	/**
	 * select의 여는 태그 추가
	 * @date : 2018. 2. 7.
	 * @param sb
	 */
	protected void appendOpenSelect(StringBuffer sb) {
		sb.append("<select");
		
		if(!CsUtil.isEmpty(getId())) {
			sb.append(" id='"+getId()+"'");
		}
		
		if(!CsUtil.isEmpty(getName())) {
			sb.append(" name='"+getName()+"'");
		}
		
		if(!CsUtil.isEmpty(getCss())) {
			sb.append(" class='"+getCss()+"'");
		}
		
		if(!CsUtil.isEmpty(getOnChangeJs())) {
			sb.append(" onchange='"+getOnChangeJs()+"(this)'");
		}
		
		if(!CsUtil.isEmpty(getValid())) {
			sb.append(" data-v='"+getValid()+"'");
		}
		
		if(!CsUtil.isEmpty(getTitle())) {
			sb.append(" title='"+getTitle()+"'");
		}
		
		sb.append(">");
	}


	/**
	 * option 추가
	 * @date : 2018. 2. 7.
	 * @param sb
	 * @param datas
	 */
	protected void appendOption(StringBuffer sb, List<Map<String, Object>> datas) {
		if(null == datas || 0 == datas.size()) {
			return;
		}
		
		Map<String,Object> map;
		for(int i=0; i<datas.size(); i++) {
			map = datas.get(i);
			
			if(null == map.get(_V)) {
				LOGGER.info(".appendOption {} {} {}", _T, _V, map);
				continue;
			}
			
			sb.append("<option value='"+map.get(_V)+"'");
			
			
			//선택값이 있으면
			if(map.get(_V).toString().equals(getSelectedValue())) {
				sb.append(" selected='selected'");
			}
			sb.append(">");
			
			sb.append(map.get(_T)).append("</option>");
		}
	}


	/**
	 * headerText값 추가
	 * @date : 2018. 2. 7.
	 * @param sb
	 */
	protected void appendHeaderText(StringBuffer sb) {
		if(null == getHeaderText()) {
			return;
		}
		
		sb.append("<option value=''>"+getHeaderText()+"</option>");
	}


}
