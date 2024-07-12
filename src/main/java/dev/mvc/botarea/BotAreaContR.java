package dev.mvc.botarea;

import dev.mvc.midarea.MidAreaProcInter;
import dev.mvc.midarea.MidAreaVO;
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
@RequestMapping("/botarea/api")
public class BotAreaContR {
	@Autowired
	@Qualifier("dev.mvc.botarea.BotAreProc")
	BotAreaProcInter botAreaProc;

	@Autowired
	@Qualifier("dev.mvc.midarea.MidAreaProc")
	MidAreaProcInter midAreaProc;

	public BotAreaContR() {
		System.out.println("-> BotAreaCont Created.");
	}



	@PostMapping("/botarea_list")
	@ResponseBody
	public ResponseEntity<ArrayList<BotAreaVO>> botAreaList(@RequestBody Map<String, Object> requestBody){
		Integer midAreano = (Integer) requestBody.get("midareano");
		System.out.println(midAreano);
		ArrayList<BotAreaVO> list = this.botAreaProc.search_by_midareano((midAreano));
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
