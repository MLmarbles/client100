package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class Controller {
	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private Label monday9;
	@FXML
	private Label monday10;
	@FXML
	private Label monday11;
	@FXML
	private Label monday12;
	@FXML
	private Label monday13;
	@FXML
	private Label monday14;
	@FXML
	private Label monday15;
	@FXML
	private Label monday16;
	@FXML
	private Label monday17;

	@FXML
	private Label tuesday9;
	@FXML
	private Label tuesday10;
	@FXML
	private Label tuesday11;
	@FXML
	private Label tuesday12;
	@FXML
	private Label tuesday13;
	@FXML
	private Label tuesday14;
	@FXML
	private Label tuesday15;
	@FXML
	private Label tuesday16;
	@FXML
	private Label tuesday17;

	@FXML
	private Label wednesday9;
	@FXML
	private Label wednesday10;
	@FXML
	private Label wednesday11;
	@FXML
	private Label wednesday12;
	@FXML
	private Label wednesday13;
	@FXML
	private Label wednesday14;
	@FXML
	private Label wednesday15;
	@FXML
	private Label wednesday16;
	@FXML
	private Label wednesday17;

	@FXML
	private Label thursday9;
	@FXML
	private Label thursday10;
	@FXML
	private Label thursday11;
	@FXML
	private Label thursday12;
	@FXML
	private Label thursday13;
	@FXML
	private Label thursday14;
	@FXML
	private Label thursday15;
	@FXML
	private Label thursday16;
	@FXML
	private Label thursday17;

	@FXML
	private Label friday9;
	@FXML
	private Label friday10;
	@FXML
	private Label friday11;
	@FXML
	private Label friday12;
	@FXML
	private Label friday13;
	@FXML
	private Label friday14;
	@FXML
	private Label friday15;
	@FXML
	private Label friday16;
	@FXML
	private Label friday17;

	@FXML
	private TextField usernameSignIn;
	private static String globalUsername = "";
	@FXML
	private TextField passwordSignIn;
	@FXML
	private Button buttonSignIn;
	@FXML
	private Label signInError;
	@FXML
	private TextField usernameSignUp;
	@FXML
	private TextField passwordSignUp;
	@FXML
	private Button buttonSignUp;
	@FXML
	private Label signUpError;
	@FXML
	private TextField confirmPassword;
	@FXML
	private TextField enterModuleCode;
	@FXML
	private Button addModule;
	@FXML
	private Button removeModule;
	@FXML
	private Label serverOutput;
	@FXML
	private Label module1;
	@FXML
	private Label module2;
	@FXML
	private Label module3;
	@FXML
	private Label module4;
	@FXML
	private Label module5;
	
    static HashMap<Integer, ArrayList<Module>> serverModules;


	public void switchToMain() throws IOException {
		root = FXMLLoader.load(getClass().getResource("test.fxml"));
		stage = (Stage) usernameSignIn.getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToSignIn(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToSignUp(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}


    public void signInButtonAction(ActionEvent event) throws IOException {
        String username = usernameSignIn.getText();
        String password = passwordSignIn.getText();
        try {
        	Main.out.println("CHECK_USER_EXISTENCE");
        	Main.out.println(username);
            String input = Main.in.readLine();
            boolean userExists = Boolean.parseBoolean(input);

            if (userExists) {
                Main.out.println("CHECK_LOGIN_MATCH");
                Main.out.println(username);
                Main.out.println(password);
                input = Main.in.readLine();
                boolean loginMatch = Boolean.parseBoolean(input);
                if (loginMatch) {
                	
                	globalUsername = username;
                	Main.out.println("TRANSFER_USERNAME");
                	Main.out.println(globalUsername);
                    // Sending a request to the server to retrieve the HashMap
                	Main.out.println("REQUEST_MAP");

                    // Receiving the HashMap from the server
                    int userId = Integer.parseInt(Main.in.readLine());
                    String mapInfo = Main.in.readLine();
                    deserializeMap(mapInfo, userId);
                    
                    System.out.println("Received HashMap from server: " + serverModules);
                	switchToMain();
                    // Login successful
                    // Switch scene or perform other actions
                } else {
                    signInError.setText("Username and Password do not match");
                }
            } else {
                signInError.setText("User does not exist");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void signUpButtonAction() {
        String username = usernameSignUp.getText();
        String password = passwordSignUp.getText();
        String confirmPasswordText = confirmPassword.getText();
        try {
            // Check if passwords match
            if (password.equals(confirmPasswordText)) {
                // Passwords match, proceed with sign-up process
            	Main.out.println("CHECK_USER_EXISTENCE");
            	Main.out.println(username);
                String input = Main.in.readLine();
                boolean userExists = Boolean.parseBoolean(input);

                if (userExists) {
                    signUpError.setText("Username Already Taken");
                } else {
                	Main.out.println("INSERT_USER");
                	Main.out.println(username);
                	Main.out.println(password);
                    signUpError.setText("Account created, Sign In");
                }
            } else {
                // Passwords do not match, display error message
                signUpError.setText("Passwords do not match");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	
	public static String getUsername() {  // we gonna need this for our server so that we can set up data from the sql database
		return globalUsername;
	}
	
	public void deserializeMap(String mapInfo, int userId) {
	    // Clear the existing serverModules HashMap
	    serverModules = new HashMap<>();

	    // Split the serialized mapInfo string by semicolon to separate modules
	    String[] moduleData = mapInfo.split(";");

	    // Iterate over each module data
	    for (String moduleStr : moduleData) {
	        // Split each module data by colon to separate module code and serialized classes
	        String[] moduleParts = moduleStr.split(":");
	        
	        // Get the module code
	        String moduleCode = moduleParts[0];
	        
	        // Create a new Module object with the module code
	        Module module = new Module(moduleCode);
	        
	        // If there are serialized classes for the module
	        if (moduleParts.length > 1) {
	            // Split the serialized classes by colon to separate individual class data
	            String[] classData = moduleParts[1].split(";");
	            
	            // Iterate over each class data and deserialize it
	            for (String classStr : classData) {
	                // Deserialize the class and add it to the module
	                Class classObj = Class.deserialize(classStr);
	                module.addClass(classObj);
	            }
	        }
	        
	        // Add the module to the serverModules HashMap
	        ArrayList<Module> userModules = new ArrayList<>();
	        userModules.add(module);
	        serverModules.put(userId, userModules);
	    }
	}


	


}