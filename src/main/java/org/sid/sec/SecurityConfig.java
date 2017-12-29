package org.sid.sec;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired //on demande à Spring d'injecter le même dataSource utilisé par l'application = celui déclaré dans application.properties
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 
	/*	auth.inMemoryAuthentication().withUser("admin").password("12345").roles("ADMIN","USER");
		auth.inMemoryAuthentication().withUser("user").password("1234").roles("USER");
   */
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select login as principal, pass as credentials, active from users where login=?")
		.authoritiesByUsernameQuery("select login as principal, role as role from users_roles where login=?")
		.passwordEncoder(new Md5PasswordEncoder())
		.rolePrefix("ROLE_");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//http.formLogin(); // permet d'utilser le formulaire d'auth basique de Spring Secur
		http.formLogin().loginPage("/login");//permet d'utilser le formulaire d'auth personnalisé à créer dans login.html
		//http.csrf().disable(); //pour désactiver les tokens de securité envoyé à chaque réponse à une requête
		http.authorizeRequests().antMatchers("/index").hasRole("USER");
		http.authorizeRequests().antMatchers("/form","/save","/edit","/delete").hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/403");
		/*
		 * pour mieux gérer cela(surtout s'il s'agit d'une grande application avec de nombreux pattern de request), on modifie les pattern ainsi:
		 *  /index --> /user/index,  /form --> /admin/form..../delete --> /admin/delete  : ceci au niveau du Controller et aussi au niveau des vues (html thymeleaf)
		 *  
		 *  et dans la configuration on change à cette façon :
		 *  http.authorizeRequests().antMatchers("/user/*").hasRole("USER");
		 *  http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
		 * */
	}

}
