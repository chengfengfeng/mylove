package com.cool.trident1.spout;

import com.cool.storm.boot.SpringUtils;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.spout.ITridentSpout;
import org.apache.storm.trident.topology.TransactionAttempt;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class LogEmitter implements ITridentSpout.Emitter, Serializable {
    public static final long serialVersionUID = 1L;
    private AtomicInteger successfulTransactions = new AtomicInteger(0);
    private static final Logger LOG = LoggerFactory.getLogger(LogEmitter.class);

    @Override
    public void emitBatch(TransactionAttempt tx, Object coordinatorMeta, TridentCollector collector) {
        RabbitTemplate rabbitTemplate = (RabbitTemplate) SpringUtils.getBean("rabbitTemplate");
        for (; ; ) {
            try {
                Message queue_a = rabbitTemplate.receive("QUEUE_A");
                if (queue_a != null) {
                    String msg = new String(queue_a.getBody());
                    collector.emit(new Values(msg));
                }else {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
//                LOG.info("it's(what) nothing(the fuck).");
            }
        }
    }
    @Override
    public void success(TransactionAttempt tx) {
//        LOG.info("success by " + Thread.currentThread().getName() + " : " + successfulTransactions.incrementAndGet());
    }

    @Override
    public void close() {

    }
}
