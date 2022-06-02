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

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * URL protocol handler supporting predefined list of classloaders
 * to load the given resource.
 * 
 * <p>Built-in logic uses following classloaders (in given order):</p><ul>
 * <li>{@link ClasspathStreamHandlerFactory#primaryClassLoader} (if set)</li>
 * <li><code>Handler</code>'s own classloader</li>
 * <li>Thread's Context classloader</li>
 * <li>System classloader</li>
 * </ul>
 * 
 * <p>One can influence this logic by setting (at any moment of time)
 * {@link ClasspathStreamHandlerFactory#primaryClassLoader} to some desired
 * classloader.
 * 
 * <p>created on Apr 28, 2005
 * @author fiykov
 * @version $Revision: 1.2 $
 * @since
 */
public class Handler extends URLStreamHandler {

    ///** initialize the logging */
    //static final Log log = LogFactory.getLog( Handler.class );


    protected URLConnection openConnection(URL u) throws IOException {
        //return new ClassloaderURLConnection(u);

        URL sysRes = getSysResURL( u );
        
        /*
        if ( log.isDebugEnabled() )
            log.debug( "openConnection() : url="+super.toExternalForm( u )+
                    ", clsldr="+sysRes.toExternalForm() );
        */
        
        return sysRes.openConnection();
    }
    
    /*
    protected String toExternalForm(URL u) {
        
        URL sysRes = getSysResURL( u );
    
        if ( log.isDebugEnabled() )
            log.debug( "toExternalForm() : url="+super.toExternalForm( u )+
                    ", clsldr="+sysRes.toExternalForm() );
        
        return sysRes.toExternalForm();
    }
        */

    /**
     * Locate the resource in the context system resources.
     * 
     * @return valid resource url
     * @throws InternalError if the resource is missing
     */
    protected URL getSysResURL( URL u ) {
        
        URL sysRes = getResource( u.getFile() );
        
        if ( sysRes == null )
            throw new InternalError("Resource "+u.toString()+
                    " not found by any of the defined classloaders" );

        return sysRes;
    }
    
    /**
     * Loks up a list of classloaders for the given resource.
     * 
     * Order of classloaders used is:<ul>
     * <li>{@link ClasspathStreamHandlerFactory#primaryClassLoader} (if given)</li>
     * <li><code>Handler</code>'s own classloader</li>
     * <li>Thread's Context classloader</li>
     * <li>System classloader</li>
     * </ul>
     * 
     * @param res to find
     * @return found resource by the first classloader or null if found by none
     * of them
     */
    protected URL getResource( String res ) {

        ClassLoader[] cls = { 
                ClasspathStreamHandlerFactory.primaryClassLoader,
                Handler.class.getClassLoader(),
                Thread.currentThread().getContextClassLoader(),
                ClassLoader.getSystemClassLoader()
        };
        
        for(int i=0; i<cls.length; i++) {
            if ( cls[i] != null ) {
                URL ret = cls[i].getResource( res );
                if ( ret != null )
                    return ret;
            }
        }
        
        return null;
    }
    
}
