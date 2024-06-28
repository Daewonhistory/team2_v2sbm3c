package dev.mvc.admitperson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dev.mvc.menu.Menu;
import dev.mvc.schedule.ScheduleVO;

@Service("dev.mvc.admitperson.AdmitPersonProc")
public class AdmitPersonProc implements AdmitPersonProcInter{
	@Autowired
	private AdmitPersonDAOInter admitPersonDAO;
	
	public int create(AdmitPersonVO admitPersonVO) {
		int cnt = this.admitPersonDAO.create(admitPersonVO);
		return cnt;
	}

	@Override
	public ArrayList<AdmitPersonVO> list_all() {
		ArrayList<AdmitPersonVO> list = this.admitPersonDAO.list_all();
		return list;
	}

	@Override
	public ArrayList<AdmitPersonVO> list_search_paging(HashMap<String, Object> map) {
		int begin_of_page = ((int) map.get("now_page") - 1) * AdmitPerson.RECORD_PER_PAGE;
		   
		
		int start_num = begin_of_page + 1;
		
		
		int end_num = begin_of_page + AdmitPerson.RECORD_PER_PAGE;   
		
		map.put("start_num", start_num);
		map.put("end_num", end_num);
		
		ArrayList<AdmitPersonVO> list = this.admitPersonDAO.list_search_paging(map);
		return list;
	}
	
	@Override
	public int list_search_count(HashMap<String, Object> map) {
		int cnt = this.admitPersonDAO.list_search_count(map);
		return cnt;
	}
	
	@Override
	public ArrayList<AdmitPersonVO> admit_list(Map<String, Object> map) {
		ArrayList<AdmitPersonVO> list = this.admitPersonDAO.admit_list(map);
		return list;
	}

	@Override
	public AdmitPersonVO read(int admitpersonno) {
		AdmitPersonVO admitPersonVO = this.admitPersonDAO.read(admitpersonno);
		return admitPersonVO;
	}

	@Override
	public int update_curr_person(int admitpersonno) {
		int cnt = this.admitPersonDAO.update_curr_person(admitpersonno);
		return cnt;
	}

	@Override
	public int update_admit_person(AdmitPersonVO admitPersonVO) {
		int cnt = this.admitPersonDAO.update_admit_person(admitPersonVO);
		return cnt;
	}
	
	@Override
	public int delete(int admitpersonno) {
		int cnt = this.admitPersonDAO.delete(admitpersonno);
		return cnt;
	}

	@Override
	public String pagingBox(int now_page, int search_count, int record_per_page, int page_per_block) {

	    int total_page = (int)(Math.ceil((double)search_count / record_per_page));
	    System.out.println("->total_page:" + record_per_page);
	    int total_grp = (int)(Math.ceil((double)total_page / page_per_block)); 
	    int now_grp = (int)(Math.ceil((double)now_page / page_per_block));  
	    System.out.println(search_count);

	    int start_page = ((now_grp - 1) * page_per_block) + 1; // 특정 그룹의 시작 페이지  
	    int end_page = (now_grp * page_per_block);               // 특정 그룹의 마지막 페이지   
	     
	    StringBuffer str = new StringBuffer(); // String class 보다 문자열 추가등의 편집시 속도가 빠름 
	    
	    
	    str.append("<div id='paging'>"); 

	    int _now_page = (now_grp - 1) * page_per_block;  
	    if (now_grp >= 2){ // 현재 그룹번호가 2이상이면 페이지수가 11페이지 이상임으로 이전 그룹으로 갈수 있는 링크 생성
	    	str.append("<span class='span_box_1' onclick='pageGrpMove(this)' data-page='"+_now_page+"'>이전</span>");
	    } 
	 
	    // 중앙의 페이지 목록
	    for(int i=start_page; i<=end_page; i++){ 
	      if (i > total_page){ // 마지막 페이지를 넘어갔다면 페이 출력 종료
	        break; 
	      } 
	  
	      if (now_page == i){ // 목록에 출력하는 페이지가 현재페이지와 같다면 CSS 강조(차별을 둠)
	        str.append("<span class='span_box_2' id='now_page'>"+i+"</span>"); // 현재 페이지, 강조 
	      }else{
	          str.append("<span class='span_box_1' onclick='pageMove(this)' data-page='"+i+"'>"+i+"</span>");
	      } 
	    } 
	 

	    _now_page = (now_grp * page_per_block)+1; //  최대 페이지수 + 1 
	    if (now_grp < total_grp){ 
	        str.append("<span class='span_box_1' onclick='pageGrpMove(this)' data-page='"+_now_page+"' >다음</span>");
	    } 
	    str.append("</div>"); 
	     
	    return str.toString(); 
	}

	@Override
	public int createBeginning(int restno, int reserveRange, ArrayList<ScheduleVO> scheduleVOList) {
		for(int i=0;i<reserveRange;i++) {
			AdmitPersonVO admitPersonVO = new AdmitPersonVO();
			
			LocalDate now = LocalDate.now();
			System.out.println(now);
			LocalDate addDate = now.plusDays(i);
			System.out.println(addDate);
			java.sql.Date sqlDate = java.sql.Date.valueOf(addDate);
			admitPersonVO.setReserve_date(sqlDate);
			
			admitPersonVO.setRestno(restno);
			
			for(int j=0;j<scheduleVOList.size();j++) {
				ScheduleVO scheduleVO = scheduleVOList.get(j);
				admitPersonVO.setTime(scheduleVO.getTime());
				admitPersonVO.setAdmit_person(scheduleVO.getAdmit_person());
				this.create(admitPersonVO);
			}
			
		}
		return 1;
	}

	
	
	
}
