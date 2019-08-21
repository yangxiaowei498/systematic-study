package com.ethen.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.text.DecimalFormat;
import java.util.Properties;

/**
 * kakfa 生产者java demo
 * <p>
 * Note
 * => acks：消息的确认机制，默认值是0。
 * acks=0：生产者不会等待Kafka响应。
 * acks=1：Kafka会把这条消息写道本地日志文件中，但是不会等待集群中其他机器的成功响应。
 * acks=all：这个配置意味着leader会等待多有的follower同步完成。这个确保消息不会丢失，除非Kafka集群中所有的机器都挂掉。
 * 这个是最强的可用性保证。
 * <p>
 * => batch.size：当多条消息需要发送到同一个分区时，生产者会尝试合并网络请求。这个会提高client和生产者的效率。
 */
public class ProducerDemo01 {
    private static final String VALUE_SEED = "orderNo-201908210000%s";
    private static final String TOPIC_01 = "kafka-topic-demo01";

    public static void main(String[] args) {
        //kafka连接配置
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaConstant.BROKER_LIST);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        //创建消息对象
        for (int i = 1; i <= 1000; i++) {
            String msgKey = String.format("kafka-msg-demo-%d", i);//消息键
            String msgValue = formatValue(i);//消息内容
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC_01, msgKey, msgValue);
            producer.send(record);
            System.err.println("发送kafka消息：" + msgValue);
        }
    }

    private static String formatValue(int i) {
        return String.format(VALUE_SEED, new DecimalFormat("00000").format(i));
    }
}
