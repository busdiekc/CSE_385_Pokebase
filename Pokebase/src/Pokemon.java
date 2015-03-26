import java.io.File;
import java.util.ArrayList;

//last test
public class Pokemon {
	
	String pokemonPulledInfo;
	int pokemonNum;
	String pokemonName;
	String type1Name;
	int type1ID;
	String type2Name;
	int type2ID;
	int totalPts;
	int hp;
	int atk;
	int def;
	int spAtk;
	int spDef;
	int spd;
	String habitatName;
	int habitatID;
	double height;
	double weight;
	File sprite;
	File shinySprite;
	String evolvesFrom;
	String evolutionMethod;
	
	
	public Pokemon() {
		
		pokemonPulledInfo = null;
		pokemonNum = -1;
		pokemonName = null;
		type1Name = null;
		type1ID = -1;
		type2Name = null;
		type2ID = -1;
		totalPts = -1;
		hp = -1;
		atk = -1;
		def = -1;
		spAtk = -1;
		spDef = -1;
		spd = -1;
		habitatName = null;
		habitatID = -1;
		height = -1;
		weight = -1;
		sprite = null;
		shinySprite = null;
		evolvesFrom = null;
		evolutionMethod = null;
	}
	// print all information for all pokemon in the array argument
	public static void printPokemonArray(ArrayList<Pokemon> array) {
		System.out.printf("%-6s %-26s %-10s %-9s %-10s %-9s %-8s %-5s %-5s %-5s %-5s %-5s %-5s %-11s %-10s %-7s %-7s %-15s %-10s\n", "Number", "Name", "Type1Name", "Type1ID", "Type2Name", 
				"Type2ID", "TotalPts", "HP", "Atk", "Def", "SpAtk", "SpDef", "Spd", "HabitatName", "HabitatID", "Weight", 
				"Height", "EvolvesFrom", "Method");
		for (Pokemon p : array) {
			System.out.printf("%-6d %-26s %-10s %-9d %-10s %-9d %-8d %-5d %-5d %-5d %-5d %-5d %-5d %-11s %-10d %-7.2f %-7.2f %-15s %-10s\n", p.pokemonNum, p.pokemonName, p.type1Name, p.type1ID, p.type2Name, p.type2ID, p.totalPts, p.hp, p.atk, p.def, p.spAtk, p.spDef, p.spd, p.habitatName, p.habitatID, p.weight, p.height, p.evolvesFrom, p.evolutionMethod);
		}
		
	}
	
}
