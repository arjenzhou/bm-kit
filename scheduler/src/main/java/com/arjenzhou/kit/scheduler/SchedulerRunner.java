package com.arjenzhou.kit.scheduler;

import com.arjenzhou.kit.scheduler.job.internal.JobRunner;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * portal to run scheduler jobs
 *
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/21
 */
public class SchedulerRunner {
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);
    private static final Logger LOG = LoggerFactory.getLogger("SchedulerRunner");

    /**
     * run all scheduler jobs
     */
    public static void run() {
        Thread thread = new Thread(() -> {
            SchedulerFactory factory = new StdSchedulerFactory();
            try {
                Scheduler scheduler = factory.getScheduler();
                JobDetail jobRunner = JobBuilder.newJob(JobRunner.class).build();
                Trigger trigger = TriggerBuilder.newTrigger()
                        .startNow()
                        // In every second, check whether each job can run or not, till forever
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                        .build();
                scheduler.scheduleJob(jobRunner, trigger);
                scheduler.start();
                COUNT_DOWN_LATCH.await();
                scheduler.shutdown();
            } catch (Exception e) {
                LOG.error("start scheduler runner failed.", e);
                COUNT_DOWN_LATCH.countDown();
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    /**
     * stop the runner
     */
    public static void stop() {
        COUNT_DOWN_LATCH.countDown();
    }
}
