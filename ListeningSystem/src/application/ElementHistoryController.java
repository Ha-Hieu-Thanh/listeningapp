package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ElementHistoryController {
	@FXML
	private Label name, time, level, date, score;
	
	private Result result;
	
	public void setData(Result rs) {
		result = rs;
		name.setText("Name's Podcast: " + result.getPodcastName());
		time.setText("Time: " + result.getPodcastLength());
		level.setText("Level: " + getStringLevel(result.getLevel()));
		date.setText("Date: " + result.getDate().toString());
		score.setText(result.getScore() + "pt");
	}
	
	public String getStringLevel(int lv) {
		if (lv == 1)
			return "Easy";
		else if (lv == 2)
			return "Medium";
		else
			return "Hard";
	}
	
	
}
