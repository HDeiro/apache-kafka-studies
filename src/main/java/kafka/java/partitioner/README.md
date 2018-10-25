## Custom Partitioner

A custom partitioner allows to define the rules that will be followed by Kafka to store message in partitions.

It's necessary to note three possibilities:

- If a partition is specified in the record, use a Default Partitioner;
- If no partition is specified but a key is present, choose a partition based on the hash of the present key.
- If no partition or key is present, let Kafka manages the partition in a Round-Robin fashion (default behavior).

The hashes will ensure that the same key will result in the same hash and the hash will not matches with any other key.