import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StockList implements Collection<Stock> {
    private ArrayList<Stock> stocks;
    public StockList() {
        stocks = new ArrayList<>();
    }

    public HashMap<String, Double> rank(String metric, int year) throws IOException {
        return rank(metric, year, false);
    }

    public HashMap<String, Double> rank(String metric, int year, boolean isInverted) throws IOException {
        HashMap<String, Double> hm = new HashMap<>();
        if(isInverted){
            for(Stock s : stocks){
                hm.put(s.getSymbol(), 1 / s.getMetric(metric, year));
            }
        }
        else {
            for(Stock s : stocks){
                hm.put(s.getSymbol(), s.getMetric(metric, year));
            }
        }
        return sort(hm);
    }

    public HashMap<String, Double> rank(String metric, int yearBegin, int yearEnd) throws IOException {
        return rank(metric, yearBegin, yearEnd, false);
    }

    public HashMap<String, Double> rank(String metric, int yearBegin, int yearEnd, boolean isInverted) throws IOException {
        HashMap<String, Double> hm = new HashMap<>();
        if(isInverted){
            for(Stock s : stocks){
                hm.put(s.getSymbol(), 1 / s.getMetric(metric, yearEnd) - 1 / s.getMetric(metric, yearBegin));
            }
        }
        else {
            for(Stock s : stocks){
                double a = s.getMetric(metric, yearEnd);
                double b = s.getMetric(metric, yearBegin);
                hm.put(s.getSymbol(), s.getMetric(metric, yearEnd) - s.getMetric(metric, yearBegin));
            }
        }
        return sort(hm);
    }

    public static HashMap<String, Double> sort(HashMap<String, Double> hashMap){
        HashMap<String, Double> result = hashMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return result;
    }

    @Override
    public int size() {
        return stocks.size();
    }

    @Override
    public boolean isEmpty() {
        return stocks.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return stocks.contains(o);
    }

    @Override
    public Iterator<Stock> iterator() {
        return stocks.iterator();
    }

    @Override
    public Object[] toArray() {
        return stocks.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return stocks.toArray(a);
    }

    @Override
    public boolean add(Stock o) {
        return stocks.add(o);
    }

    @Override
    public boolean remove(Object o) {
        return stocks.remove(o);
    }

    public Stock get(int i){
        return stocks.get(i);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return stocks.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Stock> c) {
        return stocks.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return stocks.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return stocks.retainAll(c);
    }

    @Override
    public void clear() {
        stocks.clear();
    }
}
