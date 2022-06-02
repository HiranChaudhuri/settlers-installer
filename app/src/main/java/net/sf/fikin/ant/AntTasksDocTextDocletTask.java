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

/**
 * <h4>Generate single-page TEXT documentation file for all Ant Tasks found 
 * in the source</h4>
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
 * 
 * <li>The setter method must not be deprecatated</li>
 * </ul>
 * </p>
 * 
 * <p>All setter methods are resolved automatically based on Ant specification
 * for methods introspection</p>
 * 
 * <p>This task is based on {@link VelocityDocletTask}. The Velocity file 
 * <a href="ant-doc.txt.vm">ant-doc.html.vm</a> used as text-page template is located in this 
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
 * @version $Revision: 1.2 $
 * @since
 * 
 * @antTaskName antTasksDocTextDoclet
 */
public class AntTasksDocTextDocletTask extends AntTasksDocHtmlDocletTask {

    /**
     * Get the template name.
     * @return template name
     */
    protected String getTemplateName() {
        return getPackageName() + "/ant-doc.txt.vm"; 
    }
}
