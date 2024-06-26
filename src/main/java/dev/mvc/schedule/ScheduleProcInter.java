package dev.mvc.schedule;

import java.util.ArrayList;

public interface ScheduleProcInter {
	/**
	 * 스케줄 생성
	 * @param scheduleVO
	 * @return
	 */
	public int create(ScheduleVO scheduleVO);
	
	/**
	 * 총 스케줄(24시간) 생성
	 * @param scheduleList
	 * @return
	 */
	public int createFullSchedule(int[] admitPersons, int[] times, int restno);
	/**
	 * 모든 스케줄 조회
	 * @return
	 */
	public ArrayList<ScheduleVO> list_all();
	/**
	 * 가게의 스케줄 조회
	 * @param restno
	 * @return
	 */
	public ArrayList<ScheduleVO> list_by_restno(int restno);
	
	public ScheduleVO read(int schduleno);
	/**
	 * 스케줄 조정
	 * @param scheduleVO
	 * @return
	 */
	public int update(ScheduleVO scheduleVO);
	
	/**
	 * 총 스케줄 (24시간) 수정
	 * @param scheduleList
	 * @return
	 */
	public int updateFullSchedule(int[] admitPersons, int[] times, int restno);
	
	/**
	 * 스케줄 삭제
	 * @param scheduleno
	 * @return
	 */
	public int delete(int scheduleno);
}
