package com.karlgrund.expense.tracker.util;

import com.karlgrund.expense.tracker.util.dto.Conversion;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
public class CurrencyConverter {
    private static final String URL_PATH = "http://api.fixer.io/";
    private static final String BASE = "base";
    private static final String SEK = "SEK";
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final Client client;

    public CurrencyConverter() {
        client = ClientBuilder.newClient();
    }

    public Double convertToSEK(
        Date date,
        Long amount,
        CurrencyId id
    ) {
        Conversion conversion = client.target(URL_PATH)
            .path(dateFormat.format(date))
            .queryParam(BASE, SEK)
            .request(MediaType.APPLICATION_JSON_TYPE)
            .get(Conversion.class);

        return conversion.convert(amount, id);
    }
}
