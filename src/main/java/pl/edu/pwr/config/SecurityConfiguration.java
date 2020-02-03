package pl.edu.pwr.config;

import io.github.jhipster.config.JHipsterProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;
import pl.edu.pwr.security.AuthoritiesConstants;
import pl.edu.pwr.security.SecurityUtils;
import pl.edu.pwr.security.oauth2.AudienceValidator;
import pl.edu.pwr.security.oauth2.JwtAuthorityExtractor;

import java.util.HashSet;
import java.util.Set;

@EnableWebSecurity
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final JHipsterProperties jHipsterProperties;
    private final JwtAuthorityExtractor jwtAuthorityExtractor;
    private final SecurityProblemSupport problemSupport;
    @Value("${spring.security.oauth2.client.provider.oidc.issuer-uri}")
    private String issuerUri;

    public SecurityConfiguration(CorsFilter corsFilter, JwtAuthorityExtractor jwtAuthorityExtractor, JHipsterProperties jHipsterProperties, SecurityProblemSupport problemSupport) {
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
        this.jwtAuthorityExtractor = jwtAuthorityExtractor;
        this.jHipsterProperties = jHipsterProperties;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/h2-console/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
            .addFilterBefore(corsFilter, CsrfFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
            .and()
            .headers()
            .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:")
            .and()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
            .and()
            .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
            .and()
            .frameOptions()
            .deny()
            .and()
            .authorizeRequests()
            .antMatchers("/api/auth-info").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)

            .antMatchers("/class-forms/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers(HttpMethod.GET, "/class-forms/**").hasAnyAuthority(AuthoritiesConstants.ENTRUSTER, AuthoritiesConstants.TEACHER)

            .antMatchers("/course-classes/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.ENTRUSTER)
            .antMatchers(HttpMethod.GET, "/course-classes/**").hasAuthority(AuthoritiesConstants.TEACHER)

            .antMatchers("/courses/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.ENTRUSTER)
            .antMatchers(HttpMethod.GET, "/courses/**").hasAuthority(AuthoritiesConstants.TEACHER)

            .antMatchers("/education-plans/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.ENTRUSTER)
            .antMatchers(HttpMethod.GET, "/education-plans/**").hasAuthority(AuthoritiesConstants.TEACHER)

            .antMatchers("/entrustment-plans/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.ENTRUSTER)
            .antMatchers(HttpMethod.GET, "/entrustment-plans/**").hasAuthority(AuthoritiesConstants.TEACHER)

            .antMatchers("/entrustments/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.ENTRUSTER)
            .antMatchers(HttpMethod.GET, "/entrustments/**").hasAuthority(AuthoritiesConstants.TEACHER)

            .antMatchers("/field-of-studies/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers(HttpMethod.GET, "/field-of-studies/**").hasAnyAuthority(AuthoritiesConstants.ENTRUSTER, AuthoritiesConstants.TEACHER)

            .antMatchers("/knowledge-areas/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers(HttpMethod.GET, "/knowledge-areas/**").hasAnyAuthority(AuthoritiesConstants.ENTRUSTER, AuthoritiesConstants.TEACHER)

            .antMatchers("/teachers/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers(HttpMethod.GET, "/teachers/**").hasAnyAuthority(AuthoritiesConstants.TEACHER, AuthoritiesConstants.ENTRUSTER)

            .and()
            .oauth2Login()
            .and()
            .oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtAuthorityExtractor)
            .and()
            .and()
            .oauth2Client();
        // @formatter:on
    }

    /**
     * Map authorities from "groups" or "roles" claim in ID Token.
     *
     * @return a {@link GrantedAuthoritiesMapper} that maps groups from
     * the IdP to Spring Security Authorities.
     */
    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority -> {
                OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
                mappedAuthorities.addAll(SecurityUtils.extractAuthorityFromClaims(oidcUserAuthority.getUserInfo().getClaims()));
            });
            return mappedAuthorities;
        };
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoderJwkSupport jwtDecoder = (NimbusJwtDecoderJwkSupport)
            JwtDecoders.fromOidcIssuerLocation(issuerUri);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(jHipsterProperties.getSecurity().getOauth2().getAudience());
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }
}
