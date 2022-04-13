package net.sopod.soim.core.session;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account extends NetUser {

    private static final Logger logger = LoggerFactory.getLogger(Account.class);

    // public static final AttributeKey<Account> ACCOUNT_KEY = AttributeKey.valueOf(Account.class, "ACCOUNT");

    private final long accountId;

    private final String name;

    Account(NetUser netUser, long accountId, String name) {
        super(netUser.channel.get());
        this.accountId = accountId;
        this.name = name;
    }

    @Override
    public boolean isAccount() {
        return true;
    }

    @Override
    public void upgradeAccount(Account account) {
        logger.warn("account can not upgrade as account again!");
    }

    public long getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public static class AccountBuilder {
        private long accountId;
        private String name;
        private NetUser netUser;
        public static AccountBuilder newBuilder() {
            return new AccountBuilder();
        }
        public AccountBuilder setNetUser(NetUser netUser) {
            this.netUser = netUser;
            return this;
        }
        public AccountBuilder setAccountId(long accountId) {
            this.accountId = accountId;
            return this;
        }
        public AccountBuilder setName(String name) {
            this.name = name;
            return this;
        }
        public Account build() {
            Preconditions.checkNotNull(netUser);
            Preconditions.checkNotNull(name);
            return new Account(netUser, accountId, name);
        }
    }

}
