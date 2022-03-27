package binary.wz.jvm.servivor;

/**
 * @author binarywz
 * @date 2022/3/27 15:24
 * @description:
 */
public class SurvivorRatio {

    private static final int _1MB = 1024 * 1024;

    /**
     * -verbose:gc
     * -Xmx200M
     * -Xmn50M
     * -XX:TargetSurvivorRatio=60
     * -XX:+PrintTenuringDistribution
     * -XX:+PrintGCDetails
     * -XX:+PrintHeapAtGC
     * -XX:+UseConcMarkSweepGC
     * -XX:+UseParNewGC
     *
     * Xmn: 新生代大小 50m eden=40m s_from=5m s_to=5m
     * TargetSurvivorRatio 动态年龄判断比例
     * UseParNewGC 年轻代采用parnew垃圾回收器
     * UseConcMarkSweepGC 老年代采用CMS垃圾回收器
     */
    public static void main(String[] args) {

        /**
         * 先计算理论值: survivor为5M，TargetSurvivorRatio=60，
         * 即当5M*60%=3M时，survivor对象进入老年代
         */
        byte[] m_1 = new byte[1363148]; // 1.3m
        youngGc(1, 40);

        byte[] m_2 = new byte[838860]; // 0.8m
        youngGc(2, 40);

        byte[] m_3 = new byte[_1MB]; // 1m
        youngGc(3, 40);

        youngGc(4, 40);
    }

    private static void youngGc(int n, int m) {
        System.out.println("====================发生第"+n+"次young gc==============");
        for (int i = 0; i < m; i++) {
            byte[] temp = new byte[_1MB];
        }
    }

}
