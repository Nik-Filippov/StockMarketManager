import java.io.IOException;

public class Client {


    public static void main(String[] args) throws IOException {
        run();
    }

    private static void run() throws IOException {
        Window w =  new Window();
        w.show();
        /*
        StockList stocks = new StockList();
        stocks.add(new Stock("SGML"));
        stocks.add(new Stock("LTHM"));
        stocks.add(new Stock("LAC"));
        stocks.add(new Stock("ALB"));
        stocks.add(new Stock("SLI"));
        for(Stock s : stocks){
            s.cache();
        }
        MagicFormulaInvestmentMethod mf = new MagicFormulaInvestmentMethod(stocks, 2019,2021);
         */
    }
}
