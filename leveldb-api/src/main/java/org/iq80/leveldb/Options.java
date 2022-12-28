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

public class Options
{
    private boolean createIfMissing = true;
    private boolean errorIfExists;
    private int writeBufferSize = 4 << 20;

    private int maxOpenFiles = 1000;

    private int blockRestartInterval = 16;
    private int blockSize = 4 * 1024;
    private CompressionType compressionType = CompressionType.SNAPPY;
    private boolean verifyChecksums = true;
    private boolean paranoidChecks;
    private DBComparator comparator;
    private Logger logger;
    private long cacheSize;
    private int maxBatchSize = 80_000;
    private int maxManifestSize = 0; //M
    // Return a new filter policy that uses a bloom filter with approximately
    // the specified number of bits per key.  A good value for bits_per_key
    // is 10, which yields a filter with ~ 1% false positive rate.
    //
    // Callers must delete the result after any database that is using the
    // result has been closed.
    //
    // Note: if you are using a custom comparator that ignores some parts
    // of the keys being compared, you must not use NewBloomFilterPolicy()
    // and must provide your own FilterPolicy that also ignores the
    // corresponding parts of the keys.  For example, if the comparator
    // ignores trailing spaces, it would be incorrect to use a
    // FilterPolicy (like NewBloomFilterPolicy) that does not ignore
    // trailing spaces in keys.
    private int bitsPerKey = 0;

    static void checkArgNotNull(Object value, String name)
    {
        if (value == null) {
            throw new IllegalArgumentException("The " + name + " argument cannot be null");
        }
    }

    public boolean createIfMissing()
    {
        return createIfMissing;
    }

    public Options createIfMissing(boolean createIfMissing)
    {
        this.createIfMissing = createIfMissing;
        return this;
    }

    public boolean errorIfExists()
    {
        return errorIfExists;
    }

    public Options errorIfExists(boolean errorIfExists)
    {
        this.errorIfExists = errorIfExists;
        return this;
    }

    public int writeBufferSize()
    {
        return writeBufferSize;
    }

    public Options writeBufferSize(int writeBufferSize)
    {
        this.writeBufferSize = writeBufferSize;
        return this;
    }

    public int maxOpenFiles()
    {
        return maxOpenFiles;
    }

    public Options maxOpenFiles(int maxOpenFiles)
    {
        this.maxOpenFiles = maxOpenFiles;
        return this;
    }

    public int blockRestartInterval()
    {
        return blockRestartInterval;
    }

    public Options blockRestartInterval(int blockRestartInterval)
    {
        this.blockRestartInterval = blockRestartInterval;
        return this;
    }

    public int blockSize()
    {
        return blockSize;
    }

    public Options blockSize(int blockSize)
    {
        this.blockSize = blockSize;
        return this;
    }

    public CompressionType compressionType()
    {
        return compressionType;
    }

    public Options compressionType(CompressionType compressionType)
    {
        checkArgNotNull(compressionType, "compressionType");
        this.compressionType = compressionType;
        return this;
    }

    public boolean verifyChecksums()
    {
        return verifyChecksums;
    }

    public Options verifyChecksums(boolean verifyChecksums)
    {
        this.verifyChecksums = verifyChecksums;
        return this;
    }

    public long cacheSize()
    {
        return cacheSize;
    }

    public Options cacheSize(long cacheSize)
    {
        this.cacheSize = cacheSize;
        return this;
    }

    public DBComparator comparator()
    {
        return comparator;
    }

    public Options comparator(DBComparator comparator)
    {
        this.comparator = comparator;
        return this;
    }

    public Logger logger()
    {
        return logger;
    }

    public Options logger(Logger logger)
    {
        this.logger = logger;
        return this;
    }

    public boolean paranoidChecks()
    {
        return paranoidChecks;
    }

    public Options paranoidChecks(boolean paranoidChecks)
    {
        this.paranoidChecks = paranoidChecks;
        return this;
    }

    public int maxBatchSize()
    {
        return maxBatchSize;
    }

    public Options maxBatchSize(int maxBatchSize)
    {
        if (maxBatchSize < 0) {
            maxBatchSize = Integer.MAX_VALUE;
        }
        this.maxBatchSize = maxBatchSize;
        return this;
    }

    public int maxManifestSize()
    {
        return maxManifestSize;
    }

    public Options maxManifestSize(int maxManifestSize)
    {
        if (maxManifestSize < 0) {
            maxManifestSize = -1;
            maxBatchSize(-1);
        }
        this.maxManifestSize = maxManifestSize;
        return this;
    }

    public int bitsPerKey()
    {
        return bitsPerKey;
    }

    public Options bitsPerKey(int bitsPerKey)
    {
        this.bitsPerKey = bitsPerKey;
        return this;
    }
}
