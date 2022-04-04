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

import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Javadoc;
import org.apache.tools.ant.taskdefs.Javadoc.DocletInfo;
import org.apache.tools.ant.taskdefs.Javadoc.DocletParam;
import org.apache.tools.ant.taskdefs.Javadoc.PackageName;
import org.apache.tools.ant.types.DirSet;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

/**
 * <h4>Ant task tailored at running JavaDoc Doclets</h4>
 * 
 * <p>Example: Execute <code>MyDoclet</code> against some java sources</p>
 * <code><pre>
 *      &lt;doclet name="net.sf.fikin.ant.MyDoclet" &gt;
 *          &lt;packageset dir="src" defaultexcludes="yes"&gt;
 *              &lt;include name="net/sf/fikin/ant/**" /&gt;
 *          &lt;/packageset&gt;
 *      &lt;/doclet&gt;
 * </pre></code>
 * 
 * <p>Example: Pass current timestamp as extra Java System parameters used by 
 * <code>MyDoclet</code></p>
 * <code><pre>
 *      &lt;doclet name="net.sf.fikin.ant.MyDoclet" &gt;
 *          &lt;packageset dir="src" defaultexcludes="yes"&gt;
 *              &lt;include name="net/sf/fikin/ant/**" /&gt;
 *          &lt;/packageset&gt;
 *          &lt;param name="-J-DDSTAMP=${DSTAMP}" /&gt;
 *      &lt;/doclet&gt;
 * </pre></code>
 * 
 * created on Sep 24, 2006
 * @author fiykov
 * @version $Revision: 1.7 $
 * @since
 * 
 * @antTaskName doclet
 */
public class DocletTask extends Task {

    /** internally instantiated doclet object */
    DocletInfo doclet;
    
    /** internally instantiated javadoc object */
    Javadoc javadoc;
    
    /**
     * instantiate a javadoc and embedded doclet objects
     */
    public DocletTask() {
        javadoc = new Javadoc();
        javadoc.init();
        doclet  = javadoc.createDoclet();
    }
    
    /*
     *      Bean methods
     */
    
    /**
     * set doclet's fully-qualified class name
     * @param fqClassName doclet's class name 
     * @antTaskParamRequired true
     */
    public void setName(String fqClassName) {
        doclet.setName( fqClassName );
    }
    
    /**
     * @param path doclet's classpath
     * @antTaskParamRequired false
     */
    public void setClasspath(Path path) {
        doclet.setPath( path );
    }
    
    /**
     * Adds a reference to a CLASSPATH defined elsewhere.
     *
     * @param r the reference to an instance defining the classpath.
     * @antTaskParamRequired false
     */
    public void setClasspathRef(Reference r) {
        doclet.setPathRef( r );
    }

    /**
     * Create a Path to be configured with the classpath to use
     *
     * @return a new Path instance to be configured with the classpath.
     * @antTaskParamRequired false
     */
    public Path createClasspath() {
        Path p = new Path( getProject() );
        doclet.setPath( p );
        return p.createPath();
    }
    
    /**
     * Set the boot classpath to use.
     *
     * @param path the boot classpath.
     * @antTaskParamRequired false
     */
    public void setBootclasspath(Path path) {
        javadoc.setBootclasspath( path );
    }

    /**
     * Create a Path to be configured with the boot classpath
     *
     * @return a new Path instance to be configured with the boot classpath.
     * @antTaskParamRequired false
     */
    public Path createBootclasspath() {
        return javadoc.createBootclasspath();
    }

    /**
     * Adds a reference to a CLASSPATH defined elsewhere.
     *
     * @param r the reference to an instance defining the bootclasspath.
     * @antTaskParamRequired false
     */
    public void setBootClasspathRef(Reference r) {
        javadoc.setBootClasspathRef( r );
    }

    /**
     * Specify where to find source file
     *
     * @param src a Path instance containing the various source directories.
     * @antTaskParamRequired  At least one of the three or nested 
     * &lt;sourcepath&gt;, &lt;fileset&gt; or &lt;packageset&gt;
     */
    public void setSourcepath(Path src) {
        javadoc.setSourcepath( src );
    }

    /**
     * Create a path to be configured with the locations of the source
     * files.
     *
     * @return a new Path instance to be configured by the Ant core.
     * @antTaskParamRequired  At least one of the three or nested 
     * &lt;sourcepath&gt;, &lt;fileset&gt; or &lt;packageset&gt;
     */
    public Path createSourcepath() {
        return javadoc.createSourcepath();
    }

    /**
     * Adds a reference to a CLASSPATH defined elsewhere.
     *
     * @param r the reference containing the source path definition.
     * @antTaskParamRequired  At least one of the three or nested 
     * &lt;sourcepath&gt;, &lt;fileset&gt; or &lt;packageset&gt;
     */
    public void setSourcepathRef(Reference r) {
        javadoc.setSourcepathRef( r );
    }

    /**
     * Create a doclet parameter to be configured by Ant.
     * @return a new DocletParam instance to be configured.
     * @antTaskParamRequired false
     */
    public DocletParam createParam() {
        return doclet.createParam();
    }
    
    /**
     * Should the build process fail if javadoc fails (as indicated by
     * a non zero return code)?
     *
     * <p>Default is false.</p>
     * @antTaskParamRequired false
     */
    public void setFailonerror(boolean b) {
        javadoc.setFailonerror( b );
    }

    /**
     * Enables the -source switch, will be ignored if javadoc is not
     * the 1.4 version.
     *
     * @antTaskParamRequired false
     */
    public void setSource(String source) {
        javadoc.setSource( source );
    }
    
    /**
     * Run javadoc in verbose mode
     *
     * @param b true if operation is to be verbose.
     * @antTaskParamRequired false
     */
    public void setVerbose(boolean b) {
        javadoc.setVerbose( b );
    }

    /**
     * Sets the actual executable command to invoke, instead of the binary
     * <code>javadoc</code> found in Ant's JDK.
     * @antTaskParamRequired false
     */
    public void setExecutable(String executable) {
        javadoc.setExecutable( executable );
    }

    /**
     * Adds a packageset.
     *
     * <p>All included directories will be translated into package
     * names be converting the directory separator into dots.</p>
     * @antTaskParamRequired false
     */
    public void addPackageset(DirSet packageSet) {
        javadoc.addPackageset( packageSet );
    }

    /**
     * Adds a fileset.
     *
     * <p>All included files will be added as sourcefiles.  The task
     * will automatically add
     * <code>includes=&quot;**&#47;*.java&quot;</code> to the
     * fileset.</p>
     * @antTaskParamRequired false
     */
    public void addFileset(FileSet fs) {
        javadoc.addFileset( fs );
    }

    /**
     * Enables the -linksource switch, will be ignored if javadoc is not
     * the 1.4 version. Default is false
     * @antTaskParamRequired false
     */
    public void setLinksource(boolean b) {
        javadoc.setLinksource( b );
    }

    /**
     * The name of a file containing the packages to process.
     *
     * @param src the file containing the package list.
     * @antTaskParamRequired false
     */
    public void setPackageList(String src) {
        javadoc.setPackageList( src );
    }

    /**
     * Set the package names to be processed.
     *
     * @param packages a comma separated list of packages specs
     *        (may be wildcarded).
     *
     * @see #addPackage for wildcard information.
     * @antTaskParamRequired false
     */
    public void setPackagenames(String packages) {
        javadoc.setPackagenames( packages );
    }

    /**
     * Add a single package to be processed.
     *
     * If the package name ends with &quot;.*&quot; the Javadoc task
     * will find and process all subpackages.
     *
     * @param pn the package name, possibly wildcarded.
     * @antTaskParamRequired false
     */
    public void addPackage(PackageName pn) {
        javadoc.addPackage( pn );
    }

    /**
     * Set the list of packages to be excluded.
     *
     * @param packages a comma separated list of packages to be excluded.
     *        This may not include wildcards.
     * @antTaskParamRequired false
     */
    public void setExcludePackageNames(String packages) {
        javadoc.setExcludePackageNames( packages );
    }

    /*
     *      Business methods
     */

    /**
     * validate already set properties before continuing with javadoc generation
     */
    public void execute() throws BuildException {
        
        check();
        
        // execute the javadoc
        init_javadoc();
        javadoc.execute();
    }

    /**
     * init javadoc object prior to execution
     */
    protected void init_javadoc() throws BuildException {
        // set default classpath if not explicitely given
        if ( doclet.getPath() == null ) {
            doclet.setPath( Path.systemClasspath );
        }
        
        // set javadoc members
        javadoc.setClasspath( doclet.getPath() );
        javadoc.setProject( getProject() );
        javadoc.setOwningTarget( getOwningTarget() );
        javadoc.setTaskName( getTaskName() );
    }

    /**
     * consistency check on params
     */
    protected void check() throws BuildException {
        
        if ( doclet.getName()==null || doclet.getName().length()==0 )
            throw new BuildException( "Doclet must be specified!" );
        
        //if ( doclet.getPath()==null )
            //throw new BuildException( "Doclet's classpath must be specified!" );
    }

    /**
     * an easy way to add a new Doclet parameter
     * @param name parmaeter's name
     * @param value parameter's value
     */
    protected void addParam(String name, String value) {
        DocletParam p = doclet.createParam();
        p.setName( name );
        p.setValue( value );
    }
    
}
