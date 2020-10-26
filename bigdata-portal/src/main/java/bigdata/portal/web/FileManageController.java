package bigdata.portal.web;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.service.AnalysisService;
import bigdata.portal.service.FileManageService;
import bigdata.portal.service.HdfsService;
import bigdata.portal.service.HistoryService;

@Controller
@RequestMapping("/manage/delete")
public class FileManageController extends CommonController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileManageController.class);
	@Autowired
	private HdfsService hdfsService;
	@Autowired
	private FileManageService fileManageService;
	@Autowired
	private AnalysisService analysisService;
	@Autowired
	private HistoryService historyService;

	@RequestMapping(value = "/mergeList.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String deleteMergeList(HttpServletRequest request, Model model,
			@RequestParam(value = "term", defaultValue = "1Y") String term) {

		List<EntityMap> prevDayMergeList = null;
		HashMap<String, Object> mergeListParam = null;
		int deleteCnt = 0;

		// 1년 / 6개월의 파라미터가 아닐 때 설정
		if (!term.toUpperCase().equals("1Y") || !term.toUpperCase().equals("6M")) {
			term = "1Y";
		}

		// TODO: 1. 리스트 목록 조회
		mergeListParam = new HashMap<String, Object>();
		mergeListParam.put("term", term);
		prevDayMergeList = fileManageService.selectDelToPreviousDay(mergeListParam);

		try {
			for (EntityMap mergeData : prevDayMergeList) {
				String anadmIdx = mergeData.getString("anadmIdx");
				EntityMap anaInfo = analysisService.selectBdpAnalysisDataMergeRow(anadmIdx);

				// TODO: 2. 리스트 삭제 (지금은 사용안함)
				// deleteListCnt += analysisService.deleteBdpAnalysisDataMergeRow(anadmIdx);

				// 글 삭제 이력 저장
				String anadmUserId = anaInfo.getString("anadmUserId");
				String artCont = anaInfo.getString("anadmTitle");
				String artTarget = "MERGE";
				String artStatus = "DEL";
				int artIdx = Integer.valueOf(anadmIdx);
				String artUrl = "";

				// LOG 기록
				historyService.saveBdpUserArticleLog(anadmUserId, artTarget, artCont, artStatus, artIdx, artUrl);

				// TODO: 3. HDFS 파일 삭제
				boolean isRemoveFile = hdfsService.removeHdfsFile(anaInfo.getString("anadmResult"));
				if(isRemoveFile) {
					deleteCnt++;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

			String message = String.format("저장기간이 만료된 %d개의 결합 데이터 삭제 중 오류가 발생되었습니다.", deleteCnt);
			
			model.addAttribute("execTime", Calendar.getInstance().getTime().toString());
			model.addAttribute("deleteMergeList", deleteCnt);
			model.addAttribute("message", message);
			

			LOGGER.info(message);

			return "jsonView";
		}
		
		String message = String.format("저장기간이 만료된 %d개의 결합 데이터가 자동 삭제 되었습니다.", deleteCnt);
		
		model.addAttribute("execTime", Calendar.getInstance().getTime().toString());
		model.addAttribute("deleteMergeList", deleteCnt);
		model.addAttribute("message", message);

		LOGGER.info(message);

		return "jsonView";

	}

}
