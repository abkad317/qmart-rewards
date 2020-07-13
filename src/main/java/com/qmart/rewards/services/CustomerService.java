package com.qmart.rewards.services;

import com.qmart.rewards.models.Customer;
import com.qmart.rewards.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer getCustomer(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        return customer.isPresent()?
                customer.get() : null;
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
