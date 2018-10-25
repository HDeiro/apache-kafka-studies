package kafka.java.producers;

public class AsynchronousProducerMain {
    public static void main(String[] args) {
        // Kafka Parameters
        String topic = "test";
        String key = "Key" + System.currentTimeMillis();
        String value = "[Asynchronous Method] Testing the production of a message";

        //FireAndForgetProducer configuration
        SynchronousProducer producer = new SynchronousProducer();

        for (int i = 0; i < 5; i++)
            producer.produce(topic, key, value + " [" + i + "]");

        producer.produceAndFinish(topic, key, value + " [Final]");
    }
}
