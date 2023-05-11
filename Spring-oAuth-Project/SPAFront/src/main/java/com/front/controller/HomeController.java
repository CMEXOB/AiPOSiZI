package com.front.controller;

import com.front.entity.Employee;
import com.front.entity.Product;
import com.front.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Value("${rest.server.host}")
    private String basicUrl;

    @GetMapping("")
    public String greeting(Model model, Authentication authentication, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            String a = Arrays.stream(cookies).map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(", "));
            System.out.println(a);
        }
        if(authentication != null && authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User  userDetails = (DefaultOAuth2User ) authentication.getPrincipal();
            String username = userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login") ;

            RestTemplate restTemplate = new RestTemplate();
            String add = basicUrl+"/login";
            User user = new User(username);

            ResponseEntity<String> response = restTemplate.postForEntity(add, user, String.class);

            model.addAttribute("token", response.getBody());
            model.addAttribute("name", username);


        }
        else{
            model.addAttribute("token", null);

        }

        return "index";
    }
}
