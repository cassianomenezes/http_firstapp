package hp.http;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

public class HttpActivity extends Activity {

    EditText etResponse;
    EditText etResponse2;
    TextView tvContent;
    WebView webView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        // get reference to the views
        /*
        etResponse = (EditText) findViewById(R.id.etResponse);
        etResponse2 = (EditText) findViewById(R.id.etResponse2);
        tvContent = new TextView(this);

*/
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        // call AsynTask to perform network operation on separate thread
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        //new HttpAsyncTask().execute("http://hmkcode.appspot.com/"+ message + "/controller/get.json");
        new HttpAsyncTask(webView).execute("http://www.gremiolibertador.com/tem-favorito/?json=1");
    }




}