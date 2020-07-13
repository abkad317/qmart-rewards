package com.qmart.rewards.models;

import com.qmart.rewards.Constants.TRANSACTION_STATUS;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private TRANSACTION_STATUS status;

    @ManyToOne
    private Order order;

    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

}
