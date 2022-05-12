/*
 */
package settlers.installer.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author hiran
 */
public class Configuration {
    private static final Logger log = LogManager.getLogger(Configuration.class);

    private boolean checkReleases;
    private boolean checkPrereleases;
    private boolean checkArtifacts;
    
    private boolean supportBugReporting;
    
    private String githubUsername;
    private String githubToken;
    
    private Configuration() {
    }

    public boolean isCheckReleases() {
        return checkReleases;
    }

    public void setCheckReleases(boolean checkReleases) {
        this.checkReleases = checkReleases;
    }

    public boolean isCheckPrereleases() {
        return checkPrereleases;
    }

    public void setCheckPrereleases(boolean checkPrereleases) {
        this.checkPrereleases = checkPrereleases;
    }

    public boolean isCheckArtifacts() {
        return checkArtifacts;
    }

    public void setCheckArtifacts(boolean checkArtifacts) {
        this.checkArtifacts = checkArtifacts;
    }

    public boolean isSupportBugReporting() {
        return supportBugReporting;
    }

    public void setSupportBugReporting(boolean supportBugReporting) {
        this.supportBugReporting = supportBugReporting;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public String getGithubToken() {
        return githubToken;
    }

    public void setGithubToken(String githubToken) {
        this.githubToken = githubToken;
    }
    
    public void save(File target) {
        Properties props = new Properties();
        props.put("check.releases", String.valueOf(checkReleases));
        props.put("check.prereleases", String.valueOf(checkPrereleases));
        props.put("check.artifacts", String.valueOf(checkArtifacts));

        props.put("support.bugreporting", String.valueOf(supportBugReporting));

        props.put("github.user", githubUsername);
        props.put("github.token", githubToken);
        
        try (OutputStream out = new FileOutputStream(target)) {
            props.store(out, new Date().toString());
        } catch (IOException ex) {
            log.warn("Could not save configuration in {}", target, ex);
        }
    }
    
    public static Configuration load(File source) {
        Configuration c = new Configuration();

        Properties props = new Properties();
        if (source.canRead()) {
            try (InputStream in = new FileInputStream(source)) {
                props.load(in);
                c.checkReleases = "true".equals(props.getProperty("check.releases"));
                c.checkPrereleases = "true".equals(props.getProperty("check.prereleases"));
                c.checkArtifacts = "true".equals(props.getProperty("check.artifacts"));

                c.supportBugReporting = "true".equals(props.getProperty("support.bugreporting"));

                c.githubUsername = props.getProperty("github.user");
                c.githubToken = props.getProperty("github.token");
            } catch (IOException e) {
                log.warn("Could not read configuration file {}", source, e);
            }
        }
        
        return c;
    }
}
