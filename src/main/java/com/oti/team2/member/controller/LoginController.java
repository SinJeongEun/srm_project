package com.oti.team2.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.plus.PlusOperations;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class LoginController {
	/* GoogleLogin */
	@Autowired
	private GoogleConnectionFactory googleConnectionFactory;
	@Autowired
	private OAuth2Parameters googleOAuth2Parameters;

	/**
	 * 로그인 메소드
	 *
	 * @author 최은종, 신정은
	 * 
	 * @return 로그인 페이지로 리턴
	 */
	@GetMapping("/loginForm")
	public String getLogin(Model model) {
		/* 구글code 발행 */
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		String url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);

		System.out.println("구글:" + url);

		model.addAttribute("google_url", url);
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
	public Authentication auth(Authentication auth) { // @AuthenticationPrincipal Users user
		return auth;
	}

	@PostMapping("/login/oauth2/code/google")
	public String doGoogleSignInActionPage(HttpServletResponse response, Model model) throws Exception {
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		String url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
		System.out.println("/member/googleSignIn, url : " + url);
		model.addAttribute("url", url);
		
		
		return "login/googleLogin";

	}
	
	@RequestMapping(value = "/login/oauth2/code/google", method = RequestMethod.POST)
	public String doSessionAssignActionPage(HttpServletRequest request)throws Exception{
	  log.info("=======================================");
	  String code = request.getParameter("code");
	  log.info("---  code ---" + code);
	  OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
	  AccessGrant accessGrant = oauthOperations.exchangeForAccess(code , googleOAuth2Parameters.getRedirectUri(),
	      null);

	  String accessToken = accessGrant.getAccessToken();
	  Long expireTime = accessGrant.getExpireTime();
	  if (expireTime != null && expireTime < System.currentTimeMillis()) {
		  log.info("3333333333333333333333");
	    accessToken = accessGrant.getRefreshToken();
	    log.info("accessToken is expired. refresh token = {}", accessToken);
	  }
	  Connection<Google> connection = googleConnectionFactory.createConnection(accessGrant);
	  Google google = connection == null ? new GoogleTemplate(accessToken) : connection.getApi();

	  PlusOperations plusOperations = google.plusOperations();
	  Person profile = plusOperations.getGoogleProfile();
	  /*UserVO vo = new UserVO();*/
	  log.info("222222222222222222222222222222");
	  /*vo.setUser_email("구글 로그인 계정");
	  vo.setUser_name(profile.getDisplayName());
	  vo.setUser_snsId("g"+profile.getId());*/
	  /*HttpSession session = request.getSession();
	  vo = service.googleLogin(vo);

	  session.setAttribute("login", vo );
*/

	  return "redirect:/mytortal";
	}
}
