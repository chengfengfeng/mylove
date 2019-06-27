package com.cool.storm.bolts;

import com.cool.storm.boot.SpringUtils;
import com.cool.storm.dao.entity.Words;
import com.cool.storm.dao.repository.WordsRepository;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

public class WordNormalizer extends BaseBasicBolt {

    private WordsRepository wordsRepository;

    public void cleanup() {
    }

    /**
     * The bolt will receive the line from the
     * words file and process it to Normalize this line
     * <p>
     * The normalize will be put the words in lower case
     * and split the line to get all words in this
     */
    public void execute(Tuple input, BasicOutputCollector collector) {
        String sentence = input.getString(0);
        String[] words = sentence.split(" ");
        for (String word : words) {
            word = word.trim();
            if (!word.isEmpty()) {
                word = word.toLowerCase();
                Words w = new Words();
                w.setWord(word);
                wordsRepository.saveAndFlush(w);
            }
        }
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
//        SpringStormApplication.run();
        this.wordsRepository = (WordsRepository) SpringUtils.getBean("wordsRepository");
    }


    /**
     * The bolt will only emit the field "word"
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}
