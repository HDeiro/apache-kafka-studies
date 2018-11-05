package kafka.java.commits;

import kafka.java.properties.BuilderProperties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class FirstAsyncSecondSyncConsumer {
    public static void main(String[] args) {
        String topic = "test";
        String group = "consumer-group-1async-2sync";

        KafkaConsumer<String, String> consumer = new KafkaConsumer(
                new BuilderProperties()
                        .append("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
                        .append("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
                        .append("enable.auto.commit", "false")
                        .append("group.id", group)
                        .build()
        );
        // A consumer can be subscribed to multiple topics
        consumer.subscribe(Arrays.asList(topic));


        Duration duration = Duration.of(100, ChronoUnit.MILLIS);

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(duration);

                records.forEach(record -> {
                    System.out.printf(
                            "Value = %s found on Topic %s, Partition %s, Offset %s in %s timestamp\n",
                            record.value(),
                            record.topic(),
                            record.partition(),
                            record.offset(),
                            record.timestamp()
                    );
                });

                consumer.commitAsync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.commitSync();
            consumer.close();
        }
    }
}
