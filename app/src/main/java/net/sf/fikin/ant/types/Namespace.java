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

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * 
 * created on Jan 3, 2007
 * @author fiykov
 * @version $Revision: 1.3 $
 * @since
 */
public class Namespace extends IfUnless {

    /** namespace uri */
    String uri;
    
    /** namespace prefix */
    String prefix;

    /*
     *      Bean methods
     */
    
    /**
     * set the namespace uri
     * @param uri
     * @antTaskParamRequired true
     */
    public void setUri(String uri) {
        this.uri = uri;
    }
    
    /**
     * get namespace uri
     * @return namespace uri
     */
    public String getUri() {
        return uri;
    }
    
    /**
     * set the namespace prefix
     * @param prefix
     * @antTaskParamRequired true
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    /**
     * get namespace prefix
     * @return namespace prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /*
     *      Business methods
     */
    
    /**
     * check if uri and prefix has been set
     */
    public void check(Task task) throws BuildException {
        if ( uri == null || uri.length()==0 )
            throw new BuildException( "Uri must be specified!", task.getLocation() );
        
        if ( prefix == null || prefix.length()==0 )
            throw new BuildException( "Prefix must be specified!", task.getLocation() );
    }
}
