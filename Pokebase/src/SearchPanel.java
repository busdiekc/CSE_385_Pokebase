import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Drew Jenney
 */
public class SearchPanel extends javax.swing.JPanel {

    StandardQueries std;
    boolean searchMode = true;
    Object[] SearchList = {"Show All", "Name", "Type", "Habitat", "Number",};
    Object[] TeamList = {};
    
    /**
     * Creates new form SearchPanel
     */
    public SearchPanel(StandardQueries std) {
        initComponents();
        this.std = std;
        
        AddListeners();
        setSearchTable();
        
        this.jButton2.setVisible(!searchMode);
        this.jButton3.setVisible(!searchMode);
        this.jButton4.setVisible(!searchMode);
    }
    
    void AddListeners() {
            jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if(evt.getClickCount() == 2 && searchMode) {
                        int row = jTable1.rowAtPoint(evt.getPoint());
                        int col = jTable1.columnAtPoint(evt.getPoint());
                        if (row >= 0 && col >= 0) {
                            new PokemonDetail(null, std, (int)jTable1.getModel().getValueAt(row, 0));
                        }
                    }
                }
            });
        
        for(Component comp : this.getComponents()) {
            comp.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                  if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                      jButton1.doClick();
                  }
                }

                @Override
                public void keyReleased(KeyEvent e) {}
            });
        }
    }
    
    // switch from Search to Team mode or vice versa
    public void changeMode() {
            if(searchMode) {
                searchMode = false;
                this.jButton5.setText("Search Page");
                this.jButton1.setText("Add Pokemon");
                this.jComboBox1.setModel(new DefaultComboBoxModel());
                setTeamTable();
            } else {
                searchMode = true;
                this.jButton5.setText("Teams Page");
                this.jButton1.setText("Search Pokemon");
                this.jComboBox1.setModel(new DefaultComboBoxModel(SearchList));
                setSearchTable();
            }
            
            this.jButton2.setVisible(!searchMode);
            this.jButton3.setVisible(!searchMode);
            this.jButton4.setVisible(!searchMode);
    }
    
    void setSearchTable() {

        
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Number"); 
        this.jTable1.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("Name");
        this.jTable1.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("Type 1");
        this.jTable1.getTableHeader().getColumnModel().getColumn(3).setHeaderValue("Type 2");
        this.jTable1.getTableHeader().getColumnModel().getColumn(4).setHeaderValue("Height (inches)");
        this.jTable1.getTableHeader().getColumnModel().getColumn(5).setHeaderValue("Weight (pounds)");
        this.jTable1.getTableHeader().getColumnModel().getColumn(6).setHeaderValue("Habitat");
        this.jTable1.getTableHeader().getColumnModel().getColumn(7).setHeaderValue("Evolves From");
        
        refreshSearchTable();
        repaint();
    }
    
    void refreshSearchTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            
            ResultSet getAllPokemon = std.getGeneralInfo();
        
            while(getAllPokemon.next()) {
                model.addRow(new Object[]{getAllPokemon.getInt("ID"), 
                    getAllPokemon.getObject("Name"), 
                    getAllPokemon.getObject("Type1Name"),
                    getAllPokemon.getObject("Type2Name"),
                    getAllPokemon.getObject("Height"),
                    getAllPokemon.getObject("Weight"),
                    getAllPokemon.getObject("Hab"),
                    getAllPokemon.getObject("EvolvesFrom")});
            }
        } catch(Exception ex) {
            System.err.printf(ex.getMessage());
          }
    }
    
    void setTeamTable() {
        
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Team Name"); 
        this.jTable1.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("Team Size");
        this.jTable1.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("Pokemon 1");
        this.jTable1.getTableHeader().getColumnModel().getColumn(3).setHeaderValue("Pokemon 2");
        this.jTable1.getTableHeader().getColumnModel().getColumn(4).setHeaderValue("Pokemon 3");
        this.jTable1.getTableHeader().getColumnModel().getColumn(5).setHeaderValue("Pokemon 4");
        this.jTable1.getTableHeader().getColumnModel().getColumn(6).setHeaderValue("Pokemon 5");
        this.jTable1.getTableHeader().getColumnModel().getColumn(7).setHeaderValue("Pokemon 6");
        
        refreshTeamTable();
        
        repaint();
    }
    
    void refreshTeamTable() {
    	try {
    		DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    		model.setRowCount(0);
    		
    		ResultSet allTeamInfo= std.getAllTeamInfo();
    		ResultSet members;
    		Object[] row;
    		
    		while (allTeamInfo.next()) {
    			
    			row = new Object[allTeamInfo.getInt("size") +2];
    			members = std.getTeamMembers((String) allTeamInfo.getObject("teamname"));
    			members.next();
    			
    			row[0] = allTeamInfo.getObject("teamname");
    			row[1] = allTeamInfo.getObject("size");
    			
    			for(int i = 0; i < allTeamInfo.getInt("size"); i++){
    				row[i+2] = members.getString("Name");
    				members.next();
    			}
    			
    			model.addRow(row);
    		}
    		
    		
    		
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({ "unchecked"})
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Show All", "Name", "Type", "Habitat", "Number" }));

        jTextField1.setText("Search");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Pokebase");

        jButton1.setText("Search Pokemon");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Add Team");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Remove Pokemon");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        
        jButton4.setText("Remove Team");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        

        jButton5.setText("Show Teams");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Number", "Name", "Type 1", "Type 2", "Height", "Weight", "Habitat", "Evolves From"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionBackground(new java.awt.Color(200, 100, 220));
        jTable1.setSurrendersFocusOnKeystroke(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 772, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    	
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(searchMode) {
            ResultSet tableResults;
            
            switch(jComboBox1.getSelectedIndex()) {
                case 1: //search by name
                    tableResults = std.searchName(jTextField1.getText());
                    break;
                case 2: //search by type
                    tableResults = std.searchType(jTextField1.getText());
                    break;
                case 3: //search by habitat
                    tableResults = std.searchHabitat(jTextField1.getText());
                    break;
                case 4: //search by number
                    tableResults = std.searchNumber(Integer.parseInt(jTextField1.getText()));
                    break;
                default: // show all
                    tableResults = std.getGeneralInfo();
            }
            //repopulate Table
            try {
                
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);
                while(tableResults.next()) {
                    model.addRow(new Object[]{tableResults.getInt("ID"), 
                    tableResults.getObject("Name"), 
                    tableResults.getObject("Type1Name"),
                    tableResults.getObject("Type2Name"),
                    tableResults.getObject("Height"),
                    tableResults.getObject("Weight"),
                    tableResults.getObject("Hab"),
                    tableResults.getObject("EvolvesFrom")
                    });
                }
                repaint();
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
            
        } else {
        	String teamName = JOptionPane.showInputDialog(null, "Please enter the team name: ");
        	String pokemonName = JOptionPane.showInputDialog(null, "Please enter pokemon name: ");
        	
            std.addPokemonToTeam(teamName, pokemonName);
            refreshTeamTable();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    	
    	String response = JOptionPane.showInputDialog(null, "Please enter the name of the team: ");
    
    	
    	std.addTeam(response);
    	
    	refreshTeamTable();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String teamName = JOptionPane.showInputDialog(null, "Please enter the name of the team: ");
        String pokemonName = JOptionPane.showInputDialog(null, "Please enter the name of the pokemon: ");
        
        std.removePokemonFromTeam(teamName, pokemonName);
        refreshTeamTable();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    	String response = JOptionPane.showInputDialog(null, "Please enter the name of the team to delete: ");
    	std.removeTeam(response);
    	refreshTeamTable();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        changeMode();
    }//GEN-LAST:event_jButton5ActionPerformed

    public JButton getjButton1() {
        return jButton1;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
