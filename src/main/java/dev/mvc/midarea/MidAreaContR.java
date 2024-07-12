package dev.mvc.midarea;

import dev.mvc.category.CategoryVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/midarea/api")
public class MidAreaContR {

	@Autowired
	@Qualifier("dev.mvc.midarea.MidAreaProc")
	MidAreaProcInter midAreaProc;


	@GetMapping("/midAreaList") // HTTP GET 요청을 처리하는 메서드
	public ResponseEntity<ArrayList<MidAreaVO>> findAll() {
		ArrayList<MidAreaVO> midAreaList = this.midAreaProc.list_all(); // 카테고리 목록 조회
		return new ResponseEntity<>(midAreaList, HttpStatus.OK); // 목록과 상태코드 반환
	}
	
}
