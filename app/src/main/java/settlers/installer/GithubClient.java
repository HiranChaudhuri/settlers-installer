/*
 */
package settlers.installer;

import com.owlike.genson.GenericType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.github.GHAsset;
import static settlers.installer.Util.RELEASE_URL;
import static settlers.installer.Util.WORKFLOW_RUNS_URL;
import static settlers.installer.Util.getGenson;
import static settlers.installer.Util.getManagedTempFolder;
import static settlers.installer.Util.sortReleaseByDate;
import static settlers.installer.Util.sortWorkflowByDate;

/**
 * A client for Github API.
 * @author hiran
 */
public class GithubClient {
    private static final Logger log = LogManager.getLogger(GithubClient.class);

    private String username;
    private String token;
    
    public GithubClient(String username, String token) {
        this.username = username;
        this.token = token;
    }

    /**
     * Downloads an asset into a temporary file and returns the file.
     * 
     * @param asset the asset to download
     * @return the local file
     * @throws IOException something went wrong
     */
    public static File downloadAsset(GHAsset asset) throws IOException {
        File tempFolder = getManagedTempFolder();
        tempFolder.mkdirs();
        File download = File.createTempFile(asset.getName()+"_"+asset.getId(), ".zip", tempFolder);
        
        URL url = new URL(asset.getBrowserDownloadUrl());
        log.debug("downloading from {}", url);
        try (InputStream in = url.openStream()) {
            Files.copy(in, download.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new IOException(String.format("Could not download %s to %s", url, download));
        }
        
        return download;
    }

}
