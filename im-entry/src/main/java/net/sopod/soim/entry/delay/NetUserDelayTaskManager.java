package net.sopod.soim.entry.delay;

import com.google.common.base.Preconditions;
import com.google.protobuf.MessageLite;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.core.handler.MessageHandler;
import net.sopod.soim.core.registry.ProtoMessageHandlerRegistry;
import net.sopod.soim.core.session.NetUser;
import net.sopod.soim.entry.server.ProtoMessageDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.concurrent.*;

/**
 * NetUserDelayTaskManager
 * 延时任务执行
 *
 * @author tmy
 * @date 2022-04-13 16:59
 */
public class NetUserDelayTaskManager {

    private static final Logger logger = LoggerFactory.getLogger(NetUserDelayTaskManager.class);

    private static final DelayQueue<DelayTask> DELAY_QUEUE;

    static {
        DELAY_QUEUE = new DelayQueue<>();
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.scheduleWithFixedDelay(() -> {
            try {
                DelayTask delayTask;
                // 连续批量执行50个任务
                for (int i = 0; i < 50; i++) {
                    delayTask = DELAY_QUEUE.poll();
                    if (delayTask == null) {
                        break;
                    }
                    NetUser netUser = delayTask.getNetUser();
                    if (netUser.isActive()) {
                        // 延时任务执行时 NetUser 可能已升级为 Account
                        NetUser curNetUser = netUser.channel().attr(NetUser.NET_USER_KEY).get();
                        ProtoMessageDispatcher.dispatch(curNetUser, delayTask.getTaskMsg());
                    } else {
                        logger.info("delayTask netUser inactive, task cancel!");
                    }
                }
            }catch (Throwable e) {
                logger.error("netUser delay task schedule error: ", e);
            }
        }, 100,100, TimeUnit.MILLISECONDS);
    }

    /**
     * TODO boolean netUser inactive cancel
     */
    public static void addTask(NetUser netUser, MessageLite taskMsg, long delay, TimeUnit unit) {
        Preconditions.checkNotNull(taskMsg);
        Preconditions.checkNotNull(unit);
        Preconditions.checkArgument(delay > 0, "延迟时间值必须大于0");
        // 检查任务消息有无对应 handler 执行
        MessageHandler<MessageLite> typeHandler = ProtoMessageHandlerRegistry.getTypeHandler(taskMsg.getClass());
        if (typeHandler == null) {
            throw new IllegalStateException("not found taskMsg handler for type" + taskMsg.getClass() + "!");
        }
        DELAY_QUEUE.add(new DelayTask(netUser, taskMsg, ImClock.millis() + unit.toMillis(delay)));
    }

    private static class DelayTask implements Delayed {
        // 如果 netUser 执行时不存在了，任务取消
        private final NetUser netUser;
        private final MessageLite taskMsg;
        private final long time;
        public DelayTask(NetUser netUser, MessageLite taskMsg, long time) {
            this.netUser = netUser;
            this.taskMsg = taskMsg;
            this.time = time;
        }
        @Override
        public long getDelay(TimeUnit timeUnit) {
            return time - ImClock.millis();
        }
        @Override
        public int compareTo(Delayed delayed) {
            return Long.compare(time, delayed.getDelay(TimeUnit.MILLISECONDS));
        }
        public MessageLite getTaskMsg() {
            return this.taskMsg;
        }
        public NetUser getNetUser() {
            return netUser;
        }
    }

}
