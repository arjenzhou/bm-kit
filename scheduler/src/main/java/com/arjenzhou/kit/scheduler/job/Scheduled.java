package com.arjenzhou.kit.scheduler.job;

import java.lang.annotation.*;

/**
 * Indicates that the Scheduled Job when to start.
 *
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/21
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Scheduled {
    /**
     * the quartz cron expression
     */
    String cronExpression();
}
