package com.arjenzhou.kit.scheduler.job.internal;

import com.arjenzhou.kit.scheduler.job.Scheduled;
import org.quartz.CronExpression;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * runner to run all scheduled jobs
 *
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/21
 */
public class JobRunner implements Job {
    private static final Logger LOG = LoggerFactory.getLogger("JOB RUNNER");

    /**
     * for JobBuilder to invoke
     */
    public JobRunner() {
    }

    @Override
    public void execute(JobExecutionContext context) {
        Date now = new Date();
        JobHolder.getAllJobs().forEach(scheduledJob -> {
            Scheduled scheduled = scheduledJob.getClass().getAnnotation(Scheduled.class);
            String expression = scheduled.cronExpression();
            try {
                CronExpression cronExpression = new CronExpression(expression);
                Date lastRunTime = scheduledJob.getLastRunTime();
                Date nextValidTimeAfterLastRun = cronExpression.getNextValidTimeAfter(lastRunTime);
                // it is not the time to start this job
                if (nextValidTimeAfterLastRun.after(now)) {
                    return;
                }
                scheduledJob.start();
                LOG.info("Job " + scheduledJob.getClass().getTypeName() + " started");
            } catch (ParseException ignored) {
            }
        });
    }
}
