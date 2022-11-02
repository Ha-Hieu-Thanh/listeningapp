package application;
import java.util.Date;

public class Result {
    private String podcastName;
    private int level;
    private double podcastLength;
    private double completionTime;
    private double score;
    private String date;

    // Constructor
    public Result(String podcastName, int level, double podcastLength, double completionTime){
        this.podcastName = podcastName;
        this.level = level;
        this.podcastLength = podcastLength;
        this.completionTime = completionTime;
        Date getDate = new Date();
        date = getDate.toString();
        
    }
    
    public Result(String podcastName, int level, double podcastLength, double completionTime, String date){
        this.podcastName = podcastName;
        this.level = level;
        this.podcastLength = podcastLength;
        this.completionTime = completionTime;
        this.date = date;
    }
    
    // Getter, setter
    public String getPodcastName(){
        return podcastName;
    }

    public int getLevel() {
        return level;
    }

    public double getPodcastLength() {
        return podcastLength;
    }

    public double getCompletionTime() {
        return completionTime;
    }

    public String getDate() {
        return date;
    }
    

	public int getScore() {
        score = 10 * (11 - getCompletionTime()/getPodcastLength());
        return (int) score;
    }

    public String getDetails(){
        String tmp = "";
        tmp += podcastName + ";";
        tmp += Integer.toString(level) + ";";
        tmp += Double.toString(podcastLength) + ";";
        tmp += Double.toString(completionTime) + ";";
        tmp += date.toString();
        return  tmp;
    }
}