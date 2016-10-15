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

import java.util.ArrayList;

public class Controller {
	@FXML
	private TextArea textArea1;
	public ComboBox<String> comboBox1;
	public TextField textField1;
	public Button sendButton1;


	private final String rules =
			">> type \"addpack:%string%\" to add string to message\n" +
					">> type \"addtext:%text%\" to add text to message(for try)\n" +
					">> type \"send\" to send package";
	private final String badCommandMsg = "> Error: incorrect command\n" + rules;

	public static SerialPort serialPort;
	private StringBuilder message = new StringBuilder();

	{

		serialPort = new SerialPort(Main.comName);
		SerialPortEventListener serialPortEventListener = new SerialPortEventListener() {
			@Override
			public void serialEvent(SerialPortEvent serialPortEvent) {
				if (serialPortEvent.isRXCHAR()) {
					if (serialPortEvent.getEventValue() != 0) {
						try {
								/*
								get and translate pack
								 */
							String buffer = serialPort.readString();
							addText(">> get message:<<" + buffer + ">>");
							ArrayList<String> packs = Encryption.transcriptString(buffer);
							if (packs.size() == 0) {
								addText(">> packages are not found");
							}
							for (String pack : packs) {
								addText("$ " + pack);
							}
						} catch (SerialPortException ex) {
							System.err.println(ex.getMessage());
						}
					}
				}
			}
		};

		try {
			if (!serialPort.isOpened()) {
				serialPort.openPort();
			}
			serialPort.setParams(9600, 8, 1, 0);
			serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
			serialPort.addEventListener(serialPortEventListener);
		} catch (SerialPortException ex) {
			System.err.println("Serial port Exception\n");
			System.err.println(ex.getMessage());
		}
	}

	public void handleButtonAction(MouseEvent mouseEvent) {
		String msg = textField1.getText();
		addText("# " + msg);
		String[] parts = msg.split(":", 2);
		switch (parts[0]) {
			case "send":
				try {
					serialPort.writeString(message.toString());

				} catch (SerialPortException e) {
					System.out.println(e.getMessage());
				}
				addText(">> sent message: <<" + message.toString() + ">>");
				message.delete(0, message.length());
				break;
			case "addpack":
				message.append(Encryption.encryptString(parts[1]));
				addText(">> current message: <<" + message.toString() + ">>");
				break;
			case "addtext":
				message.append(parts[1]);
				addText(">> current message: <<" + message.toString() + ">>");
				break;
			default:
				addText(badCommandMsg);
				break;
		}
		textField1.clear();
	}

	public void comboboxChanged(ActionEvent actionEvent) {
		System.out.println("bound rate changed to " + Integer.parseInt(comboBox1.getValue()));
		try {
			serialPort.setParams(Integer.parseInt(comboBox1.getValue()), 8, 1, 0);
		} catch (SerialPortException e) {
			System.out.println(e.getMessage());
		}
	}

	private void addText(String line) {
		textArea1.setText(textArea1.getText() + "\n" + line);
	}
}
