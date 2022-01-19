import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Stock {
    private String symbol;

    public Stock(String symbol) {
        this.symbol = symbol;
    }

    public Stock() {

    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    private URL generateURL() throws MalformedURLException {
        String strURL = "https://financialmodelingprep.com/api/v3/key-metrics/" + symbol +
                "?apikey=e01406bfd890bdd772698d270593fc95";
        return new URL(strURL);
    }

    private ArrayList<String> getRawData() throws IOException {
        URL url = generateURL();
        ArrayList<String> quotes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null;) {
                quotes.add(line);
            }
        }
        return quotes;
    }

    public Double getMetric(String metricName, int year) throws IOException {
        ArrayList<String> source = getRawData();
        String metric = "";
        int yearIndex = 0;

        for(String str : source){
            if(str.contains("\"" + year)){
                yearIndex = source.indexOf(str);
            }
        }

        ArrayList<String> fullYearMetrics = new ArrayList<>();
        int index = yearIndex;
        while(!source.get(index).contains("}")){
            fullYearMetrics.add(source.get(index));
            index++;
        }

        for(String str : fullYearMetrics){
            if(str.contains(metricName)){
                metric = str.substring(str.indexOf(":") + 1);
                if(metric.contains(",")){
                    metric = metric.substring(0, metric.length() - 1);
                }
            }
        }
        return Double.parseDouble(metric);
    }

    public HashMap<Integer, Double> getMetric(String metricName) throws IOException {
        ArrayList<String> source = getRawData();
        HashMap<Integer, Double> metricHistory = new HashMap<>();
        int index = 0;
        Integer currentYear = 0;
        while(!source.get(index).contains("]")){
            if(source.get(index).contains("date")) {
                currentYear = Integer.parseInt(source.get(index).substring(
                        source.get(index).indexOf(":") + 3, source.get(index).indexOf(":") + 7));
                metricHistory.put(currentYear, getMetric(metricName, currentYear));
            }
            index++;
        }
        return metricHistory;
    }

    public String toString() {
        return symbol;
    }
}
