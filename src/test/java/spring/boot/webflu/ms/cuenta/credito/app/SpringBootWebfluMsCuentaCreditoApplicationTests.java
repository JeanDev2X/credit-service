package spring.boot.webflu.ms.cuenta.credito.app;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;


@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SpringBootWebfluMsCuentaCreditoApplicationTests {
	
	@Autowired
	private WebTestClient client;

	@Test
	void contextLoads() {
	}
	
//	@Test
//	public void cantiadOrden() {
//		client.get().uri("/api/ProductCredit")
//		.accept(MediaType.APPLICATION_JSON)
//		.exchange()
//		.expectStatus().isOk() 
//		.expectHeader().contentType(MediaType.APPLICATION_JSON)
//		.expectBodyList(ProductCredit.class)
//		.hasSize(6);
//	}
//	
//	@Test
//	public void listarOrden() {
//		client.get().uri("/api/ProductCredit")
//		.accept(MediaType.APPLICATION_JSON)
//		.exchange()
//		.expectStatus().isOk() 
//		.expectHeader().contentType(MediaType.APPLICATION_JSON) //.hasSize(2);
//		.expectBodyList(ProductCredit.class).consumeWith(response -> {
//			
//			List<ProductCredit> credito = response.getResponseBody();
//			
//			credito.forEach(p -> {
//				System.out.println(p.getConsumo());
//			});
//			
//			Assertions.assertThat(credito.size() > 0).isTrue();
//		});
//	}
	
}