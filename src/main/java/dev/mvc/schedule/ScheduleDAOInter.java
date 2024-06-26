package dev.mvc.schedule;

import java.util.ArrayList;

public interface ScheduleDAOInter {
	/**
	 * 스케줄 생성
	 * @param scheduleVO
	 * @return
	 */
	public int create(ScheduleVO scheduleVO);
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
	 * 스케줄 삭제
	 * @param scheduleno
	 * @return
	 */
	public int delete(int scheduleno);
	
}
