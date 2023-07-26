package test.com.arjenzhou.kit.base.util;

import com.arjenzhou.kit.base.util.JSONUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/26
 */
public class JSONUtilTest {
    private ValueObject valueObject;
    private final String jsonString = "{\"str\":\"Hello World\"}";

    @BeforeEach
    public void init() {
        valueObject = new ValueObject();
        valueObject.setStr("Hello World");
    }

    @Test
    public void testToJSONString() {
        String json = JSONUtil.toJSON(valueObject);
        assert jsonString.equals(json);
    }

    @Test
    public void testFromJSONString() {
        ValueObject vo = JSONUtil.fromJSON(jsonString, ValueObject.class);
        assert vo.equals(valueObject);
    }
}

class ValueObject {
    String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ValueObject)) {
            return false;
        }
        return ((ValueObject) obj).getStr().equals(this.str);
    }
}
