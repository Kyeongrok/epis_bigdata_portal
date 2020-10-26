/**
 * 
 */
package bigdata.portal.web.extend.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.HashMap;

import javax.xml.bind.DatatypeConverter;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
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
public class AmbariLoginSession extends ExternalMakeSession {
	
	private String authAccount = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(AmbariLoginSession.class);
	
	public AmbariLoginSession() { }
	
	public AmbariLoginSession(String destURL) {		
		super.setDestUrl(destURL);		
	}
	
	public void setAuthAccount(String username, String password) {
		this.authAccount = String.format("%s:%s", username, password);
		
		try {
			this.authAccount = DatatypeConverter.printBase64Binary(this.authAccount.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * Get Ambari User List
	 * @return
	 * @throws ParseException
	 */
	public String getAmbariUserList() throws ParseException {
		HttpURLConnection connection = this.connect("GET");
		connection.setRequestProperty("Authorization", "Basic " + this.authAccount);

		JSONObject object = null;

		try {

			InputStream in = connection.getInputStream();
			InputStreamReader isw = new InputStreamReader(in);
			JSONParser parser = new JSONParser();
			object = (JSONObject)parser.parse(isw);
		} catch (NullPointerException e) {
			return null;
		} catch (IOException e) {			
			return null;
		}
		
		return object.toJSONString();
		
	}
	
	public void makeAmbariAccount(String userId, String password) {
		
		HttpURLConnection connection = this.connect("POST");		
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Authorization", "Basic " + this.authAccount);
		connection.setRequestProperty("X-Requested-By", "ambari");
		connection.setDoOutput(true);
        connection.setDoInput(true);
        
        LOGGER.info(this.authAccount);
        
		HashMap<String, Object> accountInfo = new HashMap<String, Object>();
		accountInfo.put("Users/user_name", userId);
		accountInfo.put("Users/password", password);
		accountInfo.put("Users/active", true);
		accountInfo.put("Users/admin", false);
				
		int responseCode = 0;
		String responseMessage = null;
		
		OutputStream os = null;
		BufferedWriter writer = null;		
		
		try {
			os = connection.getOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			
			LOGGER.debug(JSONObject.toJSONString(accountInfo).replace("\\", ""));
			
			writer.write(JSONObject.toJSONString(accountInfo).replace("\\", ""));
			writer.flush();
			writer.close();
			os.close();
			
			responseCode = connection.getResponseCode();		
			responseMessage = connection.getResponseMessage();
			
			if(responseCode != 201) {				
				LOGGER.error(String.format("Response Code %s, %s", Integer.toString(responseCode), responseMessage));
				return ;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return ;
		}
		
		LOGGER.info(String.format("Response Code %s, %s", Integer.toString(responseCode), responseMessage));
		
	}
	
	public void changeAmbariPassword(String newPassword, String adminPassword) {
		
		HttpURLConnection connection = this.connect("PUT");		
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Authorization", "Basic " + this.authAccount);
		connection.setRequestProperty("X-Requested-By", "ambari");
		connection.setDoOutput(true);
        connection.setDoInput(true);
        
        LOGGER.info(this.authAccount);
        
		HashMap<String, Object> accountInfo = new HashMap<String, Object>();
		accountInfo.put("Users/password", newPassword);
		accountInfo.put("Users/old_password", adminPassword);
				
		int responseCode = 0;
		String responseMessage = null;
		
		OutputStream os = null;
		BufferedWriter writer = null;		
		
		try {
			os = connection.getOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			
			LOGGER.debug(JSONObject.toJSONString(accountInfo).replace("\\", ""));
			
			writer.write(JSONObject.toJSONString(accountInfo).replace("\\", ""));
			writer.flush();
			writer.close();
			os.close();
			
			responseCode = connection.getResponseCode();		
			responseMessage = connection.getResponseMessage();
			
			if(responseCode != 201) {				
				LOGGER.error(String.format("Response Code %s, %s", Integer.toString(responseCode), responseMessage));
				return ;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return ;
		}
		
		LOGGER.info(String.format("Response Code %s, %s", Integer.toString(responseCode), responseMessage));
		
	}
	
	public void joinTheGroup(String userId, String groupName) {
		
		HttpURLConnection connection = this.connect("POST");		
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Authorization", "Basic " + this.authAccount);
		connection.setRequestProperty("X-Requested-By", "ambari");
		connection.setDoOutput(true);
        connection.setDoInput(true);
		
        JSONArray groupInfoArray = new JSONArray();
        JSONObject groupInfo = new JSONObject();
		groupInfo.put("MemberInfo/user_name", userId);
		groupInfo.put("MemberInfo/group_name", groupName);
		
		groupInfoArray.put(groupInfo);
		
		int responseCode = 0;
		String responseMessage = null;
		
		OutputStream os = null;
		BufferedWriter writer = null;		
		
		try {
			os = connection.getOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			
			LOGGER.debug(groupInfoArray.toString());
			
			writer.write(groupInfoArray.toString());
			writer.flush();
			writer.close();
			os.close();
			
			responseCode = connection.getResponseCode();		
			responseMessage = connection.getResponseMessage();
			
			if(responseCode != 201) {				
				LOGGER.error(String.format("Response Code %s, %s", Integer.toString(responseCode), responseMessage));
				return ;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return ;
		}
		
		LOGGER.info(String.format("Response Code %s, %s", Integer.toString(responseCode), responseMessage));        
        
	}	
	
	public void deleteAmbariAccount() {
		
		HttpURLConnection connection = this.connect("DELETE");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Authorization", "Basic " + this.authAccount);
		connection.setRequestProperty("X-Requested-By", "ambari");
		
		int responseCode = 0;
		String responseMessage = null;
		
		try {
			
			responseCode = connection.getResponseCode();
			responseMessage = connection.getResponseMessage();
			
			if(connection.getResponseCode() != 200) {
				LOGGER.error(String.format("Ambari Account Connection ERROR, Response Code %s", connection.getResponseCode()));
				LOGGER.error(responseMessage);
				return ;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return ;
		}
		
		LOGGER.info(String.format("Response Code %s, %s", Integer.toString(responseCode), responseMessage));			
	}
	
	public String getSessionID() {
				
		HttpURLConnection connection = this.connect("GET");		
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Authorization", "Basic " + this.authAccount);
		connection.setRequestProperty("X-Requested-By", "ambari");
		
		int responseCode = 0;
		String responseMessage = null;
		
		try {
			
			responseCode = connection.getResponseCode();
			responseMessage = connection.getResponseMessage();
			
			if(connection.getResponseCode() != 200) {
				LOGGER.error(String.format("Ambari Account Connection ERROR, Response Code %s", connection.getResponseCode()));
				return null;
			}
			
		} catch (IOException e) {
			e.printStackTrace();			
			return null;
		}
		
		String headerField = connection.getHeaderField("Set-Cookie");
		String[] headerValues = headerField.split(";");	
		
		LOGGER.info(String.format("Response Code %s, %s", Integer.toString(responseCode), responseMessage));
		
		return headerValues[0].substring(headerValues[0].indexOf("=") + 1);
	}
}
