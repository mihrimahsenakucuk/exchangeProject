package com.example.exchangeapi.controller;

import com.example.exchangeapi.dto.ExchangeDto;
import com.example.exchangeapi.entity.ExchangeEntity;
import com.example.exchangeapi.service.ExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exchange-rates")
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @Operation(summary="TCMB (Türkiye Cumhuriyet Merkez Bankası) üzerinden güncel döviz kurlarını çeker.")
    @GetMapping
    public List<ExchangeDto> getAllExchangeRates() {
        List<ExchangeEntity> entities = exchangeService.getExchangeRates();
        List<ExchangeDto> dtos = new ArrayList<>();
        for (ExchangeEntity entity : entities) {
            dtos.add(toDto(entity));
        }
        return dtos;
    }


    @Operation(summary="Belirli bir döviz koduna ait güncel kuru TCMB'den çeker.")
    @GetMapping("/by-code")
    public ResponseEntity<ExchangeDto> getExchangeRateByCode(@RequestParam String code) {
        ExchangeEntity exchangeEntity = exchangeService.getExchangeRateByCode(code);

        if (exchangeEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toDto(exchangeEntity));
    }

    @Operation(summary="Veritabanına daha önce kayıt edilmiş döviz kurlarını getirir.")
    @GetMapping("/from-db")
    public List<ExchangeDto> getAllFromDatabase() {
        List<ExchangeEntity> entities = exchangeService.getAllFromDatabase();
        List<ExchangeDto> dtos = new ArrayList<>();
        for (ExchangeEntity entity : entities) {
            dtos.add(toDto(entity));
        }
        return dtos;
    }

    @Operation(summary="Verilen iki döviz kuru arasında çevrim yapar (örneğin 100 USD kaç EUR eder?)")
    @GetMapping("/convert")
    public ResponseEntity<?> convertCurrency(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount) {

        Double convertedAmount = exchangeService.convertCurrency(from, to, amount);

        if (convertedAmount == null) {
            return ResponseEntity.badRequest().body("Geçersiz para birimi kodu veya veri bulunamadı.");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("from", from.toUpperCase());
        response.put("to", to.toUpperCase());
        response.put("amount", amount);
        response.put("convertedAmount", convertedAmount);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addExchangeRate(@RequestBody ExchangeDto dto) {
        ExchangeEntity newEntity = new ExchangeEntity(dto.getCode(), dto.getBuying(), dto.getSelling());
        exchangeService.save(newEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Yeni döviz kuru eklendi.");
    }

    private ExchangeDto toDto(ExchangeEntity entity) {
        return new ExchangeDto(entity.getCode(), entity.getForexBuying(), entity.getForexSelling());
    }



}