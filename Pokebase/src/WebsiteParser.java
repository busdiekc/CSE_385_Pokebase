import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;


// Author: Kyle Busdieker
// Last Edit: 03/22/2015
// Date Created: 12/13/14
// Purpose: Parse information about all the pokemon from www.pokebasedb.net

public class WebsiteParser {
	String urlStats;
	String urlWeightsHeights;
	String urlHabitats;
	String urlEvolutions;
	static Document htmlStatsFile;
	static Document htmlWeightsHeightsFile;
	static Document htmlHabitatFile;
	static Document htmlEvolutionsFile;
	static ArrayList<Pokemon> pokemonArray;

	// Constructor establishes connections to the webpages that data will be pulled from
	public WebsiteParser() throws IOException{
		urlStats = "http://pokemondb.net/pokedex/all";
		htmlStatsFile = Jsoup.connect(urlStats).get();
		urlWeightsHeights = "http://pokemondb.net/pokedex/stats/height-weight";
		htmlWeightsHeightsFile = Jsoup.connect(urlWeightsHeights).get();
		urlHabitats = "http://bulbapedia.bulbagarden.net/wiki/List_of_Pokémon_by_habitat";
		htmlHabitatFile = Jsoup.connect(urlHabitats).get();
		urlEvolutions = "http://pokemondb.net/evolution";
		htmlEvolutionsFile = Jsoup.connect(urlEvolutions).get();
		pokemonArray = new ArrayList<Pokemon>();
	}
	// retrieve the typeID from the database table Types to put in each pokemon's typeID variable
	static void getTypeIDS(ArrayList<Pokemon> pokemon) throws SQLException {
		ManipulateSprites ms = new ManipulateSprites("New_Pokebase.db");
		for (Pokemon p : pokemon) {
			Statement getType1ID = ms.c.createStatement();
			String query = "select typeid from types where typename = '" + p.type1Name + "'";
			ResultSet rs = getType1ID.executeQuery(query);
			p.type1ID = rs.getInt(1);
			if (!p.type2Name.contains("none")) {
				Statement getType2ID = ms.c.createStatement();
				String q = "select typeid from types where typename = '" + p.type2Name + "'";
				rs = getType2ID.executeQuery(q);
				p.type2ID = rs.getInt(1);
			}
		}
		ms.c.close();
	}

	static void urlStatsParser(Document statsFile) throws IOException {

		Elements table = statsFile.select("table");

		for (Element row : table.select("tr")) {

			Elements tds = row.select("td");

			if (!tds.isEmpty()) {

				Pokemon p = new Pokemon();

				p.pokemonPulledInfo = tds.text();

				String[] pStatsInfoSplit = p.pokemonPulledInfo.split(" ");

				if (pStatsInfoSplit.length == 10) {

					p.pokemonNum = Integer.parseInt(pStatsInfoSplit[0]);
					p.pokemonName = pStatsInfoSplit[1];
					p.type1Name = pStatsInfoSplit[2];
					p.type2Name = "none";
					p.totalPts = Integer.parseInt(pStatsInfoSplit[3]);
					p.hp = Integer.parseInt(pStatsInfoSplit[4]);
					p.atk = Integer.parseInt(pStatsInfoSplit[5]);
					p.def = Integer.parseInt(pStatsInfoSplit[6]);
					p.spAtk = Integer.parseInt(pStatsInfoSplit[7]);
					p.spDef = Integer.parseInt(pStatsInfoSplit[8]);
					p.spd = Integer.parseInt(pStatsInfoSplit[9]);
					
					// special case for Nidoran female
					if (Integer.parseInt(pStatsInfoSplit[0]) == 29) {
						
						p.pokemonName = "Nidoran, Female";
					}
					
					// special case for Nidoran male
					if (Integer.parseInt(pStatsInfoSplit[0]) == 32) {
						
						p.pokemonName = "Nidoran, Male";
					}

				}

				else if (pStatsInfoSplit.length == 11) {

					p.pokemonNum = Integer.parseInt(pStatsInfoSplit[0]);
					p.pokemonName = pStatsInfoSplit[1];
					p.type1Name = pStatsInfoSplit[2];
					p.type2Name = pStatsInfoSplit[3];
					p.totalPts = Integer.parseInt(pStatsInfoSplit[4]);
					p.hp = Integer.parseInt(pStatsInfoSplit[5]);
					p.atk = Integer.parseInt(pStatsInfoSplit[6]);
					p.def = Integer.parseInt(pStatsInfoSplit[7]);
					p.spAtk = Integer.parseInt(pStatsInfoSplit[8]);
					p.spDef = Integer.parseInt(pStatsInfoSplit[9]);
					p.spd = Integer.parseInt(pStatsInfoSplit[10]);
					
					if (p.pokemonPulledInfo.contains("Meowstic Male")) {
						
						p.pokemonName = "Meowstic, Male";
						p.type1Name = pStatsInfoSplit[3];
						p.type2Name = "none";
						p.totalPts = Integer.parseInt(pStatsInfoSplit[4]);
						p.hp = Integer.parseInt(pStatsInfoSplit[5]);
						p.atk = Integer.parseInt(pStatsInfoSplit[6]);
						p.def = Integer.parseInt(pStatsInfoSplit[7]);
						p.spAtk = Integer.parseInt(pStatsInfoSplit[8]);
						p.spDef = Integer.parseInt(pStatsInfoSplit[9]);
						p.spd = Integer.parseInt(pStatsInfoSplit[10]);
					}
					
					if (p.pokemonPulledInfo.contains("Meowstic Female")) {
						
						p.pokemonName = "Meowstic, Female";
						p.type1Name = pStatsInfoSplit[3];
						p.type2Name = "none";
						p.totalPts = Integer.parseInt(pStatsInfoSplit[4]);
						p.hp = Integer.parseInt(pStatsInfoSplit[5]);
						p.atk = Integer.parseInt(pStatsInfoSplit[6]);
						p.def = Integer.parseInt(pStatsInfoSplit[7]);
						p.spAtk = Integer.parseInt(pStatsInfoSplit[8]);
						p.spDef = Integer.parseInt(pStatsInfoSplit[9]);
						p.spd = Integer.parseInt(pStatsInfoSplit[10]);
					}

				}

				else if (pStatsInfoSplit.length == 12) {

					p.pokemonNum = Integer.parseInt(pStatsInfoSplit[0]);

					if (pStatsInfoSplit[3].compareTo(pStatsInfoSplit[1]) != 0)
						p.pokemonName = pStatsInfoSplit[1] + ", " + pStatsInfoSplit[2] + " " + pStatsInfoSplit[3];
					else
						p.pokemonName = pStatsInfoSplit[1] + ", " + pStatsInfoSplit[2];

					p.type1Name = pStatsInfoSplit[4];
					p.type2Name = "none";
					p.totalPts = Integer.parseInt(pStatsInfoSplit[5]);
					p.hp = Integer.parseInt(pStatsInfoSplit[6]);
					p.atk = Integer.parseInt(pStatsInfoSplit[7]);
					p.def = Integer.parseInt(pStatsInfoSplit[8]);
					p.spAtk = Integer.parseInt(pStatsInfoSplit[9]);
					p.spDef = Integer.parseInt(pStatsInfoSplit[10]);
					p.spd = Integer.parseInt(pStatsInfoSplit[11]);
					
					if (pStatsInfoSplit[1].contains("Kyurem")) {
						
						p.pokemonName = pStatsInfoSplit[1];
						p.type1Name = pStatsInfoSplit[3];
						p.type2Name = pStatsInfoSplit[4];
						p.totalPts = Integer.parseInt(pStatsInfoSplit[5]);
						p.hp = Integer.parseInt(pStatsInfoSplit[6]);
						p.atk = Integer.parseInt(pStatsInfoSplit[7]);
						p.def = Integer.parseInt(pStatsInfoSplit[8]);
						p.spAtk = Integer.parseInt(pStatsInfoSplit[9]);
						p.spDef = Integer.parseInt(pStatsInfoSplit[10]);
						p.spd = Integer.parseInt(pStatsInfoSplit[11]);

					}

					// mr. mime special case
					else if (pStatsInfoSplit[1].contains("Mr.")) {

						p.pokemonName = pStatsInfoSplit[1] + " " + pStatsInfoSplit[2];
						p.type1Name = pStatsInfoSplit[3];
						p.type2Name = pStatsInfoSplit[4];
						p.totalPts = Integer.parseInt(pStatsInfoSplit[5]);
						p.hp = Integer.parseInt(pStatsInfoSplit[6]);
						p.atk = Integer.parseInt(pStatsInfoSplit[7]);
						p.def = Integer.parseInt(pStatsInfoSplit[8]);
						p.spAtk = Integer.parseInt(pStatsInfoSplit[9]);
						p.spDef = Integer.parseInt(pStatsInfoSplit[10]);
						p.spd = Integer.parseInt(pStatsInfoSplit[11]);

					}

					// mime jr. special case
					else if (pStatsInfoSplit[1].contains("Mime")) {

						p.pokemonName = pStatsInfoSplit[1] + " " + pStatsInfoSplit[2];
						p.type1Name = pStatsInfoSplit[3];
						p.type2Name = pStatsInfoSplit[4];
						p.totalPts = Integer.parseInt(pStatsInfoSplit[5]);
						p.hp = Integer.parseInt(pStatsInfoSplit[6]);
						p.atk = Integer.parseInt(pStatsInfoSplit[7]);
						p.def = Integer.parseInt(pStatsInfoSplit[8]);
						p.spAtk = Integer.parseInt(pStatsInfoSplit[9]);
						p.spDef = Integer.parseInt(pStatsInfoSplit[10]);
						p.spd = Integer.parseInt(pStatsInfoSplit[11]);

					}

				}

				else if (pStatsInfoSplit.length == 13) {

					p.pokemonNum = Integer.parseInt(pStatsInfoSplit[0]);

					if (pStatsInfoSplit[3].compareTo(pStatsInfoSplit[1]) == 0)
						p.pokemonName = pStatsInfoSplit[1] + ", " + pStatsInfoSplit[2];
					else
						p.pokemonName = pStatsInfoSplit[1] + ", " + pStatsInfoSplit[2] + " " + pStatsInfoSplit[3];
					
					p.type1Name = pStatsInfoSplit[4];
					p.type2Name = pStatsInfoSplit[5];
					p.totalPts = Integer.parseInt(pStatsInfoSplit[6]);
					p.hp = Integer.parseInt(pStatsInfoSplit[7]);
					p.atk = Integer.parseInt(pStatsInfoSplit[8]);
					p.def = Integer.parseInt(pStatsInfoSplit[9]);
					p.spAtk = Integer.parseInt(pStatsInfoSplit[10]);
					p.spDef = Integer.parseInt(pStatsInfoSplit[11]);
					p.spd = Integer.parseInt(pStatsInfoSplit[12]);
					
					if (p.pokemonPulledInfo.contains("Mewtwo Y")) {
						
						p.pokemonName = "Mewtwo, Mega Y";
						p.type1Name = pStatsInfoSplit[5];
						p.type2Name = "none";
						p.totalPts = Integer.parseInt(pStatsInfoSplit[6]);
						p.hp = Integer.parseInt(pStatsInfoSplit[7]);
						p.atk = Integer.parseInt(pStatsInfoSplit[8]);
						p.def = Integer.parseInt(pStatsInfoSplit[9]);
						p.spAtk = Integer.parseInt(pStatsInfoSplit[10]);
						p.spDef = Integer.parseInt(pStatsInfoSplit[11]);
						p.spd = Integer.parseInt(pStatsInfoSplit[12]);
						
					}
					
					

				}

				else if (pStatsInfoSplit.length == 14) {

					p.pokemonNum = Integer.parseInt(pStatsInfoSplit[0]);

					if (pStatsInfoSplit[3].compareTo(pStatsInfoSplit[1]) == 0)
						p.pokemonName = pStatsInfoSplit[1] + ", " + pStatsInfoSplit[2] + " " + pStatsInfoSplit[4];
					else
						p.pokemonName = pStatsInfoSplit[1] + ", " + pStatsInfoSplit[2] + " " + pStatsInfoSplit[3] + " " + pStatsInfoSplit[4];
					
					p.type1Name = pStatsInfoSplit[5];
					p.type2Name = pStatsInfoSplit[6];
					p.totalPts = Integer.parseInt(pStatsInfoSplit[7]);
					p.hp = Integer.parseInt(pStatsInfoSplit[8]);
					p.atk = Integer.parseInt(pStatsInfoSplit[9]);
					p.def = Integer.parseInt(pStatsInfoSplit[10]);
					p.spAtk = Integer.parseInt(pStatsInfoSplit[11]);
					p.spDef = Integer.parseInt(pStatsInfoSplit[12]);
					p.spd = Integer.parseInt(pStatsInfoSplit[13]);
					
				}
				
				pokemonArray.add(p);
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 15) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 15;
					pp.pokemonName = "Beedrill, Mega";
					pp.type1Name = "Bug";
					pp.type2Name = "Poison";
					pp.totalPts = 495;
					pp.hp = 65;
					pp.atk = 150;
					pp.def = 40;
					pp.spAtk = 15;
					pp.spDef = 80;
					pp.spd = 145;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 18) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 18;
					pp.pokemonName = "Pidgeot, Mega";
					pp.type1Name = "Normal";
					pp.type2Name = "Flying";
					pp.totalPts = 579;
					pp.hp = 83;
					pp.atk = 80;
					pp.def = 80;
					pp.spAtk = 135;
					pp.spDef = 80;
					pp.spd = 121;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 80) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 80;
					pp.pokemonName = "Slowbro, Mega";
					pp.type1Name = "Water";
					pp.type2Name = "Psychic";
					pp.totalPts = 590;
					pp.hp = 95;
					pp.atk = 75;
					pp.def = 180;
					pp.spAtk = 130;
					pp.spDef = 80;
					pp.spd = 30;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 254) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 254;
					pp.pokemonName = "Sceptile, Mega";
					pp.type1Name = "Grass";
					pp.type2Name = "Dragon";
					pp.totalPts = 630;
					pp.hp = 70;
					pp.atk = 110;
					pp.def = 75;
					pp.spAtk = 145;
					pp.spDef = 85;
					pp.spd = 145;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 260) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 260;
					pp.pokemonName = "Swampert, Mega";
					pp.type1Name = "Water";
					pp.type2Name = "Ground";
					pp.totalPts = 635;
					pp.hp = 100;
					pp.atk = 150;
					pp.def = 110;
					pp.spAtk = 95;
					pp.spDef = 110;
					pp.spd = 70;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 302) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 302;
					pp.pokemonName = "Sableye, Mega";
					pp.type1Name = "Dark";
					pp.type2Name = "Ghost";
					pp.totalPts = 480;
					pp.hp = 50;
					pp.atk = 85;
					pp.def = 125;
					pp.spAtk = 85;
					pp.spDef = 115;
					pp.spd = 20;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 319) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 319;
					pp.pokemonName = "Sharpedo, Mega";
					pp.type1Name = "Water";
					pp.type2Name = "Dark";
					pp.totalPts = 560;
					pp.hp = 70;
					pp.atk = 140;
					pp.def = 70;
					pp.spAtk = 110;
					pp.spDef = 65;
					pp.spd = 105;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 323) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 323;
					pp.pokemonName = "Camerupt, Mega";
					pp.type1Name = "Fire";
					pp.type2Name = "Ground";
					pp.totalPts = 560;
					pp.hp = 70;
					pp.atk = 120;
					pp.def = 100;
					pp.spAtk = 145;
					pp.spDef = 105;
					pp.spd = 20;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 334) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 334;
					pp.pokemonName = "Altaria, Mega";
					pp.type1Name = "Dragon";
					pp.type2Name = "Fairy";
					pp.totalPts = 590;
					pp.hp = 75;
					pp.atk = 110;
					pp.def = 110;
					pp.spAtk = 110;
					pp.spDef = 105;
					pp.spd = 80;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 354) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 354;
					pp.pokemonName = "Banette, Mega";
					pp.type1Name = "Ghost";
					pp.type2Name = "none";
					pp.totalPts = 555;
					pp.hp = 64;
					pp.atk = 165;
					pp.def = 75;
					pp.spAtk = 93;
					pp.spDef = 83;
					pp.spd = 75;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 373) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 373;
					pp.pokemonName = "Salamence, Mega";
					pp.type1Name = "Dragon";
					pp.type2Name = "Flying";
					pp.totalPts = 700;
					pp.hp = 95;
					pp.atk = 145;
					pp.def = 130;
					pp.spAtk = 120;
					pp.spDef = 90;
					pp.spd = 120;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 376) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 376;
					pp.pokemonName = "Metagross, Mega";
					pp.type1Name = "Steel";
					pp.type2Name = "Psychic";
					pp.totalPts = 700;
					pp.hp = 80;
					pp.atk = 145;
					pp.def = 150;
					pp.spAtk = 105;
					pp.spDef = 110;
					pp.spd = 110;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 380) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 380;
					pp.pokemonName = "Latias, Mega";
					pp.type1Name = "Dragon";
					pp.type2Name = "Psychic";
					pp.totalPts = 700;
					pp.hp = 80;
					pp.atk = 100;
					pp.def = 120;
					pp.spAtk = 140;
					pp.spDef = 150;
					pp.spd = 110;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 381) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 381;
					pp.pokemonName = "Latios, Mega";
					pp.type1Name = "Dragon";
					pp.type2Name = "Psychic";
					pp.totalPts = 700;
					pp.hp = 80;
					pp.atk = 130;
					pp.def = 100;
					pp.spAtk = 160;
					pp.spDef = 120;
					pp.spd = 110;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 382) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 382;
					pp.pokemonName = "Kyogre, Primal";
					pp.type1Name = "Water";
					pp.type2Name = "none";
					pp.totalPts = 770;
					pp.hp = 100;
					pp.atk = 150;
					pp.def = 90;
					pp.spAtk = 180;
					pp.spDef = 160;
					pp.spd = 90;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 383) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 383;
					pp.pokemonName = "Groudon, Primal";
					pp.type1Name = "Ground";
					pp.type2Name = "Fire";
					pp.totalPts = 770;
					pp.hp = 100;
					pp.atk = 180;
					pp.def = 160;
					pp.spAtk = 150;
					pp.spDef = 90;
					pp.spd = 90;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 384) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 384;
					pp.pokemonName = "Rayquaza, Primal";
					pp.type1Name = "Dragon";
					pp.type2Name = "Flying";
					pp.totalPts = 780;
					pp.hp = 105;
					pp.atk = 180;
					pp.def = 100;
					pp.spAtk = 180;
					pp.spDef = 100;
					pp.spd = 115;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 428) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 428;
					pp.pokemonName = "Lopunny, Mega";
					pp.type1Name = "Normal";
					pp.type2Name = "Fighting";
					pp.totalPts = 580;
					pp.hp = 65;
					pp.atk = 136;
					pp.def = 94;
					pp.spAtk = 54;
					pp.spDef = 96;
					pp.spd = 135;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 475) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 475;
					pp.pokemonName = "Gallade, Mega";
					pp.type1Name = "Psychic";
					pp.type2Name = "Fighting";
					pp.totalPts = 618;
					pp.hp = 68;
					pp.atk = 165;
					pp.def = 95;
					pp.spAtk = 65;
					pp.spDef = 115;
					pp.spd = 110;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 531) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 531;
					pp.pokemonName = "Audino, Mega";
					pp.type1Name = "Normal";
					pp.type2Name = "Fairy";
					pp.totalPts = 545;
					pp.hp = 103;
					pp.atk = 60;
					pp.def = 126;
					pp.spAtk = 80;
					pp.spDef = 126;
					pp.spd = 50;
					
					pokemonArray.add(pp);
				}
				
				if (Integer.parseInt(pStatsInfoSplit[0]) == 719) {
					
					Pokemon pp = new Pokemon();
					pp.pokemonNum = 719;
					pp.pokemonName = "Diancie, Mega";
					pp.type1Name = "Rock";
					pp.type2Name = "Fairy";
					pp.totalPts = 700;
					pp.hp = 50;
					pp.atk = 160;
					pp.def = 110;
					pp.spAtk = 160;
					pp.spDef = 110;
					pp.spd = 110;
					
					pokemonArray.add(pp);
				}
				
			}
			
		}

	}
	// converts the a pokemon's height given in meters to inches
	static void convertHeights(ArrayList<Pokemon> pokemon) {
		for (Pokemon p : pokemon) {
			p.height = p.height * 39.3701;
			p.height = Math.round(p.height * 100.0) / 100.0;
		}
	}
	// takes the heights from the website and puts them in the pokemon objects
	static void getHeight(Document heightsWeightsFile) {
		Double[] heights = new Double[800];
		int counter = 0;
		Elements table = heightsWeightsFile.select("table");
		for (Element row : table.select("tr")) {
			Elements tds = row.select("td");
			if (!tds.isEmpty()) {
				String[] split = tds.text().split(" ");
				int i = 0;
				while (!split[i].contains("(")) {
					i++;
				}
				split[i] = split[i].replace("m", "");
				split[i] = split[i].substring(1);
				split[i] = split[i].substring(0, split[i].length()-1);
				heights[counter] = Double.parseDouble(split[i]);
			}
			counter++;
		}
		int index = 1;
		for (Pokemon p : pokemonArray) {
			p.height = heights[index];
			index++;
		}
	}
	// takes the weights from the website and puts them in the pokemon objects
	static void getWeight(Document heightsWeightsFile) {
		Double[] weights = new Double[800];
		int counter = 0;
		Elements table = heightsWeightsFile.select("table");
		for (Element row : table.select("tr")) {
			Elements tds = row.select("td");
			if (!tds.isEmpty()) {
				String[] split = tds.text().split(" ");
				int i = 0;
				while(!split[i].contains("(")) {
					i++;
				}
				weights[counter] = Double.parseDouble(split[i+1]);
			}
			counter++;
		}
		int index = 1;
		for (Pokemon p : pokemonArray) {
			p.weight = weights[index];
			index++;
		}
	}
	// work in progress
	// takes the evolution chains from the website and stores the pokemon that a particular pokemon evolves from
	public static void evolutionsParser(Document evolutionsFile) {
		String s = new String();
		Elements table = htmlEvolutionsFile.getElementsByClass("infocard-evo-list");
		s = table.text();
		String[] splitTemp = s.split(" ");
		ArrayList<String> textSplit = new ArrayList<String>();
		for (String st : splitTemp)
			textSplit.add(st);
		
		for (int i = 0; i < textSplit.size(); i++) {
			if (textSplit.get(i).contains("·"))
				textSplit.remove(i);
		}
		
		for (int i = 0; i < textSplit.size(); i++) {
			if (textSplit.get(i).contains("(") && !textSplit.get(i).contains(")")){
				if (textSplit.get(i+1).contains(")")) {
					String temp = textSplit.get(i);
					String temp2 = textSplit.get(i+1);
					textSplit.remove(i);
					textSplit.add(i, temp + " " + temp2);
					textSplit.remove(i+1);
				}
				else {
					int counter = 1;
					while (!textSplit.get(i + counter).contains(")")) {
						counter++;
					}
					for (int j = 1; j <= counter; j++){
						String temp = textSplit.get(i);
						String temp2 = textSplit.get(i+ 1);
						textSplit.remove(i);
						textSplit.add(i, temp + " " + temp2);
						textSplit.remove(i + 1);
					}
				}
			}
		}
		HashSet<String> types = new HashSet<String>();
		types.add("Normal");
		types.add("Fire");
		types.add("Water");
		types.add("Electric");
		types.add("Grass");
		types.add("Ice");
		types.add("Fighting");
		types.add("Poison");
		types.add("Ground");
		types.add("Flying");
		types.add("Psychic");
		types.add("Bug");
		types.add("Rock");
		types.add("Ghost");
		types.add("Dragon");
		types.add("Dark");
		types.add("Steel");
		types.add("Fairy");
		
		// has to run through the array twice because the first pass doesn't catch pokemon with two types
		for (int i = 0; i < textSplit.size(); i++) {
			if (types.contains(textSplit.get(i))) {
				textSplit.remove(i);
			}
		}
		for (int i = 0; i < textSplit.size(); i++) {
			if (types.contains(textSplit.get(i))) {
				textSplit.remove(i);
			}
		}
		
		
		for (int i = 0; i < textSplit.size(); i++)
			System.out.println(textSplit.get(i));
		
	}
	// runs the functions
	public static void main(String[] args) throws IOException, NumberFormatException, SQLException {

		// creates the url files to pull data from and the array of pokemon
		WebsiteParser wp = new WebsiteParser();
		
		
		//evolutionsParser(wp.htmlEvolutionsFile);
		
		
		// pulls number, name, type1name, type2name, and stats of pokemon
		urlStatsParser(wp.htmlStatsFile);
		
		// converts type1name and type2name into id for the database
		getTypeIDS(pokemonArray);
		
		// pulls height
		getHeight(wp.htmlWeightsHeightsFile);
		
		// pulls weight
		getWeight(wp.htmlWeightsHeightsFile);
	
		// convert heights from meters to inches
		convertHeights(pokemonArray);
		
		for (Pokemon p : pokemonArray) {
			System.out.println(p.pokemonNum + " " + p.pokemonName + " " + p.height + " " + p.weight + " " + p.type1ID + " " + p.type2ID + " " + p.habitatID);
		}
	}
}




