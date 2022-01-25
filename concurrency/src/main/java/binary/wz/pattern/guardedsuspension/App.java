package binary.wz.pattern.guardedsuspension;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * @author binarywz
 * @date 2022/1/25 23:05
 * @description:
 */
@Slf4j
public class App {
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(() -> {
            try {
                List<String> response = Downloader.download();
                log.debug("download complete...");
                guardedObject.execute(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        log.debug("waiting...");
        Object response = guardedObject.get();
        log.debug("get response: {}", response);
    }
}
