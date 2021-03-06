/*
 */
package settlers.installer.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.cipher.CryptoCipherFactory;
import org.apache.commons.crypto.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settlers.installer.Util;

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

    /**
     * Returns the release checking flag.
     * 
     * @return the flag
     */
    public boolean isCheckReleases() {
        return checkReleases;
    }

    /**
     * Sets the release checking flag.
     * 
     * @param checkReleases the flag
     */
    public void setCheckReleases(boolean checkReleases) {
        this.checkReleases = checkReleases;
    }

    /**
     * Returns the prerelease checking flag.
     * 
     * @return the flag
     */
    public boolean isCheckPrereleases() {
        return checkPrereleases;
    }

    /**
     * Sets the prelease checking flag.
     * 
     * @param checkPrereleases the flag
     */
    public void setCheckPrereleases(boolean checkPrereleases) {
        this.checkPrereleases = checkPrereleases;
    }

    /**
     * Returns the artifact checking flag.
     * 
     * @return the flag
     */
    public boolean isCheckArtifacts() {
        return checkArtifacts;
    }

    /**
     * Sets the artifact checking flag.
     * 
     * @param checkArtifacts the flag
     */
    public void setCheckArtifacts(boolean checkArtifacts) {
        this.checkArtifacts = checkArtifacts;
    }

    /**
     * Returns the bug reporting support flag.
     * 
     * @return the flag
     */
    public boolean isSupportBugReporting() {
        return supportBugReporting;
    }

    /**
     * Sets the bug reporting support flag.
     * 
     * @param supportBugReporting the flag
     */
    public void setSupportBugReporting(boolean supportBugReporting) {
        this.supportBugReporting = supportBugReporting;
    }

    /**
     * Returns the github username.
     * 
     * @return the username
     */
    public String getGithubUsername() {
        return githubUsername;
    }

    /**
     * Sets the github username.
     * 
     * @param githubUsername the username
     */
    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    /**
     * Returns the github token. It will be decrypted automatically.
     * 
     * @return the token
     */
    public String getGithubToken() {
        try {
            return decrypt(githubToken);
        } catch (Exception e) {
            log.warn("Could not decrypt Github token", e);
            return "";
        }
    }

    /**
     * Sets the github token. It will be encrypted immediately.
     * 
     * @param githubToken the token
     */
    public void setGithubToken(String githubToken) {
        try {
            this.githubToken = encrypt(githubToken);
        } catch (Exception e) {
            log.warn("Could not encrypt Github token", e);
        }
    }

    /**
     * Stores the configuration to the given file.
     * 
     * @param target the file to write
     */
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
    
    /**
     * Loads the configuration from the given file.
     * 
     * @param source the file to read
     * @return the parsed configuration
     */
    public static Configuration load(File source) {
        Configuration c = new Configuration();

        Properties props = new Properties();
        try (InputStream in = new FileInputStream(source)) {
            props.load(in);
            c.checkReleases = "true".equals(props.getProperty("check.releases"));
            c.checkPrereleases = "true".equals(props.getProperty("check.prereleases"));
            c.checkArtifacts = "true".equals(props.getProperty("check.artifacts"));

            c.supportBugReporting = "true".equals(props.getProperty("support.bugreporting"));

            c.githubUsername = props.getProperty("github.user");
            c.githubToken = props.getProperty("github.token");
            
            // test decrypt
            c.getGithubToken();
        } catch (IOException e) {
            log.warn("Could not read configuration file {}", source, e);
            c.setGithubToken(null);
            c.setGithubUsername(null);
        }
        
        return c;
    }

    private static final SecretKeySpec key = new SecretKeySpec(getUTF8Bytes("1234567890123456"), "AES");
    private static final IvParameterSpec iv = new IvParameterSpec(getUTF8Bytes("1234567890123456"));
    private static final String transform = "AES/CBC/PKCS5Padding";
    private static String passwordSalt = Util.getHostname();

    /**
     * Encrypts the given string.
     * 
     * @param s the string to encrypt
     * @return the encrypted string
     * @throws IOException something went wrong
     * @throws InvalidKeyException something went wrong
     * @throws InvalidAlgorithmParameterException something went wrong
     * @throws ShortBufferException something went wrong
     * @throws IllegalBlockSizeException something went wrong
     * @throws BadPaddingException  something went wrong
     */
    public static String encrypt(String s) throws IOException, InvalidKeyException, InvalidAlgorithmParameterException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        Properties properties = new Properties();
        properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CryptoCipherFactory.CipherProvider.JCE.getClassName());
        
        final CryptoCipher encipher = Utils.getCipherInstance(transform, properties);
        log.trace("Cipher:  " + encipher.getClass().getCanonicalName());
        
        //Initializes the cipher with ENCRYPT_MODE, key and iv.
        encipher.init(Cipher.ENCRYPT_MODE, key, iv);
        //Continues a multiple-part encryption/decryption operation for byte array.
        
        final byte[] input = getUTF8Bytes(passwordSalt+s);
        final byte[] output = new byte[256];
        
        final int updateBytes = encipher.update(input, 0, input.length, output, 0);
        log.debug("updateBytes {}", updateBytes);
        //We must call doFinal at the end of encryption/decryption.
        final int finalBytes = encipher.doFinal(input, 0, 0, output, updateBytes);
        log.debug("finalBytes {}", finalBytes);
        //Closes the cipher.
        encipher.close();
 
        byte[] result = Arrays.copyOf(output, updateBytes+finalBytes);
        return new String(Hex.encodeHex(result));
    }
    
    /**
     * Decrypts the given string.
     * 
     * @param s the string to decrypt
     * @return the decrypted string
     * @throws IOException something went wrong
     * @throws InvalidKeyException something went wrong
     * @throws InvalidAlgorithmParameterException something went wrong
     * @throws ShortBufferException something went wrong
     * @throws IllegalBlockSizeException something went wrong
     * @throws BadPaddingException something went wrong
     * @throws DecoderException  something went wrong
     */
    public static String decrypt(String s) throws IOException, InvalidKeyException, InvalidAlgorithmParameterException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, DecoderException {
        // Now reverse the process using a different implementation with the same settings
        Properties properties = new Properties();
        properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CryptoCipherFactory.CipherProvider.JCE.getClassName());
        
        final CryptoCipher decipher = Utils.getCipherInstance(transform, properties);
        log.trace("Cipher:  " + decipher.getClass().getCanonicalName());
 
        decipher.init(Cipher.DECRYPT_MODE, key, iv);

        final byte[] output = Hex.decodeHex(s);
        final byte [] decoded = new byte[256];
        int doFinal = decipher.doFinal(output, 0, output.length, decoded, 0);
  
        String result = new String(decoded, StandardCharsets.UTF_8).trim();
        return result.substring(passwordSalt.length());
    }

    /**
     * Converts String to UTF8 bytes.
     *
     * @param input the input string
     * @return UTF8 bytes
     */
    private static byte[] getUTF8Bytes(final String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }
}
