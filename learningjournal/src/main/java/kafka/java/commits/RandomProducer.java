package kafka.java.commits;

import kafka.java.properties.BuilderProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Calendar;
import java.util.Random;

public class RandomProducer {
    public static void main(String[] args) throws Exception {
        String topic = "random";
        String msg;

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(new BuilderProperties().build());

        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 10, 26);

        try {
            while(true) {
                for(int i = 0; i < 100; i++) {
                    msg = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DATE) + "," + random.nextInt(1000);
                    producer.send(new ProducerRecord<String, String>(topic, 0, "key", msg)).get();
                    msg = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DATE) + "," + random.nextInt(1000);
                    producer.send(new ProducerRecord<String, String>(topic, 1, "key", msg)).get();
                }
                calendar.add(Calendar.DATE, 1);
                System.out.printf(
                    "DATA SENT TO %s-%s-%s\n",
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DATE)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
