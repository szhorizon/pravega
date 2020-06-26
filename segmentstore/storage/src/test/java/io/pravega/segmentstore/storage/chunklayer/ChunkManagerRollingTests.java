/**
 * Copyright (c) Dell Inc., or its subsidiaries. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package io.pravega.segmentstore.storage.chunklayer;

import io.pravega.segmentstore.storage.Storage;
import io.pravega.segmentstore.storage.metadata.ChunkMetadataStore;
import io.pravega.segmentstore.storage.mocks.InMemoryChunkStorage;
import io.pravega.segmentstore.storage.mocks.InMemoryMetadataStore;
import io.pravega.segmentstore.storage.rolling.RollingStorageTestBase;

import java.util.concurrent.Executor;

/**
 * Unit tests for  {@link ChunkManager} and {@link ChunkStorage} based implementation that exercise scenarios
 * for {@link io.pravega.segmentstore.storage.rolling.RollingStorage}.
 */
public abstract class ChunkManagerRollingTests extends RollingStorageTestBase {
    ChunkStorage chunkStorage;
    ChunkMetadataStore chunkMetadataStore;

    /**
     * Creates a new instance of the Storage implementation to be tested. This will be cleaned up (via close()) upon
     * test termination.
     */
    @Override
    protected Storage createStorage() throws Exception {
        useOldLayout = false;
        Executor executor = executorService();
        // Initialize
        synchronized (ChunkManagerRollingTests.class) {
            if (null == chunkStorage) {
                chunkMetadataStore = getMetadataStore();
                chunkStorage = getChunkStorage();
            }
        }
        return new ChunkManager(chunkStorage,
                chunkMetadataStore,
                executor,
                ChunkManagerConfig.DEFAULT_CONFIG);
    }

    /**
     * Creates a ChunkStorage.
     *
     * @return ChunkStorage.
     * @throws Exception If any unexpected error occurred.
     */
    protected ChunkStorage getChunkStorage() throws Exception {
        return new InMemoryChunkStorage();
    }

    /**
     * Creates a ChunkMetadataStore.
     *
     * @return ChunkMetadataStore
     * @throws Exception If any unexpected error occurred.
     */
    protected ChunkMetadataStore getMetadataStore() throws Exception {
        return new InMemoryMetadataStore();
    }

    @Override
    public void testListSegmentsWithOneSegment() {
    }

    @Override
    public void testListSegmentsNextNoSuchElementException() {
    }
}