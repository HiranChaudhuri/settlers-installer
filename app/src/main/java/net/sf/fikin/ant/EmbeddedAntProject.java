/*
 * Copyright (c) 2005 Nikolay Fiykov.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.fikin.ant;

import java.io.*;

import org.apache.tools.ant.*;
import org.apache.tools.ant.input.*;

/**
 * <h4>Embedded Ant Project into custom java applciation</h4>
 * 
 * <p>Some times one would like to have an Ant Project (backed by some 
 * build.xml file) available into a custom java application. Embedding Ant
 * is not strainght formward if one would like to take into account <b>logging</b>
 * and <b>build.xml initialization</b>. For all such cases this class was
 * made for.</p>
 * 
 * <p>This class provides:</p><ul>
 * <li>Initialize Ant logging to stdout/stderr without closing it (as some 
 * tasks do just that)</li>
 * <li>Initialize Ant Project with external build.xml file or from classpath as
 * input stream of data</li>
 * <li>Cusotmize the logging outputs</li>
 * <li>Cusotmize the logging level</li>
 * <li>Execute targets</li>
 * </ul>
 * 
 * <p>Example:</p><code><pre>
 *      ClassLoader cl = this.getClass().getClassLoader();
 *      InputStream in = cl.getResourceAsStream( "some build.xml in classpath" );
 *      EmbeddedAntProject prj = new EmbeddedAntProject( binFile, "build-1.xml", in );
 *      
 *      prj.init();
 *      
 *      prj.executeTarget( prj.getDefaultTarget() );
 *      
 *      prj.setMessageLevel( Project.MSG_DEBUG );
 *      prj.executeTarget( "new-target" );
 *      
 *      String[][] params = new String[][] {
 *              { "property1", "value1" },
 *              { "property2", "value2" }
 *      };
 *      prj.executeTarget( "target2", params );
 *      
 *      ...
 * </pre></code>
 * 
 * created on Aug 9, 2006
 * @author fiykov
 * @version $Revision: 1.5 $
 * @since
 */
public class EmbeddedAntProject extends Project {

    /** Original std err. */
    PrintStream stdErr;
    /** Original stdout. */
    PrintStream stdOut;
    /** Original stdin. */
    InputStream stdIn;

    /** Build file content, its name might be temporary. */
    File buildFile;
    
    /** Build file name. */
    String buildFileName;
    
    /** Base dir of the build file. */
    File baseDir;
    
    /** Build logger, by default redirects to std streams. */
    BuildLogger logger;
    
    /** Logging level, by default MSG_INFO. */
    int loggingLevel = Project.MSG_INFO;

    /**
     * No one is allowed to instantiate Ant project
     * without build file.
     */
    private EmbeddedAntProject() {
    }
    
    /**
     * Instantiate a new Ant project with given build file.
     * 
     * @param buildFile
     */
    public EmbeddedAntProject(File buildFile) throws BuildException
    {
        if ( buildFile==null || !buildFile.exists() || !buildFile.isFile() )
            throw new BuildException( "Build file must be defined and existing" );
        
        this.buildFile     = buildFile;
        this.buildFileName = buildFile.toString();
        this.baseDir       = buildFile.getParentFile();
    }
    
    /**
     * Instantiate a new Ant project with given file name and content.
     * 
     * @param baseDir
     * @param buildFileName
     * @param buildFileContent
     * @throws BuildException
     */
    public EmbeddedAntProject(File baseDir,String buildFileName, InputStream buildFileContent) 
    throws BuildException
    {
        if ( baseDir==null || !baseDir.exists() )
            throw new BuildException( "Base dir must be defined and existing" );
        if ( buildFileName == null || buildFileName.length()==0 )
            throw new BuildException( "Build file name must be defined" );
        if ( buildFileContent == null )
            throw new BuildException( "Build file content must be defined" );
        
        this.buildFile     = makeItARealFile( buildFileContent );
        this.buildFileName = buildFileName;
        this.baseDir       = baseDir;
    }

    /**
     * Initialize Ant project and make it ready for execution of targets.
     */
    public void init() throws BuildException {

        // set parent classloader
        setCoreLoader( null );
        // by default read from input
        setDefaultInputStream( System.in );

        // set input hanlder
        setInputHandler( new DefaultInputHandler() );
        
        // set default logger
        logger = new DefaultLogger();
        logger.setMessageOutputLevel( loggingLevel );
        logger.setOutputPrintStream(  System.out );
        logger.setErrorPrintStream(   System.err );
        // Add the default listener
        addBuildListener( logger );

        // init project object with default settings
        super.init();
        
        // set ant properties
        setUserProperty("ant.version", org.apache.tools.ant.Main.getAntVersion());
        setUserProperty("ant.file", buildFileName );
        setBaseDir( baseDir );

        // parse build.xml file
        ProjectHelper.getProjectHelper().parse( this, buildFile );
        
        setBaseDir( baseDir );
    }

    /**
     * copy the stream to a temp file.
     * 
     * this is a hack until more time avail for a diff solution
     * 
     * @param in
     * @return a temporary file with the content of that stream
     * @throws IOException
     */
    protected File makeItARealFile(InputStream in) throws BuildException {
        try {
            File tmpFile = File.createTempFile( "build", "xml" );
            OutputStream os = new BufferedOutputStream( new FileOutputStream( tmpFile ) );
            
            byte[] buf = new byte[4096];
            int ret;
            while( (ret=in.read(buf)) != -1 ) {
                os.write( buf, 0, ret );
            }
            
            os.close();
            
            return tmpFile;
        } catch (IOException e) {
            throw new BuildException(e);
        }
    }

    /**
     * Set logging level for the default logger.
     * 
     * @param level as defined by Project.MSG_? constants
     */
    public void setMessageLevel(int level) {
        loggingLevel = level;
        if ( logger != null )
            logger.setMessageOutputLevel( level );
    }
    
    /**
     * Execute given target.
     * 
     * @throws FileNotFoundException
     */
    public void executeTarget(String target) throws BuildException  
    {
        executeTarget( target, new String[][]{} );
    }

    /**
     * Execute given target with supplied properties.
     * 
     * @throws FileNotFoundException
     */
    public void executeTarget(String target, String[][] params) throws BuildException  
    {
        // remember original stdio settings
        stdErr = System.err;
        stdOut = System.out;
        stdIn  = System.in;

        // redirect std streams to ant-project logging
        System.setIn( new DemuxInputStream( this ));
        System.setOut(new NonClosingPS(new PrintStream(
                new DemuxOutputStream(this, false) )));
        System.setErr(new NonClosingPS(new PrintStream(
                new DemuxOutputStream(this, true) )));

        try {

            // fire start even
            fireBuildStarted();

            // set target parameters if any
            if ( params != null ) {
                for( int i=0; i<params.length; i++ ) {
                    setProperty( params[i][0], params[i][1] );
                }
            }
            
            // execute main tafc target
            super.executeTarget( target );
            
            // fire end even
            fireBuildFinished( null );
            
        } finally {

            // restore original std streams
            System.setOut( stdOut );
            System.setErr( stdErr );
            System.setIn(  stdIn  );
        }
    }

    
    /**
     * This is a print stream but non-closing.
     * 
     * handy when playing with tasks which happen to close stdout.
     * very funny fellas.
     * 
     * @author fiykov
     *
     */
    public class NonClosingPS extends PrintStream {
        PrintStream out;
        
        /**
         * Create a new NonClosing print stream.
         * 
         * @param out the original printstream.
         */
        public NonClosingPS(PrintStream out) {
            super(out, false);
            this.out = out;
        }
        
        /**
         * Closed this print stream. It does not forward the close message to the
         * original print stream.
         */
        public void close() {
        }
        
        /**
         * Returns the original print stream.
         * 
         * @return the print stream
         */
        public PrintStream getBase() {
            return out;
        }
    }
}
