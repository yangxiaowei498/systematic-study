package com.ethen.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Kafka消费者 demo
 */
public class ConsumerDemo01 {

    private static final String GROUP_ID = "groupA";
    private static final String TOPIC_01 = "kafka-topic-demo01";


    public static void main(String[] args) {
        //消费者配置
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaConstant.BROKER_LIST);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("max.poll.records", 1000);
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("group.id", GROUP_ID);//消费者组
        //创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //订阅主题
        consumer.subscribe(Arrays.asList(TOPIC_01));
        //订阅主题之后，从Kafka拉取数据
        ConsumerRecords<String, String> recordList = consumer.poll(1000);
        //消费消息（业务处理）
        if (recordList != null) {
            int count = 1;
            for (ConsumerRecord<String, String> record : recordList) {
                consume(record);
                if (count++ % 20 == 0) break;
            }
        }
    }

    /**
     * 消费Kafka消息的业务逻辑
     *
     * @param record
     */
    public static void consume(ConsumerRecord<String, String> record) {
        if (record != null) {
            System.err.println(record.toString());
        }
    }
}
