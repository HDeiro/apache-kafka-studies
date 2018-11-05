package kafka.java.partitioner;

import kafka.java.properties.BuilderProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * This example defines a custom partitioner for Kafka that describes in wich partitions the message
 * should be stored.
 * <p>
 * This example demands that the topic has 10 partitions.
 * <p>
 * When a partition is defined by a Descriptor "A", it'll be stored in the first 3 partitions (0 to 2). Otherwise, it'll
 * be stored in the other available partitions (3 to 9)
 */
public class CustomPartitionerProducer {
    public static void main(String[] args) {
        String topic = "test";
        KafkaProducer<String, String> producer = new KafkaProducer(
                new BuilderProperties()
                        .append("partitioner.class", "kafka.java.partitioner.CustomPartitioner")
                        .append("custom.descriptor", "A")
                        .build()
        );

        System.out.println("Sending descriptor A items: ");
        for (int i = 0; i < 15; i++)
            producer.send(new ProducerRecord<String, String>(topic, "A" + i, "test" + i));

        System.out.println("Sending descriptor B items: ");
        for (int i = 0; i < 15; i++)
            producer.send(new ProducerRecord<String, String>(topic, "B" + i, "test" + i));

        producer.close();
    }
}
