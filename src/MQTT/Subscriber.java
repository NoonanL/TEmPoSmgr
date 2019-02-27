package MQTT;


import javafx.scene.control.TableView;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Subscriber {

    public Subscriber(String topic, String clientId, TableView transactionTable) throws MqttException{

        System.out.println("== START SUBSCRIBER - TOPIC : " + topic + " ==");

        MqttClient client = new MqttClient("tcp://192.168.1.192:1883", clientId);

        ///Set connection options (authentication, etc)
        MqttConnectOptions connOpt = new MqttConnectOptions();
        connOpt.setAutomaticReconnect(true);
        String test = clientId + " has disconnected unexpectedly!";
        connOpt.setWill("Debug", test.getBytes(), 2,true);

        //connOpt.setUserName(username);
        //connOpt.setPassword(password.toCharArray());

        //Connect using ConnectionOptions
        client.connect(connOpt);
        client.setCallback(new SimpleMqttCallBack(transactionTable));

        //Test subscribe to topic including QoS
        client.subscribe(topic,2);
    }

    }

