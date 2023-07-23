package com.arjenzhou.kit.scheduler.job;

import java.util.Date;

/**
 * Job to run at specific condition.
 * All subclass should be provided to this class, and declared in META-INF/services/com.arjenzhou.kit.scheduler.job.ScheduledJob
 *
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/21
 */
public interface ScheduledJob {
    /**
     * how the ScheduledJob perform action
     */
    void start();

    /**
     * @return the latest time the job completed
     */
    Date getLastRunTime();
}
