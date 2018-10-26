package kafka.java.serializer;

import kafka.java.properties.BuilderProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ComplexObjectProducer {
    public static void main(String[] args) throws Exception {
        String topic = "complex";

        KafkaProducer<String, ComplexObject> producer = new KafkaProducer(
            new BuilderProperties()
                .append("value.serializer", "kafka.java.serializer.ComplexObjectSerializer")
            .build()
        );

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        ComplexObject complexObject1 = new ComplexObject(1, "Complex Object 1", df.parse("29/01/2018"));
        ComplexObject complexObject2 = new ComplexObject(2, "Complex Object 2", df.parse("30/01/2018"));

        producer.send(new ProducerRecord(topic, "COMPLEX", complexObject1)).get();
        producer.send(new ProducerRecord(topic, "COMPLEX", complexObject2)).get();

        producer.close();
    }
}
