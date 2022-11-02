package application;

import java.io.File;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ListViewController implements Initializable{
	@FXML
	private Pane infoPodcast;
	@FXML
	private Label level, namePodcast, timePodcast,  alert, heading, team;
	@FXML
	private Text descPodcast;
	@FXML
	private ImageView imgPodcast;
	@FXML
	private Button searchButton, openButton, backButton, easyButton, mediumButton, hardButton;
	@FXML 
	private TextField searchBar;
	@FXML
	private ListView<String> listViewPodcast;
	@FXML
	private ImageView iconLogo, iconBack, iconSearch, iconStar1, iconStar2, iconStar3, iconStar4, iconStar5, iconStar6;
	
	// Attribute
	private User user;
	private PodcastManager podcastList;
	private List<Podcast> currentList;
	
	private List<String> podcasts;
	private int podcastNumber;
	// alert
	private String alertText;
	
	public ListViewController (User user,PodcastManager podcasts) {
		this.user = user;
		this.podcastList = podcasts;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Get name Podcast to list view
		podcasts = new ArrayList<String>();
		currentList = new ArrayList<Podcast>();
		// hide 
		infoPodcast.setDisable(true);
		infoPodcast.setOpacity(0);
		// Set default level
		
		try {
			setLevel(null,1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Event listener click podcast
		listViewPodcast.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// Show info podcast
				infoPodcast.setDisable(false);
				infoPodcast.setOpacity(1);
				// Get podcast's data
				podcastNumber = listViewPodcast.getSelectionModel().getSelectedIndex();
				Podcast currentPodcast = currentList.get(podcastNumber);
				// Get image
				File imgFile = new File(currentPodcast.getFileImage());
				imgPodcast.setImage(new Image(imgFile.toURI().toString()));
				// Get name,time, description
				namePodcast.setText("Name: " + currentPodcast.getPodcastName());
				timePodcast.setText("Time: " + currentPodcast.getLength() + "s");
				descPodcast.setText("Desc: " + currentPodcast.getDesc());
			}
		});	
		
		easyButton.setOnAction(event-> {
			try {
				setLevel(event, 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		mediumButton.setOnAction(event-> {
			try {
				setLevel(event, 2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		hardButton.setOnAction(event-> {
			try {
				setLevel(event, 3);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		searchBar.setOnKeyReleased(event->{
			searchPodcast();
		});
		
		// hide alert
		alertText = alert.getText();
		alert.setDisable(true);
		alert.setOpacity(0);
		
		// Set icon
		File f = new File("images\\icons8-back-64.png");
		iconBack.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-xing-96.png");
		iconLogo.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-search-100.png");
		iconSearch.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-star-96.png");
		iconStar1.setImage(new Image(f.toURI().toString()));
		iconStar2.setImage(new Image(f.toURI().toString()));
		iconStar3.setImage(new Image(f.toURI().toString()));
		iconStar4.setImage(new Image(f.toURI().toString()));
		iconStar5.setImage(new Image(f.toURI().toString()));
		iconStar6.setImage(new Image(f.toURI().toString()));
		
	}
	
	// Set level task
	public void setLevel(ActionEvent event, int level) throws IOException {
		currentList.clear();
		currentList = podcastList.getPodcastsAtLevel(level);
		podcasts.clear();
		for (int i = 0;i < currentList.size();i++) {
			podcasts.add(currentList.get(i).getPodcastName());
		}
		listViewPodcast.getItems().clear();
		listViewPodcast.getItems().addAll(podcasts);
		setTextLevel(level);	
	}
	
	
	// Search 
	public void searchPodcast() {
		String key = searchBar.getText();
		
		// Get data into list view
		listViewPodcast.getSelectionModel().clearSelection();
		PodcastManager p = new PodcastManager(currentList);
		List<Podcast> result = p.getPodcastsByName(key);
		// If not found
		if (result.size() == 0) {
			// show alert
			alert.setDisable(false);
			alert.setOpacity(1);
			alert.setText(alertText + "\"" + searchBar.getText() + "\""); 
		}
		// If found
		else {
			// Show podcast in list view
			listViewPodcast.getItems().clear();
			podcasts.clear();
			for (int i = 0;i < result.size();i++) {
				podcasts.add(result.get(i).getPodcastName());
			}
			listViewPodcast.getItems().addAll(podcasts);
			// Show info
			infoPodcast.setDisable(true);
			infoPodcast.setOpacity(0);
			// Hide alert
			alert.setDisable(true);
			alert.setOpacity(0);
		}
	}
	public void openPodcast(ActionEvent event) throws IOException{
		// Change to listening Scene
		Stage stage;
		Scene scene;
		Parent root;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ListeningScene.fxml"));
		ListeningController listeningController = new ListeningController(user, currentList.get(podcastNumber), podcastList);
		loader.setControllerFactory(ListeningController -> listeningController);
		root = loader.load();
		scene = new Scene(root);
		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
		    final KeyCombination keyCombMedia = new KeyCodeCombination(KeyCode.SPACE,
		                                                          KeyCombination.CONTROL_DOWN);
		    final KeyCombination keyCombRaiseVolume = new KeyCodeCombination(KeyCode.UP,
                    KeyCombination.CONTROL_DOWN);
		    final KeyCombination keyCombReduceVolume = new KeyCodeCombination(KeyCode.DOWN,
                    KeyCombination.CONTROL_DOWN);
		    
		    
		    public void handle(KeyEvent ke) {
		        if (keyCombMedia.match(ke)) {
		            if (listeningController.isRunning())
		            	listeningController.stopMedia();
		            else
		            	listeningController.playMedia();
		            ke.consume(); // <-- stops passing the event to next node
		        }
		        else if (keyCombRaiseVolume.match(ke)) {
		        	listeningController.getVolumeSlider().setValue(listeningController.getVolumeSlider().getValue() + 5);
		        }
		        else if (keyCombReduceVolume.match(ke)) {
		        	listeningController.getVolumeSlider().setValue(listeningController.getVolumeSlider().getValue() - 5);
		        }
		    }
		});
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		stage.setScene(scene);
		stage.show();
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
	
	public void setTextLevel(int lv) {
		if (lv == 1)
			level.setText("Level: Easy");
		else if (lv == 2)
			level.setText("Level: Medium");
		else if (lv == 3)
			level.setText("Level: Hard");
	}
	
	
}
