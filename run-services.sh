#!/bin/bash

echo "Starting Gateway Service..."
cd spam-filter-gateway-service
./mvnw spring-boot:run &

echo "Starting Charging Service..."
cd ../spam-filter-charging-service
./mvnw spring-boot:run &

#echo "Starting RabbitMQ..."
#cd /path/to/rabbitmq
#./rabbitmq-server &
