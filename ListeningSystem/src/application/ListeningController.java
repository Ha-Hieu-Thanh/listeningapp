package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ListeningController implements Initializable{
	@FXML
	private Pane pane;
	@FXML
	private Label namePodcast, track, hint,team,heading;
	@FXML
	private Text textAnswer;
	@FXML
	private Button playButton, stopButton, hintButton, replayButton, nextButton, finishButton, backButton;
	@FXML
	private ComboBox<String> speedBox;
	@FXML
	private Slider volumeSlider;
	@FXML
	private ProgressBar progressMedia;
	@FXML
	private Media media;
	private MediaPlayer mediaPlayer;
	@FXML
	private TextField input;
	@FXML
	private ImageView iconBack,iconLogo, iconPlay, iconStop, iconHint, iconReplay, iconNext, iconFinish, iconVolume;
	
	// Attribute
	private User user;
	private Podcast podcast;
	private PodcastManager podcastList;

	// File container
	private ArrayList<File> audios = new ArrayList<File>(); 
	private int audioNumber;        
	
	// Value speed box
	private int[] speeds = {25,50,75,100,125,150,175,200};
	
	// timer progress bar
	private Timer timer;
	private TimerTask task;
	private boolean running;
	// counter 
	private Timer countTimer;
	private TimerTask countTask;
	private double count;
	
	// Answer data
	private String answer;
	private String[] words;
	
	// Check showing hint or not
	private boolean isShowingHint;

	// track text
	private String trackText;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	// Constructor
	public ListeningController (User user,Podcast podcast, PodcastManager podcasts) {
		this.user = user;
		this.podcast = podcast;
		this.podcastList = podcasts;
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Get audio file from podcasts
		for (int i = 0;i < podcast.getPartList().size();i++) {
			File file = new File(podcast.getPartList().get(i).getFileAudio());
			audios.add(file);
			System.out.println(file);
		}
	
		// Open audio file
		media = new Media(audios.get(audioNumber).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		
		// Set heading, sub heading GUI
		namePodcast.setText(podcast.getPodcastName());
		trackText = track.getText();
		setTrackText(audioNumber);
		
		// Get values into speed box 
		for (int i = 0; i < speeds.length; i++) {
			speedBox.getItems().add(Integer.toString(speeds[i]) + "%");
		}
		//Change speed task
		speedBox.setOnAction(this::changeSpeed);
		
		// Change volume task
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
				
			}
		});
		
		// Set button 
		hideButton(nextButton);
		hideButton(replayButton);
		hideButton(finishButton);
		
		// Set hint button

		hint.setDisable(true);
		hint.setOpacity(0);
		isShowingHint = false;
		
		// Typing answer task
		answer = podcast.getPart(audioNumber).getAnswer().toLowerCase();
		words = answer.split("\\s");
		String hintStr = "";
		if (words[0].length() == 1)
			hintStr += "?";
		else 
			hintStr += "??";
		
		if (words[0].length() > 2) {
			hintStr += words[0].substring(2,words[0].length());
		}
		
		
		this.running = false;
		
		hint.setText(hintStr);
		input.setDisable(true);
		input.setOnKeyReleased(new EventHandler<KeyEvent>() {
			String word = "";
			int i = 0, j = 0;
			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == e.getCode().BACK_SPACE) {
					if (j > 0) {
						j--;
						word = word.substring(0,word.length()-1);
					}
				}
				else if (e.getText().length() == 0) {
					// If enter shift, space, F1, F2, .... don't process
				}
				else {
					// Enter correct character
					if (Character.toLowerCase(e.getText().charAt(0))==  Character.toLowerCase(words[i].charAt(j))) {
						word += e.getText();
						word = word.toUpperCase();
						j++;
						
						// Complete a word
						if (j == words[i].length()) {
							i++;
							if (i < words.length) {	
								String hintStr = "";
								if (words[i].length() == 1)
									hintStr += "?";
								else 
									hintStr += "??";
								
								if (words[i].length() > 2) 
									hintStr += words[i].substring(2,words[i].length());

								hint.setText(hintStr);		
							}
							j = 0;
							// If it is final word of answer
							if (i == words.length) {
								input.setDisable(true);
								showButton(replayButton);
								textAnswer.setOpacity(0.8);
								if (audioNumber < audios.size() - 1) 
									showButton(nextButton);								
								else 
									showButton(finishButton);
								
								hideButton(playButton);
								stopButton.setDisable(true);
								hintButton.setDisable(true);
								mediaPlayer.stop();
								i = 0;
								j = 0;
								cancelCounting();
							}
							
							// Set text answer of part
							String currentTextAnswer = textAnswer.getText();
							if (textAnswer.getText() == null)
								currentTextAnswer = "";
							
							textAnswer.setText(currentTextAnswer + word + " ");
							word = "";
							input.clear();
							
						}
					}
					// Enter wrong character
					else {
						input.deletePreviousChar();
					}
				}
			}
		});
		

		
		
		
		// Set value counter  
		count = 0;
		// Set icon
		File f = new File("images\\icons8-back-64.png");
		iconBack.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-xing-96.png");
		iconLogo.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-play-90.png");
		iconPlay.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-stop-90.png");
		iconStop.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-rotate-100.png");
		iconReplay.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-done-64.png");
		iconFinish.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-end-90.png");
		iconNext.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-question-mark-90.png");
		iconHint.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-sound-100.png");
		iconVolume.setImage(new Image(f.toURI().toString()));
	
		
	}
	
	public void playMedia() {
		// Start progress bar
		beginTimer();
		// Set default speed and volume
		changeSpeed(null);
		mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
		// Hide play button
		hideButton(playButton);
		// Play media
		mediaPlayer.play();
		startCounting();
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setCycleCount(10);
		input.setDisable(false);
		textAnswer.setOpacity(1);
	}
	public void stopMedia() {
		// Stop progress bar
		cancelTimer();
		// Stop media
		mediaPlayer.stop();
		cancelCounting();
//		input.setDisable(true);
		// Set button
		showButton(playButton);
		textAnswer.setOpacity(0.8);
	}
	public void resetMedia() {
		progressMedia.setProgress(0);
		beginTimer();
		mediaPlayer.seek(Duration.seconds(0));
		mediaPlayer.play();
		
	}
	public void nextMedia() {
		if (audioNumber < audios.size() - 1) {
			// Set button
			hideButton(nextButton);
			hideButton(replayButton);
			showButton(playButton);
			stopButton.setDisable(false);
			hintButton.setDisable(false);
			// Clear text 
			input.clear();
//			input.setDisable(true);
			hint.setText(null);
			isShowingHint = false;
			hintButton.setDisable(false);
			textAnswer.setOpacity(1);
			
			// Get next part of Podcast
			audioNumber++;
			answer = podcast.getPart(audioNumber).getAnswer().toLowerCase();
			
			// Get answer of podcast
			words = answer.split("\\s");
			textAnswer.setText(null);
			mediaPlayer.stop();
			if (running) {
				cancelTimer();
				running = false;
			}
			
			// Play media
			media = new Media(audios.get(audioNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);

			// Set text
			setTrackText(audioNumber);
		}
	}
	public void showHint() {
		if (!isShowingHint) {
			hint.setDisable(false);
			hint.setOpacity(1);
			isShowingHint = true;
		}
		else {
			hint.setDisable(true);
			hint.setOpacity(0);
			isShowingHint = false;
		}
	}
	public void changeSpeed(ActionEvent event) {
		if (speedBox.getValue() == null)
			mediaPlayer.setRate(1);
		else
			mediaPlayer.setRate(Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length()-1)) * 0.01);
	}
	public void beginTimer() {
		running = true;
		timer = new Timer();
		task = new TimerTask() {	
			@Override
			public void run() {
				double current = mediaPlayer.getCurrentTime().toSeconds();
				double end = media.getDuration().toSeconds();
				progressMedia.setProgress(current/end);
				
				if (current/end == 1) {
					progressMedia.setProgress(0);
				}
			}
		};
		timer.scheduleAtFixedRate(task, 100, 100);
	}
	public void cancelTimer() {
		running = false;
		timer.cancel();
	}
	
	public void startCounting() {
		countTimer = new Timer();
		countTask = new TimerTask() {
			@Override
			public void run() {
				count = count + 0.1;
			}
		};
		timer.scheduleAtFixedRate(countTask, 0, 200);
	}
	
	public void cancelCounting() {
		countTimer.cancel();
		countTask.cancel();
	}
	
	public void finishPodcast(ActionEvent event) throws IOException{
		// Get length of podcast
		mediaPlayer.stop();
		double length = podcast.getLength();
		// Initialize result after listening
		Result result = new Result(podcast.getPodcastName(),podcast.getLevel(), length,count);
		
		// Add result to user's history
		user.getHistory().addResult(result);
		try {
			writeHistoryToFile(user.getHistory().getResultList());
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		// Change scene to Result scene
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultScene.fxml"));
		loader.setControllerFactory(ResultController -> new ResultController(user,podcast,result,podcastList ));
		root = loader.load();
		scene = new Scene(root);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void hideButton(Button btn) {
		btn.setDisable(true);
		btn.setOpacity(0);
	}
	public void showButton(Button btn) {
		btn.setDisable(false);
		btn.setOpacity(1);
	}
	
	public void setTrackText (int audioNumber) {
		String tmp;
		tmp = trackText + " " + Integer.toString(audioNumber + 1) + " of " + Integer.toString(audios.size());
		track.setText(tmp);
	}
	
	public void backScene(ActionEvent event) throws IOException {
		mediaPlayer.stop();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ListViewScene.fxml"));
		loader.setControllerFactory(ListViewController -> new ListViewController(user, podcastList));
		root = loader.load();
		scene = new Scene(root);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	
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
	
	public boolean isRunning () {
		return (running == true);
	}
	
	public Slider getVolumeSlider() {
		return volumeSlider;
	}
	
}
