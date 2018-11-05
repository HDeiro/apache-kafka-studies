package kafka.java.commits;

import kafka.java.properties.BuilderProperties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;

public class RandomConsumer {
    public static void main(String[] args) {
        String topic = "random";

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(
            new BuilderProperties()
                .append("group.id", "random-consumer")
                .append("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
                .append("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
                .append("enable.auto.commit", "false")
            .build()
        );

        RebalanceListener rebalanceListener = new RebalanceListener(consumer);
        consumer.subscribe(Arrays.asList(topic), rebalanceListener);

        try {
            while(true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> {
                    rebalanceListener.addOffset(record.topic(), record.partition(), record.offset());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
}
