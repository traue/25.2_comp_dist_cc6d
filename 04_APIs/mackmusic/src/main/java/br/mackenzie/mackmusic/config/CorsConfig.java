package br.mackenzie.mackmusic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Indica que esta é uma classe de configuração do Spring Boot
@Configuration
public class CorsConfig {

    // Define um bean que personaliza o comportamento global da configuração CORS
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            // Método que será chamado pelo Spring para registrar as regras de CORS
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {

                // Aplica a configuração de CORS a todos os endpoints da aplicação (/**)
                registry.addMapping("/**")

                        // Permite apenas requisições vindas de qualquer lugar ---> Apenas para testes!!
                        // (ideal para quando o front está sendo servido pelo próprio Spring Boot)
                        .allowedOrigins("*") // <--- ajuste importante

                        // Define quais métodos HTTP são permitidos nas requisições cross-origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                        // Permite todos os cabeçalhos nas requisições
                        .allowedHeaders("*");
            }
        };
    }
}
