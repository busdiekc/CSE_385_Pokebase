import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Drew Jenney
 */
public class StandardQueries {
    
    Connection conn;
    
    public StandardQueries() throws ClassNotFoundException, SQLException {
        // register the driver 
        String sDriverName = "org.sqlite.JDBC";
        Class.forName(sDriverName);
        String sDbUrl = "jdbc:sqlite:Pokebase.db";
        conn = DriverManager.getConnection(sDbUrl);
        
        
        //Statement s = conn.createStatement();
        //ResultSet r;
        //for(int i=1; i < 331; i++){
        // 	r = s.executeQuery("select name from pokemon where id = "+i);
        //	s.execute("update swap set name = '"+r.getString("Name").replace("'", "''")+"' where pokemonid = "+i);
        //}
    }
    
    /* example of a query on the database
        Statement search = std.conn.createStatement();
        ResultSet Machoke = search.executeQuery(std.NameSearch+"'Machoke'");
        
        System.out.print(Machoke.getString("Name"));
        */
    
    ResultSet getGeneralInfo() {
    	try {
    		Statement search = this.conn.createStatement();
    		String query = "SELECT DISTINCT ID, Picture, PokemonName, Type1Name, Type2Name, Height, Weight, Hab, evolvesFrom "
    		+"FROM (pokemon "
    		+"LEFT OUTER JOIN (SELECT TypeID AS T1, Name AS Type1Name FROM types) on type1 = t1 "
    		+"LEFT OUTER JOIN (SELECT TypeID AS T2, Name AS Type2Name FROM Types) ON type2 = t2 "
    		+"NATURAL JOIN (SELECT Name AS Hab, HabitatID FROM Habitats) "
    		+"LEFT OUTER JOIN (SELECT id as tempid, evolvesfrom from pokemon "
    		+"LEFT OUTER JOIN (SELECT evolvedid, name as evolvesfrom from pokemon, evolutions WHERE pokemon.id = babyid) on id = evolvedid) on id = tempid "
    		+"JOIN (SELECT PokemonID, Name AS PokemonName, Picture FROM Sprites) ON PokemonID = Pokemon.ID AND PokemonName = Pokemon.Name)";
    		
    		return search.executeQuery(query);
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    
    /**
     * Searches the Pokemon table for the Pokemon with the specified name.
     * 
     * @param name		: the name of the Pokemon
     * @return
     */
    ResultSet searchName(String name) {
    	
    	try {
    		Statement search = this.conn.createStatement();
    		String query = "SELECT DISTINCT ID, Picture, PokemonName, Type1Name, Type2Name, Height, Weight, Hab, evolvesFrom "
    	    		+"FROM (pokemon "
    	    		+"LEFT OUTER JOIN (SELECT TypeID AS T1, Name AS Type1Name FROM types) on type1 = t1 "
    	    		+"LEFT OUTER JOIN (SELECT TypeID AS T2, Name AS Type2Name FROM Types) ON type2 = t2 "
    	    		+"NATURAL JOIN (SELECT Name AS Hab, HabitatID FROM Habitats) "
    	    		+"LEFT OUTER JOIN (SELECT id as tempid, evolvesfrom from pokemon "
    	    		+"LEFT OUTER JOIN (SELECT evolvedid, name as evolvesfrom from pokemon, evolutions WHERE pokemon.id = babyid) on id = evolvedid) on id = tempid "
    	    		+"JOIN (SELECT PokemonID, Name AS PokemonName, Picture FROM Sprites) ON PokemonID = Pokemon.ID AND PokemonName = Pokemon.Name) "
                    + "WHERE PokemonName LIKE '%"+ name.toLowerCase() + "%'";
    		
    		return search.executeQuery(query);
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    /**
     * Searches the Pokemon table for the Pokemon with the specified number.
     * 
     * @param num		: the Pokemon number
     * @return
     */
    ResultSet searchNumber(int num) {
    	try {
    		Statement search = this.conn.createStatement();
    		String query = "SELECT DISTINCT ID, Picture, PokemonName, Type1Name, Type2Name, Height, Weight, Hab, evolvesFrom "
    	    		+"FROM (pokemon "
    	    		+"LEFT OUTER JOIN (SELECT TypeID AS T1, Name AS Type1Name FROM types) on type1 = t1 "
    	    		+"LEFT OUTER JOIN (SELECT TypeID AS T2, Name AS Type2Name FROM Types) ON type2 = t2 "
    	    		+"NATURAL JOIN (SELECT Name AS Hab, HabitatID FROM Habitats) "
    	    		+"LEFT OUTER JOIN (SELECT id as tempid, evolvesfrom from pokemon "
    	    		+"LEFT OUTER JOIN (SELECT evolvedid, name as evolvesfrom from pokemon, evolutions WHERE pokemon.id = babyid) on id = evolvedid) on id = tempid "
    	    		+"JOIN (SELECT PokemonID, Name AS PokemonName, Picture FROM Sprites) ON PokemonID = Pokemon.ID AND PokemonName = Pokemon.Name) "
                                + "WHERE Pokemon.ID = " +num;
    		
    		return search.executeQuery(query);
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    /**
     * Searches the Pokemon table for the Pokemon with the specified type.
     * 
     * @param type		: the Pokemon type
     * @return
     */
    ResultSet searchType(String type) {
    	try {
    		Statement search = this.conn.createStatement();
    		String query = "SELECT DISTINCT ID, Picture, PokemonName, Type1Name, Type2Name, Height, Weight, Hab, evolvesFrom "
    	    		+"FROM (pokemon "
    	    		+"LEFT OUTER JOIN (SELECT TypeID AS T1, Name AS Type1Name FROM types) on type1 = t1 "
    	    		+"LEFT OUTER JOIN (SELECT TypeID AS T2, Name AS Type2Name FROM Types) ON type2 = t2 "
    	    		+"NATURAL JOIN (SELECT Name AS Hab, HabitatID FROM Habitats) "
    	    		+"LEFT OUTER JOIN (SELECT id as tempid, evolvesfrom from pokemon "
    	    		+"LEFT OUTER JOIN (SELECT evolvedid, name as evolvesfrom from pokemon, evolutions WHERE pokemon.id = babyid) on id = evolvedid) on id = tempid "
    	    		+"JOIN (SELECT PokemonID, Name AS PokemonName, Picture FROM Sprites) ON PokemonID = Pokemon.ID AND PokemonName = Pokemon.Name) "
                                + "WHERE Type1Name LIKE '%"+type.toLowerCase()+"%' OR Type2Name LIKE '%"+type.toLowerCase()+"%'";
    		
    		return search.executeQuery(query);
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    /**
     * Searches the Pokemon table for the Pokemon with the specified habitat.
     * 
     * @param type		: the Pokemon habitat
     * @return
     */
    ResultSet searchHabitat(String habitat) {
    	try {
    		Statement search = this.conn.createStatement();
    		String query = "SELECT DISTINCT ID, Picture, PokemonName, Type1Name, Type2Name, Height, Weight, Hab, evolvesFrom "
    	    		+"FROM (pokemon "
    	    		+"LEFT OUTER JOIN (SELECT TypeID AS T1, Name AS Type1Name FROM types) on type1 = t1 "
    	    		+"LEFT OUTER JOIN (SELECT TypeID AS T2, Name AS Type2Name FROM Types) ON type2 = t2 "
    	    		+"NATURAL JOIN (SELECT Name AS Hab, HabitatID FROM Habitats) "
    	    		+"LEFT OUTER JOIN (SELECT id as tempid, evolvesfrom from pokemon "
    	    		+"LEFT OUTER JOIN (SELECT evolvedid, name as evolvesfrom from pokemon, evolutions WHERE pokemon.id = babyid) on id = evolvedid) on id = tempid "
    	    		+"JOIN (SELECT PokemonID, Name AS PokemonName, Picture FROM Sprites) ON PokemonID = Pokemon.ID AND PokemonName = Pokemon.Name) "
                                + "WHERE Hab LIKE '%" +habitat.replace("'", "''") +"%'";
    		
    		return search.executeQuery(query);
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    ResultSet getTeamMembers(String teamName) {
    	try {
    		Statement st = this.conn.createStatement();
    		String query = "SELECT Name "
    				+ "FROM Pokemon "
    				+ "JOIN Teams ON ID = PokemonID "
    				+ "WHERE TeamID = ("
    				+ "SELECT teamID FROM teamNames where teamName = '" + teamName.replace("'", "''") + "')";
    		
    		return st.executeQuery(query);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    ResultSet getAllTeamInfo() {
    	try {
    		Statement st = this.conn.createStatement();
    		String query = "SELECT teamname, size FROM teamnames "
    				+ "LEFT OUTER JOIN (SELECT teamid as tid, COUNT(pokemonid) as size FROM teams GROUP BY teamid) on teamnames.teamid = tid";
    		
    		return st.executeQuery(query);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    void addPokemonToTeam (String teamName, String pokemonName) {
    	try {
    		Statement getTeamID = this.conn.createStatement();
    		String q = "SELECT teamid FROM teamnames WHERE teamname = '" + teamName + "'";
    		
    		ResultSet id = getTeamID.executeQuery(q);
    		int teamID = id.getInt(1);
    		
    		Statement getPokemonID = this.conn.createStatement();
    		String qqq = "SELECT id FROM pokemon where name = '" + pokemonName + "'";
    		
    		ResultSet pokemonid = getPokemonID.executeQuery(qqq);
    		int pokemonID = pokemonid.getInt(1);
    		
    		
    		Statement add = this.conn.createStatement();
    		String query = "INSERT INTO teams VALUES (" + teamID + ", " + pokemonID + ")";
    		
    		add.executeUpdate(query);
    		
    	} catch (Exception e) { 
    		e.printStackTrace();
    	}
    }
    
    void removePokemonFromTeam (String teamName, String pokemonName) {
    	try {
    		Statement getTeamID = this.conn.createStatement();
    		String q = "SELECT teamid FROM teamnames WHERE teamname = '" + teamName.replace("'", "''") + "'";
    		
    		ResultSet id = getTeamID.executeQuery(q);
    		int tid = id.getInt(1);
    		
    		
    		Statement getPokemonID = this.conn.createStatement();
    		String qq = "SELECT id FROM pokemon where name = '" + pokemonName + "'";
    		ResultSet pid = getPokemonID.executeQuery(qq);
    		int pokemonID = pid.getInt(1);
    		
    		
    		
    		Statement remove = this.conn.createStatement();
    		String query = "DELETE FROM teams WHERE teamID = " + tid + " AND " + "pokemonid = " + pokemonID;
    		
    		remove.executeUpdate(query);
    		
    	} catch (Exception e ) {
    		e.printStackTrace();
    	}
    }
    
    void addTeam (String name) {
    	try {
    		Statement add = this.conn.createStatement();
    		String query = "INSERT INTO teamnames (teamname) VALUES ('" + name.replace("'", "''") + "')";
    		
    		add.executeUpdate(query);
    		
    	} catch (Exception e) { 
    		e.printStackTrace();
    	}
    }
    
    void removeTeam (String name) {
    	try {
    		Statement findTeamID = this.conn.createStatement();
    		String q = "SELECT teamid FROM teamnames WHERE teamname = '" + name.replace("'", "''") + "'";
    		
    		ResultSet teamid = findTeamID.executeQuery(q);
    		int id = teamid.getInt(1);
    		
    		Statement deletePokemonFromTeam = this.conn.createStatement();
    		String qq = "DELETE FROM teams WHERE teamid = " + id;
    		
    		deletePokemonFromTeam.executeUpdate(qq);
    		
    		
    		
    		Statement remove = this.conn.createStatement();
    		String query = "DELETE FROM teamnames WHERE teamnames.teamid = " + id;
    		
    		remove.executeUpdate(query);
    		
    	} catch (Exception e ) {
    		e.printStackTrace();
    	}
    }

}
