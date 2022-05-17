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
import static settlers.installer.Util.RELEASE_URL;
import static settlers.installer.Util.WORKFLOW_RUNS_URL;
import static settlers.installer.Util.getGenson;
import static settlers.installer.Util.getManagedTempFolder;
import static settlers.installer.Util.sortReleaseByDate;
import static settlers.installer.Util.sortWorkflowByDate;
import settlers.installer.model.Asset;
import settlers.installer.model.Release;
import settlers.installer.model.WorkflowRun;
import settlers.installer.model.WorkflowRunResponsePage;

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

    /** Returns the releases available on Github.
     * The list is sorted by publishing date.
     * 
     * @return the list of releases
     * @throws MalformedURLException something went wrong
     * @throws IOException something went wrong
     */
    public List<Release> getGithubReleases() throws MalformedURLException, IOException {
        URL u = new URL(RELEASE_URL);
        URLConnection connection = u.openConnection();
        String authStr = Base64.getEncoder().encodeToString((username+":"+token).getBytes());
        connection.setRequestProperty("Authoriyation", "Basic " + authStr);
        
        InputStream in = connection.getInputStream();
        List<Release> releases = getGenson().deserialize(in, new GenericType<List<Release>>(){});
        return sortReleaseByDate(releases);
    }
    
    public List<WorkflowRun> getGithubWorkflowRuns() throws IOException {
        URL u = new URL(WORKFLOW_RUNS_URL);
        URLConnection connection = u.openConnection();
        String authStr = Base64.getEncoder().encodeToString((username+":"+token).getBytes());
        connection.setRequestProperty("Authoriyation", "Basic " + authStr);
        
        InputStream in = connection.getInputStream();
        WorkflowRunResponsePage wrrp = getGenson().deserialize(in, WorkflowRunResponsePage.class);
        List<WorkflowRun> wrs = (List)(wrrp.getWorkflow_runs());
        return sortWorkflowByDate(wrs);
    }
    
    /**
     * Downloads an asset into a temporary file and returns the file.
     * 
     * @param asset the asset to download
     * @return the local file
     * @throws IOException something went wrong
     */
    public static File downloadAsset(Asset asset) throws IOException {
        File tempFolder = getManagedTempFolder();
        tempFolder.mkdirs();
        File download = File.createTempFile(asset.getName()+"_"+asset.getId(), ".zip", tempFolder);
        
        URL url = new URL(asset.getBrowser_download_url());
        log.debug("downloading from {}", url);
        try (InputStream in = url.openStream()) {
            Files.copy(in, download.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new IOException(String.format("Could not download %s to %s", url, download));
        }
        
        return download;
    }

}
