package application;

import java.util.ArrayList;
import java.util.List;

public class Podcast {
    private String podcastID;
    private String podcastName;
    private int level;
    private List<Part> partList;
    private String fileImage;
    private double length;

    // Constructor
    public Podcast(String podcastID, String podcastName,String fileImage, int level, Part... parts){
        this.podcastID = podcastID;
        this.podcastName = podcastName;
        this.level = level;
        this.fileImage = fileImage;
        partList = new ArrayList<Part>();
        for (int i = 0;i < parts.length;i++)
            partList.add(parts[i]);
        for (int i = 0;i < partList.size();i++)
			length += partList.get(i).getTime();
        
    }

    // Getter, setter
     public String getPodcastID(){
        return podcastID;
     }
     public String getPodcastName(){
        return podcastName;
     }
    public int getLevel() {
        return level;
    }
    public String getFileImage() {
    	return fileImage;
    }
    public List<Part> getPartList() {
    	return partList;
    }
    
    public void setPodcastID(String podcastID) {
        this.podcastID = podcastID;
    }

    public void setPodcastName(String podcastName) {
        this.podcastName = podcastName;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public void setFileImage(String fileImage) {
    	this.fileImage = fileImage;
    }
    public boolean addPart (Part part){
        partList.add(part);
        return true;
    }

    public boolean removePart (int index){
        if (index < 0 || index >= partList.size())
            return false;
        partList.remove(index);
        return true;
    }

    public Part getPart (int index){
        return partList.get(index);
    }
    
    public double getLength() {
    	length = Math.round(length * 100)/ 100.0;
    	return length;
    }

    public String getDesc() {
        String tmp = "";
        for (int i = 0;i < 2 && i < partList.size();i++)
            tmp += partList.get(i).getAnswer() + ", ";
        
        
        if (tmp.length() > 65)
        	tmp = tmp.substring(0,65);
        tmp += "...\n";
        return tmp;
    }
    
}