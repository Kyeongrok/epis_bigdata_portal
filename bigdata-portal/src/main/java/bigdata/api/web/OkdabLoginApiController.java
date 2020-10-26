package bigdata.api.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibm.icu.util.Calendar;

import bigdata.portal.cmm.util.RSACrypt;
import bigdata.portal.cmm.util.RSACryptException;
import bigdata.portal.service.OkdabLoginService;
import egovframework.com.cmm.service.EgovProperties;

/**
 * @author THEIMC JHY
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2019. 1. 14.     JHY          최초 생성
 *      </pre>
 * 
 * @since 2019. 1. 14.
 */
@RequestMapping("/api/oksec")
@Controller
public class OkdabLoginApiController extends ApiRestController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OkdabLoginApiController.class);

	@Autowired private OkdabLoginService okdabLoginService;
	
	private String publilcKeyPath = EgovProperties.getProperty("okdab.api.rsa.public");
	private String privateKeyPath = EgovProperties.getProperty("okdab.api.rsa.private");

	/**
	 * 옥답 로그인 연동, 로그인 정보 수신 API
	 * okdab.api.access 설정에 허용된 IP만 접근됨
	 * ApiIpAuthenticInterceptor에서 접근 처리
	 * 
	 * @param body
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/okdabLoginApi.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String okdabLoginApi(@RequestBody String body, Model model) {
		LOGGER.debug(body);

		long thisTime = Calendar.getInstance().getTimeInMillis();
		if (body == null || body.trim().equals("")) {
			model.addAttribute("UUID", "");
			model.addAttribute("RESULT", "FAIL");
			model.addAttribute("ERROR", 100);
			model.addAttribute("ERRORMSG", "데이터 포멧 오류");
			return "jsonView";
		}

		String uuid = null;
		String id = null;
		String nm = null;
		String ip = null;
		String tm = null;

		try {
			JSONObject jsonObject = new JSONObject(body);

			uuid = jsonObject.getString("UUID");
			id = jsonObject.getString("ID");
			nm = jsonObject.getString("NM");
			ip = jsonObject.getString("IP");
			tm = jsonObject.getString("TM");

			if (uuid == null || uuid.trim().equals("") 
									|| id == null || id.trim().equals("") 
									|| nm == null || nm.trim().equals("") 
									|| ip == null || ip.trim().equals("") 
									|| tm == null || tm.trim().equals("")) {
				model.addAttribute("UUID", uuid);
				model.addAttribute("RESULT", "FAIL");
				model.addAttribute("ERROR", 100);
				model.addAttribute("ERRORMSG", "데이터 포멧 오류");
				return "jsonView";
			}
		} catch (JSONException e) {
			LOGGER.debug(e.getMessage());
			
			model.addAttribute("UUID", "");
			model.addAttribute("RESULT", "FAIL");
			model.addAttribute("ERROR", 100);
			model.addAttribute("ERRORMSG", "데이터 포멧 오류");
			return "jsonView";
		}

		try {
			// MCrypt mCrypt = new MCrypt(iv, seckey);
			RSACrypt rsaCrypt = new RSACrypt();
			rsaCrypt.initPrivateKey(privateKeyPath);

			id = rsaCrypt.decrypt(id);
			nm = rsaCrypt.decrypt(nm);
			ip = rsaCrypt.decrypt(ip);
			tm = rsaCrypt.decrypt(tm);
		} catch (RSACryptException e) {
			LOGGER.debug(e.getMessage());
			
			model.addAttribute("UUID", uuid);
			model.addAttribute("RESULT", "FAIL");
			model.addAttribute("ERROR", 101);
			model.addAttribute("ERRORMSG", "데이터 복호화 오류");
			return "jsonView";
		}

		// API호출 유효시간 5분
		Date dttm = null; 
		try {
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dttm = transFormat.parse(tm);
			if (thisTime - 300000 > dttm.getTime()) {
				model.addAttribute("UUID", uuid);
				model.addAttribute("RESULT", "FAIL");
				model.addAttribute("ERROR", 102);
				model.addAttribute("ERRORMSG", "API 호출 유효시간 초과");
				return "jsonView";
			}
		} catch (ParseException e) {
			LOGGER.debug(e.getMessage());
			
			model.addAttribute("UUID", uuid);
			model.addAttribute("RESULT", "FAIL");
			model.addAttribute("ERROR", 102);
			model.addAttribute("ERRORMSG", "TM 변환 오류");
			return "jsonView";
		}

		// TODO : 회원정보에 아이디가 있는지 확인

		// User user = userServer.getUser(id);
		// if (user == null || user.getUserId() == null || "".equals(user.getUserId()))
		// {
		// model.addAttribute("UUID", uuid);
		// model.addAttribute("RESULT", "FAIL");
		// model.addAttribute("ERROR", 104);
		// model.addAttribute("ERRORMSG", "빅데이터 포털 사용자가 아닙니다.");
		// return "jsonView";
		// }

		
		// TODO : 로그인 처리 시
		// UUID 로 데이터 조회
		// 아이디가 있는지 체크
		// IP가 현재 접속한 사용자의 IP와 동일한지 체크
		// 유효시간 체크
		
		
		// 데이터 입력
		 int rs = okdabLoginService.insertOkdabLogin(uuid, id, nm, ip, dttm);
		if (rs <= 0) {
			model.addAttribute("UUID", uuid);
			model.addAttribute("RESULT", "FAIL");
			model.addAttribute("ERROR", 103);
			model.addAttribute("ERRORMSG", "데이터 입력 오류");
			return "jsonView";
		}

		// 성공
		model.addAttribute("UUID", uuid);
		model.addAttribute("RESULT", "SUCCESS");
		return "jsonView";
	}

	/**
	 * 로그인 API 테스트 코드
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/okdabLoginApiTest.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String okdabLoginApiTest(HttpServletRequest request, Model model) {
		// 외부에서 실행할 수 있도록하지 말것
		// 아무나 로그인되는 문제 발생할 수 있음
		
		try {
			// MCrypt mCrypt = new MCrypt(iv, seckey);
			RSACrypt rsaCrypt = new RSACrypt();
			rsaCrypt.initPublicKey(publilcKeyPath);

			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date thisTime = Calendar.getInstance().getTime();

			String id = "student01";
			String nm = "학생1";
			String tm = transFormat.format(thisTime);
			UUID uuid = UUID.randomUUID();

			String ip = request.getHeader("X-FORWARDED-FOR");
			if (ip == null)
				ip = request.getRemoteAddr();

			model.addAttribute("UUID", uuid.toString());
			model.addAttribute("ID", rsaCrypt.encrypt(id));
			model.addAttribute("NM", rsaCrypt.encrypt(nm));
			model.addAttribute("IP", rsaCrypt.encrypt(ip));
			model.addAttribute("TM", rsaCrypt.encrypt(tm));
		} catch (RSACryptException e) {
			LOGGER.error("암호화 실패");
		}

		return "jsonView";
	}
}
