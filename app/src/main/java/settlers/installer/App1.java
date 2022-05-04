/*
 */
package settlers.installer;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.List;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settlers.installer.model.Release;

public class App1 {
    private static final Logger log = LogManager.getLogger(App1.class);
    
    /**
     * Validated a S3 data folder.
     * 
     * @param dir
     * @return 
     */
    private static boolean looksOk(File dir) {
        log.debug("looksOk({})", dir);
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
    
    private static ImageIcon settlersIcon = new javax.swing.ImageIcon(App1.class.getResource("/siedler3-helme-shape.png"));
    private static ImageIcon settlersLogoIcon = new javax.swing.ImageIcon(App1.class.getResource("/siedler3-helme-logo.png"));
    
    private static JFrame showNakedFrame(JComponent message) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());
        frame.setIconImage(settlersLogoIcon.getImage());

        frame.add(new JLabel(settlersIcon), new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, 0, new Insets(5, 5, 5, 5), 0, 0));
        frame.add(message, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, 0, new Insets(5, 5, 5, 5), 0, 0));

        frame.pack();
        frame.setLocationRelativeTo(null);
        return frame;
    }

    private static File findCdRom() throws InterruptedException {
        File mountedCd = null;
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("Please insert the Settlers installation CD now"), BorderLayout.CENTER);
        JLabel hint = new JLabel("No CD found");
        panel.add(hint, BorderLayout.SOUTH);
        
        JFrame frame = showNakedFrame(panel);
        
        try {
            frame.setVisible(true);

            while (mountedCd == null) {
                mountedCd = Util.getCdMountPoint();
                if (mountedCd == null) {
                    hint.setText("No CD inserted");
                } else {
                    File data1 = new File(mountedCd, "s3/install/data1.cab");
                    log.debug("checking for {}", data1);
                    if (!data1.exists()) {
                        hint.setText("No S3 installation files in "+mountedCd.getAbsolutePath());
                        mountedCd = null;
                    } else {
                        log.info("Found {}", data1);
                        return mountedCd;
                    }
                }
                
                Thread.sleep(3000);
            }
            
            return mountedCd;
        } finally {
            frame.setVisible(false);
        }
    }
    
    public static void main(String[] args) throws Exception {
        try {
            List<Release> githubReleases = Util.getGithubReleases();
            List<Release> installedReleases = Util.getInstalledReleases();

            // install if a newer one is available
            if (installedReleases.isEmpty() || installedReleases.get(0).getPublished_at().before(githubReleases.get(0).getPublished_at())) {
                Release latest = githubReleases.get(0);
                log.debug("Installing latest release {}", latest);

                JFrame f = null;
                try {
                    JPanel panel = new JPanel();
                    JProgressBar pb = new JProgressBar();
                    pb.setIndeterminate(true);
                    panel.add(new JLabel(String.format("Installing release %s", latest.getName())));
                    panel.add(pb);
                    
                    f = showNakedFrame(panel);
                    f.setVisible(true);

                    Util.installRelease(latest);
                } catch (Exception e) {
                    f.setVisible(false);
                    throw new Exception(String.format("Could not install %s", latest.getName()), e);
                } finally {
                    f.setVisible(false);
                }

                installedReleases = Util.getInstalledReleases();
            }

            // remove if we have more than five
            while (installedReleases.size()>5) {
                Release r = installedReleases.get(installedReleases.size()-1);
                Util.removeRelease(r);
                installedReleases = Util.getInstalledReleases();
            }

            for (Release r: installedReleases) {
                log.debug("  installed: {}", r.getName());
            }
            for (Release r: githubReleases) {
                log.debug("  github   : {}", r.getName());
            }

            log.debug("{} releases installed: {}", installedReleases.size(), installedReleases);


            log.debug("data folder {}", Util.getDataFolder());

            if (!looksOk(Util.getDataFolder())) {
                log.info("Data folder not ok? Let's install S3...");
                JFrame f = null;
                try {
                    File cdrom = findCdRom();

                    JPanel panel = new JPanel();
                    JProgressBar pb = new JProgressBar();
                    pb.setIndeterminate(true);
                    panel.add(new JLabel("Installing from CD..."));
                    panel.add(pb);
                    
                    f = showNakedFrame(panel);
                    f.setVisible(true);
                    
                    Properties props = new Properties();
                    //props.put("cdrom", "/media/hiran/S3GOLD2_G");
                    props.put("cdrom", cdrom.getAbsolutePath());
                    props.put("data", Util.getDataFolder().getAbsolutePath());
                    Util.runAnt(new File("src/main/resources/S3_Installer.xml"), props);
                } catch (Exception e) {
                    // delete data directory recursively
                    Util.deleteDir(Util.getDataFolder());
                    log.info("Removed {}", Util.getDataFolder());

                    f.setVisible(false);
                    throw new Exception("Could not install S3");        
                } finally {
                    f.setVisible(false);
                }
            }

            Util.runRelease(installedReleases.get(0));

            log.debug("Done.");
        } catch (Exception e) {
            log.error("something went wrong", e);
            String msg = e.getMessage() + "\nSee logfile for more information.";
            
            if (msg.length()>80) {
                JTextArea jta = new JTextArea();
                jta.setColumns(80);
                jta.setRows(10);
                jta.setLineWrap(true);
                jta.setWrapStyleWord(true);
                jta.setEditable(false);
                jta.setText(msg);
                JOptionPane.showMessageDialog(null, new JScrollPane(jta), "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(1);
        }
        System.exit(0);
    }
}