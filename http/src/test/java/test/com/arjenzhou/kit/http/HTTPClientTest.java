package test.com.arjenzhou.kit.http;

import com.arjenzhou.kit.base.util.JSONUtil;
import com.arjenzhou.kit.http.HTTPClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/26
 */
public class HTTPClientTest {
    private final ValueObject valueObject = new ValueObject();

    @BeforeEach
    public void init() {
        valueObject.setA(1);
        valueObject.setStr("Hello, World");
    }

    @Test
    public void testGet() throws IOException {
        try (MockWebServer ignored = setUpMockWebServer()) {
            ValueObject result = HTTPClient.get("http://localhost:7070", ValueObject.class);
            assert result.equals(valueObject);
        }
    }

    @Test
    public void testPost() throws IOException {
        try (MockWebServer ignored = setUpMockWebServer()) {
            ValueObject result = HTTPClient.post("http://localhost:7070", "", ValueObject.class);
            assert result.equals(valueObject);
        }
    }

    private MockWebServer setUpMockWebServer() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        MockResponse mockResponse = new MockResponse().setBody(JSONUtil.toJSON(valueObject));
        mockResponse.setResponseCode(200);
        mockWebServer.enqueue(mockResponse);
        mockWebServer.start(7070);
        return mockWebServer;
    }

    public static class ValueObject {
        private int a;
        private String str;

        public ValueObject() {
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

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
            return ((ValueObject) obj).getA() == this.a &&
                    ((ValueObject) obj).getStr().equals(this.str);
        }
    }
}
