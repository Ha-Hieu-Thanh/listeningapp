package application;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Part {
    private String partID;
    private String fileAudio;
    private String answer; 
    private double time;

    public Part (String partID, String fileAudio,double time, String answer){
        this.partID = partID;
        this.fileAudio = fileAudio;
        this.answer = answer;
        this.time = time;
    }

     public String getPartID(){
        return partID;
     }

    public String getFileAudio() {
        return fileAudio;
    }

    public String getAnswer() {
        return answer;
    }

    public double getTime() {
    	return time;
    }

    public void setPartID(String partID) {
        this.partID = partID;
    }

    public void setFileAudio(String fileAudio) {
        this.fileAudio = fileAudio;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public void setTime(double time) {
    	this.time = time;
    }
}