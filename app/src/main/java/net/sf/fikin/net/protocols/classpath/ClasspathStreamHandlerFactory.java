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
package net.sf.fikin.net.protocols.classpath;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 * Classloader protocol handler factory
 * 
 * created on Oct 9, 2006
 * @author fiykov
 * @version $Revision: 1.2 $
 * @since
 */
public class ClasspathStreamHandlerFactory implements URLStreamHandlerFactory {

    /** protocol name */
    public static final String PROTOCOL = "classpath";
    
    /** 
     * primary classloader to use to load the resources
     * <p>by default it is null</p>
     * 
     * <p>by default order of classloaders used by {@link Handler} is:</p><ul>
     * <li>this given (if) classloader</li>
     * <li><code>Handler</code>'s own classloader</li>
     * <li>Thread's Context classloader</li>
     * <li>System classloader</li>
     */
    public static ClassLoader primaryClassLoader = null;
    
    /**
     * create a "classpath" Handler if given protocol is "classpath"
     */
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if ( PROTOCOL.equals( protocol ) )
            return new Handler();
        return null;
    }

}
