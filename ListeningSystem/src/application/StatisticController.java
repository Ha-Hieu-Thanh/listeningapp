package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StatisticController implements Initializable {
	
    @FXML
    private Button backButton;
    @FXML
    private Label heading,team, averageScoreLabel, highestScoreLabel, completeTimesLabel;
    @FXML
    private ImageView iconBack;
    @FXML
    private ImageView iconLogo;
    @FXML
    private ChoiceBox<String> choiceBox = new ChoiceBox<String>();    
    @FXML
    private LineChart<?, ?> lineChart;
    
    // Attribute
    private User user;
    private PodcastManager podcastList;
    private List<Result> resultList;

	public StatisticController(User user, PodcastManager podcastList) {
		this.user = user;
		this.podcastList = podcastList;
		resultList = user.getHistory().getResultList();
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Get name podcasts in history
		Set<String> namesPodcast = new TreeSet<String>();
		for (int i = 0;i < resultList.size();i++)
			namesPodcast.add(resultList.get(i).getPodcastName());
		
		choiceBox.getItems().addAll(namesPodcast);
		choiceBox.setOnAction(this::statistic);	
		
		
		// switch scene
		backButton.setOnAction(event ->{
			Stage stage;
			Scene scene;
			Parent root = null;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScene.fxml"));
			loader.setControllerFactory(HomeController -> new HomeController(user, podcastList));
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
		File f = new File("images\\icons8-back-64.png");
		iconBack.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-xing-96.png");
		iconLogo.setImage(new Image(f.toURI().toString()));
		f = new File("images\\icons8-rotate-left-100.png");
	
	}
	
	public void statistic(ActionEvent event) {
		// Get value to choice box podcast
		String name = choiceBox.getValue();
		int countTimes = 0;
		int maxScore = 0;
		int totalScore = 0;
		List<Result> results = new ArrayList<Result>();
		
		// Statistic data
		for (int i = 0;i < resultList.size();i++) {
			if (resultList.get(i).getPodcastName().equals(name)) {
				results.add(resultList.get(i));
				countTimes++;
				maxScore = Math.max(maxScore, resultList.get(i).getScore());
				totalScore += resultList.get(i).getScore();
			}
		}
		
		// Show chart
		initLineChart(results);
		// Get statistic data
		double averageScore = totalScore / (double)countTimes;
		averageScore = Math.floor(averageScore * 10) / 10.0;
		// Show statistic data
		averageScoreLabel.setText(Double.toString(averageScore));
		highestScoreLabel.setText(Integer.toString(maxScore));
		completeTimesLabel.setText(Integer.toString(countTimes));
		
	}
	
	// Adding data to chart task
	@SuppressWarnings("unchecked")
	public void initLineChart(List<Result> results){
		XYChart.Series series = new XYChart.Series();
		lineChart.getData().clear();
		setupXAxis((NumberAxis) lineChart.getYAxis(), 0, 100);
//		lineChart.getXAxis().setOpacity(0);
		series.setName(results.get(0).getPodcastName());
		for (int i = 0;i < results.size();i++) {
			series.getData().add(new XYChart.Data(Integer.toString(i+1) , results.get(i).getScore()));
		}
		lineChart.getData().addAll(series);
		lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
		series.getNode().setStyle("-fx-stroke: #ffd6dc");
	
	}
	
	// Set up style to AXis of chart
	public void setupXAxis(NumberAxis numberAxis, long min, long max) {
		  numberAxis.setAutoRanging(false);
		  numberAxis.setLowerBound(min);
		  numberAxis.setUpperBound(max);
		}

}
