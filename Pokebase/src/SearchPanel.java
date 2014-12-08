import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
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
        
        
        setTableDefaults();
        AddListeners();
        setSearchTable();
        
        this.jButton2.setVisible(!searchMode);
        this.jButton3.setVisible(!searchMode);
        this.jButton4.setVisible(!searchMode);
    }
    
    void setTableDefaults() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
             @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column)
            {
                    JLabel label = new JLabel();

                    if (value!=null) {
                    label.setHorizontalAlignment(JLabel.CENTER);
                    //value is parameter which filled by byteOfImage
                        if(column == 2 && searchMode) {
                            label.setIcon((ImageIcon)value);
                        } else {
                            label.setText(value.toString());
                        }
                    }
                    
                    if(hasFocus || isSelected) {
                    	label.setOpaque(true);
                    	label.setBackground(Color.red);
                    } else {
                    	label.setOpaque(false);
                    	label.setBackground(Color.white);
                    }
                    
                    return label;
            }
        };
        jTable1.setSelectionBackground(Color.red);
        
        for(int i=0; i<jTable1.getColumnCount(); i++){
         jTable1.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        }
        
    }
    
    void AddListeners() {
    		jTextField1.addMouseListener(new MouseListener() {
    			@Override
    			public void mouseClicked(MouseEvent evt) {
    				jTextField1.setText("");
    			}
    			public void mouseEntered(MouseEvent e) {}
    			public void mouseExited(MouseEvent e) {}
				public void mousePressed(MouseEvent arg0) {}
				public void mouseReleased(MouseEvent e) {}
    		});
    	
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
        this.jTable1.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("Sprite");
        this.jTable1.getTableHeader().getColumnModel().getColumn(3).setHeaderValue("Type 1");
        this.jTable1.getTableHeader().getColumnModel().getColumn(4).setHeaderValue("Type 2");
        this.jTable1.getTableHeader().getColumnModel().getColumn(5).setHeaderValue("Height (inches)");
        this.jTable1.getTableHeader().getColumnModel().getColumn(6).setHeaderValue("Weight (pounds)");
        this.jTable1.getTableHeader().getColumnModel().getColumn(7).setHeaderValue("Habitat");
        this.jTable1.getTableHeader().getColumnModel().getColumn(8).setHeaderValue("Evolves From");
        
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
                    new ImageIcon((byte[])getAllPokemon.getObject("Picture")),
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
        this.jTable1.getTableHeader().getColumnModel().getColumn(8).setHeaderValue("");
        
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 0, 51));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Show All", "Name", "Type", "Habitat", "Number" }));

        jTextField1.setText("Search");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setBackground(java.awt.Color.white);
        jButton1.setText("Search Pokemon");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(java.awt.Color.white);
        jButton2.setText("Add Team");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(java.awt.Color.white);
        jButton3.setText("Remove Pokemon");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(java.awt.Color.white);
        jButton4.setText("Remove Team");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(java.awt.Color.white);
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
                "Number", "Name", "Sprite", "Type 1", "Type 2", "Height", "Weight", "Habitat", "Evolves From"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(75);
        jTable1.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jTable1.setShowVerticalLines(false);
        jTable1.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(jTable1);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5), null));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon_starters.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/English_Pokemon_logo.svg.png"))); // NOI18N
        jLabel1.setMaximumSize(new java.awt.Dimension(500, 200));
        jLabel1.setMinimumSize(new java.awt.Dimension(500, 200));
        jLabel1.setPreferredSize(new java.awt.Dimension(500, 200));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(78, 78, 78))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 377, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addGap(26, 26, 26))
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
                    new ImageIcon((byte[])tableResults.getObject("Picture")),
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
            if(jTable1.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(null, "Please select a team to add to!", null, JOptionPane.ERROR_MESSAGE);
            } else {
        	String teamName = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 0);
                
                String pokemonName = JOptionPane.showInputDialog(null, "Please enter pokemon name: ");
                std.addPokemonToTeam(teamName, pokemonName);
            
                refreshTeamTable();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    	
    	String response = JOptionPane.showInputDialog(null, "Please enter the name of the team: ");
    
    	
    	std.addTeam(response);
    	
    	refreshTeamTable();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(jTable1.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Please select a team to remove from!", null, JOptionPane.ERROR_MESSAGE);
        } else {
        
        String teamName = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 0);
        String pokemonName = JOptionPane.showInputDialog(null, "Please enter the name of the pokemon to remove from "+teamName);
        
        std.removePokemonFromTeam(teamName, pokemonName);
        refreshTeamTable();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    	if(jTable1.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Please select a team to remove!", null, JOptionPane.ERROR_MESSAGE);
        } else {
        
        String teamName = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 0);
        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "+teamName+"?");
    	if (response == 0) {
            std.removeTeam(teamName);
        }
    	refreshTeamTable();
        }
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
