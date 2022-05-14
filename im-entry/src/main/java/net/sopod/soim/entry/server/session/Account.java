package net.sopod.soim.entry.server.session;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account extends NetUser {

    private static final Logger logger = LoggerFactory.getLogger(Account.class);

    // public static final AttributeKey<Account> ACCOUNT_KEY = AttributeKey.valueOf(Account.class, "ACCOUNT");

    private final long uid;

    private final String name;

    private final String imRouterId;

    Account(NetUser netUser, long uid, String name, String imRouterId) {
        super(netUser.channel.get());
        this.uid = uid;
        this.name = name;
        this.imRouterId = imRouterId;
    }

    @Override
    public boolean isAccount() {
        return true;
    }

    @Override
    public void upgradeAccount(Account account) {
        logger.warn("account can not upgrade as account again!");
    }

    public long getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getImRouterId() {
        return imRouterId;
    }

    public static class AccountBuilder {
        private long uid;
        private String name;
        private String imRouterId;
        private NetUser netUser;
        public static AccountBuilder newBuilder() {
            return new AccountBuilder();
        }
        public AccountBuilder setNetUser(NetUser netUser) {
            this.netUser = netUser;
            return this;
        }
        public AccountBuilder setUid(long uid) {
            this.uid = uid;
            return this;
        }
        public AccountBuilder setName(String name) {
            this.name = name;
            return this;
        }
        public AccountBuilder setImRouterId(String imRouterId) {
            this.imRouterId = imRouterId;
            return this;
        }
        public Account build() {
            Preconditions.checkNotNull(netUser);
            Preconditions.checkNotNull(name);
            return new Account(netUser, uid, name, imRouterId);
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", channel=" + channel +
                '}';
    }

}
