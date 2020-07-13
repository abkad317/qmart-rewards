package com.qmart.rewards.models;

import com.qmart.rewards.Constants.ORDER_STATUS;
import com.qmart.rewards.Constants.PAYMENT_TYPE;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "QMART_ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal totalPrice;

    //@CreationTimestamp
    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private ORDER_STATUS status;

    @Enumerated(EnumType.STRING)
    private PAYMENT_TYPE type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<Transaction> transactions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @Column(insertable = false, updatable = false)
    private Long customer_id;
}