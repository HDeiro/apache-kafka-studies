## Commits

There are two ways to commit the receivement of a message.

- **Auto Commit**: It's the easiest method. It can be achieved by setting two properties:
    - enable.auto.commit: It's enabled by default. This property defines if the auto-commit strategy will be used or not.
    
    - auto.commit.interval.md: It refers to the interval between commits. By default it's 5s.
    
  There problem of the auto commit strategy is that may cause duplicates of the message in specific scenarios. Let's say that you use the 5s interval and before closes the interval you had processed some messages but didn't commited it yet. Let's say that a rebalance is triggered. Because of it, all consumers will be locked and the already processed message won't be commited. So, Kafka, after rebalance, will re-send based on the last commited offset.

    Even if the auto commit interval is reduced to a lower value as 1s, for example, it does not guarantee that the rebalance won't be invoked in the meantime.
    
- **Manual Commit**: The manual commit will be activated after enable.auto.commit is disable. It'll allows to programatically commits the offset of received messages. There are to approaches to accomplish that:

    - Synchronous Commit: It's the most reliable option. But the problem with the Synchronous way is that it is a blocking method. The entire consumer process will be locked until the commit happens.
    
    - Asynchronous Commit: Will send the request and continue processing. But the major drawback of the asynchronous commit is that it does not retry to fetch in case of a recoverable error. This behavior happens because of situations like the one were you processed 25 records and want to commit to the offset, but it fails. If you retry, maybe another batch has been processed and sent to the Kafka broker. So, the final commit offset won't be 50 as expected, but 25, causing message duplicity. 

A commit can have great impact in the client application. So, it's necessary to choose the appropriate technique based on the use case.