package vn.iotstar.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@Service
public class AuthSuccessHandlerImpl implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Set<String> roles = AuthorityUtils.authorityListToSet(authorities);
		String userId = authentication.getName();
		System.out.println("User ID: " + userId);
		HttpSession session = request.getSession();
		session.setAttribute("useremail", userId);
		if (roles.contains("ROLE_ADMIN")) {
			response.sendRedirect("/admin/");
		} else {
			response.sendRedirect("/user");
		}
	}

}
