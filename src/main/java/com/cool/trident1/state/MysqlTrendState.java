package com.cool.trident1.state;



import org.apache.storm.trident.state.TransactionalValue;
import org.apache.storm.trident.state.map.TransactionalMap;

public class MysqlTrendState extends TransactionalMap<TransactionalValue> {
    MysqlTrendState(MysqlTrendBackingMap mysqlTrendBackingMap) {
        super(mysqlTrendBackingMap);
    }
}
