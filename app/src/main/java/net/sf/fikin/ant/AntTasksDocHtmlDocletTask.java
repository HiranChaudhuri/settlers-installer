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

import org.apache.tools.ant.BuildException;

/**
 * Generate single-page HTML documentation file for all Ant Tasks found 
 * in the source.
 * 
 * <p>Recognized classes/tasks must qialify the following criteria:
 * <ul>
 * <li>They are public classes</li>
 * 
 * <li>Not abstract classes</li>
 * 
 * <li>Descendant (direct or indirect) of Ant's Task class 
 * <code>org.apache.tools.ant.Task</code></li>
 * 
 * <li>Having <code>@antTaskName</code> tag in their class comment, denoting the task name in Ant</li>
 * 
 * <li>Each mandatory setter method must have <code>@antTaskParamRequired</code> tag
 * in the method's comment</li>
 * </ul>
 * </p>
 * 
 * <p>All setter methods are resolved automatically based on Ant specification
 * for methods introspection</p>
 * 
 * <p>This task is based on {@link VelocityDocletTask}. The Velocity file 
 * <a href="ant-doc.html.vm">ant-doc.html.vm</a> used as HTML-page template is located in this 
 * package folder and loaded via the classloader.</p>
 * 
 * <p>Example:</p>
 * <code><pre>
 *      /**
 *       * This is example class
 *       * @antTaskName exampleTask
 *       * /
 *      public class Example extends Task {
 *          /**
 *           * setter method
 *           * @antTaskParamRequired true
 *           * /
 *          public void setName(String name) { ... }
 *          ...
 *      }
 * </pre></code>
 * 
 * created on Sep 24, 2006
 * @author fiykov
 * @version $Revision: 1.3 $
 * @since
 * 
 * @antTaskName antTasksDocHtmlDoclet
 */
public class AntTasksDocHtmlDocletTask extends VelocityDocletTask {

    /*
     *      Bean methods
     */
    
    /**
     * This is deprecated parameter, attemp to set it would result in build
     * exception.
     * @param templateName
     * @deprecated
     */
    @Deprecated
    public void setTemplate(String templateName) {
        throw new BuildException("parameter not supported!");
    }

    /*
     *      Business methods
     */
    
   /**
     * Set the template name.
     */
    public void init() throws BuildException {
        super.init();
        super.setTemplate( getTemplateName() );
    }
    
    /**
     * Get the template name.
     * @return template name
     */
    protected String getTemplateName() {
        return getPackageName() + "/ant-doc.html.vm"; 
    }
    
    /**
     * Get the current's class package name.
     * @return current's class package name
     */
    protected String getPackageName() {
        return this.getClass().getPackage().getName().replaceAll("\\.", "/" );
    }
}
