## Kafka Producer

Kafka producer is the entity that produces a message to a topic in Apache Kafka.

The Producer may have three mandatory properties in a Properties object that may be sent in its construction:

- **bootstrap.servers:** Defines the brokers that will receive the produced message
- **key.serializer:** Defines the serializer for the partition key once it'll be converted to a byte array
- **value.serializer:** Defines the serializer for the value, once it'll be converted to a byte array

The message will be shipped in an object called ProducerRecord. The producer record defines:

- **Topic name:** Mandatory parameter, defines the topic that will receive the produced message
- **Value:** Mandatory parameter, defines the value that will be stored in topic
- **Key:** Optional parameter, defines the key that will be included in the record
- **Partition N°:** Optional parameter, defines wich partition of the topic will receive the produced message
- **Timestamp:** Optional parameter, defines the timestamp of the produced record

On the Producer workflow, the Kafka Producer will user a Serializer that will convert, based on key/value the message to a byte array. 

Then, this record will be send to the Partitioner, that will assign a partition for that message. If you do not define a partition, one will be defined to you based on a Round Robin algorithm.

Once we have a partition number, the producer will be ready to send this message to the producer. But, it will be actually setted in a Partition Buffer, that will send a batch of messages to be stored in the Kafka Topic. The size of the batch or/and the time of waiting to submit may be configurable (Check the docs :D).

If the message has been succesfully received by the Kafka Broker, the producer application receives an acknowledge object (RecordMetaData) that contains the offset, the timestamp and other information.

The number and the interval of the retries were configurable through producer parameters (Check the docs again :D).If there is an error and it's recoverable (example, the leader goes down while you try to submit the message), there will be a retry and maybe the message will succeed (for example, a new leader has been defined). Otherwise, it throws back an error. 

#### Producer Types

There are three strategies about producers.

- **Fire and Forget:** Produces a message and don't check if it was really received by the broker. It's the easiest method and totally acceptable in cases that the messages are not critical.

- **Synchronous Send:** Produces a message and wait to check if there's an error. Normally we don't care about the success, but only with the failure. The major drawback of this method is this is a locakble approach, limiting the throughput.