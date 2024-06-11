package dev.mvc.admitperson;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.botarea.BotAreaVO;

@Controller
@RequestMapping("/admitPerson")
public class AdmitPersonController {
	@Autowired
	@Qualifier("dev.mvc.admitperson.AdmitPersonProc")
	private AdmitPersonProcInter admitPersonProc;
	
	@PostMapping("/searchPossibleTime")
	@ResponseBody
	public ResponseEntity<ArrayList<AdmitPersonVO>> searchPossibleTime(@RequestBody Map<String, Object> requestBody){
		
		String date = ((String) requestBody.get("date")) + " 00:00:00";
		System.out.println("date:"+ date);
		int personnel = Integer.parseInt((String) requestBody.get("personnel"));
		requestBody.replace("date", date);
		requestBody.replace("personnel", personnel);
		ArrayList<AdmitPersonVO> list = this.admitPersonProc.admit_list(requestBody);
		System.out.println("리스트사이즈:" + list.size());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}	
}
