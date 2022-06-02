package net.sopod.soim.das.user.amqp;

import lombok.AllArgsConstructor;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.das.user.api.config.LogicTables;
import net.sopod.soim.das.user.api.model.entity.ImGroupMessage;
import net.sopod.soim.das.user.api.model.entity.ImMessage;
import net.sopod.soim.das.user.api.mq.ChatQueueType;
import net.sopod.soim.logic.api.segmentid.core.SegmentIdGenerator;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Sender
 *
 * @author tmy
 * @date 2022-05-28 16:23
 */
@Component
@AllArgsConstructor
public class Sender implements ApplicationListener<ApplicationReadyEvent> {

    private SegmentIdGenerator segmentIdGenerator;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("send msg....");
            String content = "《蒙娜丽莎》的姿势，已经升华为一种神圣的符号，它不仅仅是艺术规律问题，不仅仅是对文艺复兴人文思想粗糙大潮的冲击，而是心灵回归者、觉醒者心中的一个崇高细致理想的显现。《蒙娜丽莎》具有另一种难以说明的象征意义，如佛的坐势、站势、讲道姿势，菩萨的动势，基督的几种象征性姿势：如两手摊开，一手持十字，一手指天，这有某种非世俗的意味，不是做作，而是不得不是如此，是最不自然最自然，最不简单最简单，最轻松最沉甸甸的样式，你无法赞美它，又无法挑剔它，它就是这样，原始的样子，人们只能沉醉其中，而不能有条理地褒贬它。一切欺骗与神秘都集中在《蒙娜丽莎》身上，而令人们产生一种畏惧。《蒙娜丽莎》是一个精力充沛的形象，在该幅作品面前，不能说她是女人肖像，她穿越一切无所不见、又视而不见的空洞目光，想躲避是办不到的，而想迎接那目光同样也是枉然。奇异的前额，广阔得失去了一个平凡人的味道，弥漫在脸上的那种神奇表情，似笑、非笑使人们无法相信这是一张现实的脸，而它的存在，又无法使人们的怀疑进行得彻底。《蒙娜丽莎》的表情，像东方佛教中佛陀的表情一样，是非凡人所能做出来的。“蒙娜丽莎”不是因为了什么而微笑，她只是静静地在那，脸上是自然地出现的一种永恒的、无所谓表情的表情。 [12]  《蒙娜丽莎》的美学意义，主要在于人物形象焕发出的人性的光辉；而在这之前，即使是人的形象，也带有或多或少的宗教气息。 [13] ";
            if (i % 2 == 0) {
                long id = segmentIdGenerator.nextId(LogicTables.IM_GROUP_MESSAGE);
                ImGroupMessage imGroupMessage = new ImGroupMessage()
                        .setId(id)
                        .setGroupId(10010L)
                        .setSender(10086L)
                        .setCreateTime(ImClock.date())
                        .setContent(content);
                amqpTemplate.convertAndSend(ChatQueueType.IM_GROUP_MESSAGE.getQueueName(), imGroupMessage);
                continue;
            }
            long id = segmentIdGenerator.nextId(LogicTables.IM_MESSAGE);
            ImMessage imMessage = new ImMessage()
                    .setId(id)
                    .setSender(10808L)
                    .setReceiver(180002L)
                    .setFriendId(101010L)
                    .setContent(content)
                    .setCreateTime(ImClock.date());
            amqpTemplate.convertAndSend(ChatQueueType.IM_MESSAGE.getQueueName(), imMessage);
        }
    }

}
