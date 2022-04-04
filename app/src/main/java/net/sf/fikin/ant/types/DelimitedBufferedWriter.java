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

import java.io.BufferedWriter;
import java.io.Writer;

/**
 * <p>Buffered writer with ability to overwrite new-line character.
 * 
 * <p>By setting the delimiter to some some string, calls to
 * <code>newLine()</code> will result in printing the given string.
 * 
 * <p>In case the delimiter is null it printing fails to default implementation. 
 * 
 * created on Jan 4, 2007
 * @author fiykov
 * @version $Revision: 1.3 $
 * @since
 */
public class DelimitedBufferedWriter extends BufferedWriter {

    /** newline replacement */
    String delimiter;
    
    /**
     * Create a buffered character-output stream that uses a default-sized
     * output buffer.
     *
     * @param  out  A Writer
     */
    public DelimitedBufferedWriter(Writer out, String delimiter) {
        super(out);
        setDelimiter( delimiter );
    }

    /**
     * Create a new buffered character-output stream that uses an output
     * buffer of the given size.
     *
     * @param  out  A Writer
     * @param  sz   Output-buffer size, a positive integer
     *
     * @exception  IllegalArgumentException  If sz is <= 0
     */
    public DelimitedBufferedWriter(Writer out, int sz, String delimiter) {
        super(out, sz);
        setDelimiter( delimiter );
    }

    /**
     * set the delimiter to use, can be null (resulting in default newLine()
     * handling then)
     * @param delimiter
     */
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Get the assigned delimiter.
     * 
     * <p>This is given during writer instantiation and it has been specified
     * by the user in the "output" tag
     * 
     * <p>It defaults for single space for writing in a Ant property and
     * <code>System.getProperty("line.separator")</code> for writing in a file
     * 
     * @return assigned delimiter
     */
    public String getDelimiter() {
        return delimiter;
    }

}
