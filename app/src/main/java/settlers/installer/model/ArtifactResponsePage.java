/*
 */
package settlers.installer.model;

import java.util.List;

/**
 *
 * @author hiran
 */
public class ArtifactResponsePage {
    private int total_count;
    private List<Artifact> artifacts;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }
    
}
