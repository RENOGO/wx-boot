package com.wx.common.security.config;

import org.springframework.context.annotation.Configuration;

/**
 * @Author wuweixin
 * @Date 2024/2/4 09:11
 * @Version 1.0
 * @Descritube oauth2.0配置
 */
@Configuration
public class AuthorizationConfig {
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
//        applyDefaultSecurity(http);
//        return http.build();
//    }
//
//    // @formatter:off
//    public static void applyDefaultSecurity(HttpSecurity http) throws Exception {
//        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
//                new OAuth2AuthorizationServerConfigurer<>();
//        RequestMatcher endpointsMatcher = authorizationServerConfigurer
//                .getEndpointsMatcher();
//
//        http
//                .requestMatcher(endpointsMatcher)
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests.anyRequest().authenticated()
//                )
//                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
//                .apply(authorizationServerConfigurer);
//    }
//    // @formatter:on
//
//    public static JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
//        Set<JWSAlgorithm> jwsAlgs = new HashSet<>();
//        jwsAlgs.addAll(JWSAlgorithm.Family.RSA);
//        jwsAlgs.addAll(JWSAlgorithm.Family.EC);
//        jwsAlgs.addAll(JWSAlgorithm.Family.HMAC_SHA);
//        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
//        JWSKeySelector<SecurityContext> jwsKeySelector =
//                new JWSVerificationKeySelector<>(jwsAlgs, jwkSource);
//        jwtProcessor.setJWSKeySelector(jwsKeySelector);
//        // Override the default Nimbus claims set verifier as NimbusJwtDecoder handles it instead
//        jwtProcessor.setJWTClaimsSetVerifier((claims, context) -> {
//        });
//        return new NimbusJwtDecoder(jwtProcessor);
//    }
//
//    @Bean
//    RegisterMissingBeanPostProcessor registerMissingBeanPostProcessor() {
//        RegisterMissingBeanPostProcessor postProcessor = new RegisterMissingBeanPostProcessor();
//        postProcessor.addBeanDefinition(ProviderSettings.class, () -> ProviderSettings.builder().build());
//        return postProcessor;
//    }
}
