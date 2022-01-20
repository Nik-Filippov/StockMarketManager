import java.io.IOException;
import java.util.ArrayList;

public class Client {
    public static void main(String[] args) throws IOException {

        ArrayList<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("AXP"));
        stocks.add(new Stock("AMGN"));
        stocks.add(new Stock("AAPL"));
        stocks.add(new Stock("BA"));
        stocks.add(new Stock("CAT"));
        stocks.add(new Stock("CVX"));
        stocks.add(new Stock("CSCO"));
        stocks.add(new Stock("KO"));
        stocks.add(new Stock("DIS"));
        stocks.add(new Stock("DOW"));
        stocks.add(new Stock("GS"));
        stocks.add(new Stock("HD"));
        stocks.add(new Stock("HON"));
        stocks.add(new Stock("INTC"));
        stocks.add(new Stock("JNJ"));
        stocks.add(new Stock("JPM"));
        stocks.add(new Stock("MCD"));
        stocks.add(new Stock("MRK"));
        stocks.add(new Stock("MSFT"));
        stocks.add(new Stock("NKE"));
        stocks.add(new Stock("PG"));
        stocks.add(new Stock("CRM"));
        stocks.add(new Stock("TRV"));
        stocks.add(new Stock("VZ"));
        stocks.add(new Stock("V"));
        stocks.add(new Stock("WBA"));
        stocks.add(new Stock("WMT"));

        MagicFormulaInvestmentMethod mf = new MagicFormulaInvestmentMethod(stocks, 2020);
    }
}
