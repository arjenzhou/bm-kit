package com.arjenzhou.kit.scheduler.job.internal;

import com.arjenzhou.kit.scheduler.job.Scheduled;
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
    private static final Logger LOG = LoggerFactory.getLogger("JOB HOLDER");
    /**
     * All jobs instances
     */
    private static final List<ScheduledJob> SCHEDULED_JOBS;

    static {
        ServiceLoader<ScheduledJob> jobServiceLoader = ServiceLoader.load(ScheduledJob.class);
        SCHEDULED_JOBS = jobServiceLoader.stream()
                .map(ServiceLoader.Provider::get)
                .filter(j -> j.getClass().getAnnotation(Scheduled.class) != null)
                .toList();
        String loadedJobs = SCHEDULED_JOBS.stream().map(j -> j.getClass().getTypeName()).collect(Collectors.joining("\n"));
        LOG.info("""
                load %s jobs:
                %s
                
                When your services were not loaded properly, please check as follows:
                1. whether your services are declared in /META-INF/services/com.arjenzhou.kit.scheduler.job.ScheduledJob
                2. whether your services are annotated with com.arjenzhou.kit.scheduler.job.Scheduled
                3. whether your services are provided as com.arjenzhou.kit.scheduler.job.ScheduledJob in module-info if you are using them under JPMS
                """.formatted(SCHEDULED_JOBS.size(), loadedJobs));
    }

    /**
     * @return all scheduled jobs
     */
    public static List<ScheduledJob> getAllJobs() {
        return SCHEDULED_JOBS;
    }
}
