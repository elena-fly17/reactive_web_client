package com.example.reactive_web_client;

import com.example.reactive_web_client.models.Invoice;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactiveWebClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveWebClientApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {

			WebClient webClient = WebClient.create("http://localhost:8080");

			Flux<Invoice> flux = webClient
					.get()
					.uri("/invoice/allInvoices")
					.retrieve()
					.bodyToFlux(Invoice.class);

			flux.doOnNext(System.out::println).blockLast();
			Invoice lastInvoice = flux.blockLast();

			if (lastInvoice != null) {
				webClient.get()
						.uri("/invoice/get/{id}", lastInvoice.getId())
						.retrieve()
						.bodyToMono(Invoice.class)
						.doOnSuccess(System.out::println)
				        .block();
			}
		};
	}

}
