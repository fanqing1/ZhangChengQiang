package life.majiang.community.provider;

import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;


@Component        //与controller相比，仅仅初始到spring的上下文           //provider提供对第三方支持的能力
public class GithubProvider {//用okhttp模拟post请求，传递code    https://square.github.io/okhttp/#post-to-a-server
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));//将accessTokenDTO类转换成json,fastjson
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];//处理获得token  access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&token_type=bearer
            return token;
        } catch (Exception e) {

        }
        return null;
    }


    public GithubUser getUser(String accessToken) {//用okhttp get a url ，获取user
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);//string的json对象转换成类对象，自动解析string
            return githubUser;
        } catch (Exception e) {

        }
        return null;
    }

}
