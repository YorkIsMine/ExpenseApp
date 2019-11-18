package com.yorkismine.expenseapp.service;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.yorkismine.expenseapp.model.ExchangeRate;
import com.yorkismine.expenseapp.utils.Currency;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ExchangeRateSrv implements Service<List<ExchangeRate>> {


    @Override
    public List<ExchangeRate> load() {
        try {
            Callable<InputStream> callable = this::getData;
            InputStream response = callable.call();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            ExchangeHandlerBase handler = new ExchangeHandlerBase();
            parser.parse(response, handler);
            for (int i = 0; i < handler.getExRates().size(); i++) {
                if (handler.getExRates().get(i).getCurrency() == Currency.OTH)
                    handler.getExRates().remove(i--);
            }
            return handler.getExRates();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getData() throws IOException {
        URL cbrSoap = new URL("https://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx");
        HttpsURLConnection conn = (HttpsURLConnection) cbrSoap.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        conn.setRequestProperty("Content-Length", "2048");
        conn.setRequestProperty("SOAPAction", "\"http://web.cbr.ru/GetCursOnDate\"");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        dos.writeBytes("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <GetCursOnDate xmlns=\"http://web.cbr.ru/\">\n" +
                "      <On_date>" + LocalDateTime.now().toString() + "</On_date>\n" +
                "    </GetCursOnDate>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>");
        conn.connect();
        if (conn.getResponseCode() == 200)
            return conn.getInputStream();
        else {
            StringBuilder error = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            while (reader.ready()) {
                error.append(reader.readLine());
            }
            throw new RuntimeException(conn.getResponseCode() + " " +
                    conn.getResponseMessage() + "\n" + error);
        }
    }

    private class ExchangeHandlerBase extends DefaultHandler {
        private List<ExchangeRate> exRates;
        private ExchangeRateDto rate;
        private String data;

        private boolean bVcurs;
        private boolean bVname;
        private boolean bVchCode;
        private boolean bVnom;

        List<ExchangeRate> getExRates() {
            return exRates;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (qName.equalsIgnoreCase("ValuteCursOnDate")) {
                rate = new ExchangeRateDto();
                // initialize list
                if (exRates == null)
                    exRates = new ArrayList<>();
            } else if (qName.equalsIgnoreCase("Vname")) {
                bVname = true;
            } else if (qName.equalsIgnoreCase("Vcurs")) {
                bVcurs = true;
            } else if (qName.equalsIgnoreCase("VchCode")) {
                bVchCode = true;
            } else if (qName.equalsIgnoreCase("Vnom")) {
                bVnom = true;
            }
            data = "";
        }

        public void endElement(String uri, String localName, String qName) {
            if (bVname) {
                rate.setName(data);
                bVname = false;
            }
            if (bVcurs) {
                rate.setRate(Double.parseDouble(data));
                bVcurs = false;
            }
            if (bVnom) {
                rate.setNom(Double.parseDouble(data));
                bVnom = false;
            }
            if (bVchCode) {
                try {
                    rate.setCurrency(Currency.valueOf(data));
                } catch (IllegalArgumentException e) {
                    rate.setCurrency(Currency.OTH);
                }
                bVchCode = false;
            }
            if (qName.equalsIgnoreCase("ValuteCursOnDate")) {
                ExchangeRate rate = new ExchangeRate();
                rate.setCurrency(this.rate.getCurrency());
                rate.setRate(this.rate.getRate() / this.rate.getNom());
                rate.setName(this.rate.getName());
                exRates.add(rate);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            data += new String(ch, start, length);
        }
    }

    private class ExchangeRateDto {
        String name;
        double nom;
        double rate;
        Currency currency;

        ExchangeRateDto() {
        }

        double getNom() {
            return nom;
        }

        void setNom(double nom) {
            this.nom = nom;
        }

        double getRate() {
            return rate;
        }

        void setRate(double rate) {
            this.rate = rate;
        }

        Currency getCurrency() {
            return currency;
        }

        void setCurrency(Currency currency) {
            this.currency = currency;
        }

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }
    }
}
