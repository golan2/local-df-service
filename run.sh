kill -9 $(ps aux | grep LocalServerApp | grep java | awk {'print$2'})
java -jar target/local-df-service-0.1.0.jar com.golan.local.dataflow.controllers.LocalServerApp > /tmp/local-df-service.log &
tail -f /tmp/local-df-service.log



