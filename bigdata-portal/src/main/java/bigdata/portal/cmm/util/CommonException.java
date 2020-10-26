package bigdata.portal.cmm.util;


/**
 * 기본 에러 처리
 *
 * @author THEIMC JHY
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2019. 1. 8.     JHY          최초 생성
 *      </pre>
 * 
 * @since 2019. 1. 8.
 */
public class CommonException extends Exception {
	static final long serialVersionUID = -3387516993124229948L;

	public CommonException() {
		super();
	}

	public CommonException(String message) {
		super(message);
	}

	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommonException(Throwable cause) {
		super(cause);
	}

	protected CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
