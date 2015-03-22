import java.io.File;


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
	
}
