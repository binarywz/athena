package binary.wz.jvm.servivor;

/**
 * @author binarywz
 * @date 2022/3/27 11:31
 * @description:
 */
public class SurvivorTenuring {
    private static int _1MB = 1024 * 1024;
    /**
     * -verbose:gc
     * -Xmx200M
     * -Xmn50M
     * -XX:+PrintTenuringDistribution
     * -XX:+PrintGCDetails
     * -XX:+PrintHeapAtGC
     * -XX:+UseConcMarkSweepGC
     * -XX:+UseParNewGC
     * -XX:+MaxTenuringThreshold=3
     *
     * Xmn: 新生代大小 50m eden=40m s_from=5m s_to=5m
     * PrintTenuringDistribution: 打印survivor区的age信息
     * @param args
     */
    public static void main(String[] args) {
        // 在s0和s1之间不断来回拷贝增加age，直到达到MaxTenuringThreshold晋升到old
        byte[] m1 = new byte[_1MB];

        // 第1次minor gc，m1的age为1
        youngGc(1, 40);

        // 第1次minor gc，m1的age为2
        youngGc(2, 40);

//        byte[] m2 = new byte[_1MB];

        // 第1次minor gc，m1的age为3
        youngGc(3, 40);

        /**
         * 第4次minor gc，由于m1的年龄已经是3，且MaxTenuringThreshold=3，所以m1对象
         * 会晋升到老年代，同时s_from和s_to空间中的对象被清空那
         */
        youngGc(4, 40);
    }

    private static void youngGc(int n, int m) {
        System.out.println("====================发生第"+n+"次young gc==============");
        for (int i = 0; i < m; i++) {
            byte[] temp = new byte[_1MB];
        }
    }

}
