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
 * Base for all types.
 * 
 * <p>Defined a method for consistency checking
 * 
 * created on Jan 3, 2007
 * @author fiykov
 * @version $Revision: 1.3 $
 * @since
 */
public interface Type {

    /**
     * Test if this class members are in a consistent state
     * for use in task execution.
     *  
     * @param task is enclosing ant task
     * @throws BuildException
     */
    public void check(Task task) throws BuildException;
}
