import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class CurrencyExchange {
    static HashMap<String,Double> Rates = new HashMap<>();
    private HttpURLConnection conn;

    public CurrencyExchange(){
        SetUpConnection();
    }

    public void SetUpConnection(){
        try{
            URL url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Set connection timeout to 5 seconds
            conn.setConnectTimeout(5000);
            // Set read timeout to 5 seconds
            conn.setReadTimeout(5000);
            // Reuse the connection
            conn.setRequestProperty("Connection", "keep-alive");

            if (conn.getResponseCode() != 200) {
                throw new IOException("HTTP error code: " + conn.getResponseCode());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void UpdateRates() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Use try-with-resources to close the input stream
            try (InputStream inputStream = conn.getInputStream()) {
                Document doc = dBuilder.parse(inputStream);
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("Cube");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element element = (Element) nodeList.item(i);
                    String currency = element.getAttribute("currency");
                    if (!currency.isEmpty()) {
                        String rate = element.getAttribute("rate");
                        double doubleRate = Double.parseDouble(rate);
                        Rates.put(currency, doubleRate);
                    }
                }
            }
            conn.disconnect();
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public HashMap<String,Double> GetRates(){
        return Rates;
    }

    public void PrintRates(){
        System.out.println("Currency Rates:");
        for (String currency : Rates.keySet()) {
            double rate = Rates.get(currency);
            System.out.println(currency + ": " + rate);
        }
    }

    public double Convert(double amount, String from, String to){
        double fromRate = Rates.get(from);
        double toRate = Rates.get(to);
        BigDecimal roundedNumber = new BigDecimal(amount * (toRate/fromRate)).setScale(2, RoundingMode.HALF_UP);
        return roundedNumber.doubleValue();
    }
}
