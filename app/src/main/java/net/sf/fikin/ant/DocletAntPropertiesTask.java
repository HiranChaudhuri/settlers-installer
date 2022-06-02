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
 * Generate <code>ant.properties</code> file for all Ant Tasks found 
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
 * </ul>
 * 
 * <p>Example:</p>
 * <code><pre>
 *      /**
 *       * This is example class
 *       * @antTaskName exampleTask
 *       * /
 *      public class Example extends Task {
 *          ...
 *      }
 * </pre></code>
 * would result in:
 * <code><pre>
 *      ...
 *      exampleTask=Example
 *      ...
 * </pre></code>
 * 
 * created on Sep 24, 2006
 * @author fiykov
 * @version $Revision: 1.4 $
 * @since
 * 
 * @antTaskName antTasksPropertiesDoclet
 */
public class DocletAntPropertiesTask extends AntTasksDocHtmlDocletTask {

    /**
     * Get the template name.
     * @return template name
     */
    protected String getTemplateName() {
        return getPackageName() + "/ant.properties.vm"; 
    }

}
