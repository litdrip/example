package java.util.concurrent;

public class CountDownLatchExample {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(2);

        new Thread(() -> {
            System.out.println("in1");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("out1");
            cdl.countDown();
        }).start();

        new Thread(() -> {
            System.out.println("in2");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("out2");
            cdl.countDown();
        }).start();

        System.out.println("main wait.");
        cdl.await();
        System.out.println("end");
    }
}
