/*
 */
package settlers.installer;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.filechooser.FileSystemView;
import net.sf.fikin.ant.EmbeddedAntProject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tools.ant.Project;
import settlers.installer.model.Asset;
import settlers.installer.model.Release;

/**
 * Windows:
 * <pre>
 * reg EXPORT "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\s3.exe" test.key
 * <pre>
 * Windows Registry Editor Version 5.00
 * 
 * [HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\s3.exe]
 * "Path"="f:/Ubisoft/Ubisoft Game Launcher/games/thesettlers3\\"
 * "@"="f:/Ubisoft/Ubisoft Game Launcher/games/thesettlers3\\Siedler3R.exe"
 * </pre>
 * @author hiran
 */
public class Util {
    private static final Logger log = LogManager.getLogger(Util.class);
    
    public static final String RELEASE_URL = "https://api.github.com/repos/paulwedeck/settlers-remake/releases";
    
    /** 
     * Creates a Genson parser that treats timestamps as java.util.Date.
     *
     * @return the parser
     */
    private static Genson getGenson() {
        return new GensonBuilder()
                .useDateAsTimestamp(true)
                .useDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"))
                .create();
    }
    
    /**
     * Sorts the list by publishing date.
     * 
     * @param releases the list to be sorted
     * @return the sorted list
     */
    public static List<Release> sortByDate(List<Release> releases) {
        List<Release> result = new ArrayList<>(releases);
        Collections.sort(result, new Comparator<Release>() {
            @Override
            public int compare(Release t1, Release t) {
                if (t.getPublished_at()==null) {
                    return 1;
                }
                return t.getPublished_at().compareTo(t1.getPublished_at());
            }
        });
        return result;
    }

    /** Returns the releases available on Github.
     * The list is sorted by publishing date.
     * 
     * @return the list of releases
     * @throws MalformedURLException something went wrong
     * @throws IOException something went wrong
     */
    public static List<Release> getGithubReleases() throws MalformedURLException, IOException {
        URL u = new URL(RELEASE_URL);
        InputStream in = u.openStream();
        
        List<Release> releases = getGenson().deserialize(in, new GenericType<List<Release>>(){});
        
        return sortByDate(releases);
    }
    
    /** Returns the releases locally installed.
     * The list is sorted by publishing date.
     * 
     * @return the list of releases
     * @throws FileNotFoundException something went wrong
     */
    public static List<Release> getInstalledReleases() throws FileNotFoundException {
        List<Release> result = new ArrayList<>();
        Genson genson = getGenson();
        
        File gamesFolder = getGamesFolder();
        gamesFolder.mkdirs();
        
        for(File game: gamesFolder.listFiles()) {
            File metadata = new File(game, "metadata.json");
            Release r = genson.deserialize(new FileInputStream(metadata), Release.class);
            result.add(r);
        }
        
        return sortByDate(result);
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

    /**
     * Extracts a ZIP archive.
     * 
     * @param archive The archive to unzip
     * @param target the directory to store it's content
     * @throws IOException something went wrong
     */
    public static void unzip(File archive, File target) throws IOException {
        log.debug("unzip({}, {})", archive, target);
        
        if (!target.exists()) {
            if (!target.mkdirs()) {
                throw new IOException(String.format("Could not create folder %s", target.getAbsolutePath()));
            }
        }
        
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(archive))) {
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                File destFile = new File(target, entry.getName());
                if (entry.isDirectory()) {
                    destFile.mkdirs();
                    Files.setLastModifiedTime(destFile.toPath(), entry.getLastModifiedTime());
                } else {
                    Files.copy(zipIn, destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Files.setLastModifiedTime(destFile.toPath(), entry.getLastModifiedTime());
                }
                
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }
    
    /**
     * Extracts a self-extracting ZIP archive (it is actually an EXE).
     * 
     * @param zipfile The archive to unzip
     * @param target the directory to store it's content
     * @throws IOException something went wrong
     * @throws FileNotFoundException something went wrong
     */
    public static void unzipSelfExtractingZip(File zipfile, File target) throws FileNotFoundException, IOException {
        try (ZipInputStream zis = new ZipInputStream(new WinZipInputStream(new FileInputStream(zipfile)))) {
            ZipEntry entry = null;

            while((entry = zis.getNextEntry()) != null){
                File destFile = new File(target, entry.getName());
                if(entry.isDirectory()){
                    destFile.mkdirs();
                    Files.setLastModifiedTime(destFile.toPath(), entry.getLastModifiedTime());
                } else {
                    destFile.getParentFile().mkdirs();
                    Files.copy(zis, destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Files.setLastModifiedTime(destFile.toPath(), entry.getLastModifiedTime());
                }
                zis.closeEntry();
            }
        }
    }

    /**
     * Installs a release locally. The release asset will be downloaded and
     * extracted. Finally the metadata is stored as well.
     * 
     * @param release The release to install
     * @throws IOException something went wrong
     */
    public static void installRelease(Release release) throws IOException {
        for (Asset a: release.getAssets()) {
            if ("JSettlers.zip".equals(a.getName())) {
                log.debug("check asset {}", a);
                File f = downloadAsset(a);
                File target = new File(getGamesFolder(), release.getId());
                
                int retries = 6;
                boolean done = false;
                while (retries>0 && !done) {
                    try {
                        unzip(f, target);
                        done = true;
                        continue;
                    } catch (IOException e) {
                        log.info("Could not unzip release. Maybe a virus scanner? Waiting for retry...", e);
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException ex) {
                            log.debug("Interrupted sleep");
                        }
                    }
                    
                    retries--;
                }
                if (!done) {
                    throw new IOException(String.format("Could not unzip %s to %s", f, target));
                }
                
                Files.setLastModifiedTime(target.toPath(), FileTime.from(release.getPublished_at().toInstant()));
                
                File metadata = new File(target, "metadata.json");
                try (FileOutputStream fos = new FileOutputStream(metadata)) {
                    new Genson().serialize(release, fos);
                }
                Files.setLastModifiedTime(metadata.toPath(), FileTime.from(release.getPublished_at().toInstant()));
            }
        }
    }
    
    /**
     * Deletes a file/directory recursively.
     * 
     * @param file the file to delete
     */
    public static void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (! Files.isSymbolicLink(f.toPath())) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
    }

    /**
     * Removes a release from the games folder.
     * 
     * @param release
     * @throws IOException 
     */
    public static void removeRelease(Release release) throws IOException {
        log.debug("removeRelease({})", release);
        File target = new File(getGamesFolder(), release.getId());
        deleteDir(target);
    }
    
    public static File getHomeFolder() {
        return new File(System.getProperty("user.home"));
    }
    
    public static File getManagedJSettlersFolder() {
        return new File(getHomeFolder(), ".jsettlers/managed");
    }
    
    public static File getManagedTempFolder() {
        return new File(getManagedJSettlersFolder(), "temp");
    }
    
    public static File getDataFolder() {
        return new File(getManagedJSettlersFolder(), "data");
    }
    
    public static File getGamesFolder() {
        return new File(getManagedJSettlersFolder(), "game");
    }
    
    /** 
     * Folder for savegames and logfiles.
     */
    public static File getVarFolder() {
        return new File(getManagedJSettlersFolder(), "var");
    }
    
    public static void runRelease(Release release) throws IOException, InterruptedException {
        log.debug("runRelease({})", release);
        File target = new File(getGamesFolder(), release.getId());
        File jarfile = new File(target, "JSettlers/JSettlers.jar");
        
        execJarFile(jarfile);
    }

    /**
     * Runs an executable jar in a separate JVM.
     * 
     * @param jarfile the jar file to run
     * @throws IOException something went wrong
     * @throws InterruptedException something went wrong
     */
    public static void execJarFile(File jarfile) throws IOException, InterruptedException {
        File javaHome = new File(System.getProperty("java.home"));
        File java = new File(javaHome, "bin/java"); // may need a tweak on Windows
        
        if (!java.canExecute()) {
            // maybe we are pointing to the JLink provided binaries. Let's fall
            // back to the system-provided java installation
            log.info("it seems {} is not executable, falling back", java.getAbsolutePath());
            java = new File("/usr/bin/java");
        }

        List<String> command = new ArrayList<>();
        command.add(java.getAbsolutePath());
        command.add("-Xmx2G");
        command.add("-Dorg.lwjgl.util.Debug=true");
        command.add("-jar");
        command.add(jarfile.getAbsolutePath());
        command.add("--settlers-folder="+getDataFolder().getAbsolutePath());

        log.info("executing {}", command);
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
        
        File workingDir = getVarFolder();
        if (!workingDir.isDirectory()) {
            workingDir.mkdirs();
        }
        pb.directory(workingDir);
        
        Process p = pb.start();
        p.waitFor();
        int rc = p.exitValue();
        log.info("returned with {}", rc);
    }
    
    public static void addGoodiesToData(File goodiesFile) throws IOException {
        unzip(goodiesFile, getDataFolder());
    }
    
    public static void addDemoToData(File demoFile) throws IOException {
        unzipSelfExtractingZip(demoFile, getDataFolder());
    }
    
    public static void runAnt(File buildFile, Properties props) throws MalformedURLException, IOException {
//        Project p = new Project();
//
//        ProjectHelper helper = ProjectHelper2.getProjectHelper();
//        p.addReference("ant.projectHelper", helper);
//        helper.parse(p, buildFile);
//        
//        //helper.parse(p, buildFile.toURI().toURL());
//
//        for (Object key: props.keySet()) {
//            p.setUserProperty(String.valueOf(key), props.getProperty(String.valueOf(key)));
//        }
//        p.init();
//
//        log.debug(p.getName());
//        log.debug(p.getDefaultTarget());
//        p.executeTarget(p.getDefaultTarget());    

        URL url = Util.class.getClassLoader().getResource( "S3_Installer.xml" );
        InputStream in = url.openStream();
        EmbeddedAntProject prj = new EmbeddedAntProject( new File("."), "build-1.xml", in );
      
        prj.init();
      
        prj.setMessageLevel( Project.MSG_DEBUG );
      
        for (Object key: props.keySet()) {
            prj.setUserProperty(String.valueOf(key), props.getProperty(String.valueOf(key)));
        }
        String[][] params = new String[][] {
              { "property1", "value1" },
              { "property2", "value2" }
        };
        prj.executeTarget( prj.getDefaultTarget() );

    }
    
    /**
     * Finds the CD mount point in Linux.
     * If several CDROM drives are installed, only the first will be returned.
     * 
     * @return the file object to the CDROM, or null if not found
     */
    private static File getCdMountPointLinux() {
        // we assume to run on Linux
        // read /proc/mounts and scan for iso9660 filesystem
        try (Scanner scanner = new Scanner(new File("/proc/mounts"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.toLowerCase().contains("iso9660")) {
                    log.debug("line: {}", line);
                    StringTokenizer st = new StringTokenizer(line);
                    st.nextToken();
                    String mountPoint = st.nextToken();
                    log.debug("mount point: {}", mountPoint);
                    
                    return new File(mountPoint);
                }
            }
        } catch (Exception e) {
            log.error("Could not get cd mount point", e);
            return null;
        }
        
        return null;
    }

    /**
     * Finds the CD mount point in Linux.
     * If several CDROM drives are installed, only the first will be returned.
     * 
     * @return the file object to the CDROM, or null if not found
     */
    private static File getCdMountPointWindows() {
        File result = null;
        
        FileSystemView fsv = FileSystemView.getFileSystemView();
        for (File root: fsv.getRoots()) {
            String type = fsv.getSystemTypeDescription(root);
            log.debug("Filesystem {} is {}", root, type);
            
            if (type.toLowerCase().contains("cd") && null == result) {
                result = root;
                log.debug("  seems {} is the CDROM", root);
            }
        }
        return result;
    }
    
    /**
     * Returns the mount point or the filesystem root of the CDROM drive.
     * If several CDROM drives are installed, only the first will be returned.
     * 
     * @return the file object to the CDROM, or null if not found
     */
    public static File getCdMountPoint() {
        if (OsDetector.IS_UNIX) {
            return getCdMountPointLinux();
        } else if (OsDetector.IS_WINDOWS) {
            return getCdMountPointWindows();
        } else {
            log.warn("Unknown operating system {}", OsDetector.OS);
            return null;
        }
    }
    
    /**
     * Ensures the latest release on Github is also installed locally.
     * Also ensures we have no more than the last 5 releases and cleans up
     * older ones.
     */
    public static void installLatest() throws IOException {
        List<Release> githubReleases = Util.getGithubReleases();
        List<Release> installedReleases = Util.getInstalledReleases();

        // install if a newer one is available
        if (installedReleases.isEmpty() || installedReleases.get(0).getPublished_at().before(githubReleases.get(0).getPublished_at())) {
            Release latest = githubReleases.get(0);
            log.debug("Installing latest release {}", latest);

            installRelease(latest);

        }

        // remove if we have more than five
        while (installedReleases.size()>5) {
            Release r = installedReleases.get(installedReleases.size()-1);
            Util.removeRelease(r);
            installedReleases = Util.getInstalledReleases();
        }
    }
    
}
