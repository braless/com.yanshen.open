package com.yanshen.listener;

import com.yanshen.util.SmsClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: cyc
 * @Date: 2023/3/22 16:17
 * @Description:
 */
@Slf4j
@Component
@RocketMQMessageListener(consumeMode = ConsumeMode.CONCURRENTLY,topic = "order", consumerGroup = "group-order", selectorExpression = "TAG-ORDER")
public class BusinessConsumer implements RocketMQListener<MessageExt> , RocketMQPushConsumerLifecycleListener  {
    @Override
    public void onMessage(MessageExt messageExt) {
        String fullName=this.getClass().getName();
        String[] clzNames=fullName.split("\\.");
        String clzName=clzNames[clzNames.length-1];
        String body =new String(messageExt.getBody());
        log.info(clzName+"收到消息:{},事务id: {}", body,messageExt.getTransactionId());
        if (processor(messageExt)){
            log.error("消费消息出现问题:{}",messageExt.getTransactionId());
            throw new RuntimeException();
        }else {
            log.info("消费成功:{}",messageExt.getTransactionId());
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        // 每次拉取的间隔，单位为毫秒
        consumer.setPullInterval(2000);
        // 设置每次从队列中拉取的消息数为16
        consumer.setPullBatchSize(16);
    }

    /**
     * 消息处理，第3次处理失败后，发送邮件通知人工介入
     * @param message
     * @return
     */
    private boolean processor(MessageExt message){
        boolean fail=false;
        String body = new String(message.getBody());
        try {
            log.info("消息处理....{}",body);
            int k = 1/0;
            if (k>0){

            }else {
                fail=true;
                return fail;
            }
            //假装失败
            return false;
        }catch (Exception e){
            if(message.getReconsumeTimes()>=1){
                fail=false;
                log.error("消息重试已达最大次数，将通知业务人员排查问题。{}",message.getMsgId());
                if (message.getReconsumeTimes()<3){
                    //sendMail("topic:"+message.getTopic()+",事务id: "+message.getTransactionId()+"消息消费失败,将通知岳号亮前来排查问题!");
                }else {
                    log.error("消息重试已达最大次数:{}",message.getReconsumeTimes());
                }
                //假装失败
                return fail;
            }
        }
        return fail;
    }
    public void sendMail(String msg){
        String phone ="13992608022";
        SmsClientUtil.sendPush(phone,msg);
    }

}
