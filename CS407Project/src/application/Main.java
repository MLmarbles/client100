package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
	public TextArea serverText;

	@FXML
	public TextField mainTextField1;

	@FXML
	public Button stop1;

	@FXML
	public TextField enterModuleCode;

	@FXML
	public Button addModule;

	@FXML
	public Button removeModule;
	
	@FXML
	public TextField enterModuleCode1;
	
	@FXML
	public Button addClass;
	
	@FXML
	public Button removeClass;
	
	@FXML
	public Button displaySchedule;
	
	@FXML
	public Button enterTextAdd;
	@FXML
	public Button enterTextRemove;
	@FXML
	public Button stopServer;
	
	public String moduleCode;

	public static InetAddress host;
	public static final int PORT = 9523;

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

    public void initialize() {
        addModule.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
					networkshit("ADD_MODULE", enterModuleCode.getText());
				} catch (IncorrectActionException e) {
					System.out.println(e.IncorrectActionMessage());
					
				}
            }
        });

        removeModule.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
					networkshit("REMOVE_MODULE", enterModuleCode.getText());
				} catch (IncorrectActionException e) {
					System.out.println(e.IncorrectActionMessage());
					
				}
            }
        });
        addClass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                serverText.appendText("To add enter class details for " + enterModuleCode1.getText() + "\n"
                					 +"Example (Monday, 9:00, 11:00, S206)" + "\n");
            }
        });
        
        enterTextAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	moduleCode = enterModuleCode1.getText();
            	String classData = mainTextField1.getText();
            	try {
					networkshit2("ADD_CLASS", moduleCode, classData);
				} catch (IncorrectActionException e) {
					System.out.println(e.IncorrectActionMessage());
					
				}
            }
        });
        
        removeClass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                serverText.appendText("To remove enter class details for " + enterModuleCode1.getText() + "\n"
                					 +"Example (Monday, 9:00, 11:00, S206" + "\n");
            }
        });
        
        enterTextRemove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	moduleCode = enterModuleCode1.getText();
            	String classData = mainTextField1.getText();
            	try {
					networkshit2("REMOVE_CLASS", moduleCode, classData);
				} catch (IncorrectActionException e) {
					System.out.println(e.IncorrectActionMessage());
					
				}
            }
        });
        
        displaySchedule.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	moduleCode = enterModuleCode1.getText();
            	try {
					networkshit("DISPLAY_SCHEDULE", moduleCode);
				} catch (IncorrectActionException e) {
					System.out.println(e.IncorrectActionMessage());
					
				}
            }
        });
        
        stopServer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
					networkshit("STOP", "");
				} catch (IncorrectActionException e) {
					System.out.println(e.IncorrectActionMessage());
					
				} 
            }
        });

    }
	
    private void networkshit(String operation, String moduleCode) throws IncorrectActionException {
        try {
            host = InetAddress.getLocalHost();
            Socket link = new Socket(host, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);

            out.println(operation + " " + moduleCode);
            String response = in.readLine();
            serverText.appendText(response + "\n");
            if (response.startsWith("Error: ")) {
                throw new IncorrectActionException(response);
            }

            System.out.println("\n* Closing connection... *");
            link.close();
        } catch (UnknownHostException e) {
            System.out.println("Host ID not found!");
            System.exit(1);
        } catch (IOException e) {
            
        }
    }
    
    private void networkshit2(String operation, String moduleCode, String classData) throws IncorrectActionException {
        try {
            host = InetAddress.getLocalHost();
            Socket link = new Socket(host, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);

            out.println(operation + " " + moduleCode + " " + classData);
            String response = in.readLine();
            serverText.appendText(response + "\n");
            if (response.startsWith("Error: ")) {
                throw new IncorrectActionException(response);
            }

            System.out.println("\n* Closing connection... *");
            link.close();
        } catch (UnknownHostException e) {
            System.out.println("Host ID not found!");
            System.exit(1);
        } catch (IOException e) {
            
        }
    }

	public static void main(String[] args) {
		launch(args);
	}
}
