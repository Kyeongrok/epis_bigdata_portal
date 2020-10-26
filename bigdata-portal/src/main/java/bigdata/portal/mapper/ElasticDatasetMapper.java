package bigdata.portal.mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.elastic.service.ElasticService;
import bigdata.portal.entity.EntityMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public class ElasticDatasetMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticDatasetMapper.class);

	@Autowired
	private ElasticService elasticService;

	/**
	 * esindex 조회
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public ElasticResultMap indexSearch(HashMap<String, Object> param) throws InstantiationException, IllegalAccessException, IOException {
		String indexNm = param.get("esIndexNm").toString() + "*";
		String pageNm = param.get("startNum").toString();
		String size = param.get("size").toString();
		String esOrderBy = param.get("esOrderBy") == null ? "" : param.get("esOrderBy").toString();
		String orderBy = "";
		String query = "";
		List<String> notEmptyList = (List<String>)param.get("notEmptyList");
		List<String> notMatchList = (List<String>)param.get("notMatchList");

		if(esOrderBy != null && !"".equals(esOrderBy)) {
			orderBy = "    , \"sort\" : [";

			for(String commaSplit : esOrderBy.split(",")) {
				orderBy +=	"{ \""+commaSplit.split(" ")[0] + "\" : \"" + commaSplit.split(" ")[1]+"\" }," ;
			}
			orderBy = orderBy.substring(0, orderBy.lastIndexOf(",")); // 마지막 쉼표 지우기
			orderBy += "]\n";
		}

		/* 값이 Empty, '' 인 데이터들은 제외한다.*/
		if((notEmptyList != null && notEmptyList.size() > 0)
				|| (notMatchList != null && notMatchList.size() > 0)) {

			query =		",\"query\" : {\n" +
								"	\"bool\" : {\n" ;

			//값이 Empty(null) 제외
			if(notEmptyList != null && notEmptyList.size() > 0) {
				query +=	"		\"must\" : [\n";
				// not empty
				for(String column : notEmptyList) {
					query += "		{\n"
								+	"			\"exists\" : {\n"
								+	"				\"field\" : \""+ column +"\"\n"
								+	"				}\n"
								+	"			}\n,";

				}
				query = query.substring(0, query.lastIndexOf(",")); // 마지막 쉼표 지우기
				query += 	"			]\n";
			}

			// 값이 '' 제외
			if(notMatchList != null && notMatchList.size() > 0) {
				if(notEmptyList != null && notEmptyList.size() > 0) query += ",";
				query +=	"		\"must_not\" : [\n";
				// not empty
				for(String column : notMatchList) {
					query += "		{\n"
								+	"			\"match\" : {\n"
								+	"				\""+ column +"\" : \"\"\n"
								+	"				}\n"
								+	"			}\n,";

				}
				query = query.substring(0, query.lastIndexOf(",")); // 마지막 쉼표 지우기
				query += 	"			]\n";
			}

			query +="		}\n" +
					"	}\n";
		}

		String requestJson =
				"{\n" +
				"  \"from\": \"" + pageNm + "\",\n" +
				"  \"size\": \"" + size + "\"\n" +
				orderBy +
				"\n" +
				query +
				"}\n";
		LOGGER.debug("indexSearch : {}",requestJson);
		return elasticService.postSearch(indexNm, requestJson);
	}

	/**
	 * 빅데이터셋 상세조회
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public ElasticResultMap staIndexSearch(HashMap<String, Object> param) throws InstantiationException, IllegalAccessException, IOException {
		String indexNm = param.get("esIndexNm").toString() + "*";
		String pageNm = param.get("startNum").toString();
		String esStaYear = param.get("prdDe").toString();
		String size = param.get("size").toString();
		String esOrderBy = param.get("esOrderBy") == null ? "" : param.get("esOrderBy").toString();
		String orderBy = "";
		String staYear = "";

		if(esOrderBy != null && !"".equals(esOrderBy)) {
			orderBy = "    , \"sort\" : [{ \"" + esOrderBy.split(" ")[0] + "\" : \"" + esOrderBy.split(" ")[1] + "\" }]\n";
		}

		if(esStaYear != null && !"".equals(esStaYear)) {
			staYear =         ",\"query\": {\n" +
			        "  \"bool\": {\n" +
			        "    \"must\" : [\n" +
			        "      {\n" +
			        "        \"term\": {\n" +
			        "          \"STA_YEAR\" : {\n" +
			        "            \"value\": \"" + esStaYear + "\"\n" +
			        "          }\n" +
			        "        }\n" +
			        "      }\n" +
			        "    ]\n" +
			        "  }\n" +
			        "}\n"  +
			        "";
		}

		String requestJson =
				"{\n" +
				"  \"from\": \"" + pageNm + "\",\n" +
				"  \"size\": \"" + size + "\"\n" +
				orderBy +
				staYear +
				"}\n" +
				"";
		return elasticService.postSearch(indexNm, requestJson);
	}

	public ElasticResultMap getStaYears(HashMap<String, Object> param) throws InstantiationException, IllegalAccessException, IOException {

		String indexNm = param.get("esIndexNm").toString() + "*";

		String requestJson =
		        "{ \n" +
                "  \"size\": 0, \n" +
                "  \"aggs\" : { \n" +
                "    \"unique_years\" : { \n" +
                "      \"terms\" : { \"field\" : \"STA_YEAR\" } \n" +
                "    } \n" +
                "  } \n" +
                "} \n" +
                "";

		return elasticService.postSearch(indexNm, requestJson);
	}

}
