package project.pharmacy.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import project.pharmacy.pharmacy.repository.PharmacyRepository;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }   //RestApi를 위한 빈등록
    // Spring에서 API호출하기 위해 사용되는 Http Client모듈 중 하나인 RestTemplate 사용

}
