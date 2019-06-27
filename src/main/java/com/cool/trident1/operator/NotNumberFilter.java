package com.cool.trident1.operator;

import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.tuple.TridentTuple;

public class NotNumberFilter extends BaseFilter {
    @Override
    public boolean isKeep(TridentTuple tuple) {
        Object value = tuple.getValue(0);
        try {
            Integer.parseInt(String.valueOf(value));
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
