package com.oti.team2.util.springsecurity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
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
public class NaverService {
	@Autowired
	private IJoinService joinService;
	
	@Autowired
	private IMemberService memberService;
	
	public String getAuthURL() {
		String authURL = "https://nid.naver.com/oauth2.0/authorize";
		authURL += "?client_id=AV2ZRKb9eBdiQsbemO0I";
		authURL += "&response_type=code";
		authURL += "&redirect_uri=http://localhost:8080/login/oauth2/naver/callback";
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString();
		authURL += "&state=" + state;
		return authURL;
	}	
	
	public String getAccessToken(String code, String state) {
        String access_token = "";
        String refresh_token = "";
		
        try {
	        String apiURL = "https://nid.naver.com/oauth2.0/token";
	        String reqMethod = "GET";
	        apiURL += "?grant_type=authorization_code";
	        apiURL += "&client_id=AV2ZRKb9eBdiQsbemO0I";
	        apiURL += "&client_secret=z72IVQQzpI";
	        apiURL += "&redirect_uri=" + URLEncoder.encode("http://localhost:8080/login/oauth2/naver/callback", "UTF-8");
	        apiURL += "&code=" + code;
	        apiURL += "&state=" + state;
	        
	        URL url = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(reqMethod);
			
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
        } catch(Exception e) {
        	e.printStackTrace();
        }
		
		return access_token;
	}
	
	public Users getUserInfo(String access_token) {
		Map<String, String> resultMap = new HashMap<>();
		Users user = null;
		try {
			String reqURL = "https://openapi.naver.com/v1/nid/me";
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
				JSONObject response = root.getJSONObject("response");
				
				String id = response.getString("id");
				String name = response.getString("name");
				String mobile = response.getString("mobile");
				String email = response.getString("email");
				String picture = response.getString("profile_image");
				log.info("id: " + id);
				log.info("name: " + name);
				log.info("mobile: " + mobile);
				log.info("picture: " + picture);
		
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
					join.setFlnm(name);
					join.setEml(email);
					join.setTelNo(mobile);
					join.setMemberType(Auth.ROLE_CLIENT.toString());
					log.info(join);
					joinService.getJoin(join);
					 user = new Users(email, join.getPswd(),true, roles,
							 name, picture);
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
