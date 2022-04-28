package net.sopod.soim.entry.server;

import net.sopod.soim.core.session.Account;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * NetUserRegistry
 *
 * @author tmy
 * @date 2022-04-28 15:33
 */
@Service
public class AccountRegistry {

    private ConcurrentHashMap<Long, Account> accounts = new ConcurrentHashMap<>();

    public void put(Account account) {
        accounts.put(account.getUid(), account);
    }

    public Account get(Long uid) {
        return accounts.get(uid);
    }

}
