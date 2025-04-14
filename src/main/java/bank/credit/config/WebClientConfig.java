package bank.credit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
//	 @Bean
//	    public WebClient customerWebClient(WebClient.Builder webClientBuilder) {
	//return webClientBuilder.baseUrl("http://localhost:8020").build();
//	        //return webClientBuilder.baseUrl("http://customer-service").build();
//		 	return WebClient.builder();
//	    }
	 
	@Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
	
}
