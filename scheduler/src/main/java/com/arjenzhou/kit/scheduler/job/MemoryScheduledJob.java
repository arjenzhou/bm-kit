package com.arjenzhou.kit.scheduler.job;

import java.util.Date;

/**
 * Scheduler Job whose context stored in memory, thus cannot being restored from any persistence storage.
 *
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/23
 */
public abstract class MemoryScheduledJob implements ScheduledJob {
    /**
     * the last time this job completed.
     */
    private Date lastRunTime = new Date(0L);

    /**
     * execute the job
     */
    public abstract void execute();

    @Override
    public void start() {
        execute();
        lastRunTime = new Date();
    }

    @Override
    public Date getLastRunTime() {
        return lastRunTime;
    }
}
