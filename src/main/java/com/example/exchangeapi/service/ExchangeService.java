package com.example.exchangeapi.service;

import com.example.exchangeapi.entity.ExchangeEntity;
import com.example.exchangeapi.repository.ExchangeRepository;
import com.example.exchangeapi.xmlUtils.ExchangeRateParser;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;


@Service
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final TcmbClient tcmbClient;
    private final ExchangeRateParser exchangeRateParser;

    private static final Logger logger = LoggerFactory.getLogger(ExchangeService.class);

    public ExchangeService(ExchangeRepository exchangeRepository, TcmbClient tcmbClient, ExchangeRateParser exchangeRateParser) {
        this.exchangeRepository = exchangeRepository;
        this.tcmbClient = tcmbClient;
        this.exchangeRateParser = exchangeRateParser;
    }

    public List<ExchangeEntity> getExchangeRates() {
        try {
            Document doc = tcmbClient.loadLatestRates();
            logger.info("TCMB'den döviz kurları alındı.");
            return exchangeRateParser.parse(doc);
        } catch (Exception e) {
            logger.error("TCMB verisi alınırken hata oluştu", e);
            return new ArrayList<>();
        }
    }
    public List<ExchangeEntity> getAllFromDatabase() {
        return exchangeRepository.findAll();
    }


    public ExchangeEntity getExchangeRateByCode(String code) {
        List<ExchangeEntity> allRates = getExchangeRates();

        for (ExchangeEntity entity : allRates) {
            if (entity.getCode().equalsIgnoreCase(code)) {
                return entity;
            }
        }

        return null;
    }

    public Double convertCurrency(String from, String to, double amount) {
        ExchangeEntity fromCurrency = getExchangeRateByCode(from);
        ExchangeEntity toCurrency = getExchangeRateByCode(to);

        if (fromCurrency == null || toCurrency == null) {
            return null;
        }

        double fromRate = fromCurrency.getForexSelling();
        double toRate = toCurrency.getForexBuying();

        if (fromRate <= 0 || toRate <= 0) {
            logger.warn("Kur oranları sıfır veya negatif: {} -> {}, {} -> {}", from, fromRate, to, toRate);
            return null;
        }

        double tlAmount = amount * fromRate;
        return tlAmount / toRate;
    }

    public void save(ExchangeEntity entity) {
        exchangeRepository.save(entity);
    }
}


