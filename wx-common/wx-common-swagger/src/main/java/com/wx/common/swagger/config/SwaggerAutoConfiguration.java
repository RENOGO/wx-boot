package com.wx.common.swagger.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.wx.common.constants.PropertiesPre;
import com.wx.common.swagger.model.Header;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */
@EnableSwagger2
@ConditionalOnProperty(name = PropertiesPre.SWAGGER + ".enabled", matchIfMissing = false)
public class SwaggerAutoConfiguration {
    /**
     * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
     */
    private static final List<String> DEFAULT_EXCLUDE_PATH = Arrays.asList("/error", "/actuator/**");

    private static final String BASE_PATH = "/**";


    @Bean
    @ConditionalOnMissingBean
    public CommonSwaggerProperties customSwaggerProperties() {
        return new CommonSwaggerProperties();
    }

    @Bean
    public Docket api(CommonSwaggerProperties customCommonSwaggerProperties) {
        List<String> basePathPro = customCommonSwaggerProperties.getBasePath();

        // base-path处理
        if (basePathPro.isEmpty()) {
            basePathPro.add(BASE_PATH);
        }
        // noinspection unchecked
        List<Predicate<String>> basePath = basePathPro.stream().map(new Function<String, Predicate<String>>() {
            @Override
            public Predicate<String> apply(String s) {
                return PathSelectors.ant(s);
            }
        }).collect(Collectors.toList());

        // exclude-path处理
        List<String> excludePathPro = customCommonSwaggerProperties.getExcludePath();
        if (excludePathPro.isEmpty()) {
            excludePathPro.addAll(DEFAULT_EXCLUDE_PATH);
        }
        List<Predicate<String>> excludePath = excludePathPro.stream().map(new Function<String, Predicate<String>>() {
            @Override
            public Predicate<String> apply(String s) {
                return PathSelectors.ant(s);
            }
        }).collect(Collectors.toList());

        List<Parameter> pars = customCommonSwaggerProperties.getHeaders().stream().map(new Function<Header, Parameter>() {
            @Override
            public Parameter apply(Header header) {
                //添加header参数
                ParameterBuilder ticketPar = new ParameterBuilder();
                ticketPar.name(header.getHeaderName()).description(header.getDescription())
                        .modelRef(new ModelRef(header.getType())).parameterType("header")
                        //header中的ticket参数非必填，传空也可以
                        .required(false).build();
                return ticketPar.build();
            }
        }).collect(Collectors.toList());

        //noinspection Guava
        List<Predicate<? super RequestHandler>> components = customCommonSwaggerProperties
                .getBasePackage()
                .stream()
                .map((Function<String, Predicate<? super RequestHandler>>) s -> RequestHandlerSelectors.basePackage(s))
                .collect(Collectors.toList());

        return new Docket(DocumentationType.SWAGGER_2)
                .host(customCommonSwaggerProperties.getHost())
                .apiInfo(apiInfo(customCommonSwaggerProperties)).select()
                .apis(Predicates.or(components))
                .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath)))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .globalOperationParameters(pars)
                .pathMapping("/");
    }


    /**
     * 安全模式，这里指定token通过Authorization头请求头传递
     */
    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList<ApiKey>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }

    /**
     * 安全上下文
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    /**
     * 默认的全局鉴权策略
     *
     * @return
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

    private ApiInfo apiInfo(CommonSwaggerProperties customCommonSwaggerProperties) {
        return new ApiInfoBuilder()
                .title(customCommonSwaggerProperties.getTitle())
                .description(customCommonSwaggerProperties.getDescription())
                .license(customCommonSwaggerProperties.getLicense())
                .licenseUrl(customCommonSwaggerProperties.getLicenseUrl())
                .termsOfServiceUrl(customCommonSwaggerProperties.getTermsOfServiceUrl())
                .contact(new Contact(customCommonSwaggerProperties.getContact().getName(),
                        customCommonSwaggerProperties.getContact().getUrl(),
                        customCommonSwaggerProperties.getContact().getEmail()))
                .version(customCommonSwaggerProperties.getVersion())
                .build();
    }
}
