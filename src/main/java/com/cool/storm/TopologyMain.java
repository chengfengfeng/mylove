package com.cool.storm;

import com.cool.storm.boot.SpringStormApplication;
import com.cool.storm.spouts.RabbitMqSpouts;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import com.cool.storm.bolts.WordCounter;
import com.cool.storm.bolts.WordNormalizer;
import com.cool.storm.spouts.WordReader;

import java.net.URL;


public class TopologyMain {
//	public static void main(String[] args) throws InterruptedException {
//
//        //Topology definition
//		TopologyBuilder builder = new TopologyBuilder();
//		builder.setSpout("word-reader",new WordReader());
//		builder.setBolt("word-normalizer", new WordNormalizer())
//			.shuffleGrouping("word-reader");
//		builder.setBolt("word-counter", new WordCounter(),1)
//			.fieldsGrouping("word-normalizer", new Fields("word"));
//		URL resource = TopologyMain.class.getClassLoader().getResource("words.txt");
//		//Configuration
//		Config conf = new Config();
//		conf.put("wordsFile", resource.getPath());
//		conf.setDebug(false);
//        //Topology run
//		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
//		LocalCluster cluster = new LocalCluster();
//		cluster.submitTopology("Getting-Started-Toplogie", conf, builder.createTopology());
//		Thread.sleep(100000);
////		cluster.shutdown();
//	}

	public static void main(String[] args) throws InterruptedException {
		SpringStormApplication.run();
		//Topology definition
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("mq-spout",new RabbitMqSpouts());
		builder.setBolt("mq-bolt", new WordNormalizer())
				.shuffleGrouping("mq-spout");

		//Configuration
		Config conf = new Config();
		conf.setDebug(false);
		//Topology run
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("Getting-Started-Toplogie", conf, builder.createTopology());
		Thread.sleep(100000);
//		cluster.killTopology("Getting-Started-Toplogie");
//		cluster.shutdown();
	}
}
