package test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FarmOnCopyMemberTest {

	private static String bdpUrl = "https://bigdata.agrion.kr";
//	private static String bdpUrl = "http://localhost:10100";

	private static final Logger LOGGER = LoggerFactory.getLogger(FarmOnCopyMemberTest.class);

	@Test
	public void test() throws UnsupportedEncodingException {
		String targetUrl = bdpUrl + "/member/join/copyUserAagrion.do";
		Map<String, Object> paramMap = new HashMap<>();

		paramMap.put("memberId", "whddbs311");
		String[] telArr = "010-4679-5986".split("-");


		String params = "";
		params += "mberId="      + "whddbs311";
		params += "&mberNm="         + URLEncoder.encode("김종윤", "UTF-8"); //한글
		params += "&password="          + "123456789";
		params += "&sexdstnCode="          + "M"; // 성별코드 M/F
		params += "&moblphonNo="       + "010-4679-5986";
		params += "&mberEmailAdres="        + "whddbs311@ucsit.co.kr";
		params += "&zip="         + "012345";
		params += "&adres="         + URLEncoder.encode("첨단로 245 13", "UTF-8"); //한글
		params += "&detailAdres="         + URLEncoder.encode("3층", "UTF-8"); //한글
		if(telArr.length >= 3) {
			params += "&areaNo="          + telArr[0];
			params += "&middleTelno="          + telArr[1];
			params += "&endTelno="          + telArr[2];
		}else {
			params += "&areaNo="          + "02";
		}

		System.out.println("========>>> 사용자 Copy (targetUrl) : " + targetUrl);
		System.out.println("========>>> 사용자 Copy (params) : " + params);

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 설정
			URL url = new URL(targetUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");             // HTTP POST 메소드 설정
			con.setDoOutput(true);                    // POST 파라미터 전달을 위한 설정
			con.setConnectTimeout(100);  // 컨텍션타임아웃
			con.setReadTimeout(100);        // 컨텐츠조회 타임아웃

			// 전송
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(params);
			wr.flush();
			wr.close();

			// 응답
			int responseCode = con.getResponseCode();
			map.put("responseCode",responseCode);
			System.out.println("========>>> responseCode : " + responseCode);
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			br.close();

			// 확인
			System.out.println("========>>> sb : " + sb.toString());

//			JSONObject json = (JSONObject) JSONSerializer.toJSON(sb.toString()) ;
//			String result_code = json.getString("result_code");
//			String result_msg = json.getString("result_msg");

//			LOGGER.debug("========>>> result_code : " + result_code);
//			LOGGER.debug("========>>> result_msg  : " + result_msg);

//			map.put("result_code",result_code);
//			map.put("result_msg", result_msg);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
