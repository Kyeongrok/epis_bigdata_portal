package bigdata.api.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.api.web.ApiRestController;
import bigdata.portal.entity.EntityMap;
import bigdata.portal.hive.HiveJdbcConn;
import bigdata.portal.mapper.OkdabLoginMapper;
import bigdata.portal.service.HdfsService;
import bigdata.portal.service.HiveService;

@Service
public class ApiRestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiRestController.class);

	
	private HiveJdbcConn hiveJdbcConn;
	private HiveService hiveService;
	private HdfsService hdfsService;

	public List<EntityMap> selectMarketPrice() {
		return null;
	}
}
