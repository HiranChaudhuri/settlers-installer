/*
 */
package settlers.installer.ui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.github.GHObject;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHWorkflowRun;
import settlers.installer.Util;

/**
 *
 * @author hiran
 */
public class GameList extends javax.swing.JPanel {
    private static final Logger log = LogManager.getLogger(GameList.class);
    
    private final javax.swing.ImageIcon iiRelease = new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clean-package-48.png")).getImage().getScaledInstance(16, 16, Image.SCALE_FAST));
    private final javax.swing.ImageIcon iiRun = new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/construction_FILL0_wght400_GRAD0_opsz48.png")).getImage().getScaledInstance(16, 16, Image.SCALE_FAST));
    private final javax.swing.ImageIcon iiCloud = new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cloudy_FILL0_wght400_GRAD0_opsz48.png")).getImage().getScaledInstance(16, 16, Image.SCALE_FAST));

    private class GHObjectTableModel extends AbstractTableModel {
        
        private String[] columns = new String[]{"type", "name", "built", "status"};
        private Class[] columnClass = new Class[]{ImageIcon.class, String.class, Date.class, ImageIcon.class};
        private List<GHObject> data;

        public GHObjectTableModel() {
        }
        
        public GHObjectTableModel(List<GHObject> data) {
            this.data = new ArrayList<GHObject>(data);
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
            log.debug("getColumnName({})", column);
            return columns[column];
        }

        @Override
        public Class<?> getColumnClass(int column) {
            log.debug("getColumnClass({})", column);
            return columnClass[column];
        }

        @Override
        public boolean isCellEditable(int i, int i1) {
            log.debug("isCellEditable({}, {})", i, i1);
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            log.debug("getValueAt({}, {})", rowIndex, columnIndex);
            GHObject row = data.get(rowIndex);
            switch (columnIndex) {
                case 0: // icon
                    if (row instanceof GHRelease) {
                        return iiRelease;
                    } else if (row instanceof GHWorkflowRun) {
                        return iiRun;
                    }
                case 1: // name
                    if (row instanceof GHRelease) {
                        return ((GHRelease)row).getName();
                    } else if (row instanceof GHWorkflowRun) {
                        GHWorkflowRun run = (GHWorkflowRun)row;
                        return run.getName()+" "+run.getHeadBranch()+" "+run.getRunNumber();
                    }
                case 2: // date
                    try {
                        if (row instanceof GHRelease) {
                            return ((GHRelease)row).getPublished_at();
                        } else if (row instanceof GHWorkflowRun) {
                            GHWorkflowRun run = (GHWorkflowRun)row;
                            return run.getUpdatedAt();
                        }
                    } catch (IOException e) {
                        log.debug("could not get date", e);
                        return null;
                    }
                case 3:
                    File target = null;
                    target = new File(Util.getGamesFolder(), String.valueOf(row.getId()));
                    if (target.isDirectory()) {
                        return null;
                    } else {
                        return iiCloud;
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

    }
    
    /**
     * Creates new form GameList
     */
    public GameList() {
        initComponents();
//        jList1.setCellRenderer(new ListCellRenderer<GHObject>() {
//            @Override
//            public Component getListCellRendererComponent(JList<? extends GHObject> jlist, GHObject e, int i, boolean isSelected, boolean hasFocus) {
//                JPanel result = new JPanel();
//                result.setLayout(new GridBagLayout());
//                if (e instanceof GHRelease) {
//                    GHRelease release = (GHRelease)e;
//                    result.add(new JLabel(iiRelease),         new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.EAST,GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0));
//                    result.add(new JLabel(release.getName()), new GridBagConstraints(1,0,1,1,1.0,0.0,GridBagConstraints.EAST,GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
//                } else if (e instanceof GHWorkflowRun) {
//                    GHWorkflowRun run = (GHWorkflowRun)e;
//                    result.add(new JLabel(iiRun),                                                        new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.EAST,GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0));
//                    result.add(new JLabel(run.getName()+"/"+run.getHeadBranch()+"/"+run.getRunNumber()), new GridBagConstraints(1,0,1,1,1.0,0.0,GridBagConstraints.EAST,GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
//                } else {
//                    result.add(new JLabel(String.valueOf(e)));
//                }
//                
//                if (isSelected) {
//                    result.setBackground(Color.PINK);
//                } else {
//                    result.setBackground(Color.gray);
//                }
//                return result;
//            }
//        });
        
//        jList1.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent lse) {
//                log.debug("now selected: {}", jList1.getSelectedValue());
//            }
//        });

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

    public void setData(List<GHObject> objects) {
        GHObjectTableModel tm = new GHObjectTableModel(objects);
        jTable1.setModel(tm);
        
        for (int i=0;i<tm.getRowCount();i++) {
            if (tm.getValueAt(i, 3)==null) {
                jTable1.getSelectionModel().setSelectionInterval(i, i);
                return;
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
