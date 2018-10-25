package kafka.java.producers;

import kafka.java.producers.properties.BuilderProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class FireAndForgetProducer {
    KafkaProducer<String, String> producer;

    public FireAndForgetProducer() {
        this.producer = new KafkaProducer(new BuilderProperties().build());
    }

    public void produce(String topic, String key, String value) {
        producer.send(new ProducerRecord(topic, key, value));
    }

    public void produceAndFinish(String topic, String key, String value) {
        this.produce(topic, key, value);
        this.finish();
    }

    public void finish() {
        producer.close();
    }
}
