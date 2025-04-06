package com.marley_store.stock_system.config.security;

import com.marley_store.stock_system.config.userAuthenticationFilter.UserAuthenticationFilter;
import com.marley_store.stock_system.service.user.userDetailsServiceImpl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Esta é uma anotação do Spring que indica que a classe anotada é uma classe de configuração
@Configuration
//Esta anotação é usada para ativar a segurança da web no projeto Spring Boot. Essa anotação sinaliza ao Spring que a classe anotada deve ser usada para a configuração do Spring Security
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/api-docs",
            "/api-docs/swagger-config",
            "/swagger-ui/index.html",
            "/swagger-ui/index.css",
            "/swagger-ui/swagger-initializer.js",
            "/swagger-ui/swagger-ui-standalone-preset.js",
            "/swagger-ui/swagger-ui.css",
            "/swagger-ui/swagger-ui-bundle.js",

            "/v1/user/login",
            "/v1/user/create"
    };

    // Endpoints que requerem autenticação para serem acessados
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/v1/user/test",
            "/v1/user/update",
            "/v1/user/get"
    };

    // Endpoints que só podem ser acessador por usuários com permissão de cliente
    public static final String [] ENDPOINTS_CUSTOMER = {

    };

    // Endpoints que só podem ser acessador por usuários com permissão de administrador
    public static final String [] ENDPOINTS_ADMIN = {
    };

    // retorna a configuração principal de segurança do Spring Security para a aplicação. Ele define a política de autorização para os endpoints da API REST.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, UserAuthenticationFilter userAuthenticationFilter) throws Exception{
        httpSecurity
                .csrf(csrf -> csrf.disable()) // Alternativa moderna para disable()
                .userDetailsService(userDetailsService) // força o uso da classe UserDetailsService, chamando agora o metodo loadUserByUsername
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                        .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMIN")
                        .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("SHOPKEEPER")
                        .anyRequest().denyAll()
                )
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    //retorna AuthenticationManager que é responsavel para fazer a autenticação do usuario
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // retorna um PasswordEncoder que é usado para codificar a senha dos usuarios( estamos usando o Bcrypt, mas poderiamos usar outros meios)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
