package com.qmart.rewards.services;

import com.qmart.rewards.models.Customer;
import com.qmart.rewards.models.Order;
import com.qmart.rewards.models.Reward;
import com.qmart.rewards.models.Transaction;
import com.qmart.rewards.repositories.CustomerRepository;
import com.qmart.rewards.repositories.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RewardService {

    @Value("${rewards.slab1-lower-limit}")
    Long slab1LowerLimit;

    @Value("${rewards.slab2-lower-limit}")
    Long slab2LowerLimit;

    @Value("${rewards.slab1-points}")
    Long slab1Points;

    @Value("${rewards.slab2-points}")
    Long slab2Points;

    @Autowired
    RewardRepository rewardRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Integer updateInsertReward(Order order, Transaction transaction) {

        Customer customer = order.getCustomer();
        Reward reward = null;

        // Update yearlyRewardPoints
        Integer newRewardPoints =
                calculateRewardPointsForTransaction(transaction.getTotalPrice());

        if(customer.getReward() != null) {
            reward = order.getCustomer().getReward();
        } else {
            reward = new Reward();
        }

        Integer currentPoints = reward.getYearlyRewardPoints();
        reward.setYearlyRewardPoints(
                currentPoints + newRewardPoints);

        rewardRepository.save(reward);
        customer.setReward(reward);
        customerRepository.save(customer);

        return newRewardPoints;

    }

    private Integer calculateRewardPointsForTransaction(BigDecimal orderAmount) {
        Integer newRewardPoints = Integer.valueOf(0);

        if (orderAmount.compareTo(BigDecimal.valueOf(slab1LowerLimit)) == 1
                && orderAmount.compareTo(BigDecimal.valueOf(slab2LowerLimit)) == -1 ) {
            newRewardPoints = (orderAmount
                    .subtract(BigDecimal.valueOf(slab1LowerLimit))
            )
                    .multiply(BigDecimal.valueOf(slab1Points)).intValue();

        } else if(orderAmount.compareTo(BigDecimal.valueOf(slab2LowerLimit)) == 1 ) {
            newRewardPoints = ((BigDecimal.valueOf(slab2LowerLimit)
                    .subtract(BigDecimal.valueOf(slab1LowerLimit))
            )
                    .multiply(BigDecimal.valueOf(slab1Points)))
                    .add(
                            (orderAmount.subtract(BigDecimal.valueOf(slab2LowerLimit)))
                                    .multiply(BigDecimal.valueOf(slab2Points))
                    ).intValue();
        }
        return newRewardPoints;
    }

    public void saveReward(Reward reward) {
        rewardRepository.save(reward);
    }


}
