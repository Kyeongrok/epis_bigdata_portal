package bigdata.portal.cmm.util;

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
public class MCryptException extends Exception {
	private static final long serialVersionUID = 5688811423601293350L;

	public MCryptException(String msg) {
		super(msg);
	}

	@Override
	public String toString() {
		return "MCryptException";
	}
}
