package de.zottig.clean.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private CustomUserDetailsService userDetailsService;
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth)
//			throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
//	}
//
//	@Override
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//
//	@Bean
//	public BCryptPasswordEncoder encoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().csrfTokenRepository(
//				CookieCsrfTokenRepository.withHttpOnlyFalse());
//		// http.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll();
//		http.cors();
//	}
//
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		final CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(ImmutableList.of("*"));
//		configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST",
//				"PUT", "DELETE", "PATCH", "OPTIONS"));
//		List<String> headers = new ArrayList<>();
//		headers.add("Origin");
//		headers.add("Content-Type");
//		headers.add("x-requested-with");
//		headers.add("authorization");
//		configuration.setAllowedHeaders(headers);
//		configuration.setExposedHeaders(headers);
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
}