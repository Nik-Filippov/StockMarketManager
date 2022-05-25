import java.io.*;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Stock {
    private String symbol;
    private ArrayList<String> data;
    public Stock(String symbol) throws IOException {
        this.symbol = symbol;
        data = new ArrayList<>();
        getRawData();
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

    private void getRawData() throws IOException {
        File dir = new File("cache/");
        File[] matches = dir.listFiles((dir1, name) -> name.startsWith(symbol) && name.endsWith(".txt"));

        if(matches.length == 0) {
            URL url = generateURL();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    data.add(line);
                }
            }
        }
        else{
            BufferedReader br = new BufferedReader(new java.io.FileReader(matches[0]));
            String description;
            while((description = br.readLine()) != null) {
                data.add(description);
            }
            br.close();
        }
    }

    public Double getMetric(String metricName, int year) throws IOException {
        String metric = "";
        int yearIndex = 0;

        for(String str : data){
            if(str.contains("\"" + year)){
                yearIndex = data.indexOf(str);
            }
        }

        if(yearIndex == 0){
            return null;
        }

        ArrayList<String> fullYearMetrics = new ArrayList<>();
        int index = yearIndex;
        while(!data.get(index).contains("}")){
            fullYearMetrics.add(data.get(index));
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
        if(metric.contains("null")){
            return null;
        }
        else {
            return Double.parseDouble(metric);
        }
    }

    public boolean cache() throws IOException {
        try {
            if(data.size() < 3){
                System.out.println("No quote for such stock symbol.");
                return false;
            }
            //Look for prev save file
            File dir = new File("cache/");
            File[] matches = dir.listFiles((dir1, name) -> name.startsWith(symbol) && name.endsWith(".txt"));
            if (matches.length > 0) {
                System.out.println("Stock info is already cached.");
            } else {
                String fileName = symbol + ".txt";
                File myObj = new File("cache/" + fileName);
                if (myObj.createNewFile()) {
                    FileWriter myWriter = new FileWriter("cache/" + fileName);
                    for (String stock : data) {
                        myWriter.write(stock + "\n");
                    }
                    myWriter.close();
                }
                System.out.println("Stock info cached successfully.");
                return true;
            }
        }
        catch (Exception e){
            System.out.println("Stock caching failed most likely due the exhaustion of free API calls.");
            return false;
        }
        return false;
    }

    public String toString() {
        return symbol;
    }
}
