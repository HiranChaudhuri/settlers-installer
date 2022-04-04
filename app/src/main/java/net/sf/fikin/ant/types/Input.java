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
 * <p>Implement a genuin input type
 * 
 * <p>Support file, property or nested text as an input
 * 
 * <p>Provides with convenience methods for readin the data as input stream and such
 * 
 * created on Jan 3, 2007
 * @author fiykov
 * @version $Revision: 1.3 $
 * @since
 */
public class Input implements Type {

    /** input as file */
    File inFile;
    
    /** input as tag's text */
    String inText;
    
    /** input as value of a property */
    String inPropertyName;
    
    /*
     *      Bean methods
     */
    
    /**
     * set input file
     * @param in is input file
     * @antTaskParamRequired this or property or inner text must be given
     */
    public void setFile(File in) {
        inFile = in;
    }
    
    /**
     * set input property name
     * @param propertyName
     * @antTaskParamRequired this or file or inner text must be given
     */
    public void setProperty(String propertyName ) {
        inPropertyName = propertyName;
    }
    
    /**
     * set nested tag's text
     * @param text
     * @antTaskParamRequired this or property or file must be given
     */
    public void addText(String text) {
        inText = text;
    }
    
    /*
     *      Business methods
     */
    
    /**
     * test that any of the members has been set
     * test if too many members have been set
     * test if file is existing
     */
    public void check(Task task) throws BuildException {
        if ( inFile == null && 
                (inText == null || inText.length() == 0) && 
                (inPropertyName == null || inPropertyName.length() == 0) )
            throw new BuildException( 
                    "File, property or inner text must be provided!", 
                    task.getLocation() );
        
        int i = 0;
        i = ( inFile==null ) ? i : ++i;
        i = ( inPropertyName==null ) ? i : ++i;
        i = ( inText==null ) ? i : ++i;
        if ( i > 1 )
            throw new BuildException( 
                    "Only one of file, property or inner text can be provided!", 
                    task.getLocation() );
        
        if ( inFile != null && !inFile.exists() )
            throw new BuildException( 
                    "File "+inFile+" does not exist!", 
                    task.getLocation() );
    }

    /**
     * get a reader object for the input
     * @param task enclosing task
     * @return Reader object for that input
     * @throws BuildException in case some internal error occured
     */
    public Reader getReader(Task task) throws BuildException {
        try {
            if ( inFile != null )
                return new FileReader( inFile );
            else if ( inPropertyName != null )
                return new StringReader( task.getProject().getProperty( inPropertyName ) );
            else
                return new StringReader( inText );
        } catch(FileNotFoundException e) {
            throw new BuildException( e, task.getLocation() );
        }
    }
    
    /**
     * get a input stream object for the input
     * @param task enclosing task
     * @return InputStream object for that input
     * @throws BuildException in case some internal error occured
     */
    public InputStream getInputStream(Task task) throws BuildException {
        try {
            if ( inFile != null )
                return new FileInputStream( inFile );
            else if ( inPropertyName != null )
                return new ByteArrayInputStream( task.getProject().getProperty( inPropertyName ).getBytes() );
            else
                return new ByteArrayInputStream( inText.getBytes() );
        } catch(FileNotFoundException e) {
            throw new BuildException( e, task.getLocation() );
        }
    }
}
