/*
 */
package settlers.installer;

import settlers.installer.ui.ConfigurationPanel;
import settlers.installer.ui.InstallSourcePicker;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settlers.installer.model.Artifact;
import settlers.installer.model.Configuration;
import settlers.installer.model.Release;
import settlers.installer.model.WorkflowRun;

/**
 *
 * @author hiran
 */
public class App extends javax.swing.JFrame {
    private static final Logger log = LogManager.getLogger(App.class);

    private final javax.swing.ImageIcon iiFound = new javax.swing.ImageIcon(getClass().getResource("/done_outline_FILL0_wght400_GRAD0_opsz48.png"));
    private final javax.swing.ImageIcon iiMissing = new javax.swing.ImageIcon(getClass().getResource("/dangerous_FILL0_wght400_GRAD0_opsz48.png"));
    private final javax.swing.ImageIcon iiUpdate = new javax.swing.ImageIcon(getClass().getResource("/update_FILL0_wght400_GRAD0_opsz48.png"));
    
    private Configuration configuration;
    
    // TODO: Play button should come like https://www.codejava.net/java-se/swing/how-to-create-drop-down-button-in-swing
    
    /**
     * Creates new form App
     */
    public App() {
        initComponents();
        jProgressBar.setVisible(false);
        
        configuration = Configuration.load(Util.getConfigurationFile());
        checkFiles();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lbIconGithub = new javax.swing.JLabel();
        lbGameFiles = new javax.swing.JLabel();
        lbIconSettlers = new javax.swing.JLabel();
        lbDataFiles = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbResultGame = new javax.swing.JLabel();
        lbResultData = new javax.swing.JLabel();
        btInstallGame = new javax.swing.JButton();
        btInstallData = new javax.swing.JButton();
        btUpdate = new javax.swing.JButton();
        buttonBar = new javax.swing.JPanel();
        jProgressBar = new javax.swing.JProgressBar();
        btPlay = new javax.swing.JButton();
        btOptions = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Settlers-Installer");
        setName("Settlers-Installer"); // NOI18N
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lbIconGithub.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbIconGithub.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GitHub-Mark-120px-plus.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 30;
        getContentPane().add(lbIconGithub, gridBagConstraints);

        lbGameFiles.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbGameFiles.setText("Game files");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 30;
        getContentPane().add(lbGameFiles, gridBagConstraints);

        lbIconSettlers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbIconSettlers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/siedler3-helme-circle-120.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 30;
        getContentPane().add(lbIconSettlers, gridBagConstraints);

        lbDataFiles.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbDataFiles.setText("Data Files");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 30;
        getContentPane().add(lbDataFiles, gridBagConstraints);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Welcome to settlers-remake, a clone of Bluebyte’s Settlers III.");
        jLabel5.setAutoscrolls(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jLabel5, gridBagConstraints);

        lbResultGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/done_outline_FILL0_wght400_GRAD0_opsz48.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        getContentPane().add(lbResultGame, gridBagConstraints);

        lbResultData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/done_outline_FILL0_wght400_GRAD0_opsz48.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        getContentPane().add(lbResultData, gridBagConstraints);

        btInstallGame.setBackground(java.awt.Color.orange);
        btInstallGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/construction_FILL0_wght400_GRAD0_opsz48.png"))); // NOI18N
        btInstallGame.setText("Install Game");
        btInstallGame.setOpaque(true);
        btInstallGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInstallGameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        getContentPane().add(btInstallGame, gridBagConstraints);

        btInstallData.setBackground(java.awt.Color.orange);
        btInstallData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/construction_FILL0_wght400_GRAD0_opsz48.png"))); // NOI18N
        btInstallData.setText("Install Data");
        btInstallData.setBorderPainted(false);
        btInstallData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInstallDataActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        getContentPane().add(btInstallData, gridBagConstraints);

        btUpdate.setBackground(java.awt.Color.orange);
        btUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/construction_FILL0_wght400_GRAD0_opsz48.png"))); // NOI18N
        btUpdate.setText("Update Game");
        btUpdate.setOpaque(true);
        btUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUpdateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        getContentPane().add(btUpdate, gridBagConstraints);

        buttonBar.setLayout(new java.awt.GridBagLayout());

        jProgressBar.setIndeterminate(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        buttonBar.add(jProgressBar, gridBagConstraints);

        btPlay.setBackground(new java.awt.Color(127, 255, 131));
        btPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/play_arrow_FILL0_wght400_GRAD0_opsz48.png"))); // NOI18N
        btPlay.setToolTipText("Play game!");
        btPlay.setOpaque(true);
        btPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPlayActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        buttonBar.add(btPlay, gridBagConstraints);

        btOptions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu_FILL0_wght400_GRAD0_opsz48.png"))); // NOI18N
        btOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOptionsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        buttonBar.add(btOptions, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(buttonBar, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void doInstallGame() {
        btInstallGame.setEnabled(false);
        btInstallData.setEnabled(false);
        btUpdate.setEnabled(false);
        btPlay.setEnabled(false);
        jProgressBar.setVisible(true);
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                int x = 0;
                try {

                    Util.installLatest();
                    
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(App.this, "Something went wrong.");
                } finally {
                    btInstallGame.setEnabled(true);
                    btInstallData.setEnabled(true);
                    btUpdate.setEnabled(true);
                    btPlay.setEnabled(true);
                    jProgressBar.setVisible(false);
                    
                    checkFiles();
                }
            }
        }).start();
    }
    
    private void btInstallGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInstallGameActionPerformed
        log.debug("btInstallGameActionPerformed(...)");
        doInstallGame();
    }//GEN-LAST:event_btInstallGameActionPerformed

    private void btInstallDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInstallDataActionPerformed
        log.debug("btInstallDataActionPerformed(...)");
        btInstallGame.setEnabled(false);
        btInstallData.setEnabled(false);
        btUpdate.setEnabled(false);
        btPlay.setEnabled(false);
        
        // check parameters
        InstallSourcePicker isp = new InstallSourcePicker();
        if (JOptionPane.showOptionDialog(this, isp, "Install Data files from...", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null) == JOptionPane.OK_OPTION) {
            // do the needful
            String source = isp.getPath();
            log.debug("Will grab files from {}", source);
            File srcDir = new File(source);

            jProgressBar.setVisible(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (Util.isGameFolder(srcDir)) {
                            log.debug("Want to copy files...");
                            FileUtils.copyDirectory(srcDir, Util.getDataFolder());
                            
                        } else if (Util.isInstallCD(srcDir)) {
                            log.debug("Want to install from CD");
                            Util.installFromCD(srcDir);
                        } else throw new Exception(String.format("Unknown source %s", srcDir));

                    } catch(Exception e) {
                        log.error("Could not install data from {}", srcDir, e);
                        JOptionPane.showMessageDialog(App.this, "Something went wrong.");
                    } finally {
                        btInstallGame.setEnabled(true);
                        btInstallData.setEnabled(true);
                        btUpdate.setEnabled(true);
                        btPlay.setEnabled(true);
                        jProgressBar.setVisible(false);

                        checkFiles();
                    }
                }
            }).start();
        } else {
            btInstallGame.setEnabled(true);
            btInstallData.setEnabled(true);
            btUpdate.setEnabled(true);
            btPlay.setEnabled(true);
            jProgressBar.setVisible(false);
            checkFiles();
        }
        

    }//GEN-LAST:event_btInstallDataActionPerformed

    private void btPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPlayActionPerformed
        log.debug("btPlayActionPerformed(...)");
        btInstallGame.setEnabled(false);
        btInstallData.setEnabled(false);
        btUpdate.setEnabled(false);
        btPlay.setEnabled(false);
        jProgressBar.setVisible(true);
        setVisible(false);
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                int x = 0;
                try {

                    List<Release> installedReleases = Util.getInstalledReleases();
                    if (installedReleases != null && !installedReleases.isEmpty()) {
                        Util.runRelease(installedReleases.get(0));
                    }
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(App.this, "Something went wrong.");
                } finally {
                    btInstallGame.setEnabled(true);
                    btInstallData.setEnabled(true);
                    btUpdate.setEnabled(true);
                    btPlay.setEnabled(true);
                    jProgressBar.setVisible(false);
                    setVisible(true);

                    checkFiles();
                }
            }
        }).start();
    }//GEN-LAST:event_btPlayActionPerformed

    private void btUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUpdateActionPerformed
        log.debug("btUpdateActionPerformed(...)");
        doInstallGame();
    }//GEN-LAST:event_btUpdateActionPerformed

    private void btOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOptionsActionPerformed
        ConfigurationPanel cp = new ConfigurationPanel();
        cp.setData(configuration);
        if (JOptionPane.showOptionDialog(this, cp, "Preferences", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null)==JOptionPane.OK_OPTION) {
            configuration = cp.getData();
            configuration.save(Util.getConfigurationFile());
        }
    }//GEN-LAST:event_btOptionsActionPerformed

    private void checkFiles() {
        GameState gstate = haveGameFiles();
        switch(gstate) {
            case latest:
                lbResultGame.setIcon(iiFound);
                lbResultGame.setVisible(true);
                btInstallGame.setVisible(false);
                btUpdate.setVisible(false);
                break;
            case old:
                lbResultGame.setIcon(iiUpdate);
                lbResultGame.setVisible(false);
                btInstallGame.setVisible(false);
                btUpdate.setVisible(true);
                break;
            case missing:
                lbResultGame.setIcon(iiMissing);
                lbResultGame.setVisible(false);
                btInstallGame.setVisible(true);
                btUpdate.setVisible(false);
                break;
        }
        
        boolean dataFiles = haveDataFiles();
        lbResultData.setIcon(dataFiles? iiFound: iiMissing);
        lbResultData.setVisible(dataFiles);
        btInstallData.setVisible(!dataFiles);
        
        btPlay.setVisible(dataFiles && (gstate != GameState.missing) );
    }
    
    public enum GameState {
        missing, old, latest
    }
    
    /**
     * Returns true if some game is installed that we can run.
     * 
     * @return true if a game is installed, false otherwise
     */
    private GameState haveGameFiles() {
        try {
            List<Release> installedReleases = Util.getInstalledReleases();
            if (installedReleases != null && !installedReleases.isEmpty()) {
                // check if updates are available

                try {
                    List<Release> availableReleases = Util.getGithubReleases();
                    if (installedReleases.get(0).getPublished_at().before(availableReleases.get(0).getPublished_at())) {
                        // update is available
                        log.debug("Update is available");
                        return GameState.old;
                    } else if (configuration.isCheckArtifacts()) {
                        try {
                            List<WorkflowRun> wfrs = Util.getGithubWorkflowRuns();
                            for (WorkflowRun wfr: wfrs) {
                                List<Artifact> as = wfr.getArtifacts();
                                if (as != null && !as.isEmpty()) {
                                    log.debug("found workflow run {}", wfr);
                                    log.debug("    with artifacts {}", as);
                                }
                            }
                        } catch (Exception e) {
                            log.error("Could not list workflows", e);
                        }
                        return GameState.latest;
                    } else {
                        // we already have the latest version
                        log.debug("we already have the latest version");
                        return GameState.latest;
                    }
                } catch (Exception e) {
                    // could not figure out if update is available. Let's assume we have the latest
                    log.debug("We assume to have the latest version", e);
                    return GameState.latest;
                }
            } else {
                log.debug("No good version installed locally");
                return GameState.missing;
            }
        } catch (FileNotFoundException e) {
            // if no file is found, we do not have a game
            log.debug("Could not check for local version");
            return GameState.missing;
        }
    }

    /**
     * Validated a S3 data folder.
     * 
     * @return true if data files seem ok, false otherwise
     */
    private boolean haveDataFiles() {
        File dir = Util.getDataFolder();
        
        if (!dir.isDirectory()) {
            log.warn("File {} is not a directory.", dir);
            return false;
        }
        if (dir.listFiles().length < 2) {
            log.warn("File {} contains too few files", dir);
            return false;
        }
        return true;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        log.debug("main({})", Arrays.asList(args));
        log.debug("Full command line: {}", ProcessHandle.current().info().commandLine().orElse("n/a"));
        Util.dumpEnvironment();
        Util.dumpProperties(System.getProperties());
        
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
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                App app = new App();
                app.setLocationRelativeTo(null);
                app.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btInstallData;
    private javax.swing.JButton btInstallGame;
    private javax.swing.JButton btOptions;
    private javax.swing.JButton btPlay;
    private javax.swing.JButton btUpdate;
    private javax.swing.JPanel buttonBar;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JLabel lbDataFiles;
    private javax.swing.JLabel lbGameFiles;
    private javax.swing.JLabel lbIconGithub;
    private javax.swing.JLabel lbIconSettlers;
    private javax.swing.JLabel lbResultData;
    private javax.swing.JLabel lbResultGame;
    // End of variables declaration//GEN-END:variables
}
