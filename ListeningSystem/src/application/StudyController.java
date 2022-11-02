package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StudyController implements Initializable{
	 @FXML
	    private Button backButton,button1,button2,button3,button4,button5,button6,button7;
	    @FXML
	    private Label heading, team;
	    @FXML
	    private ImageView iconBack, iconLogo;
	    @FXML
	    private ScrollPane scrollPane;
	    @FXML
	    private GridPane grid;
	     private DialogPane dialogPane;
	    // Attribute
	    private User user;
	    private List<Result> history = new ArrayList<Result>();
	    private PodcastManager podcastList;
	    
	    public StudyController (User user, PodcastManager podcastList) {
	    	this.user = user;
	    	this.history = user.getHistory().getResultList();
	    	this.podcastList = podcastList;
	    }
	    public void initialize(URL arg0, ResourceBundle arg1) {
	    File f = new File("images\\icons8-back-64.png");
		iconBack.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-xing-96.png");
		iconLogo.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-rotate-left-100.png");
		
	    }
	    public void backScene(ActionEvent event) throws IOException {
			Stage stage;
			Scene scene;
			Parent root;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScene.fxml"));
			loader.setControllerFactory(HomeController -> new HomeController(user, podcastList));
			root = loader.load();
			scene = new Scene(root);
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		}
	    
	    public void noVideoAction(ActionEvent event) throws IOException {
	    	Alert alert = new Alert(AlertType.INFORMATION,"SORRY!!!\n"+"WE HAVEN'T CREATED THIS LESSON YET" , ButtonType.OK);
			alert.setHeaderText("Study Design Thinking");
			alert.setTitle("TEAM 1");
			dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("dialogStyle.css").toString());
			dialogPane.getStyleClass().add("dialog");
			if(alert.showAndWait().get() == ButtonType.OK) {
				alert.close();
			}
	    }
	    public void noExamAction(ActionEvent event) throws IOException {
	    	Alert alert = new Alert(AlertType.INFORMATION,"SORRY!!!\n"+"WE HAVEN'T CREATED THIS EXAM YET" , ButtonType.OK);
			alert.setHeaderText("Study Design Thinking");
			alert.setTitle("TEAM 1");
			dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("dialogStyle.css").toString());
			dialogPane.getStyleClass().add("dialog");
			if(alert.showAndWait().get() == ButtonType.OK) {
				alert.close();
			}
	    }
	    
}
