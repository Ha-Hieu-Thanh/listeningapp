package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class HomeController implements Initializable {
	@FXML
    private ImageView iconHistory, iconLogout, iconHelp, iconChart, iconLogo, imgHome, iconListen, iconStudy;
    @FXML
    private Label team, heading;
	@FXML
	private Button historyButton, statisticButton, exitButton, helpButton, listenButton, studyButton;
	
	private Media media;
	private MediaPlayer mediaPlayer;
	private DialogPane dialogPane;
	
	// Attribute
	private User user;
	private PodcastManager podcastList;
	
	// Constructor
	public HomeController(User user, PodcastManager podcastList) {
		this.user = user;
		this.podcastList = podcastList;	
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
//		File file = new File("music\\Gravechill-Twilight.mp3");
//		media = new Media(file.toURI().toString());
//		mediaPlayer = new MediaPlayer(media);
//		mediaPlayer.setVolume(0.3);
//		mediaPlayer.play();
		
		
		listenButton.setOnAction(event -> {
			
//			mediaPlayer.stop();
			Stage stage;
			Scene scene;
			Parent root = null;
		
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ListViewScene.fxml"));
			loader.setControllerFactory(ListViewController -> new ListViewController(user,podcastList));
			try {
				root = loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scene = new Scene(root);
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		});
		// set icon
		File f = new File("images\\icons8-history-96.png");
		iconHistory.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-chart-bar-96.png");
		iconChart.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-xing-96.png");
		iconStudy.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-logout-66.png");
		iconLogout.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-question-mark-90.png");
		iconHelp.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-xing-96.png");
		iconLogo.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-listen-64.png");
		iconListen.setImage(new Image(f.toURI().toString()));
		// Set img
		f = new File("images\\imgHome.jpg");
		imgHome.setImage(new Image(f.toURI().toString()));		

	}	
	public void exit (ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("Do you want to exit?");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			// write file before exiting
			writeHistoryToFile(user.getHistory().getResultList());
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.close();
		}
	}

	public void showHelp() {
		Alert alert = new Alert(AlertType.INFORMATION, "1. Listening System, which help you practice your listening English skill, expand your vocabulary. "
				+ "There are 3 level : Easy, Medium, Hard. The higher level the longer podcast.\n\n"
				+ "2. Shortcut key: \n"
				+ "+ Ctrl + space: play/stop audio.\n"
				+ "+ Ctrl + UP/Down: up/down volume.\n", ButtonType.OK);
		alert.setHeaderText("Introduction");
		alert.setTitle("Infomation");
		
		dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("dialogStyle.css").toString());
		dialogPane.getStyleClass().add("dialog");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			alert.close();
		}
	}

	// Switch scene tasks
	
	public void viewHistory(ActionEvent event ) throws IOException {
//		mediaPlayer.stop();
		Stage stage;
		Scene scene;
		Parent root;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HistoryScene.fxml"));
		loader.setControllerFactory(HistoryController -> new HistoryController(user, podcastList));
		root = loader.load();
		scene = new Scene(root);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	public void viewStatisticScore(ActionEvent event) throws IOException {
//		mediaPlayer.stop();
		Stage stage;
		Scene scene;
		Parent root;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("StatisticScene.fxml"));
		loader.setControllerFactory(StatisticController -> new StatisticController(user, podcastList));
		root = loader.load();
		scene = new Scene(root);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	public void study(ActionEvent event) throws IOException {
		//mediaPlayer.stop();
		Stage stage;
		Scene scene;
		Parent root;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("StudyScene.fxml"));
		loader.setControllerFactory(StudyController -> new StudyController(user, podcastList));
		root = loader.load();
		scene = new Scene(root);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	// Write file task
	public void writeHistoryToFile (List<Result> resultList) throws IOException {
		FileWriter fw = new FileWriter("data\\history.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		for (int i = 0;i < resultList.size();i++) {
			bw.write(resultList.get(i).getDetails());
			bw.newLine();
		}
		bw.close();
		fw.close();
	}
	
	public void exit (Stage primaryStage) {
		// show alert
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("Do you want to exit?");
		
		// if OK then exit
		if(alert.showAndWait().get() == ButtonType.OK) {
			primaryStage.close();
		}
	}
	
}
