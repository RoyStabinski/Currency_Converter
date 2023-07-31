
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

import static com.sun.tools.javac.util.Constants.format;


public class Converter {

    public static void main(String []args) throws IOException {

        boolean run = true;
        do {
            int fromCode, toCode;
            String fromCoin, toCoin;
            float price;

            HashMap<Integer, String> currencies = CurrencyMap();

            Scanner scan = new Scanner(System.in);
            System.out.println("Welcome To The Currency Converter\n");
            CurrencyOptions();

            fromCode = scan.nextInt();
            while(fromCode < 1 || fromCode > 20) {
                System.out.println("Please Insert Valid Number");
                fromCode = scan.nextInt();
            }

            fromCoin = currencies.get(fromCode);

            CurrencyOptions();

            toCode = scan.nextInt();
            while (toCode < 1 || toCode > 20) {
                System.out.println("Please Insert Valid Number");
                toCode = scan.nextInt();
            }

            toCoin = currencies.get(toCode);

            Scanner amount = new Scanner(System.in);
            System.out.println("Please Insert The Requested Amount");
            price = amount.nextFloat();

            SendHTTPRequest(fromCoin, toCoin, price);

            System.out.println("Thank You For Choosing in Our Services");
            System.out.println("Would You Like To Convert Another Coin?");
            Scanner ans = new Scanner(System.in);
            System.out.println("1.Yes\n2.No");
            if (ans.nextInt() == 2)
                run = false;
        }while(run);
    }

    private static void CurrencyOptions() {
        System.out.println("Please Insert From Which CryptoCoin You Would Like To Convert");
        System.out.println("""
                1.USD - US Dollars
                2.EUR - Euros
                3.GBP - British Pound
                4.CAD - Canadian Dollars
                5.AUD - Australian Dollars
                6.JPY - Japanese Yen
                7.INR - Indian Rupee
                8.NZD - New-Zealand Dollars
                9.CHF - Swiss Franc
                10.ZAR - South-African Rand
                11.RUB - Russian Rubel
                12.BGN - Bulgarian Lev
                13.SGD - Singaporean Dollars
                14.HKD - Honk-Kong Dollars
                15.SEK - Swedish Króna
                16.THB - Thai Baht
                17.ILS - Israel Shekel
                18.TRY - Turkish Lira
                19.EGP - Egyptian Pound
                20.PLN - Polish Zloty""");
    }

    private static HashMap<Integer, String> CurrencyMap() {
        HashMap<Integer, String> currencies = new HashMap<Integer,String>();
        //Currencies
        currencies.put(1, "USD"); // US Dollars
        currencies.put(2, "EUR"); // Euros
        currencies.put(3, "GBP"); // British Pound
        currencies.put(4, "CAD"); // Canadian Dollars
        currencies.put(5, "AUD"); // Australian Dollars
        currencies.put(6, "JPY");// Japanese Yen
        currencies.put(7, "INR"); // Indian Rupee
        currencies.put(8, "NZD"); // New-Zealand Dollars
        currencies.put(9, "CHF"); // Swiss Franc
        currencies.put(10, "ZAR"); // South-African Rand
        currencies.put(11, "RUB"); // Russian Rubel
        currencies.put(12, "BGN"); // Bulgarian Lev
        currencies.put(13, "SGD"); // Singaporean Dollars
        currencies.put(14, "HKD"); // Honk-Kong Dollars
        currencies.put(15, "SEK"); // Swedish Króna
        currencies.put(16, "THB");// Thai Baht
        currencies.put(17, "ILS"); // Israel Shekel
        currencies.put(18, "TRY"); // Turkish Lira
        currencies.put(19, "EGP"); // Egyptian Pound
        currencies.put(20, "PLN"); // Polish Zloty
        return currencies;
    }


    private static void SendHTTPRequest(String fromCoin , String toCoin , float price) throws IOException {
        DecimalFormat format = new DecimalFormat("00.00");
        String url_str = "http://api.currencylayer.com/live?access_key=82ff3b378bc29b6ffd164165450cac5e&format=1" + toCoin +"&currencies" + fromCoin;
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod("GET");
        int responseCode = request.getResponseCode();

        if(responseCode == request.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String inputLine;
        StringBuffer result = new StringBuffer();

        while((inputLine = in.readLine()) !=null){
            result.append(inputLine);
        }
        in.close();

        JSONObject obj = new JSONObject(result.toString());
        Double exchangeRate = obj.getJSONObject("&source").getDouble(fromCoin);
        System.out.println(exchangeRate +"\n");
        System.out.println(format(price) + fromCoin + " = " + format(price/exchangeRate) + toCoin);

        }else{
               System.out.println("GET Request Not Found!");
            }
    }
}
