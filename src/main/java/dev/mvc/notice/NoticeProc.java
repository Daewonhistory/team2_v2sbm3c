package dev.mvc.notice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.notice.NoticeProc")
public class NoticeProc implements NoticeProcInter {
	@Autowired
	NoticeDAOInter noticeDAOInter;
	
	@Override
	public int create(NoticeVO noticeVO) {
		int cnt = this.noticeDAOInter.create(noticeVO);
		return cnt;
	}

	@Override
	public NoticeVO read(int noticeno) {
		NoticeVO noticeVO = this.noticeDAOInter.read(noticeno);
		return noticeVO;
	}

	@Override
	public ArrayList<NoticeVO> list_all() {
		ArrayList<NoticeVO> list = this.noticeDAOInter.list_all();
		return list;
	}

	@Override
	public ArrayList<NoticeVO> list_by_restno(int restno) {
		ArrayList<NoticeVO> list = this.noticeDAOInter.list_by_restno(restno);
		return list;
	}

	@Override
	public int update(NoticeVO noticeVO) {
		int cnt = this.noticeDAOInter.update(noticeVO);
		return cnt;
	}

	@Override
	public int delete(int noticenno) {
		int cnt = this.noticeDAOInter.delete(noticenno);
		return cnt;
	}

}
