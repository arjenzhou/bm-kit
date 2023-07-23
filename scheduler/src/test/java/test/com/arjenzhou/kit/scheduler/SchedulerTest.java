package test.com.arjenzhou.kit.scheduler;

import com.arjenzhou.kit.scheduler.SchedulerRunner;
import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;

import java.util.concurrent.TimeUnit;

/**
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/21
 */
public class SchedulerTest {
    @Test
    public void test() throws SchedulerException, InterruptedException {
        SchedulerRunner.run();
        TimeUnit.SECONDS.sleep(15);
    }
}
