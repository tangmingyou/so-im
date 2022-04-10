package net.sopod.soim.core.session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class Account extends NetUser {

    public static final AttributeKey<Account> ACCOUNT_KEY = AttributeKey.valueOf(Account.class, "ACCOUNT");

    private long accountId;

    private String name;

    public Account(Channel channel) {
        super(channel);
    }

    @Override
    public boolean isAccount() {
        return true;
    }

}
