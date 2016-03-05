package data;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;
import org.testng.Assert;
import java.util.HashMap;
import java.util.Map;

public enum Currency {
    USD("USD", "$"), EURO("EUR", "€"), UAH("UAH", "₴"), ZL("PLN", "zł");

    private String code;
    private String symbol;
    private static String currencyRateEndPoint = "http://api.fixer.io/latest?base=%s&symbols=%s";

    private static Map<String, Currency> currencyCodeMap = new HashMap();
    private static Map<String, Currency> currencySymbolMap = new HashMap();

    static {
        for (Currency currency : Currency.values()){
            currencyCodeMap.put(currency.getCode(), currency);
            currencySymbolMap.put(currency.getSymbol(), currency);
        }
    }

    Currency(String code, String symbol){
        this.code = code;
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol(){
        return symbol;
    }

    public Currency getCurrencyByCode(final String currencyCode){
        Currency currency = currencyCodeMap.get(currencyCode);

        if (currency == null){
            throw new IllegalArgumentException("Unknown currency code");
        }

        return currency;
    }

    public Currency getCurrencyBySymbol(final String currencySymbol){
        Currency currency = currencySymbolMap.get(currencySymbol);

        if (currency == null){
            throw new IllegalArgumentException("Unknown currency symbol");
        }

        return currency;
    }

    public static double getCurrencyRate(final Currency base, final Currency quoted){
        String currencyRateUrl = String.format(currencyRateEndPoint, base.getCode(), quoted.getCode());
        HttpResponse<JsonNode> jsonNode = null;
        try {
            jsonNode = Unirest.get(currencyRateUrl).asJson();
        } catch (Exception e){
            Assert.fail("Failed to get Currency Rate");
        }
        JSONObject obj = jsonNode.getBody().getObject();

        return Double.parseDouble(obj.getJSONObject("rates").get(quoted.getCode()).toString());
    }

}
