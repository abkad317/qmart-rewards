package com.qmart.rewards.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @OneToOne
    private Reward reward;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<RewardMonthly> rewardMonthly;
}
