package net.sopod.soim.common.dubbo;

import net.sopod.soim.common.dubbo.exception.SoimException;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.filter.ExceptionFilter;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * SoimExceptionFilter
 *
 * @author tmy
 * @date 2022-06-02 11:08
 */
public class SoimExceptionFilter extends ExceptionFilter {

    private static final String exceptPack = SoimException.class.getPackageName();

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        if (appResponse.hasException() && GenericService.class != invoker.getInterface()) {
            // 是自定义异常直接返回
            if (appResponse.getException().getClass().getName()
                    .startsWith(exceptPack)) {
                return;
            }
        }
        super.onResponse(appResponse, invoker, invocation);
    }

}
