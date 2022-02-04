package binary.wz.concurrency.pattern.syncexecute;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SyncWaitNotify {
    // 等待标记
    private int flag;
    // 循环次数
    private int loopNumber;

    public SyncWaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    /*
    输出内容       等待标记     下一个标记
       a           1             2
       b           2             3
       c           3             1
    */
    public void execute(String str, int waitFlag, int nextFlag) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info("execute job: {}", str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}