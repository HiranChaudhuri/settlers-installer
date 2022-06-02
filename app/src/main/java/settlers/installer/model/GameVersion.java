/*
 */
package settlers.installer.model;

import java.util.Date;

/**
 *
 * @author hiran
 */
public class GameVersion {
    private String name;
    private Date publishedAt;
    private Date installedAt;
    private String installPath;
    private String downloadUrl;
    private String basedOn;

    /**
     * Returns the based-on information. This is used to distinguish whether
     * we had a release or some intermediate build artifact and thus resembles the
     * class name.
     * 
     * @return  the basedOn information
     */
    public String getBasedOn() {
        return basedOn;
    }

    /**
     * Sets the based-on information. This is used to distinguish whether
     * we had a release or some intermediate build artifact and thus resembles the
     * class name.
     * 
     * @param basedOn the basedOn information
     */
    public void setBasedOn(String basedOn) {
        this.basedOn = basedOn;
    }

    /**
     * Returns the game name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the game name.
     * 
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the publishing date.
     * 
     * @return 
     */
    public Date getPublishedAt() {
        return publishedAt;
    }

    /**
     * Sets the publishing date.
     * 
     * @param publishedAt 
     */
    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    /**
     * Returns the installation date.
     * 
     * @return 
     */
    public Date getInstalledAt() {
        return installedAt;
    }

    /**
     * Sets the installation date.
     * 
     * @param installedAt the date
     */
    public void setInstalledAt(Date installedAt) {
        this.installedAt = installedAt;
    }

    /**
     * Returns the install path.
     * 
     * @return the path
     */
    public String getInstallPath() {
        return installPath;
    }

    /**
     * Sets the install path.
     * 
     * @param installPath the path
     */
    public void setInstallPath(String installPath) {
        this.installPath = installPath;
    }

    /**
     * Returns the download url.
     * 
     * @return 
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * Sets the download url.
     * 
     * @param downloadUrl the url
     */
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

}
