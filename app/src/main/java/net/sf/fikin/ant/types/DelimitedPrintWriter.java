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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * Provides with delimiter interface if the underlying writer of is 
 * of type {@link DelimitedBufferedWriter}.
 * 
 * <p>created on Jan 6, 2007
 * @author fiykov
 * @version $Revision: 1.2 $
 * @since
 */
public class DelimitedPrintWriter extends PrintWriter {

    /** 
     * Creates a new DelimitedPrintWriter.
     * 
     * @param out the original writer
     */
    public DelimitedPrintWriter(Writer out) {
        super(out);
    }

    /**
     * Creates a new DelimitedPrintWriter.
     * 
     * @param out the original writer
     * @param autoFlush whether to autoflush
     */
    public DelimitedPrintWriter(Writer out, boolean autoFlush) {
        super(out, autoFlush);
    }

    /**
     * Creates a new DelimitedPrintWriter.
     * 
     * @param out the original writer
     */
    public DelimitedPrintWriter(OutputStream out) {
        super(out);
    }

    /**
     * Creates a new DelimitedPrintWriter.
     * 
     * @param out the original stream
     * @param autoFlush whether to autoflush
     */
    public DelimitedPrintWriter(OutputStream out, boolean autoFlush) {
        super( out, autoFlush );
    }

    /**
     * Creates a new DelimitedPrintWriter.
     * 
     * @param fileName the filename to write to
     * @throws FileNotFoundException something went wrong
     */
    public DelimitedPrintWriter(String fileName) throws FileNotFoundException {
        super( fileName );
    }

    /**
     * Creates a new DelimitedPrintWriter.
     * 
     * @param fileName the filename to write to
     * @param csn
     * @throws FileNotFoundException something went wrong
     * @throws UnsupportedEncodingException something went wrong
     */
    public DelimitedPrintWriter(String fileName, String csn)
    throws FileNotFoundException, UnsupportedEncodingException
    {
        super( fileName, csn );
    }

    /**
     * Creates a new DelimitedPrintWriter.
     * 
     * @param file the file to write to
     * @throws FileNotFoundException something went wrong
     */
    public DelimitedPrintWriter(File file) throws FileNotFoundException {
        super( file );
    }

    /**
     * Creates a new DelimitedPrintWriter.
     * 
     * @param file the file to write to
     * @param csn
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException 
     */
    public DelimitedPrintWriter(File file, String csn)
    throws FileNotFoundException, UnsupportedEncodingException
    {
        super( file, csn );
    }
    
    /**
     * Get assigned delimiter if assigned writer object if of instance 
     * {@link DelimitedBufferedWriter}.
     * 
     * @return {@link DelimitedBufferedWriter#getDelimiter()} or null if
     * assigned writer object is not of such class instance
     */
    public String getDelimiter() {
        if ( out instanceof DelimitedBufferedWriter )
            return ((DelimitedBufferedWriter)out).getDelimiter();
        return null;
    }
}
