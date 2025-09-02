package com.example.exchangeapi.dto;

public class ExchangeDto {
    private String code;
    private double buying;
    private double selling;

    public ExchangeDto(String code, double buying, double selling) {
        this.code = code;
        this.buying = buying;
        this.selling = selling;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getBuying() {
        return buying;
    }

    public void setBuying(double buying) {
        this.buying = buying;
    }

    public double getSelling() {
        return selling;
    }

    public void setSelling(double selling) {
        this.selling = selling;
    }
}
