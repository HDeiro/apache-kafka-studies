package kafka.java.producers;

import kafka.java.producers.properties.BuilderProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class AsynchronousProducer {
    KafkaProducer<String, String> producer;

    public AsynchronousProducer() {
        this.producer = new KafkaProducer(new BuilderProperties().build());
    }

    public void produce(String topic, String key, String value) {
        this.produce(topic, key, value, false, new AsynchronousProducerCallback());
    }

    public void produce(String topic, String key, String value, AsynchronousProducerCallback callback) {
        this.produce(topic, key, value, false, callback);
    }

    public void produceAndFinish(String topic, String key, String value) {
        this.produce(topic, key, value, true, new AsynchronousProducerCallback());
    }

    public void produceAndFinish(String topic, String key, String value, AsynchronousProducerCallback callback) {
        this.produce(topic, key, value, true, callback);
    }

    private void produce(String topic, String key, String value, boolean mayFinish, AsynchronousProducerCallback callback) {
        producer.send(new ProducerRecord(topic, key, value), callback);
    }

    public void finish() {
        producer.close();
    }
}
