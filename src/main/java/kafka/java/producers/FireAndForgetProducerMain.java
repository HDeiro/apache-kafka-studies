package kafka.java.producers;

public class FireAndForgetProducerMain {
    public static void main(String[] args) {
        // Kafka Parameters
        String topic = "test";
        String key = "Key" + System.currentTimeMillis();
        String value = "[Fire and Forget Method] Testing the production of a message";

        //FireAndForgetProducer configuration
        FireAndForgetProducer producer = new FireAndForgetProducer();

        for (int i = 0; i < 5; i++)
            producer.produce(topic, key, value + " [" + i + "]");

        producer.produceAndFinish(topic, key, value + " [Final]");
    }
}
