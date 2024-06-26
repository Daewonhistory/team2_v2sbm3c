package dev.mvc.schedule;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("dev.mvc.schedule.ScheduleProc")
public class ScheduleProc implements ScheduleProcInter {
	@Autowired
	ScheduleDAOInter scheduleDAO;
	@Override
	public int create(ScheduleVO scheduleVO) {
		int cnt = this.scheduleDAO.create(scheduleVO);
		return cnt;
	}
	
	@Override
	public int createFullSchedule(int[] admitPersons, int[] times, int restno) {
		int cnt = 0;
		for(int i=0; i<=23; i++) {
			ScheduleVO scheduleVO = new ScheduleVO();
			scheduleVO.setTime(i);
			scheduleVO.setRestno(restno);
			scheduleVO.setAdmit_person(0);
			// 허용인원이 직접 지정된 시각의 허용인원 추가 default는 0;
			for(int j=0; j<times.length; j++) {
				if(i==times[j])
					scheduleVO.setAdmit_person(admitPersons[j]);
			}
			
			
			cnt = this.scheduleDAO.create(scheduleVO);
			if(cnt == 0) {
				System.out.println(scheduleVO.getRestno() + "번 레스토랑 " + scheduleVO.getTime() + "시 스케줄 생성실패");
			}
		}
		return cnt;
	}
	
	@Override
	public ArrayList<ScheduleVO> list_all() {
		ArrayList<ScheduleVO> list = this.scheduleDAO.list_all();
		return list;
	}

	@Override
	public ArrayList<ScheduleVO> list_by_restno(int restno) {
		ArrayList<ScheduleVO> list = this.scheduleDAO.list_by_restno(restno);
		return list;
	}

	@Override
	public ScheduleVO read(int schduleno) {
		ScheduleVO scheduleVO = this.scheduleDAO.read(schduleno);
		return scheduleVO;
	}

	@Override
	public int update(ScheduleVO scheduleVO) {
		int cnt = this.scheduleDAO.update(scheduleVO);
		return cnt;
	}
	
	@Override
	public int updateFullSchedule(int[] admitPersons, int[] times, int restno) {
		return 1;
	}
	
	@Override
	public int delete(int scheduleno) {
		int cnt = this.scheduleDAO.delete(scheduleno);
		return cnt;
	}

	

}
