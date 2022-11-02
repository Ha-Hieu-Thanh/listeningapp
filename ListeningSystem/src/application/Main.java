package application;
	
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			// podcast input
			Part p1 = new Part("s01","shortAudios\\baingheso1.mp3",5.2,"My friends have dyed their hair orange, purple and blue");
			Part p2 = new Part("s02","shortAudios\\baingheso2.mp3",9.1,"I told my mother that I would like to dye my hair green. I explained to my mother that I would only use food coloring");
			Part p3 = new Part("s03","shortAudios\\baingheso3.mp3",7.6,"The green would not last forever My mother said that dyeing your hair was a silly fad");
			Part p4 = new Part("s04","shortAudios\\baingheso4.mp3",12.5,"She said that I would not look good with green hair I said that if I couldn't dye my hair green maybe I could get a nose ring My mother said no");
			Part p5 = new Part("s05","shortAudios\\baingheso5.mp3",11.7,"I said that maybe a tattoo on my arm would be nice My mother said no way My mother said that she did some crazy things when she was a young girl");
			Part p6 = new Part("s06","shortAudios\\baingheso6.mp3",10.2,"She said that she used to iron her hair to make it straight That sounds quite boring to me My friend Joan came over Her hair is dyed bright pink");
			Part p7 = new Part("s07","shortAudios\\baingheso7.mp3",9.5,"Hey Jenny Oh hi Steve Nice to see you Good to see you too How's it going Fine thanks I'm so glad the exams have finished");
			Part p8 = new Part("s08","shortAudios\\baingheso8.mp3",12.4,"Me, too. So, are you going on holiday this summer Yes, I've decided to go to Mexico for the whole summer vacation, six weeks in total That sounds great! What are you going to do there");
			Part p9 = new Part("s09","shortAudios\\baingheso9.mp3",10.0," Good afternoon. Royal Mount Hotel How may help you Hello I'd like to book a twin room, please, for next week");
			Part p10 = new Part("s010","shortAudios\\baingheso10.mp3",9.2,"One minute, please...I'll just check if we have one available. Yes, we do, sir. Now, I just need to take down a few details, if I may.Yes, of course");
			Part p11 = new Part("s011","shortAudios\\baingheso11.mp3",10.2,"Great. Thank you.And how would you like to pay, sir? We accept cash, cheque, or credit card. I'll pay cash on arrival, if that's OK. Of course, sir. We look forward to seeing you");
			Part p12 = new Part("s012","shortAudios\\baingheso12.mp3",9.4,"So, there's a great walking tour tomorrow morning. Or tomorrow night, we could go on the cruise round the harbour. What do you think, John?");
			Part p13 = new Part("s013","shortAudios\\baingheso13.mp3",13.3,"So, there's a great walking tour tomorrow morning. Or tomorrow night, we could go on the cruise round the harbour. What do you think, John? Well, we've got theatre tickets for tonight, so, we'll be too tired for the walking tour in the morning. But I don't fancy the cruise, either. Why not? It'll be fun! Look, it's a dinner cruise, and it's only $12 each");		
			
			Part lp1 = new Part("l01","longAudios\\bainghesod1.mp3",25.8,"My father said nice hair, Joan I don't think that he really meant it" 
			+" My mother says that when I am an adult I can dye my hair whatever crazy color I like but for now she would like me to leave"
			+" my hair its natural color I tried to tell her that all my friends were doing it My mother asked if all your friends were jumping off a cliff would you do it too");
			Part lp2 = new Part("l02","longAudios\\bainghesod2.mp3",24.7,"I said no I think I'll have to wait to have green hair but maybe by the "
			+" time I'm old enough to dye my hair green I won't want it that color My mother says that fads change all the time "
			+"One day something might be popular and the next day it's not in style at all I'll just have to live without green "
			+"hair for now I wonder what the fad will be next month");
			Part lp3 = new Part("l03","longAudios\\bainghesod3.mp3",20.0,"Well actually it's a working holiday I'm going to"
			+" work at a school teaching English to children What about you I'm going to Paris for two weeks"
			+ "Are you going with your family No I'm going with my best friend We've enrolled in a language school to study French" 
			+ "That sounds like fun Have a good trip You too");
			Part lp4 = new Part("l04","longAudios\\bainghesod4.mp3",29.3," What name is the booking under?"
					+ "  My name...Duncan Geoffrey. That's G E-0-double F-R-E-Y."
					+ " G-E-0-double F-R-E-Y. Aha. And could I have a contact telephone number, please?"
					+ " Yes, 5762 23821."
					+ " When will you be arriving, sir?"
					+ " Some time on the evening of the nineteenth."
					+ " Of September?"
					+ " Yes, and we'll be leaving on the twenty-third... How much will that be in total?"
					+ " So...That's a twin room... For a twin, it would normally be E235, but I can give you a special"
					+ "rate as it's low season: £210 for the six nights");
			Part lp5 = new Part("l05","longAudios\\bainghesod5.mp3",27.5,"So, what are the differences between these four hotels?"
					+ "Well, the main difference is in the facilities they offer. The Hotel Sunshine is the only one"
					+ "which has a gym, and it's also got one of the top health spas in the area. It's next to a lake, so you can do"
					+ "water sports there. But if you really like sailing or waterskiing, then the Highland Hotel would probably"
					+ "be the best place because it offers great instruction programmes in these sports");
			Part lp6 = new Part("l06","longAudios\\bainghesod6.mp3",32.0,"OK. Er well what about the Hotel Carminia? t's a brand-new hotel, and it prides itself on its"
					+ "cinema and multimedia centre. And then there's The Roval This one has a conference room, a meeting"
					+ "room, and free computer access, but it's not really appropriate for children, there's not in the way of"
					+ "entertainment");
			Part lp7 = new Part("l07","longAudios\\bainghesod7.mp3",22.8,"I hate the sea, and I'll be sick with fear if the waves are big! And dinner... on a boat... I just"
					+ "couldn't."
					+ "But we'll be in the harbour!"
					+ "Still...Ah, but what about this? There's a bus tour tomorrow evening. It's only 5.50, and it goes all"
					+ "around the main tourist sites!"
					+ "Yeah, that sounds OK but I much...");

		
			Podcast pc1 = new Podcast("pc01", "First podcast","images\\image1.jpg", 1, p1,p2);
			Podcast pc2 = new Podcast("pc02", "Easy way","images\\image.jpg", 1, p4,p5,p6);
			Podcast pc9 = new Podcast("pc09", "Summer vacation","images\\image2.jpg", 1, p2,p3,p13); 
			Podcast pc10 = new Podcast("pc10", "When I was a child","images\\image3.jpg", 1, p5,p13);
			Podcast pc12 = new Podcast("pc11","Monster","images\\image4.jpg", 1,p7,p1,p2);
			
			Podcast pc3 = new Podcast("pc03", "Greeting","images\\image5.jpg", 2, p7,p8,p9);
			Podcast pc4 = new Podcast("pc04", "Basic conversation","images\\image6.jpg", 2, p10,p11,p12);
			Podcast pc5 = new Podcast("pc05", "Lucky day","images\\image7.jpg", 2, p1,p2,p6,p3);
			Podcast pc13 = new Podcast("pc13","End of the night","images\\image8.jpg", 2,p10,p11,p1,p2);
			Podcast pc14 = new Podcast("pc14","Havana","images\\image9.jpg", 2,p2,p13,p6,p8);
			
			Podcast pc6 = new Podcast("pc06", "I love you","images\\image10.jpg", 3, lp1);
			Podcast pc7 = new Podcast("pc07", "Oh my boy","images\\image11.jpg", 3, lp2,lp3);
			Podcast pc8 = new Podcast("pc08", "Take me away","images\\image3.jpg", 3,lp3);
			Podcast pc15 = new Podcast("pc015", "Reality","images\\image1.jpg",3,lp4,lp5);
			Podcast pc11 = new Podcast("pc11","That girl","images\\image6.jpg", 3,lp6,lp7);
			
//			Result r1 = new Result("Podcast 1", 2, 40, 82);
//			Result r2 = new Result("Podcast 1", 2, 45, 45);
//			Result r3 = new Result("Podcast 1", 1, 42, 98);
//			Result r4 = new Result("Podcast 1", 3, 36, 123);
//			Result r5 = new Result("Podcast 1", 1, 175, 342);
//			Result r6 = new Result("Podcast 1", 2, 82, 86);
//			Result r7 = new Result("Podcast 1", 1, 20, 55);
//			Result r8 = new Result("Podcast 1", 1, 25, 111);
//			user.getHistory().addResult(r1);
//			user.getHistory().addResult(r2);
//			user.getHistory().addResult(r3);
//			user.getHistory().addResult(r4);
//			user.getHistory().addResult(r5);
//			user.getHistory().addResult(r6);
//			user.getHistory().addResult(r7);
//			user.getHistory().addResult(r8);
//			loader.setControllerFactory(HistoryController-> new HistoryController(user, m));
//			loader.setControllerFactory(ListViewController -> new ListViewController(user,m));
//			loader.setControllerFactory(ListeningController -> new ListeningController(user, pc1));
//			loader.setControllerFactory(ResultController -> new ResultController(user,pc1,new Result("Vacation",2,30,75)));
			
			
			// Initialize 
			User user = new User("20204794","Dang Tien");
			PodcastManager m = new PodcastManager(pc1,pc2,pc3,pc4,pc5,pc6,pc7,pc8,pc9,pc10,pc11,pc12,pc13,pc14);
			user.getHistory().addAllResult(readHistoryFromFile());
			// set Scene
			FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScene.fxml"));
			loader.setControllerFactory(HomecController -> new HomeController(user, m));
			Parent root = loader.load();
			// show scene
			Scene scene = new Scene(root,1280,800);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
			// Close request 
			primaryStage.setOnCloseRequest(event -> {
				try {
					writeHistoryToFile(user.getHistory().getResultList());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				event.consume ();
				exit(primaryStage);
				
			});	
		}
		 catch(Exception e) {
			e.printStackTrace();
		}
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
	
	// Write to file task
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
	
	// Read from file task
	public List<Result> readHistoryFromFile() throws IOException{
		List<Result> list = new ArrayList<Result>();
		FileReader fr = new FileReader("data\\history.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		long n = countLineJavaFile("data\\history.txt");
		System.out.println(n);
		while (true) {
			line = br.readLine();
			if (line == null)
				break;
			String txt[] = line.split(";");
			String namePodcasst = txt[0];
			int level = Integer.parseInt(txt[1]);
			double length = Double.parseDouble(txt[2]);
			double completionTime = Double.parseDouble(txt[3]);
			String date = txt[4];
			Result rs = new Result(namePodcasst,level,length,completionTime,date);
			list.add(rs);
			
		}
		return list;
	}
	
	// Count line file
	public static long countLineJavaFile(String fileName) {

	      Path path = Paths.get(fileName);
	      long lines = 0;
	      try {
	          lines = Files.lines(path).count();

	      } catch (IOException e) {
	          e.printStackTrace();
	      }

	      return lines;
	  }
	
	public static void main(String[] args) {
		launch(args);
	}
}
