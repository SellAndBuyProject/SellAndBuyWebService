package com.sanvalero.SellAndBuy.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SellAndBuyConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("SellAndBuy Web Service")
                        .description("Portal de documentación, ejemplos y uso de la API -> SellAndBuy <-")
                        .contact(new Contact()
                                .name("Ana Verónica Pinos y Raúl Arriaga")
                                .email("a.veronicapinos@gmail.com | raul.arriaga.dev@gmail.com")
                                .url("https://github.com/SellAndBuyProject/SellAndBuyWebService"))
                        .version("1.0"));
    }

}
