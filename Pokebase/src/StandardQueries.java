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
    }
    
    /* example of a query on the database
        Statement search = std.conn.createStatement();
        ResultSet Machoke = search.executeQuery(std.NameSearch+"'Machoke'");
        
        System.out.print(Machoke.getString("Name"));
        */
    
    ResultSet getGeneralInfo() {
    	try {
    		Statement search = this.conn.createStatement();
    		String query = "SELECT ID, Name, Type1Name, Type2Name, Height, Weight, Hab, evolvesFrom "
    				+ "FROM pokemon "
    				+ "LEFT OUTER JOIN (SELECT TypeID AS T1, Name AS Type1Name FROM types) on type1 = t1 "
    				+ "LEFT OUTER JOIN (SELECT TypeID AS T2, Name AS Type2Name FROM Types) ON type2 = t2 "
    				+ "NATURAL JOIN (SELECT Name AS Hab, HabitatID FROM Habitats) "
    				+ "LEFT OUTER JOIN (SELECT id as tempid, evolvesfrom from pokemon "
    				+ "LEFT OUTER JOIN (SELECT evolvedid, name as evolvesfrom from pokemon, evolutions WHERE pokemon.id = babyid) on id = evolvedid) on id = tempid";
    		
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
    		String query = "SELECT ID, Name, Type1Name, Type2Name, Height, Weight, Hab, evolvesFrom "
    				+ "FROM pokemon "
    				+ "LEFT OUTER JOIN (SELECT TypeID AS T1, Name AS Type1Name FROM types) on type1 = t1 "
    				+ "LEFT OUTER JOIN (SELECT TypeID AS T2, Name AS Type2Name FROM Types) ON type2 = t2 "
    				+ "NATURAL JOIN (SELECT Name AS Hab, HabitatID FROM Habitats) "
    				+ "LEFT OUTER JOIN (SELECT id as tempid, evolvesfrom from pokemon "
    				+ "LEFT OUTER JOIN (SELECT evolvedid, name as evolvesfrom from pokemon, evolutions WHERE pokemon.id = babyid) on id = evolvedid) on id = tempid "
                                + "WHERE Name LIKE '%"+ name + "%'";
    		
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
    		String query = "SELECT ID, Name, Type1Name, Type2Name, Height, Weight, Hab, evolvesFrom "
    				+ "FROM pokemon "
    				+ "LEFT OUTER JOIN (SELECT TypeID AS T1, Name AS Type1Name FROM types) on type1 = t1 "
    				+ "LEFT OUTER JOIN (SELECT TypeID AS T2, Name AS Type2Name FROM Types) ON type2 = t2 "
    				+ "NATURAL JOIN (SELECT Name AS Hab, HabitatID FROM Habitats) "
    				+ "LEFT OUTER JOIN (SELECT id as tempid, evolvesfrom from pokemon "
    				+ "LEFT OUTER JOIN (SELECT evolvedid, name as evolvesfrom from pokemon, evolutions WHERE pokemon.id = babyid) on id = evolvedid) on id = tempid "
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
    		String query = "SELECT ID, Name, Type1Name, Type2Name, Height, Weight, Hab, evolvesFrom "
    				+ "FROM pokemon "
    				+ "LEFT OUTER JOIN (SELECT TypeID AS T1, Name AS Type1Name FROM types) on type1 = t1 "
    				+ "LEFT OUTER JOIN (SELECT TypeID AS T2, Name AS Type2Name FROM Types) ON type2 = t2 "
    				+ "NATURAL JOIN (SELECT Name AS Hab, HabitatID FROM Habitats) "
    				+ "LEFT OUTER JOIN (SELECT id as tempid, evolvesfrom from pokemon "
    				+ "LEFT OUTER JOIN (SELECT evolvedid, name as evolvesfrom from pokemon, evolutions WHERE pokemon.id = babyid) on id = evolvedid) on id = tempid "
                                + "WHERE Type1Name = '"+type+"' OR Type2Name = '"+type+"'";
    		
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
    		String query = "SELECT ID, Name, Type1Name, Type2Name, Height, Weight, Hab, evolvesFrom "
    				+ "FROM pokemon "
    				+ "LEFT OUTER JOIN (SELECT TypeID AS T1, Name AS Type1Name FROM types) on type1 = t1 "
    				+ "LEFT OUTER JOIN (SELECT TypeID AS T2, Name AS Type2Name FROM Types) ON type2 = t2 "
    				+ "NATURAL JOIN (SELECT Name AS Hab, HabitatID FROM Habitats) "
    				+ "LEFT OUTER JOIN (SELECT id as tempid, evolvesfrom from pokemon "
    				+ "LEFT OUTER JOIN (SELECT evolvedid, name as evolvesfrom from pokemon, evolutions WHERE pokemon.id = babyid) on id = evolvedid) on id = tempid "
                                + "WHERE Hab LIKE '%" +habitat +"%'";
    		
    		return search.executeQuery(query);
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
}
