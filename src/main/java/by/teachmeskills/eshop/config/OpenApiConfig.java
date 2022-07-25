package by.teachmeskills.eshop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    /**
     * This bean is needed to define your own groups of API on the combination of:
     * API paths and packages to scan.
     * Each group should have a unique groupName.
     * The OpenAPI description of this group, will be available by default
     * on http://server:port/context-path/v3/api-docs/groupName
     * More info https://springdoc.org/faq.html
     */
    @Bean
    public GroupedOpenApi publicCategoryProductApi() {
        return GroupedOpenApi.builder()
                .group("CategoryProduct API")
                .pathsToMatch("/category/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicCategoryApi() {
        return GroupedOpenApi.builder()
                .group("Category API")
                .pathsToMatch("/home/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicProductApi() {
        return GroupedOpenApi.builder()
                .group("Product API")
                .pathsToMatch("/product/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicProfileApi() {
        return GroupedOpenApi.builder()
                .group("Profile API")
                .pathsToMatch("/profile/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicAuthApi() {
        return GroupedOpenApi.builder()
                .group("Authorization API")
                .pathsToMatch("/login/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicSearchApi() {
        return GroupedOpenApi.builder()
                .group("Search API")
                .pathsToMatch("/search/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicRegistrationApi() {
        return GroupedOpenApi.builder()
                .group("Registration API")
                .pathsToMatch("/registration/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicCartApi() {
        return GroupedOpenApi.builder()
                .group("Cart API")
                .pathsToMatch("/cart/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenApi(@Value("${application.description}") String appDescription,
                                 @Value("${application.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("Application API")
                        .version(appVersion)
                        .description(appDescription)
                        .license(new License().name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .contact(new Contact().name("TeachMeSkills")
                                .email("tms@gmail.by")))
                .servers(List.of(new Server().url("http://localhost:8080")
                                .description("Dev service"),
                        new Server().url("http://localhost:8082")
                                .description("Beta service")));
    }
}

