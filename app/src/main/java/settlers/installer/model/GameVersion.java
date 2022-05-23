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

    public String getBasedOn() {
        return basedOn;
    }

    public void setBasedOn(String basedOn) {
        this.basedOn = basedOn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Date getInstalledAt() {
        return installedAt;
    }

    public void setInstalledAt(Date installedAt) {
        this.installedAt = installedAt;
    }

    public String getInstallPath() {
        return installPath;
    }

    public void setInstallPath(String installPath) {
        this.installPath = installPath;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

}
