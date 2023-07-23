/**
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/23
 */
module bm.kit.scheduler.main {
    requires transitive org.quertz.scheduler.quartz;
    requires org.slf4j;

    exports com.arjenzhou.kit.scheduler;
    exports com.arjenzhou.kit.scheduler.job;
    exports com.arjenzhou.kit.scheduler.job.internal to org.quertz.scheduler.quartz;

    // all jobs should be provided by this, for SPI reflection
    uses com.arjenzhou.kit.scheduler.job.ScheduledJob;
}