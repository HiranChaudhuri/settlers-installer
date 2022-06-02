/*
 */
/**
 * This package provides support for "classpath:" URL protocol handling.
 * 
 * 
 * <h2>Package Specification</h2>
 * 
 * This protocol hanlder allows for classpath-available resource referencing via 
 * java.net.URL objects by specifying urls in the format "classpath:net/sf/fikin/ant/version.txt".
 * 
 * <p>The resource is looked up by a predefined list of classloaders:<ul>
 * <li><a href="ClasspathStreamHandlerFactory.html#primaryClassLoader">ClasspathStreamHandlerFactory.primaryClassLoader</a> (if set)</li>
 * <li><code>Handler</code>'s own classloader</li>
 * <li>Thread's Context classloader</li>
 * <li>System classloader</li>
 * </ul>
 * 
 * <p>One can customize this order by setting its own classloader to <a href="ClasspathStreamHandlerFactory.html#primaryClassLoader">ClasspathStreamHandlerFactory.primaryClassLoader</a>.
 * <p>Classloader will be used to load the resource before any method of the URL object is executed.
 * <p>Simply creating an URL object of "classpath:" protocol would not cause any resource lookups.
 * 
 * <br>
 * <p>Using this protocol handler is possible via two ways:
 * 
 * <h3>1. Set an application's <code>URLStreamHandlerFactory</code></h3>
 * Place following in the beginning of your application, such as <code>main(String[])</code> or other simillar place:
 * <p><code>URL.setURLStreamHandlerFactory( new ClasspathStreamHandlerFactory() );</code></p>
 * <p>This method can be called at most once in a given Java Virtual Machine.
 * <p> The <code>URLStreamHandlerFactory</code> instance is used to construct a stream protocol handler from a protocol name.
 * <p> If there is a security manager, this method first calls the security manager's <code>checkSetFactory</code> method to ensure the operation is allowed. This could result in a SecurityException.
 * 
 * <h3>2. Set system property "java.protocol.handler.pkgs"</h3>
 * Set following value to the system property prior to execute your application:
 * <code>java -Djava.protocol.handler.pkgs=net.sf.fikin.net.protocols ...</code>
 * <p>If there are other values set to that propery, use "|" as an delimiter.
 * 
 * <!-- Put @see and @since tags down here. -->
 * 
 */
package net.sf.fikin.net.protocols.classpath;
