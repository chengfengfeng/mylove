package com.cool.trident.spout;


import com.cool.storm.boot.SpringUtils;
import com.cool.trident.model.DiagnosisEvent;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.spout.ITridentSpout;
import org.apache.storm.trident.topology.TransactionAttempt;
import org.apache.storm.tuple.Values;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DiagnosisEventEmitter implements ITridentSpout.Emitter<Long>, Serializable {
    private static final long serialVersionUID = 1L;
    private AtomicInteger successfulTransactions = new AtomicInteger(0);

    @Override
    public void emitBatch(TransactionAttempt tx, Long coordinatorMeta, TridentCollector collector) {
        RabbitTemplate rabbitTemplate = (RabbitTemplate) SpringUtils.getBean("rabbitTemplate");
//        for (; ; ) {
            try {
                Message queue_a = rabbitTemplate.receive("QUEUE_A");
                if (queue_a != null) {
                    String msg = new String(queue_a.getBody());
                    System.out.println(msg);
                    for (int i = 0; i < 10000; i++) {
                        List<Object> events = new ArrayList<Object>();
                        double lat = (double) (-30 + (int) (Math.random() * 75));
                        double lng = (double) (-120 + (int) (Math.random() * 70));
                        long time = System.currentTimeMillis();

                        String diag = Integer.toString(320 + (int) (Math.random() * 7));
                        DiagnosisEvent event = new DiagnosisEvent(lat, lng, time, diag);
                        events.add(event);
                        collector.emit(events);
                    }
                }else {
                    Thread.sleep(300);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
//                LOG.info("it's(what) nothing(the fuck).");
            }
//        }

    }

    @Override
    public void success(TransactionAttempt tx) {
        successfulTransactions.incrementAndGet();
    }

    @Override
    public void close() {
    }

}
