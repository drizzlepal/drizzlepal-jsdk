package com.drizzlepal.rpc.interceptor.trafficlimit;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReferenceArray;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.drizzlepal.rpc.trafficlimiit.SlideTimeWindow;

public class SlideTimeWindowTest {

    private static final long LIMIT = 5;

    private SlideTimeWindow limiter;

    @BeforeEach
    public void setUp() {
        limiter = new SlideTimeWindow((int) LIMIT);
    }

    @Test
    public void testTryUpdateShard_NewTick_Success() throws Exception {
        Field shardsField = SlideTimeWindow.class.getDeclaredField("shards");
        shardsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        AtomicReferenceArray<SlideTimeWindow.Shard> shards = (AtomicReferenceArray<SlideTimeWindow.Shard>) shardsField
                .get(limiter);
        long tick = 100;
        int index = (int) (tick % SlideTimeWindow.SHARD_COUNT);

        SlideTimeWindow.Shard old = new SlideTimeWindow.Shard();
        old.tick = 99;
        old.count = 0;
        shards.set(index, old);

        assertTrue(limiter.tryUpdateShard(tick));
        assertEquals(tick, shards.get(index).tick);
        assertEquals(1, shards.get(index).count);
    }

    @Test
    public void testTryUpdateShard_SameTick_Success() throws Exception {
        Field shardsField = SlideTimeWindow.class.getDeclaredField("shards");
        shardsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        AtomicReferenceArray<SlideTimeWindow.Shard> shards = (AtomicReferenceArray<SlideTimeWindow.Shard>) shardsField
                .get(limiter);

        long tick = 100;
        int index = (int) (tick % SlideTimeWindow.SHARD_COUNT);

        SlideTimeWindow.Shard old = new SlideTimeWindow.Shard();
        old.tick = tick;
        old.count = 2;
        shards.set(index, old);

        assertTrue(limiter.tryUpdateShard(tick));
        assertEquals(tick, shards.get(index).tick);
        assertEquals(3, shards.get(index).count);
    }

    @Test
    public void testTryUpdateShard_ClockDrift_ReturnFalse() throws Exception {
        Field shardsField = SlideTimeWindow.class.getDeclaredField("shards");
        shardsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        AtomicReferenceArray<SlideTimeWindow.Shard> shards = (AtomicReferenceArray<SlideTimeWindow.Shard>) shardsField
                .get(limiter);

        long tick = 100;
        int index = (int) (tick % SlideTimeWindow.SHARD_COUNT);

        SlideTimeWindow.Shard old = new SlideTimeWindow.Shard();
        old.tick = 101; // future tick
        shards.set(index, old);

        assertFalse(limiter.tryUpdateShard(tick)); // clock drifted back
    }

    @Test
    public void testGetCurrentCount_AllInWindow() throws Exception {
        Field shardsField = SlideTimeWindow.class.getDeclaredField("shards");
        shardsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        AtomicReferenceArray<SlideTimeWindow.Shard> shards = (AtomicReferenceArray<SlideTimeWindow.Shard>) shardsField
                .get(limiter);

        long tick = 100;
        for (int i = 0; i < SlideTimeWindow.SHARD_COUNT; i++) {
            SlideTimeWindow.Shard shard = new SlideTimeWindow.Shard();
            shard.tick = tick - i;
            shard.count = 1;
            shards.set(i, shard);
        }

        long count = limiter.getCurrentCount(tick);
        assertEquals(SlideTimeWindow.SHARD_COUNT, count);
    }

}