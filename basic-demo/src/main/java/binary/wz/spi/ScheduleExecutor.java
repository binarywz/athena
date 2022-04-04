package binary.wz.spi;

/**
 * @author binarywz
 * @date 2022/4/4 21:02
 * @description:
 */
public class ScheduleExecutor implements Executor {
    @Override
    public void execute() {
        System.out.println("schedule executor...");
    }
}
