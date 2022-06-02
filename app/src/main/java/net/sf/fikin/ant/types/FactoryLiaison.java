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
import java.util.Enumeration;

import org.apache.tools.ant.BuildException;

/**
 * A generic type of JAXP type of factory.
 * 
 * <p>Contains ability to instnatiate factory class explicitly
 * 
 * <p>Allows to set features/attributes to it, event ot default one
 * 
 * created on Jan 3, 2007
 * @author fiykov
 * @version $Revision: 1.3 $
 * @since
 */
public abstract class FactoryLiaison {

    /** Factory information to be used. */
    FactoryType factoryType;
    
    /** Factory to be used, internal member. */
    Object factory;
    
    /*
     *      Bean methods
     */
    
    /**
     * Assign factory type object used to identify the actual factory.
     * 
     * @param factoryType
     */
    public void setFactoryType(FactoryType factoryType) {
        this.factoryType = factoryType;
    }
    
    /*
     *      Business methods
     */

    /**
     * instantiate a new factory if needed.
     * 
     * @return factory to be used
     */
    public Object getFactory() {
        if ( factory == null ) {
            if ( factoryType == null || factoryType.getClassname() == null )
                factory = newFactory();
            else
                factory = newFactory( factoryType.getClassname() );
            
            if ( factoryType != null )
                setAttributes( factory, factoryType.getAttributes() );
        }
        return factory;
    }

    /**
     * Create new default factory instance.
     * 
     * @return new default factory
     */
    protected abstract Object newFactory() throws BuildException;

    /**
     * Instantiate new factory from given classname.
     * <p>uses <code>Class.findClass()</code> and <code>Class.newInstance()</code>
     * to create it
     * <p>This presumes all factories are simply instantiated
     * 
     * @param fqclassname
     * @return new factory
     * @throws BuildException
     */
    protected Object newFactory(String fqclassname) throws BuildException {
        try {
            Class cls = Class.forName( fqclassname );
            return cls.newInstance();
        } catch(ClassNotFoundException e) {
            throw new BuildException( e );
        } catch(InstantiationException e) {
            throw new BuildException( e );
        } catch(IllegalAccessException e) {
            throw new BuildException( e );
        }
    }
    
    /**
     * Set all attirbutes to the factory.
     * 
     * @param factory
     * @param attrs
     */
    protected void setAttributes( Object factory, Vector attrs ) {
        Enumeration e = attrs.elements();
        while( e.hasMoreElements() ) {
            setAttribute( factory, (Parameter) e.nextElement() );
        }
    }
    
    /**
     * Set a single attribute to the factory.
     * 
     * @param factory
     * @param param
     */
    protected abstract void setAttribute( Object factory, Parameter param ) 
    throws BuildException;
}
