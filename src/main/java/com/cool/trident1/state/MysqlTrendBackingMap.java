package com.cool.trident1.state;

import org.apache.storm.trident.state.TransactionalValue;
import org.apache.storm.trident.state.map.IBackingMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MysqlTrendBackingMap implements IBackingMap<TransactionalValue> {
    private static final Logger LOG = LoggerFactory.getLogger(MysqlTrendBackingMap.class);
    private Map<String, TransactionalValue> storage = new ConcurrentHashMap<String, TransactionalValue>();

    @Override
    public List<TransactionalValue> multiGet(List<List<Object>> keys) {
        List<TransactionalValue> values = new ArrayList<TransactionalValue>();
        for (List<Object> key : keys) {
            TransactionalValue value = storage.get(key.get(0));
            if (value == null) {
                values.add(null);
            } else {
                values.add(value);
            }
        }
        return values;
    }

    @Override
    public void multiPut(List<List<Object>> keys, List<TransactionalValue> vals) {
        for (int i = 0; i < keys.size(); i++) {
            LOG.info("Number [" + keys.get(i).get(0) + "] ==> [" + vals.get(i) + "]");
            storage.put((String) keys.get(i).get(0), vals.get(i));
        }
    }
}
