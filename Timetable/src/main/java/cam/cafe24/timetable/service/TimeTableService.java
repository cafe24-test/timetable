package cam.cafe24.timetable.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cam.cafe24.timetable.mapper.TimeTableMapper;

@Service
public class TimeTableService {

	private static final Logger LOG = LoggerFactory.getLogger(TimeTableService.class);

	@Autowired
	TimeTableMapper timeTableMapper;

	public List<HashMap> selectSubject() {
		return timeTableMapper.selectSubject();
	}

	public List<HashMap> collect() {
		
		long starttime = System.currentTimeMillis();
		List<HashMap> allList =  selectSubject();
		for(HashMap<String, Object> hm : allList) {
			String[] stringValues = ((String[])(hm.get("time")));
			List tmp = Arrays.asList(stringValues);
			hm.remove("time");
			hm.put("time",tmp);
		}
		Search search = new Search(allList);
		// 실행
		for (int i = 0; i < allList.size(); i++) {
			search.cartesianProduct(allList.subList(i, allList.size()), new HashMap<String, Object>(), 0, new String[0]);
		} 
		System.out.println(search.resultList.size());
		if(search.resultList.size()>10000) {
			return search.resultList.subList(0,1);
		}
		return search.resultList;
	}
	
}
