package com.oti.team2.member.controller;


import java.io.IOException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oti.team2.member.dto.EmployeeList;
import com.oti.team2.member.dto.File;
import com.oti.team2.member.dto.Member;
import com.oti.team2.member.dto.ProfileImg;
import com.oti.team2.member.service.IJoinService;
import com.oti.team2.member.service.IMemberService;
import com.oti.team2.srresource.service.ISrResourceService;
import com.oti.team2.util.Auth;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/member")
public class MemberController {
	@Autowired
	private IMemberService memberService;
	@Autowired
	private IJoinService joinService;
	@Autowired
	private ISrResourceService srResourceService;
	/**
	 * 멤버의 내 정보 조회
	 *@author : 신정은
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/myinfo")
	public String getMember(HttpSession session, Model model, Authentication auth) {
		//session 에서 사용자 정보 가져오기
		String role = auth.getAuthorities().stream().findFirst().get().toString();
		Member member = memberService.getMember(auth.getName(), role);
		log.info(member);
		model.addAttribute("member", member);
		return"member/myinfo";
	}
	
	/**
	 * 모든 사용자의 자신의 정보 수정
	 *@author : 신정은
	 * @param member
	 * @return
	 */
	@PostMapping("/myinfo")
	public String updateMember(Member member) {
		log.info(member);
		memberService.updateMember(member);
		return "redirect:/member/myinfo";
	}
	
	/**
	 * 
	 멤버의 프로필 사진 조회
	 수정 필요 !!!
	 *@author : 신정은
	 * @param memberId
	 * @return
	 */
	@GetMapping("/profile/{memberId}")
	public ResponseEntity<byte[]> getProfile(@PathVariable String memberId) {
		ProfileImg profileImg = memberService.getProfileImg(memberId);
		log.info(profileImg);
		HttpHeaders headers = new HttpHeaders();
		String mtypes[] = profileImg.getFileType().split("/");
		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
		return new ResponseEntity<byte[]>(profileImg.getFileData(),  headers, HttpStatus.OK);
		
	}
	
	/**
	 * 개발자 목록 조회
	 * @author : 안한길
	 * @param deptCd
	 * @return List<developer>
	 */
	@ResponseBody
	@GetMapping("/department")
	public EmployeeList getEmployeeList(@RequestParam() String deptCd){
		log.info(deptCd);
		EmployeeList employeeList = new EmployeeList();
		employeeList.setDevelopers(memberService.getEmployeeNameList(deptCd));
		employeeList.setSchedule(srResourceService.getSrResourceListByEmpId(employeeList.getDevelopers().get(0).getEmpId()));
		log.info(employeeList.getSchedule());
		return employeeList;
	}
	
	/**
	 * 프로필 사진 등록(수정)
	 * @author : 여수한
	 * @param File
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@PostMapping("/profile")
	public void addFile(@RequestParam("file") MultipartFile multi, File file, Authentication auth) throws Exception {
		String memberId = auth.getName();
		log.info("컨트롤러 들어옴 : memberId = " + memberId);
		if(multi!=null && !multi.isEmpty()) {
			file.setFileContentType(multi.getContentType());
			file.setFileData(multi.getBytes());
			log.info("fileData: " + file.getFileData());
			joinService.addFile(file,memberId);
		}
	}
}
