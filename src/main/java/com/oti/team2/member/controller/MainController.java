package com.oti.team2.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oti.team2.srdemand.dto.MytodoSrListDto;
import com.oti.team2.srdemand.service.ISrDemandService;
import com.oti.team2.util.Auth;
import com.oti.team2.util.pager.Pager;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class MainController {
	
	@Autowired
	private ISrDemandService srdemandService;
	
	@GetMapping("/myportal")
	public String mypotal(Authentication auth, Model model) {
		String role = auth.getAuthorities().stream().findFirst().get().toString();
		
		List<MytodoSrListDto> srList = null;
		Pager pager = null;
		if(role.equals(Auth.ROLE_CLIENT.toString())) {
			pager = srdemandService.getcountsByCustIdOrPicIdAndSttsCd(auth.getName() , null, 0, 1);
			srList = srdemandService.getMytodoSrList(auth.getName(), null, 0, pager);
		}else if(role.equals(Auth.ROLE_DEVELOPER.toString())) {
			pager = srdemandService.getcountsByEmpIdAndSttsCd(auth.getName(), 3, 1);
			srList = srdemandService.getMytodoSrListForDeveloper(auth.getName(), 3, pager);
		} else {
			pager = srdemandService.getcountsByCustIdOrPicIdAndSttsCd(null, auth.getName(), 0, 1);
			srList = srdemandService.getMytodoSrList(null, auth.getName(), 0, pager);
		}
		model.addAttribute("srList", srList);
		model.addAttribute("pager", pager);
		log.info(srList);
		return "member/my-todo";
	}
	
	@ResponseBody
	@GetMapping("/myportal/mytodo")
	public Map<String, Object> mytodo(Authentication auth
			, @RequestParam(required = true, name = "sttsCd", defaultValue = "0")int stts
			, @RequestParam(required = true, name = "page", defaultValue = "1")int pageNo) {

		String role = auth.getAuthorities().stream().findFirst().get().toString();		
		List<MytodoSrListDto> srList = null;
		Pager pager = null;
		
		if(role.equals(Auth.ROLE_CLIENT.toString())) {
			pager = srdemandService.getcountsByCustIdOrPicIdAndSttsCd(auth.getName() , null, stts, pageNo);
			srList = srdemandService.getMytodoSrList(auth.getName(), null, stts, pager);
		}
		else if(role.equals(Auth.ROLE_DEVELOPER.toString())) {
			pager = srdemandService.getcountsByEmpIdAndSttsCd(auth.getName(), stts, pageNo);
			srList = srdemandService.getMytodoSrListForDeveloper(auth.getName(), stts, pager);
		} 
		else {
			pager = srdemandService.getcountsByCustIdOrPicIdAndSttsCd(null, auth.getName(), stts, pageNo);
			srList = srdemandService.getMytodoSrList(null, auth.getName(), stts, pager);
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("srList", srList);
		map.put("pager", pager);
		log.info(srList);
		return map;
	}

}
