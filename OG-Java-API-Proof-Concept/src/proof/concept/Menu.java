/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proof.concept;

import OpenGlove.OpenGloveAPI;
import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfKeyValueOfintint.KeyValueOfintint;
import java.util.List;
import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfKeyValueOfstringstring.KeyValueOfstringstring;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.datacontract.schemas._2004._07.openglovewcf.Glove;
import websocketOG.Client;
import websocketOG.Client.FingerMovement;
import websocketOG.Client.allIMUValues;

/**
 *
 * @author RaySis
 */
public class Menu extends javax.swing.JFrame {
    
    private static OpenGloveAPI api;
    private List<Glove> gloves;
    private List<GloveRules> gloveRules;
    private Glove selectedGlove;
    FingerMovement vibeActivationByFlexor = (int region, int value) -> {
        activateRegion(selectedGlove, region, value);
    };
    allIMUValues imuShowData ;
    

    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        openGlove();
        gloveRules = new ArrayList<>();
        imuShowData = (float ax, float ay, float az, float gx, float gy, float gz, float mx, float my, float mz) -> {
                this.imuValues.setText("Accelerometer:  "+ax+",    "+ay+",     "+az);
            this.imuValuesGyro.setText("Gyroscope:      "+gx+",    "+gy+",     "+gz);
           this.imuValuesMagno.setText("Magnometer:     "+mx+",    "+my+",     "+mz);
        };
        
    }
    
    private void openGlove(){
        api = OpenGloveAPI.getInstance();
        gloves = api.getDevices();
        
        for(int i=0; i < gloves.size(); i++){
            if(gloves.get(i).isConnected() == true){
                jGlovesComboBox.addItem(gloves.get(i).getName().getValue());
            }        
        }
    }
    
    private void loadGloveComponents(Glove selectedGlove){
        List<KeyValueOfintint> flexMappings = selectedGlove.getGloveConfiguration().getValue().getGloveProfile().getValue().getFlexorsMappings().getValue().getKeyValueOfintint();
        jFlexSensorComboBox.removeAllItems();
        if(flexMappings != null){
            for (KeyValueOfintint item : flexMappings) {
            jFlexSensorComboBox.addItem(Integer.toString(item.getKey()));
            }     
        }
        
        List<KeyValueOfstringstring> mappings = selectedGlove.getGloveConfiguration().getValue().getGloveProfile().getValue().getMappings().getValue().getKeyValueOfstringstring();
        jVibeBoardSelect.removeAllItems();
        if(mappings != null){
            for (KeyValueOfstringstring item : mappings) {
            jVibeBoardSelect.addItem(item.getKey());
            }     
        }    
    }
    
    private void activateRegion(Glove selectedGlove, int flexorMapping, int flexorValue){
        if(selectedGlove==null){
            return;
        }
        for(Parameters params : getGloveRules(selectedGlove)){
            if(flexorMapping == params.getFlexorIndex()){
                if(flexorValue >= params.getActivationValue()){
                    api.Activate(selectedGlove, params.getVibeBoardIndex(), 255);
                }else{
                    api.Activate(selectedGlove, params.getVibeBoardIndex(), 0);
                }
            }
        }
    }
    
    private List<Parameters> getGloveRules(Glove g){
        for(GloveRules gRules : gloveRules){
            if(gRules.GloveName.equals(g.getBluetoothAddress().getValue())){
                return gRules.parameters;
            }
        }
        return null;
    }
      
    
    private void updateGloveRules(Glove selectedGlove){
        
        DefaultTableModel model = (DefaultTableModel) jRulesTable.getModel();
        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        if (gloveRules != null){
            for (GloveRules gRules : gloveRules){
                if(selectedGlove.getBluetoothAddress().getValue().equals(gRules.getGloveName())){
                    for(Parameters params : gRules.parameters){
                        model.addRow(new Object[]{params.getFlexorIndex(), params.getVibeBoardIndex(), params.getActivationValue()});
                    }
                }
            }
        }
    }
    
    private void setRuleValue(int row, int column, int value){
        DefaultTableModel model = (DefaultTableModel) jRulesTable.getModel();
        model.setValueAt(value, row, column);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jLabel1 = new javax.swing.JLabel();
        jGlovesComboBox = new javax.swing.JComboBox<>();
        jAvailableLabel = new javax.swing.JLabel();
        jFlexSensorComboBox = new javax.swing.JComboBox<>();
        jAvailableLabel1 = new javax.swing.JLabel();
        jVibeBoardLabel = new javax.swing.JLabel();
        jVibeBoardSelect = new javax.swing.JComboBox<>();
        jActivationLimitSpinner = new javax.swing.JSpinner();
        jAvailableLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jRulesTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jSelectedGloveLabel = new javax.swing.JLabel();
        jSelectGloveButton = new javax.swing.JButton();
        jAddButton = new javax.swing.JButton();
        jRemoveRuleButton = new javax.swing.JButton();
        jRefreshButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        imuValues = new javax.swing.JLabel();
        imuValuesGyro = new javax.swing.JLabel();
        imuValuesMagno = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OpenGlove Test");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("OpenGlove Test");

        jAvailableLabel.setText("Available Gloves");

        jFlexSensorComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFlexSensorComboBoxActionPerformed(evt);
            }
        });

        jAvailableLabel1.setText("Flex Sensor");

        jVibeBoardLabel.setText("VibeBoard");

        jVibeBoardSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jVibeBoardSelectActionPerformed(evt);
            }
        });

        jActivationLimitSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        jActivationLimitSpinner.setName(""); // NOI18N

        jAvailableLabel3.setText("Activation limit ");

        jRulesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Flex Sensor", "VibeBoard", "Activation Value"
            }
        ));
        jRulesTable.setName(""); // NOI18N
        jScrollPane2.setViewportView(jRulesTable);

        jLabel2.setText("Selected Glove:");

        jSelectedGloveLabel.setText("None");

        jSelectGloveButton.setText("Select");
        jSelectGloveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSelectGloveButtonActionPerformed(evt);
            }
        });

        jAddButton.setText("Add");
        jAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAddButtonActionPerformed(evt);
            }
        });

        jRemoveRuleButton.setText("Remove");
        jRemoveRuleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRemoveRuleButtonActionPerformed(evt);
            }
        });

        jRefreshButton.setText("Refresh");
        jRefreshButton.setAutoscrolls(true);
        jRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRefreshButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("IMU DATA");

        imuValues.setText("IMU Desactivated");

        imuValuesGyro.setText("-");

        imuValuesMagno.setText("-");

        jLabel4.setText("X");

        jLabel5.setText("Y");

        jLabel6.setText("Z");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(117, 117, 117)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jAvailableLabel)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jGlovesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)
                                        .addComponent(jSelectGloveButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jRefreshButton))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane2)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jFlexSensorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jAvailableLabel1))
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jVibeBoardSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jVibeBoardLabel))
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jAvailableLabel3)
                                                .addComponent(jActivationLimitSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jRemoveRuleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(imuValuesMagno, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSelectedGloveLabel))
                                    .addComponent(imuValues, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(imuValuesGyro, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addComponent(jAvailableLabel)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jGlovesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSelectGloveButton)
                    .addComponent(jRefreshButton))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jSelectedGloveLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jActivationLimitSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jVibeBoardSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFlexSensorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jAddButton)
                    .addComponent(jRemoveRuleButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jAvailableLabel3)
                    .addComponent(jVibeBoardLabel)
                    .addComponent(jAvailableLabel1))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imuValues)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imuValuesGyro, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imuValuesMagno)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void jFlexSensorComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFlexSensorComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFlexSensorComboBoxActionPerformed

    private void jVibeBoardSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jVibeBoardSelectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jVibeBoardSelectActionPerformed

    private void jSelectGloveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSelectGloveButtonActionPerformed
        // TODO add your handling code here:
        for(int i=0; i < gloves.size(); i++){
            if(gloves.get(i).getBluetoothAddress().getValue().equals(jGlovesComboBox.getSelectedItem())){
                selectedGlove = gloves.get(i);
                jSelectedGloveLabel.setText(selectedGlove.getBluetoothAddress().getValue());
                loadGloveComponents(selectedGlove);
                updateGloveRules(selectedGlove);
            }  
        }
        
       // if(api.isCapturingData(selectedGlove)!=true){
            api.startCaptureData(selectedGlove);
            api.getDataReceiver(selectedGlove).WebSocketClient.setFingerMovement(vibeActivationByFlexor);
            api.getDataReceiver(selectedGlove).WebSocketClient.setAllIMUValues(imuShowData);
      //  }
    }//GEN-LAST:event_jSelectGloveButtonActionPerformed

    private void jAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAddButtonActionPerformed
        // TODO add your handling code here:
        GloveRules selectedRules = null;      
        if(selectedGlove!=null){
            if(gloveRules!=null){
                for (GloveRules gRules : gloveRules){
                    if(gRules.GloveName.equals(selectedGlove.getBluetoothAddress().getValue())){
                        selectedRules = gRules;
                        Parameters newParam = new Parameters();
                        newParam.setFlexorIndex(Integer.parseInt(jFlexSensorComboBox.getSelectedItem().toString()));
                        newParam.setVibeBoardIndex(Integer.parseInt(jVibeBoardSelect.getSelectedItem().toString()));
                        newParam.setActivationValue(Integer.parseInt(jActivationLimitSpinner.getValue().toString()));
                        selectedRules.parameters.add(newParam);
                        break;
                    }
                }
                
                if(selectedRules == null){
                    selectedRules = new GloveRules();
                    selectedRules.setGloveName(selectedGlove.getBluetoothAddress().getValue());
                    Parameters newParam = new Parameters();
                    newParam.setFlexorIndex(Integer.parseInt(jFlexSensorComboBox.getSelectedItem().toString()));
                    newParam.setVibeBoardIndex(Integer.parseInt(jVibeBoardSelect.getSelectedItem().toString()));
                    newParam.setActivationValue(Integer.parseInt(jActivationLimitSpinner.getValue().toString()));
                    selectedRules.parameters.add(newParam);
                    gloveRules.add(selectedRules);
                }
            }
            
            updateGloveRules(selectedGlove);
        } 
    }//GEN-LAST:event_jAddButtonActionPerformed
    
    private void jRemoveRuleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRemoveRuleButtonActionPerformed

        //DefaultTableModel model = (DefaultTableModel) jRulesTable.getModel();
       // model.removeRow(jRulesTable.getSelectedRow());
       if(selectedGlove!=null){
           if(gloveRules!=null){
               for(GloveRules gRules : gloveRules){
                    if(gRules.GloveName.equals(selectedGlove.getBluetoothAddress().getValue())){
                        int i=0;
                        for(Parameters params : gRules.parameters){
                            if(params.getFlexorIndex() == Integer.parseInt(jFlexSensorComboBox.getSelectedItem().toString()) && params.getVibeBoardIndex() == Integer.parseInt(jVibeBoardSelect.getSelectedItem().toString())){
                               gRules.parameters.remove(i);
                            }
                            i++;
                        }

                    }
                }
                updateGloveRules(selectedGlove); 
           }    
       }
       
    }//GEN-LAST:event_jRemoveRuleButtonActionPerformed

    private void jRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRefreshButtonActionPerformed
        gloves = api.getDevices();
    }//GEN-LAST:event_jRefreshButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel imuValues;
    private javax.swing.JLabel imuValuesGyro;
    private javax.swing.JLabel imuValuesMagno;
    private javax.swing.JSpinner jActivationLimitSpinner;
    private javax.swing.JButton jAddButton;
    private javax.swing.JLabel jAvailableLabel;
    private javax.swing.JLabel jAvailableLabel1;
    private javax.swing.JLabel jAvailableLabel3;
    private javax.swing.JComboBox<String> jFlexSensorComboBox;
    private javax.swing.JComboBox<String> jGlovesComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JButton jRefreshButton;
    private javax.swing.JButton jRemoveRuleButton;
    private javax.swing.JTable jRulesTable;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jSelectGloveButton;
    private javax.swing.JLabel jSelectedGloveLabel;
    private javax.swing.JLabel jVibeBoardLabel;
    private javax.swing.JComboBox<String> jVibeBoardSelect;
    // End of variables declaration//GEN-END:variables
}