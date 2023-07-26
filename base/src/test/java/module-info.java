/**
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/26
 */
module test.com.arjenzhou.kit.base {
    requires com.arjenzhou.kit.base;
    requires org.junit.jupiter.api;

    opens test.com.arjenzhou.kit.base.util;

    exports test.com.arjenzhou.kit.base.util;
}