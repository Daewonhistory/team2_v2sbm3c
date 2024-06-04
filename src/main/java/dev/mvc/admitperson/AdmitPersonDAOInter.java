package dev.mvc.admitperson;

import java.util.ArrayList;
import java.util.Map;

public interface AdmitPersonDAOInter {
	/**
	 * 모든 허용인원정보 조회
	 * @return
	 */
	public ArrayList<AdmitPersonVO> list_all();
	
	/**
	 * 식당의 조건에 적합한 시간대 조회
	 * @param map person, reserve_date, time, restno
	 * @return
	 */
	public ArrayList<AdmitPersonVO> admit_list(Map<String, Object> map);
	
	public int update_admit_person();
}
