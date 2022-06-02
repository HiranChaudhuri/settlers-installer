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

import java.io.*;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Implement a genuine output type.
 * 
 * <p>Support file or property an output
 * 
 * <p>Provides with ability to specify delimiter strings if enclosing tasks
 * do wish to store records-like data
 * 
 * created on Jan 3, 2007
 * @author fiykov
 * @version $Revision: 1.6 $
 * @since
 */
public class Output implements Type {

    /** Output as file. */
    File outFile;
    
    /** Output as property name. */
    String outPropertyName;
    
    /** 
     * Records delimiter.
     * <p>by default:
     * <ul>
     * <li>single space for property</li><li>new line for file</li>
     * </ul>
     */
    String delimiter;
    
    /** Append to the output file or property, by default false. */
    boolean append = false;
    
    /** Trim the resulting value before setting it, by default false. */
    boolean trim = false;
    
    /*
     *      Bean methods
     */
    
    /**
     * Set output file.
     * 
     * @param in is output file
     * @antTaskParamRequired this or property must be given
     */
    public void setFile(File in) {
        outFile = in;
    }
    
    /**
     * Set output property name.
     * 
     * @param propertyName
     * @antTaskParamRequired this or file must be given
     */
    public void setProperty(String propertyName ) {
        outPropertyName = propertyName;
    }
    
    /**
     * Set records delimiter.
     * <p>typically tasks can use that string to separate lines of data instead
     * of new line
     * @param delimiter to use
     * <p>by default:<ul><li>single space for property</li><li>new line for file</li></ul> 
     * @antTaskParamRequired false
     */
    public void setDelimiter(String delimiter ) {
        this.delimiter = delimiter;
    }
    
    /**
     * Append to the output file.
     * 
     * @param flg append to the file or property value if true, 
     * <p>by default it is false
     * @antTaskParamRequired false
     */
    public void setAppend(boolean flg) {
        this.append = flg;
    }
    
    /**
     * Trim the resulting value before setting it to the property.
     * <p>by default true
     * <p>has no meaning for file
     * 
     * @param flg trim if set to true
     * @antTaskParamRequired false
     */
    public void setTrim(boolean flg) {
        trim = flg;
    }
    
    /*
     *      Business methods
     */
    
    /**
     * Test that any of the members has been set.
     */
    public void check(Task task) throws BuildException {
        if ( outFile == null && 
                (outPropertyName == null || outPropertyName.length() == 0) )
            throw new BuildException( 
                    "File or property must be provided!", 
                    task.getLocation() );
        
        if ( outFile != null && outFile.exists() )
            task.log( "File "+outFile+" exists, will overwrite it" );
    }
    
    /**
     * Get writer associated with this output tag.
     * @param task is enclosing task
     * @return writer associated with this output tag
     */
    public DelimitedBufferedWriter getWriter(Task task) throws BuildException {
        
        // set delimiter accordingly
        // for properties it is a single space
        // for files it is a new line
        if ( outPropertyName!=null && delimiter==null )
            delimiter = " ";
        else if ( outFile!=null && delimiter==null )
            delimiter = System.getProperty( "line.separator" );

        return new DelimitedBufferedWriter( 
                new OutputStreamWriter( getOutputStream(task) ), delimiter );
    }
    
    /**
     * Get output stream associated with this output tag.
     * 
     * @param task is enclosing task
     * @return output stream associated with this output tag
     */
    public OutputStream getOutputStream(Task task) {
        try {
            
            OutputStream out;
            
            if ( outFile != null ) {
                // file, use line separator as default delimiter
                out = new FileOutputStream( outFile, append );

            } else { 
                // property, use single space as default delimiter
                out = new PropertyOutputStream( outPropertyName, task, append, trim );
            }
            
            return new BufferedOutputStream( out );
            
        } catch(IOException e) {
            throw new BuildException(e, task.getLocation() );
        }
    }
    
    /**
     * Get output writer.
     * @param task
     * @return print writer
     */
    public DelimitedPrintWriter getPrintWriter(Task task) {
        return new DelimitedPrintWriter( getWriter( task ) );
    }
    
}
