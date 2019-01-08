package cam.cafe24.timetable.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TimeTableMapper {

	@Select("select t.full_code,  t.subject_code as code, t.name, t.code as \"sn\", t.score, array_agg(t.subject_time) AS \"time\" from (\r\n" + 
			"select \r\n" + 
			"b.subject_code,\r\n" + 
			"b.subject_code || b.subject_detail_code as \"full_code\",\r\n" + 
			"b.subject_detail_code as \"code\",\r\n" + 
			"A.subject_name as \"name\",\r\n" + 
			"A.SUBJECT_POINT AS \"score\",\r\n" + 
			"B.SUBJECT_DAY || B.SUBJECT_CLASS_TIME AS \"subject_time\"\r\n" + 
			"from SUBJECT_TIME B\r\n" + 
			"INNER JOIN subject A ON B.SUBJECT_CODE = A.SUBJECT_CODE\r\n" + 
			") t group by t.full_code, t.subject_code,  t.name, t.code,  t.score\r\n" + 
			"ORDER BY SUBJECT_CODE, CODE;")
	List<HashMap> selectSubject();

}
