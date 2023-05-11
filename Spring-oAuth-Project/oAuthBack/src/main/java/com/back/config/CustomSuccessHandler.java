//package com.back.config;
//
//import com.back.entity.User;
//import com.back.repository.UserRepository;
//import com.back.security.DefaultUserServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class CustomSuccessHandler implements AuthenticationSuccessHandler{
//
//	@Autowired
//	UserRepository userRepo;
//
//	@Autowired
//	DefaultUserServiceImpl userService;
//
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//			Authentication authentication) throws IOException, ServletException {
//
//		String redirectUrl = null;
//		if(authentication.getPrincipal() instanceof DefaultOAuth2User) {
//		DefaultOAuth2User  userDetails = (DefaultOAuth2User ) authentication.getPrincipal();
//         String username = userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login")+"@gmail.com" ;
//          if(userRepo.findUserByEmail(username) == null) {
//        	  User user = new User();
//        	  user.setEmail(username);
//        	  user.setName(userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login"));
////        	  user.setPassword(("Dummy"));
////        	  user.setRole("USER");
//        	  userService.save(user);
//          }
//		}  redirectUrl = "/dashboard";
//		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
//	}
//
//}
