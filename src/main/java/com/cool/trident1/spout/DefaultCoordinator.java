package com.cool.trident1.spout;

import org.apache.storm.trident.spout.ITridentSpout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class DefaultCoordinator implements ITridentSpout.BatchCoordinator, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(DefaultCoordinator.class);

    @Override
    public Object initializeTransaction(long txid, Object prevMetadata, Object currMetadata) {
//        LOG.info("Initializing Transaction [" + txid + "]");
        return txid;
    }

    @Override
    public void success(long txid) {
//        LOG.info("Successful Transaction [" + txid + "]");
    }

    @Override
    public boolean isReady(long txid) {
        return true;
    }

    @Override
    public void close() {

    }
}
