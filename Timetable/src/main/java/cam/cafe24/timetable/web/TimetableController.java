package cam.cafe24.timetable.web;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cam.cafe24.timetable.service.TimeTableService;

@RestController
public class TimetableController {

	@Autowired
	private TimeTableService timeTableService;
	
	@GetMapping("/")
	public List<HashMap> selectSubjectInfo(){
		return  timeTableService.collect();
		 
	}
	

	
	
}


