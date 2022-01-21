import java.io.IOException;
import java.util.ArrayList;

public class Client {
    public static void main(String[] args) throws IOException {

        ArrayList<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("F"));
        stocks.add(new Stock("TSLA"));
        stocks.add(new Stock("AAPL"));

        MagicFormulaInvestmentMethod mf = new MagicFormulaInvestmentMethod(stocks, 2020);

        stocks.get(2).cache();
    }
}
