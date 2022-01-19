import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException {
        Stock F = new Stock("AAPL");
        System.out.println(F.getMetric("roic"));
    }
}
