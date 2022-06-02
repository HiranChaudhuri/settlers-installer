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

import org.apache.tools.ant.Task;

/**
 * An Ant-type class providing with ability to  set
 * <code>if="property"</code> and/or <code>unless="property"</code>.
 * 
 * created on Dec 28, 2006
 * @author fiykov
 * @version $Revision: 1.3 $
 * @since
 */
public abstract class IfUnless implements Type {

    /** If property-set name. */
    private String ifProperty;
    
    /** Unless property-set name. */
    private String unlessProperty;

    /*
     *      Bean methods
     */
    
    /**
     * Set whether this param should be used.  It will be
     * used if the property has been set, otherwise it won't.
     * @param ifProperty name of property
     * @antTaskParamRequired false
     */
    public void setIf(String ifProperty) {
        this.ifProperty = ifProperty;
    }

    /**
     * Set whether this param should NOT be used. It
     * will not be used if the property has been set, otherwise it
     * will be used.
     * @param unlessProperty name of property
     * @antTaskParamRequired false
     */
    public void setUnless(String unlessProperty) {
        this.unlessProperty = unlessProperty;
    }
    
    /*
     *      Business methods
     */
    
    /**
     * Ensures that the param passes the conditions placed
     * on it with <code>if</code> and <code>unless</code> properties.
     */
    public boolean shouldUse(Task task) {
        if (ifProperty != null && task.getProject().getProperty(ifProperty) == null) {
            return false;
        } else if (unlessProperty != null
                && task.getProject().getProperty(unlessProperty) != null) {
            return false;
        }

        return true;
    }
    
}
