import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MagicFormulaInvestmentMethod {

    private StockList stocks;

    public MagicFormulaInvestmentMethod(StockList stocks, int year) throws IOException {
        this.stocks = stocks;

        HashMap<String, Double> finalRanking = new HashMap<>();

        HashMap<String, Double> enterpriseValueOverEBITDA = stocks.rank("enterpriseValueOverEBITDA", year, true);
        Double i = 0.0;
        for(String str : enterpriseValueOverEBITDA.keySet()){
            finalRanking.put(str, i);
            i++;
        }
        Double j = 0.0;
        HashMap<String, Double> roic = stocks.rank("roic", year);
        for(String str : roic.keySet()){
            finalRanking.put(str, finalRanking.get(str) + j);
            j++;
        }

        System.out.println(enterpriseValueOverEBITDA);
        System.out.println(roic);
        System.out.println(StockList.sort(finalRanking));
    }

    public MagicFormulaInvestmentMethod(StockList stocks, int yearBegin, int yearEnd) throws IOException {
        this.stocks = stocks;

        HashMap<String, Double> finalRanking = new HashMap<>();

        HashMap<String, Double> enterpriseValueOverEBITDA = stocks.rank("enterpriseValueOverEBITDA", yearBegin, yearEnd, true);
        Double i = 0.0;
        for(String str : enterpriseValueOverEBITDA.keySet()){
            finalRanking.put(str, i);
            i++;
        }
        Double j = 0.0;
        HashMap<String, Double> roic = stocks.rank("roic", yearBegin, yearEnd);
        for(String str : roic.keySet()){
            finalRanking.put(str, finalRanking.get(str) + j);
            j++;
        }

        System.out.println(enterpriseValueOverEBITDA);
        System.out.println(roic);
        System.out.println(stocks.sort(finalRanking));
    }
}
