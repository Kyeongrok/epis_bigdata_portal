/**
 * 
 */
package bigdata.portal.web.extend.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

/**
*
*
* @author THEIMC theimc
* @version 1.0
* @see
*
*      <pre>
* << 개정이력(Modification Information) >>
*
*      수정일         수정자           수정내용
*  -------------    --------    ---------------------------
*   2018. 10. 18.     theimc          최초 생성
*      </pre>
*
* @since 2018. 10. 18.
*/
public class ZeppelinLoginSession extends ExternalMakeSession {
	
	private String username = null;
	private String password = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(ZeppelinLoginSession.class);
	
	public ZeppelinLoginSession() { }
	
	public ZeppelinLoginSession(String destURL) {		
		super.setDestUrl(destURL);		
	}
	
	public void setAuthAccount(String username, String password) {
		this.username = username;
		this.password = password;		
	}
	
	public String getSessionID() {
		
		OutputStream os = null;
		BufferedWriter writer = null;
		
		HttpURLConnection connection = this.connect("POST");
		
		try {
			os = connection.getOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			
			writer.write(getParams());
			writer.flush();
			writer.close();
			os.close();
			
			if(connection.getResponseCode() != 200) {
				LOGGER.error(String.format("Zeppelin Account Connection ERROR, Response Code %s", connection.getResponseCode()));		
				return null;
			}
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			try {
				writer.close();
				os.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
				
				return null;
			}	
		}
		
		return parseSessionID(connection.getHeaderFields());

	}
	
	private String parseSessionID (Map<String,List<String>> m) {

		String[] cookieValues = null;
		
		if (m.containsKey("Set-Cookie")) {
			Collection<String> c = (Collection<String>) m.get("Set-Cookie");
			
			for (Iterator<String> i = c.iterator(); i.hasNext();) {
				String value = ((String) i.next());
				if (value.indexOf("ZEPPELINID") == 0 && value.indexOf("deleteMe") == -1) {
					cookieValues = value.split(";");
					break;
				}
			}
		}
		
		return cookieValues[0].split("=")[1];
		
	}
	
	private String getParams() {
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userName", this.username));
		params.add(new BasicNameValuePair("password", this.password));
		
		StringBuilder result = new StringBuilder();
		boolean first = true;
		
		try {
			
			for (NameValuePair pair : params) {
				if (first) {
					first = false;
				} else {
					result.append("&");
				}
				
				result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
				result.append("=");
				result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
					
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result.toString();
	}
}
