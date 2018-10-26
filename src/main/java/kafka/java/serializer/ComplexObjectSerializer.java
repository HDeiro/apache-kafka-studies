package kafka.java.serializer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class ComplexObjectSerializer implements Serializer<ComplexObject> {
    private String encoding = "UTF8";

    // Initialization
    public void configure(Map<String, ?> configs, boolean isKey) {}

    public byte[] serialize(String topic, ComplexObject data) {
        try {
            if(data == null)
                return null;

            byte[] serializedDescription = data.getDescription().getBytes(encoding);
            int sizeOfDescription = serializedDescription.length;
            byte[] serializedSnapshot = data.getSnapshot().toString().getBytes(encoding);
            int sizeOfSnapshot = serializedSnapshot.length;

            ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + sizeOfDescription + 4 + sizeOfSnapshot);
            buffer.putInt(data.getId());        // 4
            buffer.putInt(sizeOfDescription);   // 4
            buffer.put(serializedDescription);  // sizeOfDescription
            buffer.putInt(sizeOfSnapshot);      // 4
            buffer.put(serializedSnapshot);     // sizeOfSnapshot

            return buffer.array();
        } catch (Exception e) {
            throw new SerializationException("Error while serializes ComplexObject");
        }
    }

    // Cleanup
    public void close() {}
}
