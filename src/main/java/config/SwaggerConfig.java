package config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    /*Schema Exception = new Schema<Map<String, Object>>()
            .addProperties("errorCode",new StringSchema().example("404"))
            .addProperties("errorMessage",new StringSchema().example("not found"));*/

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Product info")
                                .description("Test project for Setronica")
                                .version("1.0.0")
                )
                .components(new Components()
                        .addSchemas("Exception", new Schema<Map<String, Object>>()
                                .addProperties("errorCode",new IntegerSchema().example(404))
                                .addProperties("errorMessage",new StringSchema().example("not found"))));
    }
}
