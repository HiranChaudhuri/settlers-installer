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

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * <p>Support retrieval of an input XML document as DOM tree
 * 
 * <p>Provides with customization of the DOM facotry
 * 
 * created on Jan 3, 2007
 * @author fiykov
 * @version $Revision: 1.2 $
 * @since
 */
public class XMLInput extends Input {

    /** indicate if XML factory is to be namespace aware, by default true */
    boolean namespaceAware = true;
    
    /*
     *      Bean methods
     */
    
    /**
     * set if DOM factory to be namespace aware
     * @param flg when true DOM facotry will be namespace aware, <p>by default true
     * @antTaskParamRequired false
     */
    public void setNamespaceAware(boolean flg) {
        namespaceAware = flg;
    }
    
    /*
     *      Business methods
     */

    /**
     * get the input as a DOM tree
     * @param task enclosing task
     * @return InputStream object for that input
     * @throws FileNotFoundException is not supposed to happen because file 
     * existence has been tested by {@link #check(Task)} already
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public Document getDocument(Task task) throws BuildException {
        try {
            DocumentBuilderFactory factory = getFactory();
            
            setFactorySettings( factory );
            
            DocumentBuilder builder = factory.newDocumentBuilder();
        
            return builder.parse( getInputStream(task) );
        } catch(ParserConfigurationException e) {
            throw new BuildException( e, task.getLocation() );
        } catch(SAXException e) {
            throw new BuildException( e, task.getLocation() );
        } catch(IOException e) {
            throw new BuildException( e, task.getLocation() );
        }
    }

    /**
     * obtain a reference to the DOM factory
     * @return DOM factory builder
     */
    protected DocumentBuilderFactory getFactory() {
        return DocumentBuilderFactory.newInstance();
    }
    
    /**
     * set internal properties of the DOM facotry before creating a builder
     * @param factory
     */
    protected void setFactorySettings(DocumentBuilderFactory factory) {
        factory.setNamespaceAware( namespaceAware );
    }
}
