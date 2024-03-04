package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Main extends Application {
    public static InetAddress host;
    public static final int PORT = 1234;
    public static Socket link;
    public static PrintWriter out;
    public static BufferedReader in;
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the Sign In scene
        	host = InetAddress.getLocalHost();
            link = new Socket(host, PORT);
            out = new PrintWriter(link.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
