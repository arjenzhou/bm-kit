package test.com.arjenzhou.kit.scheduler;

import com.arjenzhou.kit.scheduler.job.MemoryScheduledJob;
import com.arjenzhou.kit.scheduler.job.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Test Job which runs in every 5 seconds, and prints current system time.
 *
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/23
 */
@Scheduled(cronExpression = "0/5 * * * * ?")
public class LogTimeJob extends MemoryScheduledJob {
    private static final Logger LOG = LoggerFactory.getLogger(LogTimeJob.class);

    @Override
    public void execute() {
        LOG.info(new Date().toString());
    }
}
