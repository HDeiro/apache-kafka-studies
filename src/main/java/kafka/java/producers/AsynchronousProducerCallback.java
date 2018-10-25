package kafka.java.producers;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class AsynchronousProducerCallback implements Callback {
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e != null) {
            System.err.println("There was an error in asynchronous producer");
            return;
        }

        System.out.printf(
                "\tAsynchronous message sent to topic %s on partition %s [offset %s] with timestamp %s\n",
                recordMetadata.topic(),
                recordMetadata.partition(),
                recordMetadata.offset(),
                recordMetadata.timestamp()
        );
    }
}
