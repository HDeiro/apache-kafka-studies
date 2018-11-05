## Consumer Offsets

Kafka uses offsets to maintain the data in a partition. The structure is: Topic > Partition > Offset.

There are two types of offset in Kafka:

- **Current offset:** When we call a poll method, Kafka sends some messages to the consumer. As an example, let's say that in the first polling, Kafka sents twenty messages. The current offset will me shifted from 0 to 20. If more 20 messages are polled, the current offset will be shifted again, but now to offset 40.

    A consumer does not get the same message twice because of the current offset.

- **Commited offset:** It's related to the position already processed by the consumer. Using the same example of the current offset, when the current offset shifts from 0 to 20, the commited offset is 0. It'll be 20 after the consumer process and commit the 20 messages.

    The commited offset is critical in case of **Partition Rebalance** (If you don't remember is when the Kafka partition were rebalanced by the Coordinator in order to balance the partitions through the consumers).
    
 TL;DR: Current offset = reference to sent records. Commited offset = reference to processed records.