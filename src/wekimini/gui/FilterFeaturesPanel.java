/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;


/**
 *
 * @author louismccallum
 */
public class FilterFeaturesPanel extends javax.swing.JPanel {
    
    public FeatureMenuDelegate delegate;
    private String highlightedTag = "";
    private ArrayList<String> selectedFilters = new ArrayList();
    private boolean adding = true;
    
    /**
     * Creates new form FilterFeaturesPanel
     */
    public FilterFeaturesPanel() {
        initComponents();
        setUpTables();
    }
    
    public void setUpTables()
    {
        MouseListener tableMouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable source = (JTable)e.getSource();
                int row = source.rowAtPoint(e.getPoint());
                int col = source.columnAtPoint(e.getPoint());
                String tag = (String)source.getModel().getValueAt(row, col);
                 if(selectedFilters.contains(tag))
                {
                    selectedFilters.remove(tag);
                    delegate.filtersUpdated();
                }
                else
                {
                    selectedFilters.add(tag);
                    delegate.filtersUpdated();
                }
                source.repaint();
            }
        };
        
        MouseMotionListener tableMouseMotionListener = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                JTable source = (JTable)e.getSource();
                int row = source.rowAtPoint(e.getPoint());
                int col = source.columnAtPoint(e.getPoint());
                highlightedTag = (String)source.getModel().getValueAt(row, col);
                source.repaint();
            }
        };
        
        inputFiltersTable.setTableHeader(null);
        operationFiltersTable.setTableHeader(null);
        
        inputFiltersTable.setRowHeight(140/4);
        operationFiltersTable.setRowHeight(140/4);
        
        String[] inputs = new String[] {"Accelerometer", "Gyroscope",
                "AccelerometerX", "AccelerometerY", "AccelerometerZ", "GyroscopeX", "GyroscopeY", "GyroscopeZ"

        };
        inputFiltersTable.setModel(new FiltersTableModel(inputs));
        inputFiltersTable.setDefaultRenderer(String.class, new FiltersTableRenderer());
        inputFiltersTable.addMouseMotionListener(tableMouseMotionListener);
        inputFiltersTable.addMouseListener(tableMouseListener);
        inputFiltersTable.repaint();
        
        String[] operations = {
                "Raw", "Buffer", "Mean", "1st Order Diff", "FFT", "Max", "Min",
                "Energy", "IQR", "Correlation", "Standard Deviation", "Magnitude"
        };
        
        operationFiltersTable.setModel(new FiltersTableModel(operations));
        operationFiltersTable.setDefaultRenderer(String.class, new FiltersTableRenderer());
        operationFiltersTable.addMouseMotionListener(tableMouseMotionListener);
        operationFiltersTable.addMouseListener(tableMouseListener);
        operationFiltersTable.repaint();
        
    }
    
    public void setIsAdding(boolean adding)
    {
        this.adding = adding;
        addRemoveButton.setText(adding ? "Add" : "Remove");
    }
    
    public boolean getIsAdding()
    {
        return adding;
    }
    
    public ArrayList<String> getSelectedFilters()
    {
        return selectedFilters;
    }
    
        
    class FiltersTableModel extends AbstractTableModel
    {
        private String[] tags;
        
        public FiltersTableModel(String[] tags)
        {
            this.tags = tags;
        }

        @Override
        public int getRowCount() {
            return (int)Math.ceil((double)tags.length/(double)getColumnCount());
        }

        @Override
        public int getColumnCount() {
            return 6;
        }

        @Override
        public String getValueAt(int rowIndex, int columnIndex) {
            int r = rowIndex * getColumnCount();
            int c = columnIndex % getColumnCount();
            int index = c + r;
            return index < tags.length ? tags[index] : "";
        }
        
        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
    }
    
    public class FiltersTableRenderer extends JLabel implements TableCellRenderer 
    {
        FiltersTableRenderer()
        {
            setOpaque(true);
        }

        @Override
        public JLabel getTableCellRendererComponent(
                        JTable table, Object value,
                        boolean isSelected, boolean hasFocus,
                        int row, int column) {
            String tag = (String)value;
            FiltersTableModel model = (FiltersTableModel)table.getModel();
            Color textColor = FeatureSetPlotPanel.colorForTag(tag, false);
            Color borderColor = textColor;
            Color selectedTextColor = Color.DARK_GRAY;
            if(textColor == null)
            {
                textColor = Color.DARK_GRAY;
                borderColor = new Color(245, 245, 245, 255);
                selectedTextColor = borderColor;
            }
            if(selectedFilters.contains(tag))
            {
                setBackground(textColor);
                setBorder(BorderFactory.createEmptyBorder(4, 3, 4, 1));
                setForeground(selectedTextColor);
            }
            else
            {
                setBackground(Color.WHITE);
                setBorder(BorderFactory.createLineBorder(borderColor, 4));
                setForeground(textColor);
            }
            if(tag.equals(highlightedTag))
            {
                setBorder(BorderFactory.createLineBorder(Color.black));
            }
            setHorizontalAlignment(CENTER);
            setText(tag);
            return this;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        inputFiltersTable = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        operationFiltersTable = new javax.swing.JTable();
        selectAllButton = new javax.swing.JButton();
        addRemoveButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();

        inputFiltersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(inputFiltersTable);

        jLabel6.setText("Input");

        jLabel7.setText("Operation");

        operationFiltersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(operationFiltersTable);

        selectAllButton.setText("Select All");
        selectAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllButtonActionPerformed(evt);
            }
        });

        addRemoveButton.setText("Add");
        addRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRemoveButtonActionPerformed(evt);
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(selectAllButton)
                                .addGap(279, 279, 279))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(addRemoveButton)
                                .addGap(290, 290, 290))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addComponent(backButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(backButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectAllButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addRemoveButton))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        delegate.backPressed();
    }//GEN-LAST:event_backButtonActionPerformed

    private void selectAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllButtonActionPerformed
        // TODO add your handling code here:
        delegate.selectAllFeatures();
    }//GEN-LAST:event_selectAllButtonActionPerformed

    private void addRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRemoveButtonActionPerformed
        // TODO add your handling code here:
        delegate.updateFeatures();
    }//GEN-LAST:event_addRemoveButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRemoveButton;
    private javax.swing.JButton backButton;
    private javax.swing.JTable inputFiltersTable;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable operationFiltersTable;
    private javax.swing.JButton selectAllButton;
    // End of variables declaration//GEN-END:variables
}