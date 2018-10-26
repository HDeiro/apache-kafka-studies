package kafka.java.producers.properties;

import java.util.Properties;

public class BuilderProperties {
    private Properties properties;

    public BuilderProperties() {
        properties = new Properties();

        this.append("bootstrap.servers", "localhost:9092")
                .append("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
                .append("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    }

    public Properties build() {
        return this.properties;
    }

    public BuilderProperties append(String key, String value) {
        this.properties.put(key, value);
        return this;
    }
}
