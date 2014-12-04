
import com.alee.laf.WebLookAndFeel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Main {
	public static void main(String[] args) throws Exception	{
		//ManipulateSprites ms = new ManipulateSprites();
		//ms.insertAllSprites();
		
        WebLookAndFeel.install();
            
        StandardQueries std = new StandardQueries();
       
        SearchPanel pane = new SearchPanel(std);
        
        JFrame frame = new JFrame();
        frame.setContentPane(pane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
}


