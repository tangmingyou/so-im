package net.sopod.soim.router.cache;

/**
 * BiSync
 *
 * @author tmy
 * @date 2022-05-04 17:22
 */
public interface DataSync {

    /** 不是更新数据的方法开头 */
    String[] nonUpdateMethodStart = new String[]{"get", "find", "list",  "is", "select"};

    /** 不是更新数据的方法 */
    String[] nonUpdateMethod = new String[]{"toString", "equals", "hashCode", "wait", "getClass", "notify", "notifyAll"};

    /**
     * 如果是更新方法会同步数据，到新增节点或备份节点
     */
    default boolean isUpdateMethod(String methodName) {
        for (String nonUpdateStart : nonUpdateMethodStart) {
            if (methodName.startsWith(nonUpdateStart)) {
                return false;
            }
        }
        for (String method : nonUpdateMethod) {
            if (method.equals(methodName)) {
                return false;
            }
        }
        return true;
    }

}
