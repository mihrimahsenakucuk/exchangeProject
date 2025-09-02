package com.example.exchangeapi.xmlUtils;

import com.example.exchangeapi.entity.ExchangeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;


@Component
public class ExchangeRateParser {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateParser.class);

    public List<ExchangeEntity> parse(Document doc) {
        List<ExchangeEntity> rates = new ArrayList<>();

        try {
            NodeList currencyNodes = doc.getElementsByTagName("Currency");

            for (int i = 0; i < currencyNodes.getLength(); i++) {
                Node node = currencyNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element currencyElement = (Element) node;
                    String code = currencyElement.getAttribute("CurrencyCode");
                    double forexBuying = parseDoubleSafe(getTagValue(currencyElement, "ForexBuying"));
                    double forexSelling = parseDoubleSafe(getTagValue(currencyElement, "ForexSelling"));

                    ExchangeEntity exchangeEntity = new ExchangeEntity(code, forexBuying, forexSelling);
                    rates.add(exchangeEntity);
                }
            }

            logger.info("XML parse işlemi tamamlandı. Toplam {} kur bulundu.", rates.size());

        } catch (Exception e) {
            logger.error("XML parse hatası", e);
        }

        return rates;
    }

    private double parseDoubleSafe(String value) {
        try {
            return Double.parseDouble(value.replace(",", "."));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private String getTagValue(Element element, String tagName) {
        NodeList nodes = element.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        } else {
            return "";
        }
    }
}
