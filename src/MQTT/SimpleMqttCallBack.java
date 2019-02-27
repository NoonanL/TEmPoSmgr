package MQTT;


import daos.TRANSACTION;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.eclipse.paho.client.mqttv3.*;

public class SimpleMqttCallBack implements MqttCallback {

    private  TableView transactionTable;

    public SimpleMqttCallBack(TableView transactionTable){
        this.transactionTable = transactionTable;
    }

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");

    }

    @FXML
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("Message received:\t"+ new String(mqttMessage.getPayload()) );
//        transactionTable.setItems(FXCollections.observableList(TRANSACTION.getTransactions()));
        transactionTable.refresh();
        //on message recieved from update topic, refresh table

    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    }

}