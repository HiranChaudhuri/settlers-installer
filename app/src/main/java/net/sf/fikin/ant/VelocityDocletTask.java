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

import java.io.File;

import org.apache.tools.ant.BuildException;

/**
 * <h4>Dedicated Ant task for executing a Velocity Template via 
 * {@link net.sf.fikin.doclets.VelocityDoclet} <code>doclet</code></h4>
 * 
 * <p>Example: Execute a template against some java sources</p>
 * <code><pre>
 *      &lt;velocityDoclet template="my-template.vm" file="my-output.txt" &gt;
 *          &lt;packageset dir="src" defaultexcludes="yes"&gt;
 *              &lt;include name="net/sf/fikin/ant/**" /&gt;
 *          &lt;/packageset&gt;
 *      &lt;/velocityDoclet&gt;
 * </pre></code>
 * 
 * <p>Example: Pass current timestamp as extra Java System parameters read by 
 * the template</p>
 * <code><pre>
 *      &lt;velocityDoclet template="my-template.vm" file="my-output.txt" &gt;
 *          &lt;packageset dir="src" defaultexcludes="yes"&gt;
 *              &lt;include name="net/sf/fikin/ant/**" /&gt;
 *          &lt;/packageset&gt;
 *          &lt;param name="-J-DDSTAMP=${DSTAMP}" /&gt;
 *      &lt;/velocityDoclet&gt;
 * </pre></code>
 * 
 * created on Sep 24, 2006
 * @author fiykov
 * @version $Revision: 1.3 $
 * @since
 * 
 * @antTaskName velocityDoclet
 */
public class VelocityDocletTask extends DocletTask {

    /** template name */
    String templateName;
    
    /** output file */
    File outFile;
    
    /*
     *      Bean methods
     */
    
    /**
     * @param fqClassName doclet's class name
     * @deprecated One can't replace the built-in Velocity Doclet
     * @antTaskParamRequired false
     */
    @Deprecated
    public void setName(String fqClassName) {
        throw new BuildException( "parameter setting not allowed for this task" );
    }
    
    /**
     * set template name, it resolves either to a file or a classloader resource
     * @param templateName
     * @antTaskParamRequired true
     */
    public void setTemplate(String templateName) {
        addParam( "-template", templateName );
        this.templateName = templateName;
    }

    /**
     * set output file to be generated
     * @param outFile
     * @antTaskParamRequired true
     */
    public void setFile(File outFile) {
        addParam( "-file", outFile.toString() );
        this.outFile = outFile;
    }

    /**
     * if true indicate that a velocity.log is to be generated
     * @param flg
     * @antTaskParamRequired false
     */
    public void setVelocityLog(boolean flg) {
        if ( flg )
            addParam( "-velocitylog", "" );
    }
    
    /*
     *      Business methods
     */
    
    /**
     * check parameters
     */
    protected void check() throws BuildException {
    
        if ( templateName==null || templateName.length()==0 )
            throw new BuildException( "Template name must be specified!" );

        super.check();
    }
    
    /**
     * set the doclet class name
     */
    public void init() throws BuildException {
        super.init();
        super.setName( "net.sf.fikin.doclets.VelocityDoclet" );
    }

}
