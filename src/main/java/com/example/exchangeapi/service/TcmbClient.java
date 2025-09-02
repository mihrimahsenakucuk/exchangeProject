package com.example.exchangeapi.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;

@Service
public class TcmbClient {
    private static final String TCMB_URL_BASE = "https://www.tcmb.gov.tr/kurlar/";

    public Document loadLatestRates() {
        LocalDate date = LocalDate.now();
        int attempts = 0;
        final int maxAttempts = 30;

        while (attempts < maxAttempts) {
            Document doc = loadXmlForDate(date);
            if (doc != null) {
                return doc;
            }
            date = date.minusDays(1);
            attempts++;
        }
        throw new RuntimeException("Son 30 gün içinde TCMB verisi bulunamadı");
    }

    private Document loadXmlForDate(LocalDate date) {
        try {
            String urlStr = buildUrl(date);
            URL url = new URL(urlStr);
            InputStream inputStream = url.openStream();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(inputStream);

        } catch (Exception e) {
            return null;
        }
    }

    private String buildUrl(LocalDate date) {
        String yearMonth = String.format("%04d%02d", date.getYear(), date.getMonthValue());
        String dayMonthYear = String.format("%02d%02d%04d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        return TCMB_URL_BASE + yearMonth + "/" + dayMonthYear + ".xml";
    }
}