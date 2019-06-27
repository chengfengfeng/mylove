package com.cool.storm.spouts;


import com.cool.storm.boot.SpringUtils;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


import java.util.Map;

public class RabbitMqSpouts extends BaseRichSpout {
    private SpoutOutputCollector collector;

    private RabbitTemplate rabbitTemplate;
    private boolean completed = false;

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
//        SpringStormApplication.run();
        this.collector = collector;
        this.rabbitTemplate = (RabbitTemplate) SpringUtils.getBean("rabbitTemplate");
//        rabbitTemplate.send("test", MessageBuilder.withBody("123".getBytes()).build());

    }

    @Override
    public void nextTuple() {
        if (completed) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //Do nothing
            }
            return;
        }
        for (; ; ) {
            try {
                Message queue_a = rabbitTemplate.receive("QUEUE_A");
                if (queue_a != null) {
                    String msg = new String(queue_a.getBody());
                    this.collector.emit(new Values(msg), msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                completed = true;
            }

        }


    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("line"));
    }
}
