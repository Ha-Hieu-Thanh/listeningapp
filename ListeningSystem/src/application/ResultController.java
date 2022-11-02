package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ResultController implements Initializable {
	@FXML
	private Label podcastName, levelPodcast, lengthPodcast, completedTime, score, date, heading, subHeading, team;
	@FXML
	private Button backButton, listenAgainButton;
	@FXML
	private ImageView iconLogo, iconBack, iconReplay;
	
	// Attribute
	private Result result;	
	private Podcast podcast;
	private User user;
	private PodcastManager podcastList;
	
	// Constructor
	public ResultController (User user,Podcast podcast,Result result, PodcastManager podcasts) {
		this.result = result;
		this.podcast = podcast;
		this.user = user;
		this.podcastList = podcasts;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Set info result
		podcastName.setText(podcastName.getText() + " " + result.getPodcastName());
		String level;
		if (result.getLevel() == 1)
			level = "Easy";
		else if (result.getLevel() == 2)
			level = "Medium";
		else
			level = "Hard";
		
		// Show info podcast
		levelPodcast.setText(levelPodcast.getText() + " " + level);
		lengthPodcast.setText(lengthPodcast.getText() + " " + Math.round(result.getPodcastLength() * 10)/10.0 + "s");
		completedTime.setText(completedTime.getText() + " " + Math.round(result.getCompletionTime() * 10)/10.0 + "s");
		score.setText(score.getText() + " " + Math.round(result.getScore() * 10)/10.0 + " point");
		date.setText(date.getText() + " " + result.getDate());
		
		// Set icon
		File f = new File("images\\icons8-back-64.png");
		iconBack.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-xing-96.png");
		iconLogo.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-rotate-left-100.png");
		iconReplay.setImage(new Image(f.toURI().toString()));
	}
	
	
	public void listenAgain(ActionEvent event) throws IOException  {
		Stage stage;
		Scene scene;
		Parent root;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ListeningScene.fxml"));
		loader.setControllerFactory(ListeningController -> new ListeningController(user,podcast,podcastList));
		root = loader.load();
		scene = new Scene(root);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	
	public void backHome(ActionEvent event) throws IOException {
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
