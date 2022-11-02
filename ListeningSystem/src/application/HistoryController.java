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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HistoryController implements Initializable{
    @FXML
    private Button backButton;
    @FXML
    private Label heading, team;
    @FXML
    private ImageView iconBack, iconLogo;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane grid;

    // Attribute
    private User user;
    private List<Result> history = new ArrayList<Result>();
    private PodcastManager podcastList;

    // Constructor
    public HistoryController (User user, PodcastManager podcastList) {
    	this.user = user;
    	this.history = user.getHistory().getResultList();
    	this.podcastList = podcastList;
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Get data from history
		try {
			for (int i = history.size()-1;i >= 0;i--) {
				FXMLLoader fxmlLoader  = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("ElementHistory.fxml"));
				AnchorPane anchorPane = fxmlLoader.load();
				
				ElementHistoryController element = fxmlLoader.getController();
				element.setData(history.get(i));
				grid.add(anchorPane, 0, history.size()-1 -i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Set icon GUI
		File f = new File("images\\icons8-back-64.png");
		iconBack.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-xing-96.png");
		iconLogo.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-rotate-left-100.png");
		
	}
	
	// switch Home scene task
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

}
