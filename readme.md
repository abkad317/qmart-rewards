# QMART Rewards
Demo Spring Boot REST application to place orders and calculate rewards

# Code Walkthrough
#### 1. Orders can be placed through Http Post http://<Host_name>/orders
Sample input json:
```{"totalPrice":110,"status":"SUCCESS", "type":"CREDIT_CARD", "customer_id":100, "createDate":"2020-05-09T06:00:45"}``` 
#### 2. Point accrual parameters are abstracted as properties in application.yaml
```rewards:
     slab1-lower-limit: 50
     slab2-lower-limit: 100
     slab1-points: 1
     slab2-points: 2
```
#### 3. The models Reward hold total reward points and RewardMonthly hold the monthly breakup. The points are updated for every incoming order.

See RewardService.calculateRewardPointsForTransaction(BigDecimal orderAmount)

See RewardMonthlyService.updateInsertRewardMonthly(Transaction transaction, Customer customer, Integer newRewardPoints)

#### 4. Sample customers are added using src/main/resources/data.sql

```
insert into CUSTOMER (ID, NAME) values (100, 'Matt');
insert into CUSTOMER (ID, NAME) values (101, 'Jenny');
```

# Demo:
#### 1. git clone https://github.com/abkad317/qmart-rewards.git
#### 2. ```cd qmart-rewards```
#### 3. ```mvn spring-boot:run```
#### 4. Run following curl commands for customer-1 orders

May-2020 - (2x$10 + 1x$50) + 1x10 = 80 points

```curl -d '{"totalPrice":110,"status":"SUCCESS", "type":"CREDIT_CARD", "customer_id":100, "createDate":"2020-05-09T06:00:45"}' -H 'Content-Type: application/json' http://localhost:8080/orders```

```curl -d '{"totalPrice":60,"status":"SUCCESS", "type":"CREDIT_CARD", "customer_id":100, "createDate":"2020-05-30T06:00:45"}' -H 'Content-Type: application/json' http://localhost:8080/orders```

June-2020 - (2x$10 + 1x$50) + 1x$10 + (2x$20 + 1x$50) = 170

```curl -d '{"totalPrice":110,"status":"SUCCESS", "type":"CREDIT_CARD", "customer_id":100, "createDate":"2020-06-09T06:00:45"}' -H 'Content-Type: application/json' http://localhost:8080/orders```

```curl -d '{"totalPrice":60,"status":"SUCCESS", "type":"CREDIT_CARD", "customer_id":100, "createDate":"2020-06-15T20:00:45"}' -H 'Content-Type: application/json' http://localhost:8080/orders```

```curl -d '{"totalPrice":120,"status":"SUCCESS", "type":"CREDIT_CARD", "customer_id":100, "createDate":"2020-06-30T16:00:45"}' -H 'Content-Type: application/json' http://localhost:8080/orders```

July-2020 - 2x$900 + 1x$50 = 1850

```curl -d '{"totalPrice":1000,"status":"SUCCESS", "type":"CASH", "customer_id":100, "createDate":"2020-07-01T06:00:45"}' -H 'Content-Type: application/json' http://localhost:8080/orders```

#### 5. Run following curl commands for customer-2 orders

Apr-2020 - (2x$11 + 1x$50) + 1x$10 = 80 points

```curl -d '{"totalPrice":110.89,"status":"SUCCESS", "type":"CREDIT_CARD", "customer_id":101, "createDate":"2020-04-09T06:00:45"}' -H 'Content-Type: application/json' http://localhost:8080/orders```

```curl -d '{"totalPrice":60.02,"status":"SUCCESS", "type":"CHECK", "customer_id":101, "createDate":"2020-04-30T06:00:45"}' -H 'Content-Type: application/json' http://localhost:8080/orders```

June-2020 - (2x$10 + 1x$50) + 1x$10 + (2x$20 + 1x$50) + 2x$1400 + 1x$50 = 3020

```curl -d '{"totalPrice":110,"status":"SUCCESS", "type":"CREDIT_CARD", "customer_id":101, "createDate":"2020-06-09T06:00:45"}' -H 'Content-Type: application/json' http://localhost:8080/orders```

```curl -d '{"totalPrice":60,"status":"SUCCESS", "type":"CREDIT_CARD", "customer_id":101, "createDate":"2020-06-15T20:00:45"}' -H 'Content-Type: application/json' http://localhost:8080/orders```

```curl -d '{"totalPrice":120,"status":"SUCCESS", "type":"CREDIT_CARD", "customer_id":101, "createDate":"2020-06-30T16:00:45"}' -H 'Content-Type: application/json' http://localhost:8080/orders```

```curl -d '{"totalPrice":1500,"status":"SUCCESS", "type":"CREDIT_CARD", "customer_id":101, "createDate":"2020-06-01T06:00:45"}' -H 'Content-Type: application/json' http://localhost:8080/orders```


#### 6. Login to H2 database console - http://localhost:8080/h2-console (No password required)

#### 7. Run the below queries to get the total rewards and monthtly breakup

```SELECT C.ID, C.NAME, R.YEARLY_REWARD_POINTS FROM CUSTOMER C, REWARD R WHERE C.REWARD_ID = R.ID;```
      
```SELECT C.ID, C.NAME, RM.MONTH_YEAR, RM.MONTHLY_REWARD_POINTS FROM CUSTOMER C, REWARD_MONTHLY RM WHERE C.ID = RM.CUSTOMER_ID;```

![H2 Console](/images/h2-console.png)
