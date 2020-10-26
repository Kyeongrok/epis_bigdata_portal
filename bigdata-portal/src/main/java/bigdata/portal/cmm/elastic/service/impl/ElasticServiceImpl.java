/**
 *
 */
package bigdata.portal.cmm.elastic.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.elastic.service.ElasticService;
import bigdata.portal.cmm.service.impl.BigdataServiceImpl;
import bigdata.portal.service.CtvtArSttemntService;

/**
 * 공통 엘라스틱 service
 * @author hyunseongkil
 * @since
 * 	0204	init
 */
@EnableAsync
@Service("elasticService")
public class ElasticServiceImpl extends BigdataServiceImpl implements ElasticService {

	@Value("${app.elastic.id}")
	private String id;

	@Value("${app.elastic.pw}")
	private String pw;

	@Value("${app.elastic.hostname}")
	private String hostname;

	@Value("${app.elastic.port}")
	private String port;

	@Value("${app.elastic.scheme}")
	private String scheme;


	@Autowired
	private CtvtArSttemntService ctvtArSttemntService;


	/**
	 *
	 */
	private RestClient restClient = null;

	/**
	 * ssl 연결 테스트
	 * @throws IOException
	 */
	@Async
	private void connectSslTest() throws IOException {

		//
//		Header[] headers = createHeaders();
//		Map<String, String> params =  createDefaultParams();
//
//		//
//		String requestJson = "";
//		HttpEntity entity = new NStringEntity(requestJson, ContentType.APPLICATION_JSON);
//
//		//
//		Response response = restClient.performRequest(METHOD_GET, POSTFIX_SEARCH, params, entity, headers);
//
//		//
//		int statusCode = response.getStatusLine().getStatusCode();
//		String result = EntityUtils.toString(response.getEntity());
//
//		//
//		LOG.debug("statusCode:{}", statusCode);
//		LOG.debug("result:{}", result);
	}


	/**
	 * @return
	 */
	private HttpAsyncResponseConsumerFactory createConsumerFactory() {
		return new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(200 * 1024 * 1024);
	}

	/**
	 * @return
	 */
	private Map<String,String> createDefaultParams(){
		return Collections.singletonMap("pretty", "true");
	}

	/**
	 * @param s
	 * @return
	 */
	private HttpEntity createEntity(String s) {
		return new NStringEntity(s, ContentType.APPLICATION_JSON);
	}

	/**
	 * ssl 인증을 위한 header 생성
	 * @return
	 */
	private Header[] createHeaders() {
		//
		String auth = id + ":" + pw;
		String basicAuth = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());

		//
		Header[] headers = {new BasicHeader("Authorization", basicAuth)};

		return headers;
	}




	/**
	 * RestClient 생성
	 * @return
	 */
	private RestClient createRestClient() {
		RestClientBuilder restClient = RestClient.builder(new HttpHost(hostname, Integer.parseInt(port) , scheme));
		restClient.setRequestConfigCallback(new RequestConfigCallback() {
			@Override
			public Builder customizeRequestConfig(Builder requestConfigBuilder) {
				return requestConfigBuilder.setConnectTimeout(5000).setSocketTimeout(1200000);
			}
		}).setMaxRetryTimeoutMillis(1200000);


		//
		return restClient.build();

	}


	@Override
	public ElasticResultMap getCount(String index, Class<?> clazz)
			throws IOException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ElasticResultMap getMapping(String index, Class<?> clazz)
			throws IOException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ElasticResultMap getSearch(String index, Class<?> clazz) throws IOException, InstantiationException, IllegalAccessException {
		if(null == restClient) {
			return null;
		}

		//
		String jsonString = performRequest(index, "", METHOD_GET, POSTFIX_SEARCH);

		//
		return new ElasticResultMap(jsonString, clazz);
	}


	@PostConstruct
	private void init() throws IOException, InstantiationException, IllegalAccessException {
		this.restClient = createRestClient();
		LOG.debug("restClient:" + restClient);

		//
//		connectSslTest();

	}



	@Override
	public void postDoc(String index, String qry)
			throws IOException, InstantiationException, IllegalAccessException {
		String jsonString = performRequest(index, qry, METHOD_POST, POSTFIX_DOC);

	}


	@Override
	public ElasticResultMap postCount(String index, String qry)	throws IOException, InstantiationException, IllegalAccessException {
		String jsonString = performRequest(index, qry, METHOD_POST, POSTFIX_COUNT);
		return new ElasticResultMap(jsonString);
	}


	@Override
	public ElasticResultMap postMapping(String index, String qry)
			throws IOException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ElasticResultMap postSearch(String index, String qry) throws IOException, InstantiationException, IllegalAccessException {
		String jsonString = performRequest(index, qry, METHOD_POST, POSTFIX_SEARCH);
		return new ElasticResultMap(jsonString);
	}

	/**
	 * @param index
	 * @param qry
	 * @param method
	 * @param postfix
	 * @return
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private String performRequest(String index, String qry, String method, String postfix) throws IOException, InstantiationException, IllegalAccessException {
		if(null == restClient) {
			LOG.info("<<.performRequest - restClient is null");
			return null;
		}


		//
		Map<String,String> params = createDefaultParams();
		HttpEntity entity = createEntity(qry);
		HttpAsyncResponseConsumerFactory consumerFactory = createConsumerFactory();
		Header[] headers = createHeaders();

		//
		LOG.debug(".performRequest - index:{}", index);
		LOG.debug(".performRequest - qry:{}", qry);

		//
		Response response = restClient.performRequest(method, index + postfix, params, entity, consumerFactory, headers);
		String jsonString = EntityUtils.toString(response.getEntity());

		//
		LOG.trace(".performRequest - jsonString:{}", jsonString);
		return jsonString;
	}

}
