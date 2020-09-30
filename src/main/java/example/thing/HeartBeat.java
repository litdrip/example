package example.thing;

public class HeartBeat {

    //上次执行时间
    private long preExecutionTime;
    //心跳间隔时间 毫秒
    private long interval;
    //执行任务
    private final Beat task;

    public HeartBeat(Beat task, long interval) {
        this.interval = interval;
        this.task = task;
        this.preExecutionTime = 0L;
        new Thread(this::mainLoop).start();
    }

    public void resetInterval(long interval) {
        synchronized (this) {
            this.interval = interval;
            this.preExecutionTime = 0L;
            this.notify();
        }
    }

    private void mainLoop() {
        while (true) {
            try {
                synchronized (this) {
                    long passTime = System.currentTimeMillis() - preExecutionTime;
                    System.out.println(passTime);
                    if (passTime >= interval) {
                        try {
                            this.interval = task.run();
                        } catch (Exception ignored) {
                        }
                        preExecutionTime = System.currentTimeMillis();
                    }
                    this.wait(interval);
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static interface Beat {
        long run();
    }

}
