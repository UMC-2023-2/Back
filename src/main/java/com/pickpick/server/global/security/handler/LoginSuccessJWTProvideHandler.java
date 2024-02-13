package com.pickpick.server.global.security.handler;

import com.pickpick.server.global.security.service.JwtService;
import com.pickpick.server.member.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
public class LoginSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private final JwtService jwtService;
	@Autowired
	private final MemberRepository memberRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		String email = extractEmail(authentication);
		String accessToken = jwtService.createAccessToken(email);
		String refreshToken = jwtService.createRefreshToken();

		jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
		memberRepository.findByEmail(email).ifPresent(
				member -> member.updateRefreshToken(refreshToken)
		);

		log.info( "로그인에 성공합니다. email: {}" , email);
		log.info( "AccessToken 을 발급합니다. AccessToken: {}" ,accessToken);
		log.info( "RefreshToken 을 발급합니다. RefreshToken: {}" ,refreshToken);

		response.getWriter().write("success");
	}

	private String extractEmail(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userDetails.getUsername();
	}
}
