package br.com.erudio.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.repository.UserRepository;
import br.com.erudio.security.AccountCredentialsVO;
import br.com.erudio.security.jwt.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserRepository repository;
	
	@ApiOperation(value = "Authenticate a user by credentials")
	@PostMapping(value = "/signin", produces = {"application/json" , "application/xml" , "application/x-yaml"},
			consumes = {"application/json" , "application/xml" , "application/x-yaml"})
	public ResponseEntity signin (@RequestBody AccountCredentialsVO data) {
		
		try {
			
			var userName = data.getUserName();
			var password = data.getPassword();
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
			
			var user = repository.findByUserName(userName);
			
			var token = " ";
			
			if(user != null) {
				token = tokenProvider.createToken(userName, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username + " + userName + " Not Found");
			}
			
			//montar um objeto de retorno
			Map<Object, Object> model = new HashMap<>();
			
			model.put("userName", userName);
			model.put("token",token);

			return ok(model);
			
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
		
	}
	
	
	
}
