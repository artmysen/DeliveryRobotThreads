import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq =new HashMap<>();
    final static int ROUTE_LENGTH = 100;
    final static int NUM_THREADS = 1000;
    final static String STEPS = "RLRFR";

    public static void main(String[] args) {
        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(() -> {
                String route = generateRoute(STEPS, ROUTE_LENGTH);
                int freq = 0;
                for(int k = 0; k < ROUTE_LENGTH; k++){
                    if (route.charAt(k) == 'R'){
                        freq++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(freq)) {
                        sizeToFreq.put(freq, sizeToFreq.get(freq) + 1);
                    } else {
                        sizeToFreq.put(freq, 1);
                    }
                }
            }).start();
        }

        Map.Entry<Integer, Integer> max = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("Наиболее частое количество повторений " + max.getKey() + " встретилось " + max.getValue() + " раз");

        System.out.println("Другие размеры: ");
        sizeToFreq
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println(" - " + e.getKey() + " (" + e.getValue() + " раз)" ));
    }
    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}