package com.cool.trident1.spout;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.trident.spout.ITridentSpout;
import org.apache.storm.tuple.Fields;

import java.util.Map;

public class RabbitMqSpout implements ITridentSpout {
    private BatchCoordinator batchCoordinator = new DefaultCoordinator();
    private Emitter emitter = new LogEmitter();

    @Override
    public BatchCoordinator getCoordinator(String txStateId, Map conf, TopologyContext context) {
        return batchCoordinator;
    }

    @Override
    public Emitter getEmitter(String txStateId, Map conf, TopologyContext context) {
        return emitter;
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    @Override
    public Fields getOutputFields() {
        return new Fields("log");
    }
}
