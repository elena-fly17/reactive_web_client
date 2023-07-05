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

	// получение всех сущностей из БД
	@Bean
	CommandLineRunner runner() {
		return args -> {

			// указываем адрес приложения, от которого хотим получить данные
			WebClient webClient = WebClient.create("http://localhost:8080");

			// получаем данные от стороннего приложения и формируем из них поток
			Flux<Invoice> flux = webClient
					.get() // тип запроса
					.uri("/invoice/allInvoices") // урл, на который отправляем запрос
					.retrieve() // отправляем запрос и получаем ответ
					.bodyToFlux(Invoice.class); // преобразование тело ответа в поток элементов

			// выводим каждый элемент в консоль и блокируем
			// выполнение программы, пока не получим последний элемент
			flux.doOnNext(System.out::println).blockLast();
		};
	}

}
