package com.dstz.base.core.id;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dstz.base.core.id.IdGenerator;
import com.dstz.base.core.id.snowflake.SnowflakeIdGenerator;
import com.dstz.base.core.id.snowflake.SnowflakeIdMeta;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 雪花算法单元测试
 * 
 * @author wacxhs
 */
public class SnowflakeIdGeneratorTest {

    private IdGenerator idGenerator;

    @Before
    public void setUp() {
        SnowflakeIdMeta idMeta = new SnowflakeIdMeta(1, (byte) 3, (byte) 15, (byte) 45);
        this.idGenerator = new SnowflakeIdGenerator(idMeta);
    }


    /**
     * 生成的UID并发重复测试
     *
     * @throws InterruptedException 线程中断异常
     */
    @Test
    public void getUId_concurrentRepeat() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        Executor executor = Executors.newFixedThreadPool(10);
        ConcurrentHashMap<Long, Long> uidMap = new ConcurrentHashMap<>();
        AtomicLong repeatCount = new AtomicLong(0);
        long startTime=System.currentTimeMillis(); 
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                for (int j = 0; j < 10000; j++) {
                    Long uid = idGenerator.getUId();
                    Long oldId = uidMap.put(uid, uid);
                    if (oldId != null) {
                        repeatCount.incrementAndGet();
                    }
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        
        long endTime = System.currentTimeMillis(); 
        
        Assert.assertEquals("生成ID存在重复", 0, repeatCount.intValue());
        
       /* for(Entry<Long, Long> entry : uidMap.entrySet()) {
        	System.out.println(entry.getKey());
        }*/
        
        System.out.println(uidMap.size());
        System.out.println(endTime-startTime);
    }
}