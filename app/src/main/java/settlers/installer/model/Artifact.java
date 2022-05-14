/*
 */
package settlers.installer.model;

import java.util.Date;

/**
 * 
 * curl -H "Accept: application/vnd.github.v3+json" https://api.github.com/repos/paulwedeck/settlers-remake/actions/artifacts
 * 
 * @author hiran
 */
public class Artifact {
    private String id;
    private String node_id;
    private String name;
    private long size;
    private String url;
    private String archive_download_url;
    private boolean expired;
    private Date created_at;
    private Date updated_at;
    private Date expires_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArchive_download_url() {
        return archive_download_url;
    }

    public void setArchive_download_url(String archive_download_url) {
        this.archive_download_url = archive_download_url;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(Date expires_at) {
        this.expires_at = expires_at;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName()).append("(");
        sb.append("id=").append(id);
//        sb.append(", node_id=").append(node_id);
        sb.append(", name=").append(name);
        sb.append(", size=").append(size);
        sb.append(", url=").append(url);
        sb.append(", archive_download_url=").append(archive_download_url);
        sb.append(", expired=").append(expired);
        sb.append(", created_at=").append(created_at);
        sb.append(", updated_at=").append(updated_at);
        sb.append(", expires_at=").append(expires_at);
        sb.append(")");
        return sb.toString();
    }
}
