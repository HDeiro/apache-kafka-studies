package kafka.java.serializer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ComplexObjectDeserializer implements Deserializer {
    private String encoding = "UTF8";

    public void configure(Map configs, boolean isKey) {}

    public ComplexObject deserialize(String topic, byte[] data) {
        try {
            if(data == null) {
                System.out.println("Null data received on ComplexObjectDeserializer");
                return null;
            }

            ByteBuffer buffer = ByteBuffer.wrap(data);

            // Deserialize id
            int id = buffer.getInt();

            //Deserialize Description
            int sizeOfDescription = buffer.getInt();
            byte[] serializedDescription = new byte[sizeOfDescription];
            buffer.get(serializedDescription);
            String deserializedDescription = new String(serializedDescription, encoding);

            //Deserialize Snapshot as String date
            int sizeOfSnapshot = buffer.getInt();
            byte[] serializedSnapshot = new byte[sizeOfSnapshot];
            buffer.get(serializedSnapshot);
            String deserializedSnapshotString = new String(serializedSnapshot, encoding);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date date;

            try {
                date = df.parse(deserializedSnapshotString);
            } catch (Exception e) {
                date = new Date();
            }

            return new ComplexObject(id, deserializedDescription, date);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to ComplexObject");
        }
    }

    public void close() {}
}
