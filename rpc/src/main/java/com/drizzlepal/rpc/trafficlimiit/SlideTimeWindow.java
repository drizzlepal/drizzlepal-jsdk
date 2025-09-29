package com.drizzlepal.rpc.trafficlimiit;

import java.util.concurrent.atomic.AtomicReferenceArray;

import com.drizzlepal.rpc.TrafficLimit;

import lombok.extern.log4j.Log4j2;

/**
 * 滑动窗口限流算法
 * 通过将时间划分为多个片段，并统计每个时间段的请求量来实现限流
 */
@Log4j2
public class SlideTimeWindow extends TrafficLimit {

    // 时间段的数量
    public static final int SHARD_COUNT = 20;

    // 每个时间段的毫秒数
    public static final long SHARD_SIZE_MS = 1000L / SHARD_COUNT;

    // 更新尝试的次数
    public static final long UPDATE_RETRY_TIMES = SHARD_COUNT * 2;

    // 限流的阈值
    public final long limit;

    // 存储每个时间段的请求量
    private final AtomicReferenceArray<Shard> shards = new AtomicReferenceArray<>(SHARD_COUNT);

    /**
     * 构造函数，初始化滑动窗口限流算法
     * 
     * @param limit 限流的阈值
     */
    public SlideTimeWindow(int limit) {
        this.limit = limit;
        for (int i = 0; i < SHARD_COUNT; i++) {
            shards.set(i, new Shard());
        }
    }

    /**
     * 获取当前的时间刻度
     * 
     * @return 当前时间刻度
     */
    public long getCurrentTick() {
        long now = System.currentTimeMillis();
        return (now + SHARD_SIZE_MS - 1) / SHARD_SIZE_MS;
    }

    /**
     * 尝试更新时间段的请求量
     * 
     * @param tick 当前时间刻度
     * @return 更新是否成功
     */
    public boolean tryUpdateShard(long tick) {
        int index = (int) (tick % SHARD_COUNT);
        int retry = 0;
        Shard updated = new Shard();
        while (retry++ < UPDATE_RETRY_TIMES) {
            Shard old = shards.get(index);
            if (tick > old.tick) {
                updated.tick = tick;
                updated.count = 1;
                if (shards.compareAndSet(index, old, updated)) {
                    break;
                }
            } else if (tick == old.tick) {
                updated.tick = old.tick;
                updated.count = old.count + 1;
                if (shards.compareAndSet(index, old, updated)) {
                    break;
                }
            } else {
                log.warn("时钟回拨异常");
                return false;
            }
        }
        if (retry == UPDATE_RETRY_TIMES) {
            log.warn("尝试修改滑动时间分片统计数据失败！");
            return false;
        }
        return true;
    }

    /**
     * 获取当前时间段及其前几个时间段的请求总量
     * 
     * @param tick 当前时间刻度
     * @return 请求总量
     */
    public long getCurrentCount(long tick) {
        long count = 0L;
        long step = tick - SHARD_COUNT + 1;
        for (int i = 0; i < SHARD_COUNT; i++) {
            Shard shard = shards.get(i);
            if (shard.tick >= step && shard.tick <= tick) {
                count += shard.count;
            }
        }
        return count;
    }

    /**
     * 尝试获取限流令牌
     * 
     * @return 是否成功获取限流令牌
     */
    @Override
    public boolean tryAcquire() {
        long tick = getCurrentTick();
        return tryUpdateShard(tick) ? getCurrentCount(tick) <= limit : false;
    }

    /**
     * 时间段类，用于存储每个时间段的请求量
     */
    public static class Shard {
        public long tick = -1;
        public long count = 0;
    }

}