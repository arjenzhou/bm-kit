import com.arjenzhou.kit.scheduler.job.ScheduledJob;
import test.com.arjenzhou.kit.scheduler.CalculationJob;
import test.com.arjenzhou.kit.scheduler.LogTimeJob;

/**
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/23
 */
module bm.kit.scheduler.test {
    exports test.com.arjenzhou.kit.scheduler;

    requires bm.kit.scheduler.main;
    requires org.slf4j;
    requires org.junit.jupiter.api;

    provides ScheduledJob with LogTimeJob, CalculationJob;
}