/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini.gui;

import java.awt.Adjustable;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;

/**
 *
 * @author louismccallum
 */
public class PlotRowPanel extends javax.swing.JPanel {

    /**
     * Creates new form PlotRowPanel
     */
    
    public PlotRowModel model;
    private String[] outputs = new String[0];
    private String[] features = new String[0];
    private String state;
    private PlotRowDelegate delegate;
    private PlotPanel plotPanel;
    private boolean ignoreDelegate = false;
    private static final int PLOT_W = 600;
    private static final int PLOT_H = 70;
    
    public PlotRowPanel(String[] outputs, String[] features, PlotRowDelegate delegate) {
        initComponents();
        this.delegate = delegate;
        this.outputs = outputs;
        this.features = features;
        for(String output : outputs)
        {
            outputComboBox.addItem(output);
        }
        
        for(String feature : features)
        {
            featureComboBox.addItem(feature);
        }
        plotPanel = new PlotPanel(PLOT_W, PLOT_H, 100);        
        plotScrollPanel.setViewportView(plotPanel);
        plotScrollPanel.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        AdjustmentListener listener = new MyAdjustmentListener();
        plotScrollPanel.getHorizontalScrollBar().addAdjustmentListener(listener);
    }
    
    public void updateModel(PlotRowModel model)
    {
        this.model = model;
        plotPanel.updateModel(model);
        
        plotPanel.updateWidth(model.isStreaming);
        repaint();
        plotScrollPanel.revalidate();
        validate();
        plotScrollPanel.setViewportView(plotPanel);
        plotScrollPanel.revalidate();
        validate();
        
        ignoreDelegate = true;
        outputComboBox.setSelectedIndex(model.pathIndex);
        featureComboBox.setSelectedIndex(model.feature.getOutputIndexes()[0]);
        liveToggle.setSelected(model.isStreaming);
        ignoreDelegate = false;

    }
    
    class MyAdjustmentListener implements AdjustmentListener {
        public void adjustmentValueChanged(AdjustmentEvent evt) {
          Adjustable source = evt.getAdjustable();
          if (evt.getValueIsAdjusting()) {
            //return;
          }
          int orient = source.getOrientation();
          if (orient == Adjustable.HORIZONTAL) {
            //System.out.println("from horizontal scrollbar" + plotScrollPanel.getViewport().getViewPosition().x); 
          } else {
            //System.out.println("from vertical scrollbar");
          }
          int type = evt.getAdjustmentType();
          switch (type) {
          case AdjustmentEvent.UNIT_INCREMENT:
            //System.out.println("Scrollbar was increased by one unit");
            break;
          case AdjustmentEvent.UNIT_DECREMENT:
            //System.out.println("Scrollbar was decreased by one unit");
            break;
          case AdjustmentEvent.BLOCK_INCREMENT:
            //System.out.println("Scrollbar was increased by one block");
            break;
          case AdjustmentEvent.BLOCK_DECREMENT:
            //System.out.println("Scrollbar was decreased by one block");
            break;
          case AdjustmentEvent.TRACK:
            //System.out.println("The knob on the scrollbar was dragged");
            delegate.wasScrolled(plotScrollPanel.getViewport().getViewPosition().x);
            break;
          }
          int value = evt.getValue();
        }
      }
    
    public void scroll(int xPos)
    {
        plotScrollPanel.getViewport().setViewPosition(new Point(xPos,0));
    }

    /**
     * This method is called from within the constructor to  the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        outputComboBox = new javax.swing.JComboBox<>();
        featureComboBox = new javax.swing.JComboBox<>();
        liveToggle = new javax.swing.JCheckBox();
        closeButton = new javax.swing.JButton();
        plotScrollPanel = new javax.swing.JScrollPane();

        outputComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputComboBoxActionPerformed(evt);
            }
        });

        featureComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                featureComboBoxActionPerformed(evt);
            }
        });

        liveToggle.setText("Streaming");
        liveToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liveToggleActionPerformed(evt);
            }
        });

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(outputComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(featureComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(liveToggle)
                .addGap(18, 18, 18)
                .addComponent(closeButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(plotScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(featureComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liveToggle)
                    .addComponent(closeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plotScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // TODO add your handling code here:
        delegate.closeButtonPressed(model);
    }//GEN-LAST:event_closeButtonActionPerformed

    private void outputComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputComboBoxActionPerformed
        // TODO add your handling code here:
        int i = outputComboBox.getSelectedIndex();
        if(i > 0 && !ignoreDelegate)
        {
            model.pathIndex = i;
            delegate.modelChanged(model);
        }
    }//GEN-LAST:event_outputComboBoxActionPerformed

    private void featureComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_featureComboBoxActionPerformed
        // TODO add your handling code here:
        int i = featureComboBox.getSelectedIndex();
        if(model != null && !ignoreDelegate)
        {
            //model.feature.outputIndex = i;
            delegate.modelChanged(model);
        }
    }//GEN-LAST:event_featureComboBoxActionPerformed

    private void liveToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liveToggleActionPerformed
        // TODO add your handling code here:
        model.isStreaming = liveToggle.isSelected();
        if(!ignoreDelegate)
        {
            delegate.streamingToggleChanged(model);
            delegate.modelChanged(model);
        }
    }//GEN-LAST:event_liveToggleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JComboBox<String> featureComboBox;
    private javax.swing.JCheckBox liveToggle;
    private javax.swing.JComboBox<String> outputComboBox;
    private javax.swing.JScrollPane plotScrollPanel;
    // End of variables declaration//GEN-END:variables
}
