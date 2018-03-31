package br.com.marcos.twitter.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import br.com.marcos.twitter.R;
import br.com.marcos.twitter.adapters.TweetAdapter;
import br.com.marcos.twitter.dominio.Tweet;

/**
 * Created by Marcos on 17/03/18.
 */

public class TwitterActivity extends Activity {

    /**
     * Lista do histórico de Tweets
     */
    private ArrayList<Tweet> tweets;

    /**
     * Adapter para o ListView de Tweets
     */
    private TweetAdapter tweetsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
        if(savedInstanceState == null) {
            tweets = new ArrayList<>();
        }else{
            tweets = (ArrayList<Tweet>) savedInstanceState.getSerializable("tweets");
        }
        ListView historicoTweets = findViewById(R.id.historicoTweets);
        tweetsAdapter = new TweetAdapter(this, tweets);
        historicoTweets.setAdapter(tweetsAdapter);

        historicoTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Tweet tweetSelecionado = tweets.get(i);

                Toast.makeText(getApplicationContext(), "Item Selecionado: " + tweetSelecionado, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Insere o tweet dentro do histórico
     */
    public void tweetar(View view){
        EditText tweetText = findViewById(R.id.tweet);
        Tweet tweet = new Tweet(tweetText.getText().toString(), "Autor", new Date());
        tweets.add(tweet);
        tweetsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tweets",tweets);
    }
}
