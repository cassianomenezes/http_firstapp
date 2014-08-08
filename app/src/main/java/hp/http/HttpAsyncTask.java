package hp.http;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpAsyncTask extends AsyncTask<String, Void, String> {

    WebView webView;
    String css;

    public HttpAsyncTask(WebView webView) {

        this.webView = webView;
        css = "<style>img{display: inline;height: auto;max-width: 100%;}</style>";
    }

    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    @Override
    protected String doInBackground(String... urls) {

        return GET(urls[0]);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        JSONObject json = null;
        String title = "";
        String content = "";

        try {
            json = new JSONObject(result);
            JSONObject post = json.getJSONObject("post");
            title = post.get("title").toString();
            content = post.get("content").toString();
            Document doc = Jsoup.parse(content);
            Elements img = doc.select("src");
            System.out.println("==================> " + img.toString());
            //JSONArray articles = json.getJSONArray("articleList");
            //title1 = articles.getJSONObject(0).get("title").toString();
            //title2 = articles.getJSONObject(1).get("title").toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
/*
            etResponse.setText(title);
            tvContent.setText(content);
            setContentView(tvContent);
            //etResponse2.setText(content);*/
        String html = "<h1>" + title + "</h1>" + content;
        this.webView.loadDataWithBaseURL(null, css + html, "text/html", "UTF-8", null);
        //webView.loadData(content, "text/html", "utf-8");
    }

}
