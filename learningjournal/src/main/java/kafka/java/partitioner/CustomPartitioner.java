package kafka.java.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

/**
 * This Paritioner defines a rule that where the descriptorName matches with the key, those messages
 * shall be stored in the first three partitions of the topic.
 */
public class CustomPartitioner implements Partitioner {
    private String descriptorName;

    /**
     * Called all time that a message is sent
     * <p>
     * This method defines in wich partition that message shall be stored.
     */
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        int reservedPartitions = (int) Math.abs(numPartitions * 0.3); //30% of the partitions
        int partition = 0;

        // The key is mandatory
        if ((keyBytes == null) || (!(key instanceof String)))
            throw new InvalidRecordException("All messages must have descriptor as key");

        // Determines partition number
        if (((String) key).startsWith(descriptorName)) //In this case, partitions may be 0, 1 or 2
            partition = Utils.toPositive(Utils.murmur2(valueBytes))
                    % reservedPartitions;
        else //In this case, partitions may be 3, 4, 5, 6, 7, 8, 9
            partition = Utils.toPositive(Utils.murmur2(keyBytes))
                    % (numPartitions - reservedPartitions)
                    + reservedPartitions; // Shift the result to not matches with reserved partitions

        System.out.printf("\tKey = %s / Partition = %s\n", (String) key, partition);
        return partition;
    }

    /**
     * Initialization method, called on partition instanciation
     */
    public void configure(Map<String, ?> properties) {
        // Get the name of the descriptor that'll be stored in the first three partitions only
        this.descriptorName = (String) properties.get("custom.descriptor");
    }

    /**
     * Cleanup method called on partition.close()
     */
    public void close() {
    }
}
