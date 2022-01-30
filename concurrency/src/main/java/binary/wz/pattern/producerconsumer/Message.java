package binary.wz.pattern.producerconsumer;

import lombok.AllArgsConstructor;

/**
 * @author binarywz
 * @date 2022/1/30 15:19
 * @description:
 */
@AllArgsConstructor
public final class Message {
    private int id;
    private Object value;

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value + "}";
    }
}
