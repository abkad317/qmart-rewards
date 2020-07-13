package com.qmart.rewards.services;

import com.qmart.rewards.Constants;
import com.qmart.rewards.models.Order;
import com.qmart.rewards.models.Transaction;
import com.qmart.rewards.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public Transaction saveTransaction(Order order) {
        Transaction transaction = new Transaction();
        if(order.getStatus().equals(Constants.ORDER_STATUS.SUCCESS)) {
            transaction.setStatus(Constants.TRANSACTION_STATUS.COMPLETE);
        }
        transaction.setTotalPrice(order.getTotalPrice());
        transaction.setOrder(order);
        transaction.setCreateDate(order.getCreateDate());
        return transactionRepository.save(transaction);
    }
}
