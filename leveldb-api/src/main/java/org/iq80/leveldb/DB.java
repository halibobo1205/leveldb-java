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
import java.util.Map;

/**
 * A DB is a persistently ordered map from keys to values.
 * A DB is safe for concurrent access from multiple threads without
 * any external synchronization.
 * @author <a href="http://hiramchirino.com">Hiram Chirino</a>
 */
public interface DB
        extends Iterable<Map.Entry<byte[], byte[]>>, Closeable
{
    byte[] get(byte[] key)
            throws DBException;

    byte[] get(byte[] key, ReadOptions options)
            throws DBException;

    @Override
    DBIterator iterator();

    /**
     * Return a heap-allocated iterator over the contents of the database.
     * The result of NewIterator() is initially invalid (caller must
     * call one of the Seek methods on the iterator before using it).
     *
     * Caller should delete the iterator when it is no longer needed.
     * The returned iterator should be deleted before this db is deleted.
     * @param options ReadOptions
     * @return iterator
     */
    DBIterator iterator(ReadOptions options);

    void put(byte[] key, byte[] value)
            throws DBException;

    void delete(byte[] key)
            throws DBException;

    void write(WriteBatch updates)
            throws DBException;

    WriteBatch createWriteBatch();

    /**
     * Note: consider setting options.sync = true.
     * @return null if options.isSnapshot()==false otherwise returns a snapshot
     * of the DB after this operation.
     */
    Snapshot put(byte[] key, byte[] value, WriteOptions options)
            throws DBException;

    /**
     * Note: consider setting options.sync = true.
     * @return null if options.isSnapshot()==false otherwise returns a snapshot
     * of the DB after this operation.
     */
    Snapshot delete(byte[] key, WriteOptions options)
            throws DBException;

    /**
     * Note: consider setting options.sync = true.
     * @return null if options.isSnapshot()==false otherwise returns a snapshot
     * of the DB after this operation.
     */
    Snapshot write(WriteBatch updates, WriteOptions options)
            throws DBException;

    /**
     * Iterators created with
     * this handle will all observe a stable snapshot of the current DB
     * state.  The caller must call ReleaseSnapshot(result) when the
     *  snapshot is no longer needed.
     * @return a handle to the current DB state
     */
    Snapshot getSnapshot();

    /**
     * For each i in [0,n-1], store in "sizes[i]", the approximate
     * file system space used by keys in "[range[i].start .. range[i].limit)".
     *
     * Note that the returned sizes measure file system space usage, so
     * if the user data compresses by a factor of ten, the returned
     * sizes will be one-tenth the size of the corresponding user data size.
     *
     * The results may not include the sizes of recently written data.
     * @param ranges keys
     * @return the approximate file system space
     */
    long[] getApproximateSizes(Range... ranges);

    /**
     * DB implementations can export properties about their state
     * via this method.  If "property" is a valid property understood by this
     * DB implementation, fills "*value" with its current value and returns
     * true.  Otherwise returns false.
     *
     *
     * Valid property names include:
     *
     *  "leveldb.num-files-at-level<N>" - return the number of files at level <N>,
     *     where <N> is an ASCII representation of a level number (e.g. "0").
     *  "leveldb.stats" - returns a multi-line string that describes statistics
     *     about the internal operation of the DB.
     *  "leveldb.sstables" - returns a multi-line string that describes all
     *     of the sstables that make up the db contents.
     *  "leveldb.approximate-memory-usage" - returns the approximate number of
     *     bytes of memory in use by the DB.
     * @param name property name
     * @return status
     */
    String getProperty(String name);

    /**
     * Suspends any background compaction threads.  This methods
     * returns once the background compactions are suspended.
     */
    void suspendCompactions()
            throws InterruptedException;

    /**
     * Resumes the background compaction threads.
     */
    void resumeCompactions();

    /**
     * Compact the underlying storage for the key range [*begin,*end].
     * In particular, deleted and overwritten versions are discarded,
     * and the data is rearranged to reduce the cost of operations
     * needed to access the data.  This operation should typically only
     * be invoked by users who understand the underlying implementation.
     *
     * begin==nullptr is treated as a key before all keys in the database.
     * end==nullptr is treated as a key after all keys in the database.
     * Therefore, the following call will compact the entire database:
     *    db->CompactRange(nullptr, nullptr);
     *
     * @param begin if null then compaction start from the first key
     * @param end if null then compaction ends at the last key
     */
    void compactRange(byte[] begin, byte[] end)
            throws DBException;
}
