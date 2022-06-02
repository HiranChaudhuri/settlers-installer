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
 * Provide with simple <code>location="..."</code> attribute which later on
 * can be used for dependency (timestamping) checks.
 * 
 * created on Dec 28, 2006
 * @author fiykov
 * @version $Revision: 1.3 $
 * @since
 */
public class Depends extends IfUnless {

    File file;
    
    /**
     * Set parameter's location.
     * 
     * @param file
     * @throws MalformedURLException
     * @antTaskParamRequired true
     */
    public void setLocation(File file) throws MalformedURLException {
        this.file = file;
    }
    
    /**
     * Get the file set as location if nay.
     * 
     * @return file if location is set, otherwise null
     */
    public File getLocation() {
        return file;
    }

    /**
     * Test that name has been set.
     */
    public void check(Task task) throws BuildException {
        if ( file==null )
            throw new BuildException("Location must be specified", task.getLocation() );
    }

}
