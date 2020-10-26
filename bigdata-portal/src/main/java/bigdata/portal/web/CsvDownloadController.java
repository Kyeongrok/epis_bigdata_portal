package bigdata.portal.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.service.AnalysisService;
import bigdata.portal.service.HdfsService;

@Controller
@RequestMapping("/csv/download")
public class CsvDownloadController extends CommonController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CsvDownloadController.class);
	
	@Autowired AnalysisService analysisService;
	@Autowired CsvDownloadService csvDownloadService;
	@Autowired HdfsService hdfsService;
	
	// 데이터 결합셋 csv 다운로드 컨트롤러
	@RequestMapping(value="/mergeCsv.do", method = RequestMethod.GET)
	public void mergeCsv(HttpServletRequest request, HttpServletResponse response) {
		try {
			String anadmIdx = request.getParameter("anadmIdx");
			String anadmUserId = getLoginUserId();
		
			EntityMap dsInfo = analysisService.selectBdpAnalysisDataMergeRow(anadmIdx, anadmUserId);
			
			String anadmTitle = dsInfo.getString("anadmTitle");
			String fileName = anadmTitle.replaceAll("!\"#[$]%&\\(\\)\\{\\}@`[*]:[+];-.<>,\\^~|'\\[\\]", "_");
			fileName = fileName.replaceAll(" ", "_");
			fileName = fileName+".csv";
			

			InputStream inputStream = null;
			inputStream = csvDownloadService.getAnalysisMergeCsv(dsInfo);
			
			try {				
				String header = getBrowser(request);

				if (header.contains("MSIE")) {
				       String docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
				       response.setHeader("Content-Disposition", "attachment;filename=" + docName + ";");
				} else if (header.contains("Firefox")) {
				       String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				       response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
				} else if (header.contains("Opera")) {
				       String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				       response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
				} else if (header.contains("Chrome")) {
				       String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				       response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
				}

				response.setHeader("Content-Type", "application/octet-stream");
				//response.setContentLength((int)file.getSize());
				response.setHeader("Content-Transfer-Encoding", "binary;");
				response.setHeader("Pragma", "no-cache;");
				response.setHeader("Expires", "-1;");
				
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.debug(e.getMessage());
			} finally {
				inputStream.close();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.debug(e.getMessage());
		}
	}
	
	
	/**
	 * 통계표 테이블 csv 다운로드 컨트롤러
	 * @param dsId
	 * @param prdDe
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/staCsv.do", method = RequestMethod.GET)
	public void staCsv(
			@RequestParam(value="dsId", defaultValue="") String dsId,
			@RequestParam(value="prdDe", defaultValue="") String prdDe,
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		if(dsId.equals("") || prdDe.equals("")) {
			return;
		}
		
		try {
			
			EntityMap dsInfo = csvDownloadService.getStaDsInfo(dsId);
			
			String dsName = dsInfo.getString("dsName");
			String fileName = dsName.replaceAll("!\"#[$]%&\\(\\)\\{\\}@`[*]:[+];-.<>,\\^~|'\\[\\]", "_");
			fileName = prdDe+"_"+fileName+"_utf8.csv";
			

			InputStream inputStream = null;
			inputStream = csvDownloadService.getStaTableCsv(dsInfo, prdDe);
		
		
			try {
				String header = getBrowser(request);

				if (header.contains("MSIE")) {
				       String docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
				       response.setHeader("Content-Disposition", "attachment;filename=" + docName + ";");
				} else if (header.contains("Firefox")) {
				       String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				       response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
				} else if (header.contains("Opera")) {
				       String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				       response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
				} else if (header.contains("Chrome")) {
				       String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				       response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
				}

				response.setHeader("Content-Type", "application/octet-stream");
				//response.setContentLength((int)file.getSize());
				response.setHeader("Content-Transfer-Encoding", "binary;");
				response.setHeader("Pragma", "no-cache;");
				response.setHeader("Expires", "-1;");
				
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.debug(e.getMessage());
			} finally {
				inputStream.close();
			}
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.debug(e.getMessage());
		}
		
	}
	
	// 사용자가 파일업로드 한 파일을 다운로드
	@RequestMapping(value="/uploadLogFile.do", method = RequestMethod.GET)
	public void uploadLogFile(
				HttpServletRequest request,
				HttpServletResponse response
			) {
		try {
			// 로그인, ulId값을 체크함.
			// 관리자인 경우는 ulId만 확인
			int ulId = Integer.valueOf(String.valueOf(request.getParameter("ulId")));			
			String userId = getLoginUserId();
			EntityMap rowInfo = null;

			if(isRoleAdmin()) {
				//csvDownloadService.uploadLogFileDownload(ulId);
				rowInfo = csvDownloadService.selectUserUploadLogInfo(ulId);
			} else {
			 	rowInfo = csvDownloadService.selectUserUploadLogInfo(ulId, userId);
			}
			String fileLocation = String.valueOf(rowInfo.getString("fileLocation"));
			String filePath = "";
			String fileName = rowInfo.getString("fileName");			
			fileName = fileName.replaceAll("!\"#[$]%&\\(\\)\\{\\}@`[*]:[+];-.<>,\\^~|'\\[\\]", "_");
			boolean isExist = false;
			File file = null;
			
			LOGGER.debug("ROWINFO = " + rowInfo.toString());
			
			// 파일위치 확인, 파일 존재여부 확인
			if(fileLocation.equals("LOCAL")) {
				filePath = rowInfo.getString("filePath") + "/" + rowInfo.getString("fileRealName");
				file = new File(filePath);
				isExist = file.exists();
			} else {
				// hdfs
				filePath = rowInfo.getString("hdfsFilePath");
				
				hdfsService.fileSystemOpen();
				filePath = hdfsService.getHdfsUrl() + filePath;
				isExist = hdfsService.isDupFile(filePath);
			}
			
			if(isExist) {
				try {
					String header = getBrowser(request);

					if (header.contains("MSIE")) {
					       String docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
					       response.setHeader("Content-Disposition", "attachment;filename=" + docName + ";");
					} else if (header.contains("Firefox")) {
					       String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
					       response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
					} else if (header.contains("Opera")) {
					       String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
					       response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
					} else if (header.contains("Chrome")) {
					       String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
					       response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
					}

					response.setHeader("Content-Type", "application/octet-stream");
					//response.setContentLength((int)file.getSize());
					response.setHeader("Content-Transfer-Encoding", "binary;");
					response.setHeader("Pragma", "no-cache;");
					response.setHeader("Expires", "-1;");
			
					if(fileLocation.equals("LOCAL")) {
						FileInputStream fileInputStream = null;
						fileInputStream = csvDownloadService.getInputStreamLocalFielPath(filePath);
						LOGGER.info("FilePath = " + filePath);
						FileCopyUtils.copy(fileInputStream, response.getOutputStream());
						fileInputStream.close();
					} else {
						InputStream inputStream = null;
						inputStream = csvDownloadService.getInputStreamHdfsFilePath(filePath);
						FileCopyUtils.copy(inputStream, response.getOutputStream());
						inputStream.close();
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					LOGGER.debug(e.getMessage());
				}
				
			
			} else {
				
				response.setContentType("application/x-msdownload");

                PrintWriter printwriter = response.getWriter();

                printwriter.println("<html>");
                printwriter.println("<br><br><br><h2>Could not get file name:<br>" + fileName + "</h2>");
                printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
                printwriter.println("<br><br><br>&copy; webAccess");
                printwriter.println("</html>");

                printwriter.flush();
                printwriter.close();
                
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	
	
	private String getBrowser(HttpServletRequest request) {
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
	
}
