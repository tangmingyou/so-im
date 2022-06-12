package net.sopod.soim.das.user.cache;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

/**
 * FriendRelationCache
 *
 * @author tmy
 * @date 2022-06-12 09:38
 */
@Service
public class FriendRelationCache {

    private final Table<Long, Long, Long> friendRelationTable = HashBasedTable.create();;

    public Long computeIfAbsent(Long uid, Long fid, BiFunction<Long, Long, Long> mappingFunction) {
        Long relationId;
        if (null != (relationId = friendRelationTable.get(uid, fid))) {
            return relationId;
        }
        // TODO lockFunc(uid, fid)
        synchronized (FriendRelationCache.class) {
            if (null != (relationId = friendRelationTable.get(uid, fid))) {
                return relationId;
            }
            relationId = mappingFunction.apply(uid, fid);
            friendRelationTable.put(uid, fid, relationId);
        }
        return relationId;
    }

    public Long remove(Long uid, Long fid) {
        return friendRelationTable.remove(uid, fid);
    }

}
