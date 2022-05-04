package net.sopod.soim.router.cache;

/**
 * BiSync
 *
 * @author tmy
 * @date 2022-05-04 17:22
 */
public interface DataSync {

    /** 不是更新数据的方法开头 */
    String[] nonUpdateMethodStart = new String[]{"get", "select", "list"};

    /**
     * 如果是更新方法会同步数据，到新增节点或备份节点
     */
    default boolean isUpdateMethod(String methodName) {
        for (String nonUpdateStart : nonUpdateMethodStart) {
            if (methodName.startsWith(nonUpdateStart)) {
                return false;
            }
        }
        return true;
    }

}
