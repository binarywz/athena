package binary.wz.concurrency.basic.atomic.field;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author binarywz
 * @date 2022/2/12 9:57
 * @description:
 */
@Slf4j
public class App {
    public static void main(String[] args) {
        Student student = new Student();

        AtomicReferenceFieldUpdater updater =
                AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");

        log.info("update success: {}", updater.compareAndSet(student, null, "张三"));
        log.info("student content: {}", student);
    }
}

class Student {
    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}