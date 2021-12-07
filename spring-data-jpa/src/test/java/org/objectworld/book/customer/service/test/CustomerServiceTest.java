package org.objectworld.book.customer.service.test;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.objectworld.book.customer.domain.Customer;
import org.objectworld.book.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Rollback(false)
@Slf4j
@DisplayName("CustomerService test case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {
	private static int beforeCustomers;
	private static Long lastCustomerId;

	@Autowired
	private CustomerService customerService;

	@Test
	@Order(1)
	@DisplayName("Select All Before Insert Test")
//	@Disabled("Tempory Disable")
	public void init() {
		List<Customer> customerList = customerService.findAll();
		log.info("customerList.size={}", customerList.size());
		log.info("customerList={}", customerList);
		beforeCustomers = customerList.size();
	}
	
	@Test
	@Order(2)
	@DisplayName("Insert Test")
//	@Disabled("Tempory Disable")
	public void create() {		
		// given
		Customer customer = Customer.builder()
				.firstName("철수")
				.lastName("배")
				.enabled(true)
				.build();
		customer = customerService.create(customer);
		lastCustomerId = customer.getId();
		log.info("created id = {}", lastCustomerId);
		log.info("customer={}", customer);
	}
	
	@Test
	@Order(3) 
	@DisplayName("Select All After Insert Test")
//	@Disabled("Tempory Disable")
	public void findById() {
		Customer customer = customerService.findById(3L);
		
		Assertions.assertNotNull(customer);
		Assertions.assertEquals(customer.getFirstName(), "철수");
	}

	@Test
	@Order(4) 
	@DisplayName("Select All After Insert Test")
//	@Disabled("Tempory Disable")
	public void findAll() {
		List<Customer> customerList = customerService.findAll();
		log.info("customerList.size={}", customerList.size());
		log.info("customerList={}", customerList);
		Assertions.assertEquals(customerList.size(), beforeCustomers + 1);
	}
	
	@Test
	@Order(5)
	@DisplayName("Delete Test")
//	@Disabled("Tempory Disable")	
	public void delete() {
		log.info("delete id = {}", lastCustomerId);
		customerService.delete(lastCustomerId);
	}
	
	@Test
	@Order(6)
	@DisplayName("Select Enabled Test")
//	@Disabled("Tempory Disable")	
	public void findAllActive() {
		List<Customer> customerList = customerService.findAllActive();
		log.info("customerList.size={}", customerList.size());
		log.info("customerList={}", customerList);
		Assertions.assertEquals(customerList.size(), beforeCustomers);
	}	
}
