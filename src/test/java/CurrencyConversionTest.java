import mockit.NonStrictExpectations;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class CurrencyConversionTest {

    @Test
    public void returnExpectedConversion_v3() throws Exception {
        final ByteArrayInputStream bais = new ByteArrayInputStream(
            "<div id=\"converter_results\"><ul><li><b>1 USD = 0.98 EUR</b>".getBytes());

        new NonStrictExpectations() {
            DefaultHttpClient httpclient;
            HttpResponse response;
            HttpEntity entity;

            {
                httpclient.execute((HttpUriRequest) any);
                result = response;
                response.getEntity();
                result = entity;
                entity.getContent();
                result = bais;
            }
        };

        new NonStrictExpectations(CurrencyConversion.class) {{
            CurrencyConversion.currencySymbols();
            result = mapFrom("USD", "EUR");
        }};
        BigDecimal result = CurrencyConversion.convertFromTo("USD", "EUR");
        assertThat(result.subtract(new BigDecimal(2)), is(lessThanOrEqualTo(new BigDecimal(0.001))));
    }

    private Map<String, String> mapFrom(String... keyValuePairs) {
        Map<String, String> result = new ConcurrentHashMap<String, String>();
        for (int i = 0; i < keyValuePairs.length; ++i)
            result.put(keyValuePairs[i], keyValuePairs[i]);
        return result;
    }

}
