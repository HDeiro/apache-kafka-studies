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

- **Asynchronous Send**: Produces a message and waits for acknowledgement in an asynchronous way. An asynchronous send let the request inflight until acknowledge. An important side-effect of this approat is that depending on message batches acknowledgement, you may loss your messages order. For example, let's say that you have ten messages divided in two batches. The first one (with messages 1 to 5) fails, while it awaits for a retry, the second one will be send. If it is concluded with succes so as the retry, you may have a order of messages like "6, 7, 8, 9, 10, 1, 2, 3, 4, 5". In order to prevent this effect, you may use Synchronous Send or set the property max.in.flight.requests.per.connection as 1.

### Important Configuration Properties

- acks: The acks configuration is to configure acknowledgement. It can be configured with three values:
  - 0: The producer will not wait for the response;
    - The loss of messages in case of error is ignored;
    - Implies in a high throughput since the producer does not wait for any confirmation;
    - Does not retries in case of recoverable errors.
  - 1: The producer waits for the response of the leader partition. If the leader is down, this option allows retries in a few milliseconds. This method does not guarantee the replication of the message to the followers of the leader.
  - all: Waits for the acknowledge of all of the live replicas. It gives the highest reliability but costs the highest latency since the producer waits the response of the leader and it's replicas. The impact of this can be reduced if an Asynchronous Producer is used.
  
- retries: Defines how many times the producer will retry after get an error. The default value is 0. The interval can be defined by the property retry.backoff.ms (1000 milliseconds);

- max.in.flight.requests.per.connection: This property defines how many requests are inflight waiting for acknowledgement. It's really important when uses the Asynchronous Producer. Increase this value may reflect negatively in throughput.