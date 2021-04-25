1. Install Apache Kafka from: https://kafka.apache.org/quickstart
2. Create 'data' folder in kafka.
3. Create 'kafka', 'zookeeper' folder in 'data' folder.
4. Update config/zookeeper.properties -> dataDir to refer the new 'data/zookeeper' folder
5. Update config/server.properties -> log.dirs to refer the new 'data/kafka' folder
6. Create topic:
- kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic tacocloud.orders.topic
7. Start from kafka/bin/windows:
- zookeeper-server-start.bat ../../config/zookeeper.properties
- kafka-server-start.bat ../../config/server.properties