import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static void main(String[] args) {
        final CountDownLatch cd = new CountDownLatch(CARS_COUNT);
        final CyclicBarrier cb = new CyclicBarrier(CARS_COUNT + 1);
        final Semaphore sm = new Semaphore(CARS_COUNT / 2);
        AtomicBoolean win = new AtomicBoolean(true);

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < CARS_COUNT; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), sm);
        }
        for (int i = 0; i < CARS_COUNT; i++) {
            final int w = i;
            new Thread(() -> {
                cars[w].prepare();
                try {
                    cb.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                cars[w].run();
                if (win.get()) {
                    System.out.println(cars[w].getName() + " - WIN!!!");
                    win.set(false);
                }
                cd.countDown();
            }).start();
        }
        try {
            cb.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        try {
            cd.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}