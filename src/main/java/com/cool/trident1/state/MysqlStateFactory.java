package com.cool.trident1.state;


import org.apache.storm.task.IMetricsContext;
import org.apache.storm.trident.state.State;
import org.apache.storm.trident.state.StateFactory;

import java.util.Map;

public class MysqlStateFactory implements StateFactory {

    @Override
    public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
        return new MysqlTrendState(new MysqlTrendBackingMap());
    }
}
