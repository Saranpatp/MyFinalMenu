package application;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.input.KeyCode;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;
import javafx.util.Duration;

//sound
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Main extends Application {
	
	private static Font font;
	private MenuBox menu;
	
	
	
    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(800, 600);
        try (InputStream is = Files.newInputStream(Paths.get("res/image/CyberPunk2.jpg"));
        		InputStream fontStream = Files.newInputStream(Paths.get("res/font/BLADRMF_.TTF"))){// load resorce font and image
        	ImageView img = new ImageView(new Image(is));
        	img.setFitWidth(1066);
        	//img.setX(value);
        	//img.setWidth(val);
        	img.setFitHeight(600);
        	
        	root.getChildren().add(img);
        	
        	font = Font.loadFont(fontStream, 35);
			
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("Error with load image or font");
		}
        
        MenuItem itemQuit = new MenuItem("QUIT");
        itemQuit.setOnMouseClicked(event -> System.exit(0)); // ใส่ function ให้ ปุ่ม quit
        
        menu = new MenuBox("cy3erpunk", 
        		new MenuItem("START GAME"),
        		new MenuItem("HELP"),
        		new MenuItem("CREDITS"),
        		itemQuit); // set iteamQuit เสดก็ใส่ตรวนี้
        
        root.getChildren().add(menu);
        return root;
   }
   
    public void playTheme() {
    	try{
            AudioInputStream audioInputStream =
                AudioSystem.getAudioInputStream(
                    this.getClass().getResource("Ost.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception ex)
        {
        	System.out.println("Cant load wav");
        }
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        
        playTheme();  
        
        primaryStage.setTitle("CyberPunk Theme Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private static class MenuBox extends StackPane{
    	public MenuBox(String title, MenuItem...items ) { //Menuitems... เเปลว่าจะรับข้อมูลเเนว menuitem ได้หลายตัว
    		Rectangle bg = new Rectangle(300,600);
    		bg.setOpacity(0.5); // set opacity ที่ 20% 
    		
    		DropShadow shadow = new DropShadow(7,5,0,Color.BLACK);
    		shadow.setSpread(0.8);// เงา spread มากขึ้น
    		
    		bg.setEffect(shadow); 
    		
    		Text text = new Text(title+ " ");
    		text.setFont(font);
    		text.setFill(Color.WHITE);
    		
    		Line hSep = new Line(); //เส้นใต้ title
    		hSep.setEndX(250); //เส้นราบเเนวเเกน x เเนวนิน
    		hSep.setStroke(Color.PLUM);
    		hSep.setOpacity(0.4);//********************************change
    		
    		Line vSep = new Line();// เส้นเเนตั้ง
    		vSep.setStartX(300);
    		vSep.setEndX(300);
    		vSep.setEndY(600);
    		vSep.setStroke(Color.DARKMAGENTA);
    		vSep.setOpacity(0.4);
    		
    		VBox vbox = new VBox();
    		vbox.setAlignment(Pos.TOP_RIGHT);
    		vbox.setPadding(new Insets(60,0,0,0));
    		vbox.getChildren().addAll(text,hSep);//ใส่พวก menu item ทั้งหมดลง ใน vbox 
    		vbox.getChildren().addAll(items);
    		
    		setAlignment(Pos.TOP_RIGHT); //top right of stack pane
    		getChildren().addAll(bg,vSep,vbox);
    		
    	}
    }
    
    private static class MenuItem extends StackPane{// ตัวเมนูต่างๆ
    	public MenuItem(String name) {
    		Rectangle bg = new Rectangle(300,24);// background ของ menu เเต่ละอัน
    		
    		LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
                    new Stop(0, Color.WHITE),
                    new Stop(0.2, Color.VIOLET)
            });
    		
    		bg.setFill(gradient);
    		bg.setVisible(false);
    		bg.setEffect(new DropShadow(5, 0, 5, Color.DARKMAGENTA));// was Black***************** //radius,drop at x,drop at y,colour
    		
    		Text text = new Text(name + "      ");
            text.setFill(Color.LIGHTGREY);
            text.setFont(Font.font(20));
    		
    		setAlignment(Pos.CENTER_RIGHT);
    		getChildren().addAll(bg,text);
    		
    		setOnMouseEntered( event ->{ // lambda function
    			bg.setVisible(true);
    			text.setFill(Color.WHITE);
    		});
    		
    		setOnMouseExited( event ->{
    			bg.setVisible(false);
    			text.setFill(Color.LIGHTGRAY);
    		});
    		
    		setOnMousePressed(event ->{
    			bg.setFill(Color.WHITE);
    			text.setFill(Color.VIOLET);
    		});
    		
    		setOnMouseReleased(event -> {
    			bg.setFill(gradient);
    			text.setFill(Color.WHITE);
    		});
    		
    	}
    }

    public static void main(String[] args) {
        launch(args);
    }
}
