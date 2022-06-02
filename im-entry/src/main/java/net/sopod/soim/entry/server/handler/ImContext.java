package net.sopod.soim.entry.server.handler;

/**
 * ExecContext
 *
 * @author tmy
 * @date 2022-06-02 23:31
 */
public class ImContext {

    /** 请求id */
    private final Integer serialNo;

    public ImContext(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

}
