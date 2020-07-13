package com.qmart.rewards.services;

import com.qmart.rewards.models.Customer;
import com.qmart.rewards.models.RewardMonthly;
import com.qmart.rewards.models.Transaction;
import com.qmart.rewards.repositories.RewardMonthlyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class RewardMonthlyService {

    @Autowired
    RewardMonthlyRepository rewardMonthlyRepository;

    public void saveRewardMonthly(RewardMonthly rewardMonthly) {
        rewardMonthlyRepository.save(rewardMonthly);
    }

    public RewardMonthly getAllRewardMonthly(Long customerId, String monthYear) {
        return rewardMonthlyRepository.findByCustomerIdAndMonthYear(customerId, monthYear);
    }

    public void updateInsertRewardMonthly(Transaction transaction, Customer customer, Integer newRewardPoints) {

        String key = transaction.getCreateDate()
                .format(DateTimeFormatter.ofPattern("MMM-yyyy"));
        Integer currentValue = 0;

        String transactionMonthYear = transaction.getCreateDate()
                .format(DateTimeFormatter.ofPattern("MMM-yyyy"));

        RewardMonthly rewardMonthly = rewardMonthlyRepository
                .findByCustomerIdAndMonthYear(customer.getId(), transactionMonthYear);

        if (rewardMonthly ==  null) {
            rewardMonthly = new RewardMonthly();
            rewardMonthly.setCustomer(customer);
            rewardMonthly.setMonthYear(transactionMonthYear);
            rewardMonthly.setMonthlyRewardPoints(newRewardPoints);
        } else {
            Integer currentRewardPoints = rewardMonthly.getMonthlyRewardPoints();
            rewardMonthly.setMonthlyRewardPoints(currentRewardPoints + newRewardPoints);
        }

        rewardMonthlyRepository.save(rewardMonthly);
    }

}
