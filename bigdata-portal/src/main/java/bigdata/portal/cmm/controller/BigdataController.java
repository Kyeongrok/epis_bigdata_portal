/**
 *
 */
package bigdata.portal.cmm.controller;

//import static bigdata.portal.web.CommonController.LOGGER;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import bigdata.portal.web.CommonController;
import egovframework.com.cmm.service.EgovProperties;
import kr.co.ucsit.core.CsFileVO;
import kr.co.ucsit.core.CsUtil;

/**
 * 공통 controller
 * @author hyunseongkil
 *
 */
@Controller
public class BigdataController extends CommonController {
	private static Logger log = LoggerFactory.getLogger(BigdataController.class);

	protected static final String P_BIGDATA_PORTAL = "bigdata/portal/";

	protected static final String JSON_VIEW = "jsonView";

	//@Value("${app.upload.file.path}")
	private String uploadFilePath = EgovProperties.getProperty("app.upload.file.path");

	protected String getBrowser(HttpServletRequest request) {
        String header =request.getHeader("User-Agent");
        if (header.contains("MSIE")) {
               return "MSIE";
        } else if(header.contains("Chrome")) {
               return "Chrome";
        } else if(header.contains("Opera")) {
               return "Opera";
        }

        return "Firefox";
  }

	protected CsFileVO uploadFile(MultipartFile  mpf, String gbn) throws IOException {

		CsFileVO vo = new CsFileVO();

		if(mpf.isEmpty()) {
			return vo;
		}

		//
		if("image".equals(gbn)) {
			boolean b = CsUtil.isImageFile(mpf.getOriginalFilename());
			if(!b) {
				throw new RuntimeException("black ext " + mpf.getOriginalFilename());
			}
		}

		//
		vo.setOriginFileName(mpf.getOriginalFilename());
		vo.setSize(""+mpf.getSize());
		vo.setContentType(mpf.getContentType());
		vo.setStreFileName(System.nanoTime() + "."+FilenameUtils.getExtension(mpf.getOriginalFilename()));


		//
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String yyyymm = sdf.format(new Date());

		vo.setSubPath(yyyymm.substring(0, 4) + File.separator + yyyymm.substring(4));
		vo.setStrePathName(vo.getSubPath());

		//
		String fullpath = uploadFilePath;
		fullpath += File.separator + vo.getSubPath();
		fullpath += File.separator + vo.getStreFileName();

		File f = new File(fullpath);
		f.mkdirs();

		mpf.transferTo(f);

		//
		log.debug(".uploadFile {} {}",f, vo);


		return vo;
	}
}
