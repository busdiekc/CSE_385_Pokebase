import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;

// Author: Kyle Busdieker
// Purpose: Insert and retrieve Pokemon sprites into/from the Pokebase database
//		as well as convert a retrieved sprite into a usable image.
// Also has the functionality to add the first 151 pokemon sprites and shiny sprites
//	into the Pokebase database with one method call.


public class ManipulateSprites {
	
	Connection c = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	// constructor establishes a connection to the Pokebase database
	public ManipulateSprites() {
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Pokebase.db");
			
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	// used to insert the sprite and shiny sprite of a single pokemon
	private void insertSprites(int pokemonID, String pokemonName) throws FileNotFoundException, SQLException {
		
		File pic = new File("C:/Pokemon_Sprites/" + pokemonName + ".png");
		File shinyPic = new File("C:/Pokemon_Sprites/" + pokemonName + ".png");
		
		InputStream picStream = new FileInputStream(pic);
		InputStream shinyPicStream = new FileInputStream(shinyPic);
		
		ps = c.prepareStatement("insert into sprites (PokemonID, Picture, ShinyPicture) " + "values (?, ?, ?)");
		
		ps.setInt(1, pokemonID);
		ps.setBinaryStream(2, picStream, (int) pic.length());
		ps.setBinaryStream(3, shinyPicStream, (int) shinyPic.length());
		
		ps.executeUpdate();
		
	}
	
	// used to retrieve just the sprite of a single pokemon
	private byte[] retrieveASprite(int pokemonID) throws SQLException {
		
		byte[] sprite = null;
		
		ps = c.prepareStatement("select picture from sprites where pokemonid = " + pokemonID);
		rs = ps.executeQuery();
		
		while (rs.next())
			sprite = rs.getBytes(1);
		
		return sprite;
	}
	
	// used to retrieve just the shiny sprite of a single pokemon
	private byte[] retrieveAShinySprite(int pokemonID) throws SQLException {
		
		byte[] shinySprite = null;
		
		ps = c.prepareStatement("select shinypicture from sprites where pokemonid = " + pokemonID);
		rs = ps.executeQuery();
		
		while(rs.next())
			shinySprite = rs.getBytes(1);
		
		return shinySprite;
	}
	
	// used to convert a retrieved pokemon's sprite or shiny sprite into a usable image
	private BufferedImage generateImage(byte[] spriteArray) throws IOException {
		
		InputStream arrayIn = new ByteArrayInputStream(spriteArray);
		
		BufferedImage spriteImage = null;
		spriteImage = ImageIO.read(arrayIn);
		
		return spriteImage;
	}
	
	// automates the insertion of the first 151 pokemon into the Pokebase database
	private void insertAllSprites() throws FileNotFoundException, SQLException {
		
		// an array to hold the 151 unique pokemonID's
		int[] pokemonIDs = new int[152];
		
		// pokemonIDs[0] is unused in order to match up the ID numbers with their respective index number
		pokemonIDs[0] = -1;
		
		// fills the array with the correct pokemonID numbers
		// there is not a pokemon with ID of 0 so it is unused
		for (int i = 1; i < 152; i++)
			pokemonIDs[i] = i;
		
		// fills an array with all of the pokemon names
		// pokemonNames[0] is unused in order to match a pokemon's ID number with its respective index
		String[] pokemonNames = new String[] {"not a pokemon", "bulbasaur", "ivysaur", "venusaur", "charmander",
				"charmeleon", "charizard", "squirtle", "wartortle", "blastoise", "caterpie", "metapod", "butterfree",
				"weedle", "kakuna", "beedrill", "pidgey", "pidgeotto", "pidgeot", "rattata", "raticate", "spearow",
				"fearow", "ekans", "arbok", "pikachu", "raichu", "sandshrew", "sandslash", "nidoran-f", "nidorina",
				"nidoqueen", "nidoran-m", "nidorino", "nidoking", "clefairy", "clefable", "vulpix", "ninetales",
				"jigglypuff", "wigglytuff", "zubat", "golbat", "oddish", "gloom", "vileplume", "paras", "parasect",
				"venonat", "venomoth", "diglett", "dugtrio", "meowth", "persian", "psyduck", "golduck", "mankey",
				"primeape", "growlithe", "arcanine", "poliwag", "poliwhirl", "poliwrath", "abra", "kadabra", "alakazam",
				"machop", "machoke", "machamp", "bellsprout", "weepinbell", "victreebel", "tentacool", "tentacruel",
				"geodude", "graverler", "golem", "ponyta", "rapidash", "slowpoke", "slowbro", "magnemite", "magneton",
				"farfetchd", "doduo", "dodrio", "seel", "dewgong", "grimer", "muk", "shellder", "cloyster", "gastly",
				"haunter", "gengar", "onix", "drowzee", "hypno", "krabby", "kingler", "voltorb", "electrode", "exeggcute",
				"exeggutor", "cubone", "marowak", "hitmonlee", "hitmonchan", "lickitung", "koffing", "weezing", "rhyhorn",
				"rhydon", "chansey", "tangela", "kangaskhan", "horsea", "seadra", "goldeen", "seaking", "staryu", "starmie",
				"mr-mime", "scyther", "jynx", "electabuzz", "magmar", "pinsir", "tauros", "magikarp", "gyarados","lapras",
				"ditto", "eevee", "vaporeon", "jolteon", "flareon", "porygon", "omanyte", "omastar", "kabuto", "kabutops", "aerodactyl",
				"snorlax", "articuno", "zapdos", "moltres", "dratini", "dragonair", "dragonite", "mewtwo", "mew"};
		
		// calls the insertSprites() method for actual inserting
		for (int i = 1; i < 152; i++)
			insertSprites(pokemonIDs[i], pokemonNames[i]);
		
	}
	
}
