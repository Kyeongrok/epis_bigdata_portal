package egovframework.com.utl.fcc.service;

import egovframework.com.cmm.EgovWebUtil;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 공통컴포넌트 개발팀 한성곤
 * @version 1.0
 * @Class Name  : EgovFileUploadUtil.java
 * @Description : Spring 기반 File Upload 유틸리티
 * @Modification Information
 * <p>
 * 수정일         수정자                   수정내용
 * -------          --------        ---------------------------
 * 2009.08.26       한성곤                  최초 생성
 * @see
 * @since 2009.08.26
 */
public class EgovFileUploadUtil extends EgovFormBasedFileUtil {
    /**
     * 파일을 Upload 처리한다.
     *
     * @param request
     * @param where
     * @param maxFileSize
     * @return
     * @throws Exception
     */
    public static List<EgovFormBasedFileVo> uploadFiles(HttpServletRequest request, String where, long maxFileSize) throws Exception {
        List<EgovFormBasedFileVo> list = new ArrayList<EgovFormBasedFileVo>();

        MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
        Iterator<?> fileIter = mptRequest.getFileNames();

        while (fileIter.hasNext()) {
            MultipartFile mFile = mptRequest.getFile((String) fileIter.next());

            EgovFormBasedFileVo vo = new EgovFormBasedFileVo();

            String tmp = mFile.getOriginalFilename();

            if (tmp.lastIndexOf("\\") >= 0) {
                tmp = tmp.substring(tmp.lastIndexOf("\\") + 1);
            }

            vo.setFileName(tmp);
            vo.setContentType(mFile.getContentType());
            vo.setServerSubPath(getTodayString());
            vo.setPhysicalName(getPhysicalFileName());
            vo.setSize(mFile.getSize());

            if (tmp.lastIndexOf(".") >= 0) {
                vo.setPhysicalName(vo.getPhysicalName()); // 2012.11 KISA 보안조치
            }

            if (mFile.getSize() > 0) {
                InputStream is = null;

                try {
                    is = mFile.getInputStream();
                    saveFile(is, new File(EgovWebUtil.filePathBlackList(where + SEPERATOR + vo.getServerSubPath() + SEPERATOR + vo.getPhysicalName())));
                } finally {
                    if (is != null) {
                        is.close();
                    }
                }
                list.add(vo);
            }
        }

        return list;
    }
}
