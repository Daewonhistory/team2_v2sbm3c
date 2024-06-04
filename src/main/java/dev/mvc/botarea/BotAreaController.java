package dev.mvc.botarea;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/botarea")
public class BotAreaController{
	@Autowired
	@Qualifier("dev.mvc.botarea.BotAreProc")
	BotAreaProcInter botAreaProc;
	
	@PostMapping("/botarea_list")
	@ResponseBody
	public ResponseEntity<ArrayList<BotAreaVO>> botAreaList(@RequestBody Map<String, Object> requestBody){
		String midAreano = (String) requestBody.get("midareano");
		System.out.println(midAreano);
		ArrayList<BotAreaVO> list = this.botAreaProc.search_by_midareano(Integer.parseInt(midAreano));
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
}
