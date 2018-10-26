package kafka.java.serializer;

import kafka.java.properties.BuilderProperties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class ComplexObjectConsumer {
    public static void main(String[] args) throws  Exception {
        String topic = "complex";
        String group = "consumer";

        KafkaConsumer<String, ComplexObject> consumer = new KafkaConsumer(
            new BuilderProperties()
                .append("group.id", group)
                .append("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
                .append("value.deserializer", "kafka.java.serializer.ComplexObjectDeserializer")
            .build()
        );

        consumer.subscribe(Arrays.asList(topic));
        Duration duration = Duration.of(100, ChronoUnit.MILLIS);

        while(true) {
            ConsumerRecords<String, ComplexObject> records = consumer.poll(100);

            records.forEach(record -> {
                System.out.printf(
                    "ID = %s, Description = %s, Snapshot: %s\n",
                    record.value().getId(),
                    record.value().getDescription(),
                    record.value().getSnapshot()
                );
            });

            if(records.count() == 0)
                System.out.println("No records on topic");
        }
    }
}
