/**
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/23
 */
module com.arjenzhou.kit.scheduler {
    requires transitive org.quartz;
    requires org.slf4j;

    exports com.arjenzhou.kit.scheduler;
    exports com.arjenzhou.kit.scheduler.job;
    exports com.arjenzhou.kit.scheduler.job.internal to org.quartz;

    // all jobs should be provided by this, for SPI reflection
    uses com.arjenzhou.kit.scheduler.job.ScheduledJob;
}