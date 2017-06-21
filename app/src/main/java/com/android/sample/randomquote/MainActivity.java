package com.android.sample.randomquote;

import android.os.AsyncTask;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.sample.randomquote.Utilities.NetworkUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView quote_txt,txtAuthor;
    Button btnShare, btnNew;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    private String shareText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quote_txt = (TextView)findViewById(R.id.quoteTxt);
        txtAuthor = (TextView)findViewById(R.id.txtAuthor);
        btnNew = (Button)findViewById(R.id.btnNew);
        btnShare = (Button)findViewById(R.id.btnShear);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        relativeLayout = (RelativeLayout)findViewById(R.id.layoutR);

        URL url = NetworkUtilities.buildUri();
        new RandomQuoteTak().execute(url);
    }

    public void newQoute(View view) throws IOException {
        URL url = NetworkUtilities.buildUri();
        new RandomQuoteTak().execute(url);
    }

    public void btnShearMe(View view){
        shareQuote();
    }

    public void shareQuote(){
        if(shareText != null) {
            String TITLE = "Share this Quote Via";
            String M_TYPE = "text/plain";
            ShareCompat.IntentBuilder.from(this).setChooserTitle(TITLE).setType(M_TYPE).setText(shareText).startChooser();
        }
    }
    public void showItems(){
        relativeLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
    public void hideItem(){

        relativeLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public class RandomQuoteTak extends AsyncTask<URL,Void,String>{

        @Override
        protected void onPreExecute() {
            hideItem();
        }

        @Override
        protected String doInBackground(URL... params) {

            String requestDate = null;
            try {
                requestDate = NetworkUtilities.getResponseFromHttpUrl(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return requestDate;
        }

        @Override
        protected void onPostExecute(String s) {
            showItems();
            if(s == null || s.equals("")){
                quote_txt.setText("OOOP!! No Quote for you\n Check your Internet Connection and try again!!!");
            }else{
                quote_txt.setText(s);
                try {
                    JSONReader(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void JSONReader(String jsonData) throws JSONException {
        JSONObject quoteMachine = new JSONObject(jsonData);
        String quoteText = quoteMachine.getString("quoteText");
        String quoteAuthor = quoteMachine.getString("quoteAuthor");
        quote_txt.setText(quoteText);
        if(quoteAuthor.equals("")){
            txtAuthor.setText("-UNKNOWN");
        }else{
            txtAuthor.setText("By -"+quoteAuthor);
            shareText = quoteText+ "\n By -"+quoteAuthor;
        }
    }
}
