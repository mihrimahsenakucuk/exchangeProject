package com.example.exchangeapi.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="exchange_rates")
public class ExchangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private double forexBuying;

    private double forexSelling;

    private LocalDateTime createdDate;



    public ExchangeEntity() {
    }

    public ExchangeEntity(Long id, String code, double forexBuying, double forexSelling) {
        this.id = id;
        this.code = code;
        this.forexBuying = forexBuying;
        this.forexSelling = forexSelling;
    }

    public ExchangeEntity(String code, double forexBuying, double forexSelling) {
        this.code = code;
        this.forexBuying = forexBuying;
        this.forexSelling = forexSelling;
    }

    public ExchangeEntity(Long id, String code, double forexBuying, double forexSelling, LocalDateTime createdDate) {
        this.id = id;
        this.code = code;
        this.forexBuying = forexBuying;
        this.forexSelling = forexSelling;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getForexBuying() {
        return forexBuying;
    }

    public void setForexBuying(double forexBuying) {
        this.forexBuying = forexBuying;
    }

    public double getForexSelling() {
        return forexSelling;
    }

    public void setForexSelling(double forexSelling) {
        this.forexSelling = forexSelling;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
