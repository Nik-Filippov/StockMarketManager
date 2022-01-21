import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MagicFormulaInvestmentMethod {

    private ArrayList<Stock> stocks;
    private int year;

    public MagicFormulaInvestmentMethod(ArrayList<Stock> stocks, int year) throws IOException {
        this.stocks = stocks;
        this.year = year;

        HashMap<String, Double> finalRanking = new HashMap<>();

        HashMap<String, Double> ebitdaToEvRanking = ebitdaToEvRanking();
        Double i = 0.0;
        for(String str : ebitdaToEvRanking.keySet()){
            finalRanking.put(str, i);
            i++;
        }
        Double j = 0.0;
        HashMap<String, Double> returnOnCapitalRanking = returnOnCapitalRanking();
        for(String str : returnOnCapitalRanking.keySet()){
            finalRanking.put(str, finalRanking.get(str) + j);
            j++;
        }

        System.out.println(ebitdaToEvRanking());
        System.out.println(returnOnCapitalRanking());
        System.out.println(sort(finalRanking));
    }

    public MagicFormulaInvestmentMethod(){

    }

    public HashMap<String, Double> ebitdaToEvRanking() throws IOException {
        HashMap<String, Double> ebitdaToEv = new HashMap<>();

        for(Stock s : this.stocks){
            ebitdaToEv.put(s.getSymbol(), 1 / s.getMetric("enterpriseValueOverEBITDA", year));
        }

        return sort(ebitdaToEv);
    }

    public HashMap<String, Double> returnOnCapitalRanking() throws IOException {
        HashMap<String, Double> returnOnCapital = new HashMap<>();

        for(Stock s : this.stocks){
            returnOnCapital.put(s.getSymbol(), s.getMetric("roic", year));
        }

        return sort(returnOnCapital);
    }

    private HashMap<String, Double> sort(HashMap<String, Double> hashMap){
        HashMap<String, Double> result = hashMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return result;
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(ArrayList<Stock> stocks) {
        this.stocks = stocks;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
