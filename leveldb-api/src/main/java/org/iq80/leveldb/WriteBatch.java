/*
 * Copyright (C) 2011 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
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
package org.iq80.leveldb;

import java.io.Closeable;

/**
 * WriteBatch holds a collection of updates to apply atomically to a DB.
 * //
 * The updates are applied in the order in which they are added
 * to the WriteBatch.  For example, the value of "key" will be "v3"
 * after the following batch is written:
 * //
 *    batch.Put("key", "v1");
 *    batch.Delete("key");
 *    batch.Put("key", "v2");
 *    batch.Put("key", "v3");
 *
 * Multiple threads can invoke const methods on a WriteBatch without
 * external synchronization, but if any of the threads may call a
 * non-const method, all threads accessing the same WriteBatch must use
 * external synchronization.
 * @author <a href="http://hiramchirino.com">Hiram Chirino</a>
 */
public interface WriteBatch
        extends Closeable
{
    /**
     * Store the mapping "key->value" in the database.
     */
    WriteBatch put(byte[] key, byte[] value);

    /**
     * If the database contains a mapping for "key", erase it.  Else do nothing.
     */
    WriteBatch delete(byte[] key);

    /**
     * Clear all updates buffered in this batch.
     */
    WriteBatch clear();

    /**
     * The size of the database changes caused by this batch.
     * Note: This number is tied to implementation details, and may change across
     * releases. It is intended for LevelDB usage metrics.
     * @return size
     */
    long approximateSize();

    /**
     * Copies the operations in "source" to this batch.
     * This runs in O(source size) time. However, the constant factor is better
     * than calling Iterate() over the source batch with a Handler that replicates
     * the operations into this batch.
     * @param source writeBatch
     */
    WriteBatch append(WriteBatch source);
}
