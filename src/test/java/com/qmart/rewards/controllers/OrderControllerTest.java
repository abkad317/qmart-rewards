package com.qmart.rewards.controllers;

import com.qmart.rewards.Constants;
import com.qmart.rewards.QmartRewardsApplication;
import com.qmart.rewards.models.Customer;
import com.qmart.rewards.models.Order;
import com.qmart.rewards.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QmartRewardsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer testCustomer = new Customer();
    private Order testOrder = new Order();

    @Before
    public void init() {
        testCustomer.setName("Bob");
        testCustomer = customerRepository.save(testCustomer);

        testOrder.setCustomer(testCustomer);
        testOrder.setCustomer_id(testCustomer.getId());
        testOrder.setTotalPrice(BigDecimal.valueOf(60));
        testOrder.setCreateDate(LocalDateTime.now().minusMonths(4L));
        testOrder.setType(Constants.PAYMENT_TYPE.CASH);
        testOrder.setStatus(Constants.ORDER_STATUS.SUCCESS);
    }

    @Test
    public void testPlaceOrder() {
        WebTestClient webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
                //.get()
        webTestClient
                .post()
                .uri("/orders")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(testOrder))
                .exchange()
                .expectStatus()
                .isCreated();
    }

}
