package net.sopod.soim.entry.server;

import net.sopod.soim.core.session.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AccountRegistry.class);

    private ConcurrentHashMap<Long, Account> accounts = new ConcurrentHashMap<>();

    public void put(Account account) {
        logger.info("registry account: {}", account);
        accounts.put(account.getUid(), account);
    }

    public Account get(Long uid) {
        logger.info("account list: {}", accounts);
        return accounts.get(uid);
    }

}
