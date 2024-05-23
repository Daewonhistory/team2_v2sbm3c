package dev.mvc.botarea;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.botarea.BotAreProc")
public class BotAreaProc implements BotAreaProcInter {
	@Autowired
	BotAreaDAOInter botAreaDAO;
	
	@Override
	public int create(BotAreaVO botAreaVO) {
		int cnt = this.botAreaDAO.create(botAreaVO);
		return cnt;
	}

	@Override
	public BotAreaVO read(int botareano) {
		BotAreaVO botAreaVO = this.read(botareano);
		return botAreaVO;
	}

	@Override
	public ArrayList<BotAreaVO> list_all() {
		ArrayList<BotAreaVO> list = this.list_all();
		return list;
	}

	@Override
	public ArrayList<BotAreaVO> search_list(String word) {
		ArrayList<BotAreaVO> list = this.search_list(word);
		return list;
	}

	@Override
	public ArrayList<BotAreaVO> search_by_midareano(int midareano) {
		ArrayList<BotAreaVO> list = this.search_by_midareano(midareano);
		return list;
	}

	@Override
	public int update(BotAreaVO botAreaVO) {
		int cnt = this.update(botAreaVO);
		return cnt;
	}

	@Override
	public int delete(int botareano) {
		int cnt = this.delete(botareano);
		return cnt;
	}

}
