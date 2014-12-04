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
		
		File pic = new File("C:/PokemonSprites/" + pokemonName + ".png");
		File shinyPic = new File("C:/PokemonSprites/" + pokemonName + "2.png");
		
		InputStream picStream = new FileInputStream(pic);
		InputStream shinyPicStream = new FileInputStream(shinyPic);
		
		ps = c.prepareStatement("insert into sprites (PokemonID, Picture, ShinyPicture) " + "values (?, ?, ?)");
		
		ps.setInt(1, pokemonID);
		ps.setBinaryStream(2, picStream, (int) pic.length());
		ps.setBinaryStream(3, shinyPicStream, (int) shinyPic.length());
		
		ps.executeUpdate();
		
	}
	
	// used to retrieve just the sprite of a single pokemon
	public byte[] retrieveASprite(int pokemonID) throws SQLException {
		
		byte[] sprite = null;
		
		ps = c.prepareStatement("select picture from sprites where pokemonid = " + pokemonID);
		rs = ps.executeQuery();
		
		while (rs.next())
			sprite = rs.getBytes(1);
		
		return sprite;
	}
	
	// used to retrieve just the shiny sprite of a single pokemon
	public byte[] retrieveAShinySprite(int pokemonID) throws SQLException {
		
		byte[] shinySprite = null;
		
		ps = c.prepareStatement("select shinypicture from sprites where pokemonid = " + pokemonID);
		rs = ps.executeQuery();
		
		while(rs.next())
			shinySprite = rs.getBytes(1);
		
		return shinySprite;
	}
	
	// used to convert a retrieved pokemon's sprite or shiny sprite into a usable image
	public BufferedImage generateImage(byte[] spriteArray) throws IOException {
		
		InputStream arrayIn = new ByteArrayInputStream(spriteArray);
		
		BufferedImage spriteImage = null;
		spriteImage = ImageIO.read(arrayIn);
		
		return spriteImage;
	}
	
	// automates the insertion of the first 151 pokemon into the Pokebase database
	public void insertAllSprites() throws FileNotFoundException, SQLException {
		
		// an array to hold the 151 unique pokemonID's
		int[] pokemonIDs = new int[152];
		
		// pokemonIDs[0] is unused in order to match up the ID numbers with their respective index number
		pokemonIDs[0] = -1;
		
		// fills the array with the correct pokemonID numbers
		// there is not a pokemon with ID of 0 so it is unused
		for (int i = 1; i < 152; i++)
			pokemonIDs[i] = i+151;
		
		// fills an array with all of the pokemon names
		// pokemonNames[0] is unused in order to match a pokemon's ID number with its respective index
		String[] pokemonNames = new String[] {"not a pokemon", "chikorita", "bayleef", "meganium", "cyndaquil",
				"quilava", "typhlosion", "totodile", "croconaw", "feraligatr", "sentret", "furret", "hoothoot", "noctowl",
				"ledyba", "ledian", "spinarak", "ariados", "crobat", "chinchou", "lanturn", "pichu", "cleffa",
				"igglybuff", "togepi", "togetic", "natu", "xatu", "mareep", "flaafy", "ampharos", "bellossom",
				"marill", "azumarill", "sudowoodo", "politoed", "hoppip", "skiploom", "jumpluff", "aipom",
				"sunkern", "sunflora", "yanma", "wooper", "quagsire", "espeon", "umbreon", "murkrow", "slowking",
				"misdreavus", "unown", "wobbuffet", "girafarig", "pineco", "forretress", "dunsparce", "gligar", "steelix",
				"snubbull", "granbull", "qwillfish", "scizor", "shuckle", "heracross", "sneasel", "teddiursa", "ursaring",
				"slugma", "magcargo", "swinub", "piloswine", "corsola", "remoraid", "octillery", "delibird",
				"mantine", "skarmory", "houndour", "houndoom", "kingdra", "phanphy", "donphan", "porygon2", "stantler",
				"smeargle", "tyrogue", "hitmontop", "smoochum", "elekid", "magby", "miltank", "blissey", "raikou", "entei",
				"suicune", "larvitar", "pupitar", "tyranitar", "lugia", "ho-oh", "celebi"};
		
		// calls the insertSprites() method for actual inserting
		for (int i = 1; i < 152; i++)
			insertSprites(pokemonIDs[i], pokemonNames[i]);
		
	}
	
}
