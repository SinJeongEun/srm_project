
package com.oti.team2;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oti.team2.util.springsecurity.MyPasswordEncoder;

import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author kosa
 *
 */
@Controller
@Log4j2
public class HomeController {
	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * 
	 *
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		/*String prepw = "1234";

		PasswordEncoder passwordEncoder = MyPasswordEncoder.createDelegatingPasswordEncoder();
		String postpw = passwordEncoder.encode(prepw);
		boolean match = passwordEncoder.matches(prepw, postpw);
		log.info(postpw);
		log.info(match);*/
		return "member/my-todo";
	}

}
