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

import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.apache.tools.ant.BuildException;

/**
 * XPathFactory implementation for the FactoryLiaison interface
 * 
 * <p>Supports only boolean (true/false) attribute values
 * 
 * created on Jan 3, 2007
 * @author fiykov
 * @version $Revision: 1.2 $
 * @since
 */
public class XPathFactoryLiaison extends FactoryLiaison {

    /**
     * @return new XPathFactory
     */
    protected Object newFactory() {
        return XPathFactory.newInstance();
    }

    /**
     * @param factory is an XPathFactory
     * @param param is name and value (boolean values only)
     */
    protected void setAttribute(Object factory, Parameter param)
    throws BuildException
    {
        try {
            XPathFactory xpf = (XPathFactory) factory;
            xpf.setFeature( param.getName(), Boolean.parseBoolean( param.getValue() ) );
        } catch(XPathFactoryConfigurationException e) {
            throw new BuildException( e );
        }
    }

}
