package com.arjenzhou.kit.scheduler.job.internal;

import com.arjenzhou.kit.scheduler.job.ScheduledJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

/**
 * Container which holds all instances that implemented ScheduledJob
 *
 * @author bm-kit@arjenzhou.com
 * @see ScheduledJob
 * @since 2023/7/23
 */
public class JobHolder {
    private static final Logger LOG = LoggerFactory.getLogger("JOB FACTORY");
    /**
     * All jobs instances
     */
    private static final List<ScheduledJob> SCHEDULED_JOBS;

    static {
        ServiceLoader<ScheduledJob> jobServiceLoader = ServiceLoader.load(ScheduledJob.class);
        SCHEDULED_JOBS = jobServiceLoader.stream().map(ServiceLoader.Provider::get).toList();
        String loadedJobs = SCHEDULED_JOBS.stream().map(j -> j.getClass().getTypeName()).collect(Collectors.joining("\n"));
        LOG.info("load " + SCHEDULED_JOBS.size() + " jobs: \n" + loadedJobs);
        LOG.info("check whether your service is declared in /META-INF/serivces/com.arjenzhou.kit.scheduler.job.ScheduledJob, " +
                "or provides with com.arjenzhou.kit.scheduler.job.ScheduledJob or not.");
    }

    /**
     * @return all scheduled jobs
     */
    public static List<ScheduledJob> getAllJobs() {
        return SCHEDULED_JOBS;
    }
}
