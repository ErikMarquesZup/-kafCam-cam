package org.app.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document
public class CurrencyData {

    @Id
    private String mongoId;
    private String requestId;
    private String symbol;
    private BigDecimal bid,
            ask,
            price;
    private Long time;
    private Date ttlTime;


    //for mongo
    public CurrencyData() {
    }


    public String getRequestId() {
        return requestId;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getTime() {
        return time;
    }
}
