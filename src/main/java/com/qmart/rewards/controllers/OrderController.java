package com.qmart.rewards.controllers;

import com.qmart.rewards.models.Customer;
import com.qmart.rewards.models.Order;
import com.qmart.rewards.models.Transaction;
import com.qmart.rewards.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    CustomerService customerService;

    @Autowired
    RewardService rewardService;

    @Autowired
    RewardMonthlyService rewardMonthlyService;

    @GetMapping("/{id}")
    public ResponseEntity getOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id);

            return new ResponseEntity<>(
                    order == null ? null : order,
                    order == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void placeOrder(@RequestBody Order order) {

        Customer customer = null;
        Order savedOrder = null;
        if (order.getId() != null) {
            savedOrder = orderService.getOrder(order.getId());
        }
        if (savedOrder == null) {
            savedOrder = orderService.saveOrder(order);
        }
        if (order.getCustomer_id() != null) {
            customer = customerService.getCustomer(order.getCustomer_id());
            savedOrder.setCustomer(customer);
        }
        Transaction transaction = transactionService.saveTransaction(savedOrder);
        savedOrder.getTransactions().add(transaction);

        //update rewards
        Integer  newRewardPoints = rewardService.updateInsertReward(savedOrder, transaction);

        rewardMonthlyService.updateInsertRewardMonthly(transaction, customer, newRewardPoints);

        customerService.saveCustomer(customer);

    }
}
