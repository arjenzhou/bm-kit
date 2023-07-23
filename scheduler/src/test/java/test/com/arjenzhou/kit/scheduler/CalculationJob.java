package test.com.arjenzhou.kit.scheduler;

import com.arjenzhou.kit.scheduler.job.InMemorySchedulerJob;
import com.arjenzhou.kit.scheduler.job.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * Test Job which runs in every 3 seconds, and do a random calculation
 *
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/24
 */
@Scheduled(cronExpression = "0/3 * * * * ?")
public class CalculationJob extends InMemorySchedulerJob {
    private static final Logger LOG = LoggerFactory.getLogger(CalculationJob.class);

    @Override
    public void execute() {
        double random = Math.random();
        double result = Calendar.getInstance().getWeekYear() * random;
        LOG.info(String.valueOf(result));
    }
}
