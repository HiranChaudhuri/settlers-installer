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
package net.sf.fikin.ant.types;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Adds <code>expression="..."</code> attribute to the standard Parameter type.
 * 
 * It adds also <code>location="some file"</code> attribute which resolts to
 * an file URL. This is needed by Xalan for instance where passing location
 * of external to the transofmation files (resolved via XPath document() function)
 * require an URL rather than simple file name.
 * 
 * created on Dec 28, 2006
 * @author fiykov
 * @version $Revision: 1.3 $
 * @since
 */
public class XsltParam extends Parameter {

    File file;
    
    /**
     * parameter's expression
     * @param value
     * @antTaskParamRequired this or "location" must be specified
     */
    public void setExpression(String value) {
        setValue( value );
    }

    /**
     * set parameter's location
     * @param file
     * @throws MalformedURLException
     * @antTaskParamRequired this or "expression" must be specified
     */
    public void setLocation(File file) throws MalformedURLException {
        this.file = file;
        setValue( file.toURL().toExternalForm() );
    }
    
    /**
     * get the file set as location if nay
     * @return file if location is set, otherwise null
     */
    public File getLocation() {
        return file;
    }

    /**
     * test that expression or location has been set
     */
    public void check(Task task) throws BuildException {
        if ( getValue()==null || getValue().length()==0 )
            throw new BuildException("Expression or location must be speficied", 
                    task.getLocation() );
    }

}
