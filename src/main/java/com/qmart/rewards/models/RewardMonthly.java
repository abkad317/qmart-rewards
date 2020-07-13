package com.qmart.rewards.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class RewardMonthly {

    @Id
    @GeneratedValue
    private Long id;

    //private Long customerId;

    @ManyToOne
    private Customer customer;

    private String monthYear;

    private Integer monthlyRewardPoints;

}
