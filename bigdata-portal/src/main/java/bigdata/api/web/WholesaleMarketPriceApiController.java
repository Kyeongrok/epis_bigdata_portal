package bigdata.api.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bigdata.api.service.ApiRestService;
import bigdata.portal.entity.EntityList;
import bigdata.portal.entity.EntityMap;
import bigdata.portal.service.UserSettingService;

@RequestMapping("/api")
@Controller
public class WholesaleMarketPriceApiController extends ApiRestController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WholesaleMarketPriceApiController.class);
	@Autowired private UserSettingService userSettingService;

	@RequestMapping(value = "/test.{dataType}", method = RequestMethod.GET)
	public String wholesaleMarketPriceApi(@PathVariable String dataType, Model model) {

		EntityMap result = userSettingService.selectUserSetting("TEST1");
		EntityMap test = userSettingService.selectUserSetting("TEST1");
		EntityMap test2 = userSettingService.selectUserSetting("TEST1");

		ArrayList<EntityMap> result2 = new ArrayList<EntityMap>();
		result.put("tests", test);
		
		Map aaa = new HashMap<String, String>();
		aaa.put("xxx", "xxxxx");
		test.put("tests2", test2);
				
		result2.add(result);
		result2.add(result);
		result2.add(result);
		
		EntityList<EntityMap> entityList = new EntityList<EntityMap>();
		entityList.addAll(result2);
		
		model.addAttribute("result", entityList);

		// TODO : 
		// <result><items><item><a>b</a></item><item><a>b</a></item></items></result>
		// {result : {items : [{a:b}, {a:b}]}}
		
		return dataType + "View";
	}
//
//	// TODO XML 출력 테스트 코드
//	@SuppressWarnings("rawtypes")
//	public String getTransitionListXML(EntityList entityList) throws Exception {
//		JAXBContext ctx = JAXBContext.newInstance(EntityList.class);
//		Marshaller m = ctx.createMarshaller();
//		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//		m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//
//		JAXBElement<EntityList> tramlWrap = new JAXBElement<EntityList>(new QName("ssss", "item"), EntityList.class, entityList);
//
//		StringWriter sw = new StringWriter();
//		m.marshal(tramlWrap, sw);
//
//		return sw.toString();
//	}
}
