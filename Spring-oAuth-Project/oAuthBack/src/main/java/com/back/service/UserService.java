package com.back.service;

import com.back.config.TokenConventor;
import com.back.entity.User;
import com.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenConventor tokenConventor;



	public String getToken(User user) {
		if(!userRepository.existsByEmail(user.getEmail())){
			userRepository.save(user);
		}
		User repUser = userRepository.findUserByEmail(user.getEmail());
		return tokenConventor.createJWT(repUser);
	}
}
