package com.oti.team2.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oti.team2.member.dao.IMemberDao;
import com.oti.team2.member.dto.Users;
import com.oti.team2.util.springsecurity.MyPasswordEncoder;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AccountService implements UserDetailsService{
	
	@Autowired
	private IMemberDao memberDao;

	/**
	 * 로그인 인증 처리
	 */
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		log.info("서비스 들어옴~~~~~~~~~~~~~~");
		Users user = memberDao.selectByMemberId(memberId);
		if(user == null) throw new UsernameNotFoundException("없는 사용자입니다.");
		
		String pw = user.getPassword();

		PasswordEncoder passwordEncoder = MyPasswordEncoder.createDelegatingPasswordEncoder();
		String postpw = passwordEncoder.encode(pw);
		
		user.setPswd(postpw);
		
		
		log.info(user);
		return user;
	}

}
