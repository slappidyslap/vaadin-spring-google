package com.example.crudwithvaadin;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, OAuth2ClientAutoConfiguration.class})
@SpringBootApplication
@RestController
public class CrudWithVaadinApplication {

	private static final Logger log = LoggerFactory.getLogger(CrudWithVaadinApplication.class);

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	public static void main(String[] args) {
		SpringApplication.run(CrudWithVaadinApplication.class);
	}

	@GetMapping("/sheets")
	public String sheets(Authentication authentication) throws IOException {

		Sheets sheets = null;/*new Sheets.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance(), cred)
				.setApplicationName("seo")
				.build();*/
		ValueRange body = new ValueRange().setValues(List.of(List.of("Test")));
		sheets.spreadsheets().values()
				.update("1V9Zr1zzGqY8TPS2V8ovnXvIRc1RrQfNmmipZghNsNbc", "A1", body)
				.setValueInputOption("RAW")
				.execute();
		return "success";
	}

	@Bean
	public CommandLineRunner loadData(CustomerRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Customer("Jack", "Bauer"));
			repository.save(new Customer("Chloe", "O'Brian"));
			repository.save(new Customer("Kim", "Bauer"));
			repository.save(new Customer("David", "Palmer"));
			repository.save(new Customer("Michelle", "Dessler"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Customer customer = repository.findById(1L).get();
			log.info("Customer found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastNameStartsWithIgnoreCase('Bauer'):");
			log.info("--------------------------------------------");
			for (Customer bauer : repository
					.findByLastNameStartsWithIgnoreCase("Bauer")) {
				log.info(bauer.toString());
			}
			log.info("");
		};
	}

}
