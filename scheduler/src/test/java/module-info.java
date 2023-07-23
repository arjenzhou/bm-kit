import com.arjenzhou.kit.scheduler.job.ScheduledJob;
import test.com.arjenzhou.kit.scheduler.CalculationJob;
import test.com.arjenzhou.kit.scheduler.LogTimeJob;
import test.com.arjenzhou.kit.scheduler.ParseFailedJob;

/**
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/23
 */
module test.com.arjenzhou.kit.scheduler {
    exports test.com.arjenzhou.kit.scheduler;

    requires com.arjenzhou.kit.scheduler;
    requires org.slf4j;
    requires org.junit.jupiter.api;

    provides ScheduledJob with LogTimeJob, CalculationJob, ParseFailedJob;
}