## Serializers

The serializers are necessary to properly parses the keys and values to byte arrays that will be stored on the Kafka broker.

Kafka provides several serializers for String, Int, Double and Long Serializers. But, they don't cover all possible cases. So, it's possible to implement a custom serializer to cover the cases of more complex structures, as a Table or Objects for example.

The best pratices suggest to use the Kafka Serializers instead of creating a custom one, but sometimes it's necessary to do it to store complex or specific items.

When the base object of the Serializer/Deserializer changes, the Serializer and Deserializer of that object **must** be updated too. A problem with this approach is that the old messages will not be valid because of the format of the serializer.