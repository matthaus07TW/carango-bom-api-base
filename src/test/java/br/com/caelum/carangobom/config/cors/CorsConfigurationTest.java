package br.com.caelum.carangobom.config.cors;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

class CorsConfigurationTest {

	private CorsConfiguration cors;
	
	@Test
	void test() {
		CorsRegistry registry = new CorsRegistry();
		cors.addCorsMappings(registry);
	}

}
