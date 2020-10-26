package bigdata.portal.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.JSchException;

import bigdata.portal.entity.CodeDetail;
import bigdata.portal.entity.EntityMap;
import bigdata.portal.web.extend.util.AmbariLoginSession;
import bigdata.portal.web.extend.util.RStudioConnector;
import bigdata.portal.web.extend.util.ZeppelinLoginSession;

@Service
public class ExtendUserViewService {
		
	@Autowired private UserSettingService userSettingService;
	@Autowired private CodeService codeService;
	
	@Value("${ambari.api.id}") 	private String ambariKey;
	@Value("${ambari.api.password}") private String ambariKeyValue;
	@Value("${ambari.api.group}") private String ambariGroupName;
	
	@Value("${rstudio.api.id}") private String rstudioApiKey;
	@Value("${rstudio.api.password}") private String rstudioApiKeyValue;	
	@Value("${rstudio.api.host}") private String rstudioApiHost;
	@Value("${rstudio.api.port}") private int rstudioApiPort;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExtendUserViewService.class);
	
	/**
	 * Ambari View 링크 (사용자별 설정된 도메인/아이피 매핌)
	 * @param param
	 * @return
	 */
	public String selectUserViewFullUrl(HashMap<String, String> param) {
		String userId = param.get("userId");
		EntityMap map = userSettingService.selectUserSetting(userId);
		CodeDetail codeDetail  = codeService.selectCodeDetail("P0010", param.get("viewId"));
				
		String fullUrl = codeDetail.getCodeVal();
		Pattern pattern = Pattern.compile("\\{([0-9a-zA-Z-_]+)\\}");
		Matcher matcher = pattern.matcher(fullUrl);
		
		while(matcher.find()) {
			String column = "us" + EntityMap.pascalCase(matcher.group(1));			
			String domain = map.getString(column);			
			fullUrl = fullUrl.replaceAll("\\{" + matcher.group(1) + "\\}", domain);
		}
		
		return fullUrl;
	}
	
	/**
	 * Ambari 세션 값 생성
	 * @param username
	 * @param password
	 * @return
	 */
	public String makeAmbariLoginSession(String username, String password) {		
		CodeDetail codeDetail  = codeService.selectCodeDetail("P0010", "0090");
		String ambariURL = String.format("%s/api/v1/views", codeDetail.getCodeVal());
		
		AmbariLoginSession ambari = new AmbariLoginSession(ambariURL);
		ambari.setAuthAccount(username, password);
		
		return ambari.getSessionID();
	}
	
	/**
	 * Zeppelin 세션 값 생성
	 * @param username
	 * @param password
	 * @return
	 */
	public String makeZeppelinLoginSession(String username, String password) {
		CodeDetail codeDetail  = codeService.selectCodeDetail("P0010", "0040");
		String zeppelinURL = String.format("%s/api/login", codeDetail.getCodeVal());		
		ZeppelinLoginSession zeppelin = new ZeppelinLoginSession(zeppelinURL);
		zeppelin.setAuthAccount(username, password);
		
		return zeppelin.getSessionID();
	}
	
	/**
	 * Ambari 계정 생성
	 * @param userId
	 * @param password
	 */
	public void makeAmbariAccount(String userId, String password) {		
		CodeDetail codeDetail  = codeService.selectCodeDetail("P0010", "0090");
		String createAccountURL = String.format("%s/api/v1/users", codeDetail.getCodeVal());
		AmbariLoginSession ambari = new AmbariLoginSession(createAccountURL);
		ambari.setAuthAccount(ambariKey, ambariKeyValue);
		
		ambari.makeAmbariAccount(userId, password);
		
	}

	/**
	 * Ambari 그룹 가입
	 * @param userId
	 * @param groupName
	 */
	public void joinGroupAmbari(String userId, String groupName) {		
		CodeDetail codeDetail  = codeService.selectCodeDetail("P0010", "0090");
		String joinGroupURL = String.format("%s/api/v1/groups/%s/members", codeDetail.getCodeVal(), ambariGroupName);
		AmbariLoginSession ambari = new AmbariLoginSession(joinGroupURL);
		ambari.setAuthAccount(ambariKey, ambariKeyValue);
		
		ambari.joinTheGroup(userId, groupName);
		
	}
	
	/**
	 * RStudio 로그인 시 암호화를 위한 공개 키 로드
	 * @return auth-public-key
	 */
	public String getRStudioPublicKey() {		
		CodeDetail codeDetail  = codeService.selectCodeDetail("P0010", "0030");
		String authPublicKeyUrl = String.format("%s/auth-public-key", codeDetail.getCodeVal());
		RStudioConnector r = new RStudioConnector(authPublicKeyUrl);
		
		return r.getRStudioPublicKey();		
	}

	/**
	 * Ambari User 삭제
	 * @param entrprsmberId
	 */
	public void removeAmbariUser(String userId) {		
		CodeDetail codeDetail  = codeService.selectCodeDetail("P0010", "0090");
		String removeAccountUrl = String.format("%s/api/v1/users/%s", codeDetail.getCodeVal(), userId);
		AmbariLoginSession ambari = new AmbariLoginSession(removeAccountUrl);
		ambari.setAuthAccount(ambariKey, ambariKeyValue);
		
		ambari.deleteAmbariAccount();
		
	}
	
	/**
	 * RStudio 계정 생성
	 * @param userId, password
	 * @throws JSchException
	 * @throws IOException
	 */
	public void makeRStudioAccount(String userId, String password) throws JSchException, IOException {		
		RStudioConnector r = new RStudioConnector(rstudioApiHost, rstudioApiKey, rstudioApiKeyValue, rstudioApiPort);
		r.makeAccount(userId);
		r.changeRStudioPassword(userId, password);
		
	}

	/**
	 * RStudio 계정 삭제
	 * @param userId
	 * @throws JSchException
	 * @throws IOException
	 */
	public void removeRStudioAccount(String userId) throws JSchException, IOException {		
		RStudioConnector r = new RStudioConnector(rstudioApiHost, rstudioApiKey, rstudioApiKeyValue, rstudioApiPort);
		r.removeRStudioAccount(userId);
		
	}

	/**
	 * RStudio 패스워드 변경
	 * 
	 * @param userId
	 * @param newPassword
	 * @throws JSchException
	 * @throws IOException
	 */
	public void changeRStudioAccountPassword(String userId, String newPassword) throws JSchException, IOException {
		// TODO Auto-generated method stub
		RStudioConnector r = new RStudioConnector(rstudioApiHost, rstudioApiKey, rstudioApiKeyValue, rstudioApiPort);
		r.changeRStudioPassword(userId, newPassword);
		
	}

	/**
	 * Ambari 패스워드 변경
	 * 
	 * @param userId
	 * @param newPassword
	 */
	public void changeAmbariPassword(String userId, String newPassword) {
		// TODO Auto-generated method stub
		CodeDetail codeDetail  = codeService.selectCodeDetail("P0010", "0090");
		String removeAccountUrl = String.format("%s/api/v1/users/%s", codeDetail.getCodeVal(), userId);
		AmbariLoginSession ambari = new AmbariLoginSession(removeAccountUrl);
		ambari.setAuthAccount(ambariKey, ambariKeyValue);
		// TODO: 비밀번호 변경
		ambari.changeAmbariPassword(newPassword, ambariKeyValue);
		
	}	
}
