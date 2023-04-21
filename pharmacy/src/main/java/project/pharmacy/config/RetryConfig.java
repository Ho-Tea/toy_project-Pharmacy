package project.pharmacy.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry
public class RetryConfig {

//    @Bean
//    public RetryTemplate retryTemplate(){
//        return new RetryTemplate();
//    }     이것도 가능하다는 예시

}
