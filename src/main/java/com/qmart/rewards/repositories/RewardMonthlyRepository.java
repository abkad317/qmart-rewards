package com.qmart.rewards.repositories;

import com.qmart.rewards.models.Reward;
import com.qmart.rewards.models.RewardMonthly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardMonthlyRepository extends JpaRepository<RewardMonthly, Long> {

    public List<RewardMonthly> findByCustomerId(Long customerId);
    public RewardMonthly findByCustomerIdAndMonthYear(Long customerId, String monthYear);

}
