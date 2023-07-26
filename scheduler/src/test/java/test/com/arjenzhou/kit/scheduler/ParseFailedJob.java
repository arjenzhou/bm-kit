package test.com.arjenzhou.kit.scheduler;

import com.arjenzhou.kit.scheduler.job.MemoryScheduledJob;
import com.arjenzhou.kit.scheduler.job.Scheduled;

/**
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/24
 */
@Scheduled(cronExpression = "1113 * * * * ?")
public class ParseFailedJob extends MemoryScheduledJob {
    @Override
    public void execute() {
        // do nothing
    }
}
