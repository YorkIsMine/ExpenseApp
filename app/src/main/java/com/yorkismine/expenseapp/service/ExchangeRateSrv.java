package com.yorkismine.expenseapp.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ExchangeRate implements Service<List<ExchangeRate>> {



    @Override
    public List<ExchangeRate> load() {
        try {
            InputStream response = getData();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(response, );
            return getData();
        } catch (IOException | ParserConfigurationException | SAXException e) {
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
        if(conn.getResponseCode()==200)
            return conn.getInputStream();
        else{
            String error = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            while (reader.ready()){
                error += reader.readLine();
            }
            throw new RuntimeException(conn.getResponseCode() + " " +
                    conn.getResponseMessage() + "\n" + error);
        }
    }

    private class ExchangeHandlerBase extends DefaultHandler{
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

            if (qName.equalsIgnoreCase("ValuteCursOnDate")) {
                // create a new Employee and put it in Map
                String id = attributes.getValue("id");
                // initialize Employee object and set id attribute
                emp = new Employee();
                emp.setId(Integer.parseInt(id));
                // initialize list
                if (empList == null)
                    empList = new ArrayList<>();
            } else if (qName.equalsIgnoreCase("name")) {
                // set boolean values for fields, will be used in setting Employee variables
                bName = true;
            } else if (qName.equalsIgnoreCase("age")) {
                bAge = true;
            } else if (qName.equalsIgnoreCase("gender")) {
                bGender = true;
            } else if (qName.equalsIgnoreCase("role")) {
                bRole = true;
            }
            // create the data container
            data = new StringBuilder();
        }
    }
}
