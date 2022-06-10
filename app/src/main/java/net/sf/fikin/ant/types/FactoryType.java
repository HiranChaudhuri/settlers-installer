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

import java.util.Vector;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * A generic type of JAXP type of factory.
 * 
 * <p>Contains ability to specify factory class explicitly
 * 
 * <p>Allows to set features/attributes to it, event ot default one
 * 
 * created on Jan 3, 2007
 * @author fiykov
 * @version $Revision: 1.2 $
 * @since
 */
public class FactoryType extends IfUnless {

    /** Custom defined factory class. */
    String fqclassname;
    
    /** Factory features/attributes. */
    Vector<Parameter> attributes = new Vector<>();
    
    /*
     *      Bean methods
     */
    
    /**
     * Set the factory class name.
     * 
     * @param fqclassname factory class name
     * @antTaskParamRequired false
     */
    public void setClassname(String fqclassname) {
        this.fqclassname = fqclassname;
    }
    
    /**
     * @return given factory classname
     */
    public String getClassname() {
        return fqclassname;
    }
    
    /**
     * Add factory attribute.
     * 
     * @param attribute to set to the factory
     * @antTaskParamRequired false
     */
    public void addAttibute(Parameter attribute) {
        attributes.add( attribute );
    }
    
    /**
     * @return given factory attributes
     */
    public Vector getAttributes() {
        return attributes;
    }
    
    /*
     *      Business methods
     */

    /**
     * Test for defined factory name and defined attrbutes/features (if any).
     */
    public void check(Task task) throws BuildException {
        if ( fqclassname != null && fqclassname.length()==0 )
            throw new BuildException( "Factory class must be specified!", 
                    task.getLocation() );
        
        if ( fqclassname==null && attributes.size()==0 )
            throw new BuildException( "Factory class or at least one factory attribute must be specified!", 
                    task.getLocation() );
    }
    
}
