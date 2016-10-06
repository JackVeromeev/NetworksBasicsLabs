package lab1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import lab2.Encryption;
import lab2.PackageBoundsException;

public class Controller {
    @FXML
    private TextArea textArea1;
    public ComboBox<String> comboBox1;
    public TextField textField1;
    public Button sendButton1;


    public static SerialPort serialPort;


    {
        serialPort = new SerialPort(Main.comName);

        try {
            if (!serialPort.isOpened()) {
                serialPort.openPort();
            }
            serialPort.setParams(9600, 8, 1, 0);
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
            serialPort.addEventListener(new SerialPortEventListener() {
                @Override
                public void serialEvent(SerialPortEvent serialPortEvent) {
                    if (serialPortEvent.isRXCHAR()) {
                        if (serialPortEvent.getEventValue() != 0) {
                            try {
                                /*
                                get and translate pack
                                 */
                                String buffer = serialPort.readString();
                                textArea1.setText(textArea1.getText() +
                                        "\n> Get pack <<" + buffer +
                                        ">>");
                                textArea1.setText(textArea1.getText() +
                                        "\n$ " + Encryption.transcript(buffer));
                            } catch (SerialPortException ex) {
                                System.err.println(ex.getMessage());
                            } catch (PackageBoundsException e) {
                                textArea1.setText(textArea1.getText() +
                                        "\n! Error! Pack <<" +
                                        e.getMessage() + ">> is corrupted");
                            }
                        }
                    }
                }
            });
        } catch (SerialPortException ex) {
            System.err.println("Serial port Exception\n");
            System.err.println(ex.getMessage());
        }
    }

    public void handleButtonAction(MouseEvent mouseEvent) {
        String msg = textField1.getText();
        String encMsg = Encryption.encryptString(msg);
        if(msg == "" || msg == null) {
            System.out.println("Empty message");
        }
        else {

            try {
                serialPort.writeString(encMsg);
            } catch (SerialPortException e) {
                System.out.println(e.getMessage());
            }
            textArea1.setText(textArea1.getText() + "\n# " + msg +
                                "\n < Send pack <<" + encMsg + ">>");
            textField1.clear();

        }
    }

    public void comboboxChanged(ActionEvent actionEvent) {
        System.out.println("bound rate changed to "+ Integer.parseInt(comboBox1.getValue()));
        try {
            serialPort.setParams(Integer.parseInt(comboBox1.getValue()), 8, 1, 0);

        } catch (SerialPortException e) {
            System.out.println(e.getMessage());
        }
    }

}
