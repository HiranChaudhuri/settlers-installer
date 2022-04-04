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
import org.apache.tools.ant.BuildException;

/**
 * An extended version of Ant's Parameter type with ability to set 
 * <code>if="property"</code> and <code>unless="property"</code>
 * 
 * created on Dec 28, 2006
 * @author fiykov
 * @version $Revision: 1.3 $
 * @since
 */
public class Parameter extends IfUnless {

    protected String name = null;
    protected String type = null;
    protected String value = null;

    /**
     * name of the parameter
     * @param name of the parameter
     * @antTaskParamRequired true
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * type of the parameter
     * @param type of the parameter
     * @antTaskParamRequired false
     */
    public final void setType(final String type) {
        this.type = type;
    }

    /**
     * value of the parameter
     * @param value of the parameter
     * @antTaskParamRequired false
     */
    public final void setValue(final String value) {
        this.value = value;
    }

    /**
     * get the name
     * @return name of the parameter
     */
    public final String getName() {
        return name;
    }

    /**
     * type of the parameter
     * @return type of the parameter
     */
    public final String getType() {
        return type;
    }

    /**
     * value of the parameter
     * @return value of the parameter
     */
    public final String getValue() {
        return value;
    }

    /**
     * test that name has been set
     */
    public void check(Task task) throws BuildException {
        if ( name==null || name.length()==0 )
            throw new BuildException("Name must be speficied", task.getLocation() );
    }

}
