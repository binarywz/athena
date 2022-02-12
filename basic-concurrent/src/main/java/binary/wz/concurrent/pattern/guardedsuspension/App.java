package binary.wz.concurrent.pattern.guardedsuspension;

import lombok.extern.slf4j.Slf4j;

/**
 * @author binarywz
 * @date 2022/1/25 23:05
 * @description: Postman(结果产生线程)跟People(结果接收线程)是一一对应的关系
 */
@Slf4j
public class App {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        Thread.sleep(1_000);
        for (Integer id : GuardManager.getIds()) {
            new Postman(id, "postman-" + id).start();
        }
    }
}

@Slf4j
class People extends Thread {
    @Override
    public void run() {
        GuardedObject guardedObject = GuardManager.createGuardedObject();
        log.debug("等待收信, id: {}", guardedObject.getId());
        Object res = guardedObject.get(5_000);
        log.debug("收到信, id: {}, content: {}", guardedObject.getId(), res);
    }
}

@Slf4j
class Postman extends Thread {
    private int id;
    private String mail;

    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardedObject guardedObject = GuardManager.getGuardedObject(this.id);
        log.debug("开始送信, id: {}, content: {}", guardedObject.getId(), mail);
        guardedObject.complete(mail);
    }
}
