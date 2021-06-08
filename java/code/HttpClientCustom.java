package ogame.httpclient;

import org.apache.http.NameValuePair;

import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HttpClientCustom {

    private CloseableHttpClient httpClient;
    private CookieStore cookieStore;
    private String phpSessid;
    private String domain;

    private static long preRequestTime = 0l;
    private static Random random = new Random();

    /**
     * structure
     */
    public HttpClientCustom(String domain) {
        cookieStore = new BasicCookieStore();
        httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();
        this.domain = domain;
    }

    /**
     * @param universe 宇宙
     * @param userName 用户
     * @param passWord 密码
     */
    public String login(String url, String universe, String userName, String passWord) throws IOException {
        sleep();
        HttpPost httpPost = new HttpPost(domain + url);

        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("username", userName));
        param.add(new BasicNameValuePair("password", passWord));
        param.add(new BasicNameValuePair("universe", universe));
        httpPost.setEntity(new UrlEncodedFormEntity(param, Charset.forName("UTF-8")));

        CloseableHttpResponse response = httpClient.execute(httpPost);

        for (Cookie c : cookieStore.getCookies()) {
            if ("PHPSESSID".equals(c.getName())) {
                phpSessid = c.getValue();
                System.out.println("PHPSESSID = [" + phpSessid + "]");
            }
        }

        if (null != response.getEntity()) {
            return EntityUtils.toString(response.getEntity(), Charset.forName("GBK"));
        }
        return null;
    }

    /** http get */
    public String get(String url) throws IOException {
        sleep();
        HttpGet httpGet = new HttpGet(domain + url);
        httpGet.addHeader("PHPSESSID", phpSessid);

        CloseableHttpResponse response = httpClient.execute(httpGet);
        if(null != response.getEntity()){
            return EntityUtils.toString(response.getEntity(), Charset.forName("GBK"));
        }
        return null;
    }

    /** sleep */
    private void sleep(){
        if(System.currentTimeMillis() - preRequestTime < 1000l){
            try {
                Thread.sleep(1000l+random.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        preRequestTime = System.currentTimeMillis();
    }

}
