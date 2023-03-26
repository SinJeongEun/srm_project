package com.oti.team2.util.springsecurity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.oti.team2.member.dto.Join;
import com.oti.team2.member.dto.Member;
import com.oti.team2.member.dto.Users;
import com.oti.team2.member.service.IJoinService;
import com.oti.team2.member.service.IMemberService;
import com.oti.team2.util.Auth;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class KakaoService {
	
	@Autowired
	private IJoinService joinService;
	
	@Autowired
	private IMemberService memberService;
	
	public String getAuthURL() {
		String authURL = "https://kauth.kakao.com/oauth/authorize";
		authURL += "?client_id=33c22536c8a70efab80b6220fb9c3035";
		authURL += "&response_type=code";
		authURL += "&redirect_uri=http://localhost:8080/login/oauth2/kakao/callback";
		return authURL;
	}
	
	public String getAccessToken(String code) {
		String access_token = "";
		String refresh_token = "";
		
		try {
			String apiURL = "https://kauth.kakao.com/oauth/token";
			String reqMethod = "POST";
			String bodyData = "grant_type=authorization_code";
			bodyData += "&client_id=33c22536c8a70efab80b6220fb9c3035";
			bodyData += "&redirect_uri=http://localhost:8080/login/oauth2/kakao/callback";
			bodyData += "&code=" + code;
			
			URL url = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(reqMethod);
			conn.setDoOutput(true);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(bodyData);
			bw.flush();

			int responseCode = conn.getResponseCode();
			log.info("토큰 응답코드 : " + responseCode);
			
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}			
			
			String br_line = "";
			String resBody = "";
			while ((br_line = br.readLine()) != null) {
				resBody += br_line;
			}
			br.close();
			bw.close();
			
			if (responseCode == 200) {
				log.info(resBody);
				JSONObject root = new JSONObject(resBody);
				access_token = root.getString("access_token");
				refresh_token = root.getString("refresh_token");
				log.info("access_token: " + access_token);
				log.info("refresh_token: " + refresh_token);			
			} else {
				throw new Exception(resBody);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return access_token;
	}

	public Users getUserInfo(String access_token) {
		Map<String, String> resultMap = new HashMap<>();
		Users user = null;
		
		try {
			String reqURL = "https://kapi.kakao.com/v2/user/me";
			String reqMethod = "GET";
		
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Bearer " + access_token);

			int responseCode = conn.getResponseCode();
			log.info("사용자 응답코드 : " + responseCode);

			BufferedReader br;
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}			
			
			String br_line = "";
			String resBody = "";
			while ((br_line = br.readLine()) != null) {
				resBody += br_line;
			}
			br.close();

			if (responseCode == 200) {
				log.info(resBody);
				JSONObject root = new JSONObject(resBody);
				JSONObject properties = root.getJSONObject("properties");
				JSONObject kakao_account = root.getJSONObject("kakao_account");
				
				String id = String.valueOf(root.getLong("id"));
				String nickname = properties.getString("nickname");
				String email = kakao_account.getString("email");
				String picture = properties.getString("profile_image");
				log.info("id: " + id);
				log.info("nickname: " + nickname);
				log.info("email: " + email);
				
				resultMap.put("id", id);
				resultMap.put("nickname", nickname);
				resultMap.put("email", email);	
				Member member = memberService.isMember(email);
				List<GrantedAuthority> roles = new ArrayList<>();
				roles.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
				// 비회원인 경우 회원가입 진행
				if(member == null) {
					log.info("비회원이므로 조인한다~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					Join join = new Join();
					String randomPswd = UUID.randomUUID().toString().replaceAll("-", "");
					randomPswd = randomPswd.substring(0, 10);
					join.setMemberId(email);
					join.setPswd(joinService.getEncodedPassword(randomPswd));
					join.setFlnm(nickname);
					join.setEml(email);
					join.setMemberType(Auth.ROLE_CLIENT.toString());
					log.info(join);
					joinService.getJoin(join);
					 user = new Users(email, join.getPswd(),true, roles,
							 nickname, picture);
				}
				else {			      
			        user = new Users(member.getMemberId(), member.getPswd(),true, roles,
			        		member.getFlnm(), picture);
				}
			} else {
				throw new Exception(resBody);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
