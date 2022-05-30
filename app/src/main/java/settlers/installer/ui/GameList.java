/*
 */
package settlers.installer.ui;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.github.GHObject;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHWorkflowRun;
import settlers.installer.Util;
import settlers.installer.model.GameVersion;

/**
 *
 * @author hiran
 */
public class GameList extends javax.swing.JPanel {
    private static final Logger log = LogManager.getLogger(GameList.class);
    
    private final javax.swing.ImageIcon iiRelease = new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clean-package-48.png")).getImage().getScaledInstance(16, 16, Image.SCALE_FAST));
    private final javax.swing.ImageIcon iiRun = new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/construction_FILL0_wght400_GRAD0_opsz48.png")).getImage().getScaledInstance(16, 16, Image.SCALE_FAST));
    private final javax.swing.ImageIcon iiCloud = new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cloudy_FILL0_wght400_GRAD0_opsz48.png")).getImage().getScaledInstance(16, 16, Image.SCALE_FAST));

    private class ObjectTableModel extends AbstractTableModel {
        
        private String[] columns = new String[]{"type", "name", "built", "status"};
        private Class[] columnClass = new Class[]{ImageIcon.class, String.class, Date.class, ImageIcon.class};
        private List<Object> data;

        public ObjectTableModel() {
        }
        
        public ObjectTableModel(List<Object> data) {
            this.data = new ArrayList<Object>(data);
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int column) {
            log.trace("getColumnName({})", column);
            return columns[column];
        }

        @Override
        public Class<?> getColumnClass(int column) {
            log.trace("getColumnClass({})", column);
            return columnClass[column];
        }

        @Override
        public boolean isCellEditable(int i, int i1) {
            log.debug("isCellEditable({}, {})", i, i1);
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            log.trace("getValueAt({}, {})", rowIndex, columnIndex);
            Object row = data.get(rowIndex);
            switch (columnIndex) {
                case 0: // icon
                    if (row instanceof GHRelease) {
                        return iiRelease;
                    } else if (row instanceof GHWorkflowRun) {
                        return iiRun;
                    } else if (row instanceof GameVersion) {
                        switch (((GameVersion)row).getBasedOn()) {
                            case "org.kohsuke.github.GHRelease":
                                return iiRelease;
                            default:
                                log.error("basedon={}", ((GameVersion)row).getBasedOn());
                                return null;
                        }
                    }


                case 1: // name
                    if (row instanceof GHRelease) {
                        return ((GHRelease)row).getName();
                    } else if (row instanceof GHWorkflowRun) {
                        GHWorkflowRun run = (GHWorkflowRun)row;
                        return run.getName()+" "+run.getHeadBranch()+" "+run.getRunNumber();
                    } else if (row instanceof GameVersion) {
                        return ((GameVersion)row).getName();
                    }
                case 2: // date
                    try {
                        if (row instanceof GHRelease) {
                            try {
                                return ((GHRelease)row).getPublished_at();
                            } catch (Exception e) {
                                return null;
                            }
                        } else if (row instanceof GHWorkflowRun) {
                            GHWorkflowRun run = (GHWorkflowRun)row;
                            return run.getUpdatedAt();
                        } else if (row instanceof GameVersion) {
                            return ((GameVersion)row).getPublishedAt();
                        }
                    } catch (IOException e) {
                        log.debug("could not get date", e);
                        return null;
                    }
                case 3: // status
                    if (row instanceof GHObject) {
                        if (Util.isInstalled((GHObject)row)) {
                            return null;
                        } else {
                            return iiCloud;
                        }
                    } else {
                        return null;
                    }
                default:
                    return "n/a";
            }
        }

        @Override
        public void setValueAt(Object o, int i, int i1) {
            log.debug("setValueAt({}, {})", i, i1);
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public Object getRow(int row)  {
            return data.get(row);
        }
    }
    
    private ObjectTableModel model;
    
    /**
     * Creates new form GameList
     */
    public GameList() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(500, 16));

        jScrollPane1.setMinimumSize(new java.awt.Dimension(500, 150));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(500, 402));

        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setShowGrid(false);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public void setData(List<Object> objects) {
        model = new ObjectTableModel(objects);
        jTable1.setModel(model);
        
        for (int i=0;i<model.getRowCount();i++) {
            if (model.getValueAt(i, 3)==null) {
                jTable1.getSelectionModel().setSelectionInterval(i, i);
                return;
            }
        }
    }
    
    public List<Object> getData() {
        if (model != null) {
            return model.data;
        } else {
            return null;
        }
    }

    public Object getSelection() {
        int row = jTable1.getSelectedRow();
        if (row>=0) {
            return model.getRow(row);
        } else {
            return null;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}