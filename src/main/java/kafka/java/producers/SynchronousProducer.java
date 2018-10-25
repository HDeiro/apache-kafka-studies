package kafka.java.producers;

import kafka.java.producers.properties.BuilderProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class SynchronousProducer {
    KafkaProducer<String, String> producer;

    public SynchronousProducer() {
        this.producer = new KafkaProducer(new BuilderProperties().build());
    }

    public void produce(String topic, String key, String value) {
        this.produce(topic, key, value, false);
    }

    public void produceAndFinish(String topic, String key, String value) {
        this.produce(topic, key, value, true);
    }

    private void produce(String topic, String key, String value, boolean mayFinish) {
        try {
            RecordMetadata recordMetadata = (RecordMetadata) producer.send(new ProducerRecord(topic, key, value)).get();
            System.out.printf(
                    "\tSynchronous message sent to topic %s on partition %s [offset %s] with timestamp %s\n",
                    recordMetadata.topic(),
                    recordMetadata.partition(),
                    recordMetadata.offset(),
                    recordMetadata.timestamp()
            );
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("SynchronousProducer has failed");
        } finally {
            if (mayFinish)
                this.finish();
        }
    }

    public void finish() {
        producer.close();
    }
}
