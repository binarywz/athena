package binary.wz.concurrent.basic.immutable;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author binarywz
 * @date 2022/2/13 11:24
 * @description:
 */
@Slf4j
public class App {
    public static void main(String[] args) {
        concurrentSimpleDateFormat();
        concurrentDateTimeFormatter();
    }

    /**
     * 多线程下使用SimpleDateFormat，不修改SimpleDateFormat属性也会存在线程不安全问题
     * TODO: 需进一步分析为什么会出现这种现象
     */
    private static void concurrentSimpleDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    log.debug("concurrentSimpleDateFormat, res: {}", sdf.parse("1951-04-21"));
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }).start();
        }
    }

    /**
     * 使用Immutable Class解决SimpleDateFormat的问题
     */
    private static void concurrentDateTimeFormatter() {
        DateTimeFormatter stf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                LocalDate date = stf.parse("1951-04-21", LocalDate::from);
                log.debug("concurrentDateTimeFormatter, res: {}", date);
            }).start();
        }
    }
}
