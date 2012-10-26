import mockit.NonStrictExpectations;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CurrencyConversionTest {

    @Test
    public void returnExpectedConversion_v2() {
        new NonStrictExpectations(CurrencyConversion.class) {{
            CurrencyConversion.currencySymbols();
            result = mapFrom("USD", "EUR");
        }};
        CurrencyConversion.convertFromTo("USD", "EUR");
    }

    private Map<String, String> mapFrom(String... keyValuePairs) {
        Map<String, String> result = new ConcurrentHashMap<String, String>();
        for (int i = 0; i < keyValuePairs.length; ++i)
            result.put(keyValuePairs[i], keyValuePairs[i]);
        return result;
    }

}
