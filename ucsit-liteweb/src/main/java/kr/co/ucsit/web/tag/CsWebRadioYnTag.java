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
public class CsWebRadioYnTag extends CsWebRadioTag {

	@Override
	public void doTag() throws IOException {
		//
		super.setDataSource("[{'t':'Y','v':'Y'},{'t':'N','v':'N'}]");
		
		StringBuffer sb = new StringBuffer();
		
		//
		super.appendHeaderText(sb);
		
		//
		List<Map<String,Object>> datas = super.getDatas();
		
		if(CsUtil.isEmpty(datas)) {
			getJspContext().getOut().write(sb.toString());
			
			return;
		}

		//
		sb = super.createRadios(datas, sb);
		
		
		//
		getJspContext().getOut().write(sb.toString());
	}


}
