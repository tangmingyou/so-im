package net.sopod.soim.router.cache;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.sopod.soim.router.datasync.DataSync;
import net.sopod.soim.router.datasync.annotation.SyncIgnore;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

class A {
    private String aName;
}

/**
 * RouterUser
 *
 * @author tmy
 * @date 2022-04-28 11:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class RouterUser extends A implements DataSync {

    public static final int a = 1;

    private long uid;

    private String account;

    private Boolean isOnline;

    /** 在线时间戳 */
    private long onlineTime;

    private String imEntryAddr;

    @SyncIgnore
    public RouterUser setUid(long uid) {
        this.uid = uid;
        return this;
    }

    public static void main(String[] args) {

        for (Field field : RouterUser.class.getDeclaredFields()) {
            System.out.println(field.getName());
        }
        System.out.println("=============");
        for (Field field : RouterUser.class.getDeclaredFields()) {
            System.out.println(field.getName());
        }
        System.out.println("=============");
        for (Field field : RouterUser.class.getSuperclass().getDeclaredFields()) {
            System.out.println(field.getName());
        }
        System.out.println(RouterUser.class.getSuperclass().getSuperclass());
        for (Field field : RouterUser.class.getSuperclass().getSuperclass().getDeclaredFields()) {
            System.out.println(field.getName());
        }
        // Modifier.isFinal()
    }

}
