package dev.mvc.reserve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.mvc.dto.ReserveDTO;


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
    
    @Override
    public ArrayList<ReserveDTO> list_reserve_paging(int now_page, int record_per_page) {
      /*
      예) 페이지당 10개의 레코드 출력
      1 page: WHERE r >= 1 AND r <= 10
      2 page: WHERE r >= 11 AND r <= 20
      3 page: WHERE r >= 21 AND r <= 30

       예) 페이지당 3개의 레코드 출력
      1 page: WHERE r >= 1 AND r <= 3
      2 page: WHERE r >= 4 AND r <= 6
      3 page: WHERE r >= 7 AND r <= 9

      페이지에서 출력할 시작 레코드 번호 계산 기준값, nowPage는 1부터 시작
      1 페이지 시작 rownum: now_page = 1, (1 - 1) * 10 --> 0
      2 페이지 시작 rownum: now_page = 2, (2 - 1) * 10 --> 10
      3 페이지 시작 rownum: now_page = 3, (3 - 1) * 10 --> 20
      */
      int begin_of_page = (now_page - 1) * record_per_page;

      // 시작 rownum 결정
      // 1 페이지 = 0 + 1: 1
      // 2 페이지 = 10 + 1: 11
      // 3 페이지 = 20 + 1: 21
      int start_num = begin_of_page + 1;

      //  종료 rownum
      // 1 페이지 = 0 + 10: 10
      // 2 페이지 = 10 + 10: 20
      // 3 페이지 = 20 + 10: 30
      int end_num = begin_of_page + record_per_page;
      /*
      1 페이지: WHERE r >= 1 AND r <= 10
      2 페이지: WHERE r >= 11 AND r <= 20
      3 페이지: WHERE r >= 21 AND r <= 30
      */

      // System.out.println("begin_of_page: " + begin_of_page);
      // System.out.println("WHERE r >= "+start_num+" AND r <= " + end_num);
      Map<String, Object> map = new HashMap<String, Object>();

      map.put("start_num", start_num);
      map.put("end_num", end_num);
      ArrayList<ReserveDTO> list = this.reserveDAO.list_reserve_paging(map);
      
      System.out.println(start_num);
      System.out.println(end_num);
      return list;
    }
    
    @Override
    public ArrayList<ReserveDTO> list_owner_page(int now_page, int record_per_page, String reserve_date) {
        int begin_of_page = (now_page - 1) * record_per_page;
        int start_num = begin_of_page + 1;
        int end_num = begin_of_page + record_per_page;

        Map<String, Object> map = new HashMap<>();
        map.put("start_num", start_num);
        map.put("end_num", end_num);
        map.put("reserve_date", reserve_date); // reserve_date 추가

        ArrayList<ReserveDTO> list = this.reserveDAO.list_owner_page(map);
        return list;
    }

    @Override
    public int count_all() {
        return reserveDAO.count_all();
    }

    @Override
    public String pagingBox(int now_page, String list_file, int total_count, int record_per_page, int page_per_block) {
        int total_page = (int) (Math.ceil((double) total_count / record_per_page));
        int total_grp = (int) (Math.ceil((double) total_page / page_per_block));
        int now_grp = (int) (Math.ceil((double) now_page / page_per_block));

        int start_page = ((now_grp - 1) * page_per_block) + 1;
        int end_page = (now_grp * page_per_block);

        StringBuilder str = new StringBuilder();
        str.append("<nav aria-label='...'>");
        str.append("<ul class='pagination justify-content-center'>");

        int _now_page = (now_grp - 1) * page_per_block;
        if (now_grp >= 2) {
            str.append("<li class='page-item'><a class='page-link' href='")
                .append(list_file).append("?now_page=").append(_now_page)
                .append("'>이전</a></li>");
        }

        for (int i = start_page; i <= end_page; i++) {
            if (i > total_page) {
                break;
            }
            if (now_page == i) {
                str.append("<li class='page-item active'><a class='page-link'>")
                    .append(i).append("</a></li>");
            } else {
                str.append("<li class='page-item'><a class='page-link' href='")
                    .append(list_file).append("?now_page=").append(i)
                    .append("'>").append(i).append("</a></li>");
            }
        }

        _now_page = (now_grp * page_per_block) + 1;
        if (now_grp < total_grp) {
            str.append("<li class='page-item'><a class='page-link' href='")
                .append(list_file).append("?now_page=").append(_now_page)
                .append("'>다음</a></li>");
        }
        str.append("</ul>");
        str.append("</nav>");

        return str.toString();
    }




}
