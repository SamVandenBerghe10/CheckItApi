package be.vives.ti.CheckIt.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().addServersItem(new Server().url("https://checkit-444817.ew.r.appspot.com/"));
    }
}
