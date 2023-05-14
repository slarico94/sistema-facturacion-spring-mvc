package com.samuelito.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.samuelito.app.auth.filter.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;

	//@Autowired
	//private BCryptPasswordEncoder passwordEncoder;

	/*@Autowired
	private LoginSuccessHandler loginSuccessHandler;*/

	/*@Autowired
	private DataSource dataSource;*/

	/*
	 * @Bean public DataSource dataSource() { return new EmbeddedDatabaseBuilder()
	 * .setType(EmbeddedDatabaseType.H2)
	 * .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION) .build(); }
	 */

	// IN MEMORY AUTHENTICATION
	/*
	 * @Bean public UserDetailsService users() { PasswordEncoder encoder =
	 * passwordEncoder; UserDetails user = User.builder()
	 * .passwordEncoder(encoder::encode) .username("admin") .password("123456")
	 * .roles("ADMIN", "USER") .build();
	 * 
	 * UserDetails admin = User.builder() .passwordEncoder(encoder::encode)
	 * .username("samuel") .password("Arleth15$") .roles("USER", "CUSTOMER")
	 * .build(); return new InMemoryUserDetailsManager(user, admin); }
	 */

	// JDBC AUTHENTICATION	
	/*@Bean
	public UserDetailsService users() {
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		users.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?");
		users.setAuthoritiesByUsernameQuery("SELECT u.username, a.authority FROM authorities a JOIN users u ON a.user_id = u.user_id WHERE u.username = ?");
		return users;
	}*/
	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/", "/css/**", "/js/**", "/images/**", "/clientes/listar**", "/locale").permitAll()
				// .requestMatchers("/clientes/ver/**").hasAnyRole("USER")
				// .requestMatchers("/uploads/**").hasAnyRole("USER")
				// .requestMatchers("/*/form/**").hasAnyRole("ADMIN")
				// .requestMatchers("/*/editar/**").hasAnyRole("ADMIN")
				// .requestMatchers("/*/eliminar/**").hasAnyRole("ADMIN")
				// .requestMatchers("/factura/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated())
		.csrf().disable()
		.addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		

		return http.build();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
