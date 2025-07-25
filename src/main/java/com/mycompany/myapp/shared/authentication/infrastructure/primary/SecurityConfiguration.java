package com.mycompany.myapp.shared.authentication.infrastructure.primary;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.*;

import com.mycompany.myapp.shared.authentication.domain.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtAuthenticationProperties.class)
@EnableMethodSecurity(securedEnabled = true)
class SecurityConfiguration {

  private final JwtAuthenticationProperties properties;
  private final CorsFilter corsFilter;

  public SecurityConfiguration(JwtAuthenticationProperties properties, CorsFilter corsFilter) {
    this.properties = properties;
    this.corsFilter = corsFilter;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .csrf(csrf -> csrf.disable())
      .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
      .headers(headers -> headers
        .contentSecurityPolicy(csp -> csp.policyDirectives(properties.getContentSecurityPolicy()))
        .frameOptions(FrameOptionsConfig::deny)
        .referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
        .permissionsPolicyHeader(permissions ->
          permissions.policy("camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()"))
      )
      .formLogin(AbstractHttpConfigurer::disable)
      .httpBasic(AbstractHttpConfigurer::disable)
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(authz -> authz
        .requestMatchers(antMatcher(HttpMethod.OPTIONS, "/**")).permitAll()
        .requestMatchers(antMatcher("/app/**")).permitAll()
        .requestMatchers(antMatcher("/i18n/**")).permitAll()
        .requestMatchers(antMatcher("/content/**")).permitAll()
        .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()
        .requestMatchers(antMatcher("/swagger-ui.html")).permitAll()
        .requestMatchers(antMatcher("/v3/api-docs/**")).permitAll()
        .requestMatchers(antMatcher("/test/**")).permitAll()
        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/api/authenticate")).permitAll()
        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/api/register")).permitAll()
        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/api/activate")).permitAll()
        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/api/account/reset-password/init")).permitAll()
        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/api/account/reset-password/finish")).permitAll()
        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/api/admin/**")).hasAuthority(Role.ADMIN.key())
        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/api/**")).authenticated()
        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/management/health")).permitAll()
        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/management/health/**")).permitAll()
        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/management/info")).permitAll()
        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/management/prometheus")).permitAll()
        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/management/**")).hasAuthority(Role.ADMIN.key())
        .anyRequest().authenticated()
      );

      var jwtConfigurer = new JWTConfigurer(authenticationTokenReader());
      http.with(jwtConfigurer, Customizer.withDefaults());
      return http.build();
    // @formatter:on
  }

  @Bean
  @ConditionalOnMissingBean
  AuthenticationTokenReader authenticationTokenReader() {
    return new JwtReader(Jwts.parser().verifyWith(signingKey()).build());
  }

  private SecretKey signingKey() {
    return Keys.hmacShaKeyFor(properties.getJwtBase64Secret().getBytes(StandardCharsets.UTF_8));
  }
}
