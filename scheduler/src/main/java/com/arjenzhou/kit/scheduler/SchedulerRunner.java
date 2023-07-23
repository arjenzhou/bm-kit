package com.arjenzhou.kit.scheduler;

import com.arjenzhou.kit.scheduler.job.internal.JobRunner;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * portal to run scheduler jobs
 *
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/21
 */
public class SchedulerRunner {
    /**
     * run all scheduler jobs
     *
     * @throws SchedulerException when scheduler failed to start
     */
    public static void run() throws SchedulerException {
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        JobDetail jobRunner = JobBuilder.newJob(JobRunner.class).build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .startNow()
                // In every second, check whether each job can run or not, till forever
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();
        scheduler.scheduleJob(jobRunner, trigger);
        scheduler.start();
    }
}
