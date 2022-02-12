package binary.wz.concurrent.pattern.syncexecute;

/**
 * @author binarywz
 * @date 2022/2/4 14:32
 * @description:
 */
public class App {
    public static void main(String[] args) {
        syncWaitNotifyExecute();
        syncParkExecute();
    }

    private static void syncWaitNotifyExecute() {
        SyncWaitNotify wn = new SyncWaitNotify(1, 5);
        new Thread(() -> {
            wn.execute("a", 1, 2);
        }).start();
        new Thread(() -> {
            wn.execute("b", 2, 3);
        }).start();
        new Thread(() -> {
            wn.execute("c", 3, 1);
        }).start();
    }

    private static void syncParkExecute() {
        SyncPark syncPark = new SyncPark(3);
        Thread t1 = new Thread(() -> {
            syncPark.execute("a");
        });
        Thread t2 = new Thread(() -> {
            syncPark.execute("b");
        });
        Thread t3 = new Thread(() -> {
            syncPark.execute("c");
        });
        syncPark.setThreads(t1, t2, t3);
        syncPark.start();
    }
}
