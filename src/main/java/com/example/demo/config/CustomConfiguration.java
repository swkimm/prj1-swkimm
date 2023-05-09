package com.example.demo.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.security.web.access.expression.*;

import jakarta.annotation.*;
import jakarta.servlet.*;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.*;
import software.amazon.awssdk.services.s3.*;

@Configuration
@EnableMethodSecurity
public class CustomConfiguration {

	@Value("${aws.accessKeyId}")
	private String accessKey;
	
	@Value("${aws.secretAccessKey}")
	private String secretKey;
	
	@Value("${aws.bucketUrl}")
	private String bucketUrl;
	
	@Autowired
	private ServletContext application;
	
	@PostConstruct
	public void init() {
		application.setAttribute("bucketUrl", bucketUrl);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		// 기본 로그인 폼 사용
//		http.formLogin(Customizer.withDefaults());
		
		http.formLogin().loginPage("/member/login");
		http.logout().logoutUrl("/member/logout");
		
		// 일반적인 표현 
		// add 창에서 게시물 등록은 로그인한 회원만 가능하도록 설정
		// http.authorizeHttpRequests().requestMatchers("/add").authenticated();

		// 로그인 하지 않은 사람만 접근
		// http.authorizeHttpRequests().requestMatchers("/member/signup").anonymous();
		
		// 그 외 다른 기능은 아무나 가능하도록 설정
		// http.authorizeHttpRequests().requestMatchers("/**").permitAll();
		
		// spring security expression 사용
		// add 창에서 게시물 등록은 로그인한 회원만 가능하도록 설정
//		http.authorizeHttpRequests()
//			.requestMatchers("/add")
//			.access(new WebExpressionAuthorizationManager("isAuthenticated()"));
//		
//		// 로그인 하지 않은 사람만 접근
//		http.authorizeHttpRequests()
//			.requestMatchers("/member/signup")
//			.access(new WebExpressionAuthorizationManager("isAnonymous()"));
//		
//		// 그 외 다른 기능은 아무나 가능하도록 설정
//		http.authorizeHttpRequests()
//			.requestMatchers("/**")
//			.access(new WebExpressionAuthorizationManager("permitAll"));
		
		return http.build();
	}
	
	
	// aws Config 코드
	@Bean
	public S3Client s3client() {
		
		AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
		AwsCredentialsProvider provider = StaticCredentialsProvider.create(credentials);
		
		S3Client s3client = S3Client.builder()
				.credentialsProvider(provider)
				.region(Region.AP_NORTHEAST_2)
				.build();
		
		return s3client;
	}
}













