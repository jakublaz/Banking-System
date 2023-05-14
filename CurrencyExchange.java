import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class CurrencyExchange {
    static HashMap<String,Double> Rates = new HashMap<>();

    public CurrencyExchange(){
        UpdateRates();
    }

    public void UpdateRates(){
        try {
            // Create URL object for the ECB API
            URL url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");

            // Create HttpURLConnection object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Check for successful response code
            if (conn.getResponseCode() != 200) {
                throw new IOException("HTTP error code: " + conn.getResponseCode());
            }

            // Parse the response XML using DOM parser
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(url.openStream());
            doc.getDocumentElement().normalize();

            // Get the USD and GBP exchange rates from the parsed XML
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

            // Close the HttpURLConnection object
            conn.disconnect();

        } catch (Exception e) {
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
}
