## Consumers

The consumers is the structure provided by Kafka to fetch the messages stored on topic partitions.

Depending on the context, maybe only one consumer is sufficient. But, to allow a better throughput of this consumption, you may be want to run multiple consumers.

It's possible to allocate the consumers in the same consumer group. This consumer group defines a set of consumers and Kafka distributes the messages to the consumers without duplicate.

In order to avoid duplicity two consumers will never share a partition in same time. But, a partition may be read by more than one partition.

If you have more consumers than partitions, this consumer will read nothing. But, if one of the other partitions goes down, Kafka would activate the excedent consumer.

When consumers goes up and down a mechanism called **Group Coordinator** will act. This coordinator manages the list of group members. So, whenever it's necessary, the coordinator may trigger a **rebalance** of the consumers. During a rebalance, the reading is blocked to all consumers, going back only after this activity ends.

In order to accomplish it's tasks, the coordinator may define a consumer as leader. This leader is the one that executes the rebalance activity and manages the list of active consumers, sending the new partition assignment back to the coordinator. If the leader consumer goes down, a new one is elected. 

### Preparing a Consumer

- bootstrap.servers: Mandatory, defines the brokers that will be consumed;
- key.deserializer: Mandatory, defines the deserializer for the key of the partitions;
- value.deserializer: Mandatory, defines the deserializer for the value stored;
- group.id: Optional, defines the id the consumer group;

In the Java code, the same consumer can be connected to multiple topics.

There is a method poll, this method is responsible to connect to the group coordinator, join this group, receives partition assignment, sends heartbeat (something like an "I'm alive" signal) and fetch the messages.

It's important to know that the treatment of the record may be quick enough to finish before the hearbeat timing (3s by default), if it does not, the coordinator will trigger a consumer rebalance, as this consumer is "dead".