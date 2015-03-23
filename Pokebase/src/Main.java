
import com.alee.laf.WebLookAndFeel;
import java.awt.Toolkit;
import java.awt.Window;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Main {
	public static void main(String[] args) throws Exception	{
        ManipulateSprites ms = new ManipulateSprites();
	  //ms.insertAllSprites();
       /* ms.insertSprites(3, "mega Venusaur");
        ms.insertSprites(6, "mega Charizard Y");
        ms.insertSprites(6, "mega Charizard X");
        ms.insertSprites(9, "mega Blastoise");
        ms.insertSprites(65, "mega Alakazam");
        ms.insertSprites(94, "mega Gengar");
        ms.insertSprites(115, "mega Kangaskhan");
        ms.insertSprites(127, "mega Pinsir");
        ms.insertSprites(130, "mega Gyarados");
        ms.insertSprites(142, "mega Aerodactyl");
        ms.insertSprites(150, "mega Mewtwo X");
        ms.insertSprites(150, "mega Mewtwo Y"); */
	
        WebLookAndFeel.install();
     
        StandardQueries std = new StandardQueries(); 
        
        SearchPanel pane = new SearchPanel(std);
        
        JFrame frame = new JFrame();
        frame.setContentPane(pane);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/pokemon_starters.jpg"));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
        //possible improvements
       /*
        * dual type searching
        * transparent background on mega sprites
        * remaining 319 pokemon + megas
        */
	}	
        
        
}


