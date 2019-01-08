package cam.cafe24.timetable.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

public class Search {
   static int minimumScore = 18;
   static int maximumScore = 21;
   static List<HashMap> resultList = new ArrayList<HashMap>();
   
   List<HashMap> allList;

   public Search(List<HashMap> allList) {
	this.allList = allList;
}

/**
    * 조합 만드는 재귀함수
    * @param TargetList 더 담아볼 후보 리스트
    * @param thisMap 지금 만들고있는 조합
    * @param totalScore 지금까지 만들어진 조합의 총 학점
    * @param totalTimes 지금까지 만들어진 조합의 시간표
    */
   protected void cartesianProduct(List<HashMap> TargetList, HashMap<String, Object> thisMap, int totalScore, String[] totalTimes) {
      HashMap<String, Object> returnHm = new HashMap<String, Object>();
      
      // 남은 모든 TargetList에 대하여
      for (int i = 0; i < TargetList.size(); i++) {
         HashMap tmpMap = TargetList.get(i);
         int tmpTotalScore = totalScore + (int) tmpMap.get("score");
         
         // 더 담으려니까 학점 초과
         if (tmpTotalScore <= maximumScore) {
            // 지금까지 만들어진 조합에 이번 과목을 추가
            HashMap<String, Object> tmpReturnMap = (HashMap<String, Object>) thisMap.clone();
            ArrayList<String> both = new ArrayList(Arrays.asList(totalTimes));
            
            both.addAll(((List) tmpMap.get("time")));
            tmpReturnMap.put("tTime", both.toString());
            tmpReturnMap.put("tScore", tmpTotalScore);
            
            // 이미 듣는 과목이 아니고, 이미 듣는 시간이 아니면
            if (!hasThisTime(tmpMap, totalTimes) && !hasThisProduct(tmpMap, tmpReturnMap)) tmpReturnMap.put((String) tmpMap.get("full_code"), tmpMap);
            // 이미 듣는 과목이거나 이미 듣는 시간이면 이 조합은 더 이상 진행하지 않는다.
            else break;
            
            if (tmpTotalScore >= minimumScore) {
               // 이번거 담고 마감
               resultList.add(tmpReturnMap);
            }
            
            // 빈 학점 있고 후보가 남아있으면 한번 더 도전
            if (minimumScore < maximumScore) {
               if (i < TargetList.size() - 1) {
                  for (int j = i + 1; j < TargetList.size(); j++) { 
                     cartesianProduct(allList.subList(j, allList.size()), tmpReturnMap, tmpTotalScore, both.toArray(new String[0]));
                  }
               }
            }
            
         }
      }
   }
   
   // 이미 듣는 과목인가?
   protected boolean hasThisProduct(HashMap<String, Object> tmpMap, HashMap<String, Object> thisMap) {
      for (String k : thisMap.keySet()) {
         if (k != "tScore" && k != "tTime") {
            HashMap o = (HashMap) thisMap.get(k);
            if ((String) o.get("code") == (String) tmpMap.get("code")) return true;
         }
      }
      return false;
   }
   
   // 이미 듣는 시간인가?
   protected static Boolean hasThisTime(HashMap<String, Object> tmpMap, String[] totalTimes) {
	      Boolean bool = false;
	      List times = (List) tmpMap.get("time");
	      for (Object s : times) {
	         if (Arrays.asList(totalTimes).contains(s)) {
	            bool = true;
	            break;
	         }
	      }
	      return bool;
	   }
}
