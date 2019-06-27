package com.cool.trident1;

import com.cool.storm.boot.SpringStormApplication;
import com.cool.storm.boot.SpringUtils;
import com.cool.trident1.operator.NotNumberFilter;
import com.cool.trident1.spout.RabbitMqSpout;
import com.cool.trident1.state.MysqlStateFactory;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.tuple.Fields;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Random;


public class TopologyMain {


    public static void main(String[] args) throws InterruptedException {
        SpringStormApplication.run();
        TridentTopology topology = new TridentTopology();
        RabbitMqSpout rabbitMqSpout = new RabbitMqSpout();
        Stream inputStream = topology.newStream("first-log", rabbitMqSpout);
        inputStream.each(new Fields("log"), new NotNumberFilter()).parallelismHint(5).groupBy(new Fields("log"))
                .persistentAggregate(new MysqlStateFactory(), new Count(), new Fields("count"));
        Config conf = new Config();
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("cdc", conf, topology.build());
        RabbitTemplate rabbitTemplate = (RabbitTemplate) SpringUtils.getBean("rabbitTemplate");
        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            rabbitTemplate.send("QUEUE_A",
                    MessageBuilder.withBody(String.valueOf(random.nextInt(10)).getBytes()).build());
//			Thread.sleep(100);
        }
//		Thread.sleep(200000);
//		cluster.shutdown();
        //Topology definition
//		TopologyBuilder builder = new TopologyBuilder();
//		builder.setSpout("mq-spout",new RabbitMqSpouts());
//		builder.setBolt("mq-bolt", new WordNormalizer())
//				.shuffleGrouping("mq-spout");
//
//		//Configuration
//		Config conf = new Config();
//		conf.setDebug(false);
//		//Topology run
//		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
//		LocalCluster cluster = new LocalCluster();
//		cluster.submitTopology("Getting-Started-Toplogie", conf, builder.createTopology());
//		Thread.sleep(100000);
//		cluster.killTopology("Getting-Started-Toplogie");
//		cluster.shutdown();
    }
}
