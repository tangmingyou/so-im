package net.sopod.soim.router.datasync;

import net.sopod.soim.common.util.StringUtil;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.cache.RouterUserStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SyncTypeEnum
 * 需要同步的数据类型
 *
 * @author tmy
 * @date 2022-05-05 10:40
 */
public class SyncTypes {

    /**
     * 模拟枚举根据顺序值获取数据类型对象
     */
    public static <T extends DataSync> SyncType<T> getSyncType(int ordinal) {
        return SyncType.getSyncType(ordinal);
    }

    /**
     * 用户数据同步处理:
     * 改: 代理对象方法监控(getData(dataKey), updateMethod(args))
     * 增: 增数据(data)
     * 删: 删数据(dataKey)
     */
    public static final SyncType<RouterUser> ROUTER_USER = new SyncType<>(RouterUser.class, "ROUTER_USER") {
        @Override
        public String getDataKey(RouterUser data) {
            return StringUtil.toString(data.getUid());
        }

        @Override
        public RouterUser getData(String uid) {
            return RouterUserStorage.getInstance().get(Long.valueOf(uid));
        }

        @Override
        public boolean addData(RouterUser data) {
            RouterUserStorage.getInstance().put(data.getUid(), data);
            return true;
        }

        @Override
        public boolean removeData(String uid) {
            return null != RouterUserStorage.getInstance().remove(Long.valueOf(uid));
        }

        @Override
        public int onceSyncSize() {
            return 30;
        }
    };

    public static abstract class SyncType<T extends DataSync> {
        private static final List<SyncType<?>> TYPES = new ArrayList<>();
        private static int ordinalSeq = Byte.MIN_VALUE;

        private final int ordinal;
        private final Class<T> dataType;
        private final String name;

        SyncType(Class<T> dataType, String name) {
            this.ordinal = ordinalSeq++;
            if (ordinal > Byte.MAX_VALUE) {
                throw new IllegalStateException("类型超过255个,需修改协议");
            }
            this.dataType = dataType;
            this.name = name;
            TYPES.add(this);
        }

        public Class<T> dataType() {
            return dataType;
        }

        public String name() {
            return name;
        }

        public int ordinal() {
            return ordinal;
        }

        public abstract String getDataKey(T data);

        public abstract T getData(String key);

        public abstract boolean addData(T data);

        public abstract boolean removeData(String key);

        public abstract int onceSyncSize();

        @Nullable
        @SuppressWarnings("unchecked")
        static <T extends DataSync> SyncType<T> getSyncType(int ordinal) {
            for (SyncType<?> type : TYPES) {
                if (type.ordinal == ordinal) {
                    return (SyncType<T>) type;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return "SyncType{" +
                    "ordinal=" + ordinal +
                    ", dataType=" + dataType +
                    ", name='" + name + '\'' +
                    '}' + "@" + Integer.toHexString(this.hashCode());
        }

    }

    public static void main(String[] args) {
        System.out.println(ROUTER_USER.ordinal());
        System.out.println(SyncTypes.getSyncType(0));
        System.out.println(SyncTypes.getSyncType(1));
        System.out.println(SyncTypes.getSyncType(2));
        Class<RouterUser> routerUserClass = SyncTypes.ROUTER_USER.dataType();

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "2");
        System.out.println(map.remove("2"));
        System.out.println(map.remove("1"));
    }

}
