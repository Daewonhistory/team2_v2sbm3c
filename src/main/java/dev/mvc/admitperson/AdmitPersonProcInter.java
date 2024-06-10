package dev.mvc.admitperson;

import java.util.ArrayList;
import java.util.Map;

public interface AdmitPersonProcInter {
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
	
	/**
	 * 예약정보 조회
	 * @param admitpersonno
	 * @return
	 */
	public AdmitPersonVO read(int admitpersonno);
	
	/**
	 * 현재 예약인원 갱신
	 * @param admitpersonno
	 * @return
	 */
	public int update_curr_person(int admitpersonno);
	
	/**
	 * 정원 변경
	 * @param admitpersonno
	 * @return
	 */
	public int update_admit_person(int admitpersonno);
}
