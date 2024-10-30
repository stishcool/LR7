import java.util.concurrent.*;

public class MainClass {
    public static final int CARS_COUNT = 15;

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        CyclicBarrier startBarrier = new CyclicBarrier(CARS_COUNT, new Runnable() {
            @Override
            public void run() {
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            }
        });
        CyclicBarrier finishBarrier = new CyclicBarrier(CARS_COUNT + 1);

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 20), startBarrier, finishBarrier);
        }

        for (Car car : cars) {
            new Thread(car).start();
        }

        finishBarrier.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        Car.getWinners();
    }
}