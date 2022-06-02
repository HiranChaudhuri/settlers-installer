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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.tools.ant.Task;

/**
 * Output stream writing in a Ant Project property.
 * 
 * <p>All write opearations are backed by a StringWriter
 * 
 * <p>Upon close() it will populate the property's value
 * 
 * created on Jan 3, 2007
 * @author fiykov
 * @version $Revision: 1.4 $
 * @since
 */
public class PropertyOutputStream extends ByteArrayOutputStream {

    /** Property name. */
    String propertyName;
    
    /** Enclosing task. */
    Task task;
    
    /** Trim value before setting it. */
    boolean trim;
    
    /**
     * Instantiate a new property writer with given data.
     * 
     * @param propertyName to store the value to, if exists it will overwrite it
     * @param task enclosing task
     * @param append it will add to the property value if set to true
     * @param trim value before setting it
     */
    public PropertyOutputStream(String propertyName, Task task, boolean append, 
            boolean trim) 
    {
        this.task = task;
        this.propertyName = propertyName;
        this.trim = trim;
        if ( append ) {
            String val = task.getProject().getProperty( propertyName );
            if ( val != null && val.length() > 0 ) {
                byte[] arr = val.getBytes();
                write( arr, 0, arr.length );
            }
        }
    }
    /**
     * Close the stream and populate property's value.
     */
    public void close() throws IOException {
        super.close();
        // force property value set
        String str = super.toString();
        if ( trim )
            str = str.trim();
        task.getProject().setProperty( propertyName, str );
    }
    
    /**
     * For convenience only method.
     * 
     * @param str
     */
    public void write(String str) {
        byte[] arr = str.getBytes();
        write( arr, 0, arr.length );
    }
    
}
