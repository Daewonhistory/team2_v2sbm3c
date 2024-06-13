package dev.mvc.reserve;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("reserveProc")
public class ReserveProc implements ReserveProcInter {

    @Autowired
    private ReserveDAOInter reserveDAO;

    @Override
    public int create(ReserveVO reserve) {
        int cnt =  this.reserveDAO.create(reserve);
        return cnt;
    }

    @Override
    public ArrayList<ReserveVO> list_all() {
      ArrayList<ReserveVO> list = this.reserveDAO.list_all();
      return list;

    }

    @Override
    public ArrayList<ReserveVO> list_search_by_custno(int custno) {
      ArrayList<ReserveVO> list = this.reserveDAO.list_search_by_custno(custno);
      return list;
    }
    
    @Override
    public ArrayList<ReserveVO> list_search_by_reserve_date(Map<String, Object> params) {
        return this.reserveDAO.list_search_by_reserve_date(params);
    }

    @Override
    public int delete(int reserveno) {
      int cnt = this.reserveDAO.delete(reserveno);
      return cnt;
    }



}
