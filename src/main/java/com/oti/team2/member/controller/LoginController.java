package com.oti.team2.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oti.team2.member.dto.Users;
import com.oti.team2.util.springsecurity.GoogleService;
import com.oti.team2.util.springsecurity.KakaoService;
import com.oti.team2.util.springsecurity.NaverService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class LoginController {
	
	@Autowired
    private GoogleService googleService;
	
	@Autowired
	private KakaoService kakaoService;
	
	@Autowired
	private NaverService naverService;
	
	/**
	 * 로그인 메소드
	 *
	 * @author 최은종, 신정은
	 * 
	 * @return 로그인 페이지로 리턴
	 */
	@GetMapping("/loginForm")
	public String getLogin() {		
		
		return "member/login";
	}
	

	/**
	 * 로그인 메소드
	 *
	 * @author 최은종, 신정은
	 * @param Authentication 스프링 시큐리티가 제공하는 유저의 권한을 얻어오는 객체 매개변수로 사용
	 * @return auth 객체 리턴
	 */
	@ResponseBody
	@GetMapping("/auth")
	public Authentication auth(Authentication auth) { //@AuthenticationPrincipal Users user
		return auth;
	}	
	
	@GetMapping("/google/login")
	public String googleLogin() {
		log.info("실행");
		return "redirect:" + googleService.getAuthURL();
	}     
    
    @RequestMapping("/login/oauth2/google/callback")
    public String googleLogin(String code) throws Exception {
    	log.info("code: " + code);
    	
    	String access_token = googleService.getAccessToken(code);
    	
    	Users user = googleService.getUserInfo(access_token);    	
                
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());        
        SecurityContextHolder.getContext().setAuthentication(auth);
       
        return "redirect:/";
    }    
    
    @GetMapping("/kakao/login")
	public String kakaoLogin() {
		log.info("실행");
		return "redirect:" + kakaoService.getAuthURL();
	}  
    
    @RequestMapping("/login/oauth2/kakao/callback")
    public String kakaoLogin(String code) throws Exception {
    	log.info("code: " + code);
    	
    	String access_token = kakaoService.getAccessToken(code);
    	Users user = kakaoService.getUserInfo(access_token);    
    	log.info(user);             
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());        
        SecurityContextHolder.getContext().setAuthentication(auth);
       
        return "redirect:/";
    }   
    
    @GetMapping("/naver/login")
	public String naveroLogin() {
		log.info("실행");
		return "redirect:" + naverService.getAuthURL();
	}  
    
    @RequestMapping("/login/oauth2/naver/callback")
    public String naverLogin(String code, String state) throws Exception {
    	log.info("code: " + code);
    	log.info("state: " + state);
    	
    	String access_token = naverService.getAccessToken(code, state);
    	Users user = naverService.getUserInfo(access_token);    
    	log.info(user); 
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());        
        SecurityContextHolder.getContext().setAuthentication(auth);
       
        return "redirect:/";
    }   
}
