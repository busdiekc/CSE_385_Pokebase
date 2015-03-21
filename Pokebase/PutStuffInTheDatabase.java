import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class PutStuffInTheDatabase {
	
	 static Connection c = null;
	
	// makes a connection to the database
	public PutStuffInTheDatabase() {
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:New_Pokebase.db");
			
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	// insert all pokemon passed in the array to the database
	private static void insertPokemon(ArrayList<Pokemon> pokemonArray) throws SQLException {
		
		for (Pokemon p : pokemonArray) {
			
			PreparedStatement ps = c.prepareStatement("insert into pokemon (PokemonNumber, PokemonName, Weight, Height, Type1, Type2, HabitatID) values (?, ?, ?, ?, ?, ?, ?)");
			
			ps.setInt(1, p.pokemonNum);
			ps.setString(2, p.pokemonName);
			ps.setDouble(3, p.weight);
			ps.setDouble(4, p.height);
			ps.setInt(5, p.type1ID);
			ps.setInt(6, p.type2ID);
			ps.setInt(7, p.habitatID);
			
			ps.executeUpdate();
			
		}
		
	}
	
	// insert all the stats of the pokemon passed in the array to the database
	private static void insertStats(ArrayList<Pokemon> pokemonArray) throws SQLException {
		
		for (Pokemon p : pokemonArray) {
			
			PreparedStatement ps = c.prepareStatement("insert into stats (PokemonNumber, Total, HP, Atk, Def, SpAtk, SpDef, Spd) values (?, ?, ?, ?, ?, ?, ?, ?)");
			
			ps.setInt(1, p.pokemonNum);
			ps.setInt(2, p.totalPts);
			ps.setInt(3, p.hp);
			ps.setInt(4, p.atk);
			ps.setInt(5, p.def);
			ps.setInt(6, p.spAtk);
			ps.setInt(7, p.spDef);
			ps.setInt(8, p.spd);
			
			ps.executeUpdate();
			
		}
		
	}
	
	// used to insert the sprite and shiny sprite of a single pokemon
	private void insertSprites(int pokemonNum, String pokemonName) throws FileNotFoundException, SQLException {
		
		File pic = new File("C:/PokemonSprites/" + pokemonName + ".png");
		File shinyPic = new File("C:/PokemonSprites/" + pokemonName + " (1).png");
		
		InputStream picStream = new FileInputStream(pic);
		InputStream shinyPicStream = new FileInputStream(shinyPic);
		
		PreparedStatement ps = c.prepareStatement("insert into sprites (PokemonNumber, Picture, ShinyPicture) " + "values (?, ?, ?)");
		
		ps.setInt(1, pokemonNum);
		ps.setBinaryStream(2, picStream, (int) pic.length());
		ps.setBinaryStream(3, shinyPicStream, (int) shinyPic.length());
		
		ps.executeUpdate();
		
	}
	
	
	public static void main(String[] args) throws IOException, SQLException {
		
		// creates the connection to the database New_Pokebase.db
		PutStuffInTheDatabase db = new PutStuffInTheDatabase();
		
		// creates the connection to the html files to parse information from
		WebsiteParser wp = new WebsiteParser();
		
		// parses the information of all pokemon from this url and puts it in an array of pokemon objects
		wp.urlStatsParser(wp.htmlStatsFile);
		
		// converts typeNames to typeIDs to insert into the database
		wp.getTypeIDS(wp.pokemonArray);
		
		// parses the height information of all pokemon from this url and stores it in the array of pokemon objects
		wp.getHeight(wp.htmlWeightsHeightsFile);
		
		// parses the weight information of all pokemon from this url and stores it in the array of pokemon objects
		wp.getWeight(wp.htmlWeightsHeightsFile);
		
		// converts the parsed height from meters to inches
		wp.convertHeights(wp.pokemonArray);
		
		// inserts all the pokemon gathered from the urlStatsParser into the database New_Pokebase.db
		insertPokemon(wp.pokemonArray);
		
		// inserts all the stats associated with the all the pokemon gathered from urlStatsParser into the database New_Pokebase.db
		insertStats(wp.pokemonArray);
		

	}

}
