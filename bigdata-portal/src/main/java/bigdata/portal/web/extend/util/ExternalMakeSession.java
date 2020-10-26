/**
 * 
 */
package bigdata.portal.web.extend.util;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
*
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
public abstract class ExternalMakeSession {

	private URL destUrl = null;

	protected HttpURLConnection connect(String method) {

		HttpURLConnection connection = null;

		try {
			connection = (HttpURLConnection) destUrl.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod(method);
			connection.setDoInput(true);

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return connection;
	}

	public void setDestUrl(String destURL) {
		URL url = null;

		try {
			url = new URL(destURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.destUrl = url;
	}

}
