package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main extends Application {
	@FXML
	public TextField enterModuleCode;
	@FXML
	public Button addModule;
	@FXML
	public TextArea serverText;
	
    public static InetAddress host;
    public static final int PORT = 1238;

    @Override
    public void start(Stage primaryStage) throws IOException {
		addModule.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				try {
					host = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					System.out.println("Host ID not found!");
					System.exit(1);
				}
				Socket link = null;
				try {
					link = new Socket(host, PORT);
					BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
					PrintWriter out = new PrintWriter(link.getOutputStream(), true);

			        out.println("ADD_MODULE " + enterModuleCode.getText());
			        String response = "";
					try {
						response = in.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
			        serverText.appendText(response + "\n");
					
				}
				 catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						System.out.println("\n* Closing connection... *");
						link.close(); // Step 4.
					} catch (IOException e) {
						System.out.println("Unable to disconnect/close!");
						System.exit(1);
					}
				}
			}
		});

            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
    

    public static void main(String[] args) {
        launch(args);
    }
}
