import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Window {
    final String[] metrics = {"revenuePerShare", "netIncomePerShare", "operatingCashFlowPerShare", "freeCashFlowPerShare",
            "cashPerShare", "bookValuePerShare", "tangibleBookValuePerShare", "shareholdersEquityPerShare",
            "interestDebtPerShare", "marketCap", "enterpriseValue", "peRatio", "priceToSalesRatio", "pocfratio",
            "pfcfRatio", "pbRatio", "ptbRatio", "evToSales", "enterpriseValueOverEBITDA", "evToOperatingCashFlow",
            "evToFreeCashFlow", "earningsYield", "freeCashFlowYield", "debtToEquity", "debtToAssets", "netDebtToEBITDA",
            "currentRatio", "interestCoverage", "incomeQuality", "dividendYield", "payoutRatio",
            "salesGeneralAndAdministrativeToRevenue", "researchAndDdevelopementToRevenue", "intangiblesToTotalAssets",
            "capexToOperatingCashFlow", "capexToRevenue", "capexToDepreciation", "stockBasedCompensationToRevenue",
            "grahamNumber", "roic", "returnOnTangibleAssets", "grahamNetNet", "workingCapital", "tangibleAssetValue",
            "netCurrentAssetValue", "investedCapital", "averageReceivables", "averagePayables", "averageInventory",
            "daysSalesOutstanding", "daysPayablesOutstanding", "daysOfInventoryOnHand", "receivablesTurnover",
            "payablesTurnover", "inventoryTurnover", "roe", "capexPerShare"};
    final String[] header = {"Metric", "Value"};
    final int WIDTH = 1080, HEIGHT = WIDTH / 12 * 9;
    final int B_WIDTH = 450, B_HEIGHT = 120;
    JFrame frame;
    public Window (){
        frame = new JFrame("StockManager");
        openMainMenu();
    }

    private void openMainMenu(){
        JButton b1 = new JButton("Compare two stocks");

        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);

        b1.setBounds(WIDTH / 2 - B_WIDTH / 2, HEIGHT / 2 - B_HEIGHT / 2, B_WIDTH, B_HEIGHT);
        b1.addActionListener(e -> {
            b1.setVisible(false);
            openCompareTwoMenu();
        });

        frame.add(b1);
    }

    private void openCompareTwoMenu(){
        ArrayList<Component> components =  new ArrayList<>();
        Calendar c = new GregorianCalendar();
        String[][] table1 = new String[metrics.length][2];
        String[][] table2 = new String[metrics.length][2];

        JTextField tf1 = new JTextField("Stock 1");
        JTextField tf2 = new JTextField("Stock 2");
        JTextField tf3 = new JTextField(Integer.toString(c.get(Calendar.YEAR) - 1));

        JButton b1 = new JButton("Run");
        JButton b2 = new JButton("Clear");
        JButton b3 = new JButton("Exit");

        JTable t1 = new JTable();
        JTable t2 = new JTable();

        tf1.setBounds((int)(0.05 * WIDTH), (int)(0.1 * HEIGHT), (int)(WIDTH * 0.35), B_HEIGHT);
        tf1.setVisible(true);
        tf1.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(tf1.getText().equals("Stock 1")) {
                    tf1.setText("");
                }
            }
            public void focusLost(FocusEvent e) {
                if(tf1.getText().isBlank()) {
                    tf1.setText("Stock 1");
                }
            }
        });

        tf2.setBounds((int)(0.6 * WIDTH), (int)(0.1 * HEIGHT), (int)(WIDTH * 0.35), B_HEIGHT);
        tf2.setVisible(true);
        tf2.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(tf2.getText().equals("Stock 2")) {
                    tf2.setText("");
                }
            }
            public void focusLost(FocusEvent e) {
                if(tf2.getText().isBlank()) {
                    tf2.setText("Stock 2");
                }
            }
        });

        tf3.setBounds((int)(0.45 * WIDTH), (int)(0.125 * HEIGHT), (int)(WIDTH * 0.1), B_HEIGHT / 2);
        tf3.setVisible(true);

        b1.setBounds((int)(0.45 * WIDTH), (int)(0.25 * HEIGHT), (int)(WIDTH * 0.1), B_HEIGHT / 2);
        b1.addActionListener(e -> {
            Stock stock1 = null;
            try {
                stock1 = new Stock(tf1.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Stock stock2 = null;
            try {
                stock2 = new Stock(tf2.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            int year = Integer.parseInt(tf3.getText());
            for(int i = 0; i < metrics.length; i++){
                table1[i][0] = metrics[i];
                table2[i][0] = metrics[i];
                try {
                    if(stock1.getMetric(metrics[i], year) == null){
                        table1[i][1] = "N/A";
                    }
                    else{
                        table1[i][1] = Double.toString(stock1.getMetric(metrics[i], year));
                    }
                    if(stock2.getMetric(metrics[i], year) == null) {
                        table2[i][1] = "N/A";
                    }
                    else{
                        double a = stock2.getMetric(metrics[i], year);
                        table2[i][1] = Double.toString(a);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            TableModel m1 = new DefaultTableModel(table1, header);
            t1.setModel(m1);

            TableModel m2 = new DefaultTableModel(table2, header);
            t2.setModel(m2);

            JScrollPane s1 = new JScrollPane(t1);
            JScrollPane s2 = new JScrollPane(t2);

            s1.setBounds((int)(0.05 * WIDTH), (int)(0.3 * HEIGHT), (int)(WIDTH * 0.35), HEIGHT / 2);

            s2.setBounds((int)(0.6 * WIDTH), (int)(0.3 * HEIGHT), (int)(WIDTH * 0.35), HEIGHT / 2);

            components.add(frame.add(s1));
            components.add(frame.add(s2));
        });

        b2.setBounds((int)(0.45 * WIDTH), (int)(0.35 * HEIGHT), (int)(WIDTH * 0.1), B_HEIGHT / 2);
        b2.addActionListener(e -> {
            tf1.setText("");
            tf2.setText("");
        });

        b3.setBounds((int)(0.45 * WIDTH), (int)(0.45 * HEIGHT), (int)(WIDTH * 0.1), B_HEIGHT / 2);
        b3.addActionListener(e -> {
            for(Component comp : components){
                comp.setVisible(false);
            }
            openMainMenu();
            for(Component comp : components){
                frame.remove(comp);
            }
        });
        components.add(frame.add(tf1));
        components.add(frame.add(tf2));
        components.add(frame.add(tf3));
        components.add(frame.add(b1));
        components.add(frame.add(b2));
        components.add(frame.add(b3));
        components.add(frame.add(t1));
        components.add(frame.add(t2));
    }

    public void show(){
        frame.show();
    }
}
