import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static List<String> winnerList = new ArrayList<>();
    private static int CARS_COUNT = 0;
    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier startBarrier;
    private CyclicBarrier finishBarrier;
    private static final Object lock = new Object();

    public Car(Race race, int speed, CyclicBarrier startBarrier, CyclicBarrier finishBarrier) {
        this.race = race;
        this.speed = speed;
        this.startBarrier = startBarrier;
        this.finishBarrier = finishBarrier;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");

            startBarrier.await();

            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }

            synchronized (lock) {
                winnerList.add(this.name);
            }

            finishBarrier.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getWinners() {
        System.out.println("Победители:");
        for (int i = 0; i < Math.min(3, winnerList.size()); i++) {
            System.out.println(winnerList.get(i) + " занял " + (i + 1) + " место");
        }
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }
}