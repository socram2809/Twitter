package br.com.marcos.twitter.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import br.com.marcos.twitter.R;
import br.com.marcos.twitter.dominio.Tweet;

/**
 * Created by Marcos on 06/04/18.
 */

public class DetalhesTweetActivity extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhes_tweet);

        TextView autor = findViewById(R.id.autor);
        TextView email = findViewById(R.id.email);
        TextView telefone = findViewById(R.id.telefone);
        TextView conteudo = findViewById(R.id.texto);
        TextView data = findViewById(R.id.data);

        Intent detalhesTweet = getIntent();

        Tweet tweet = (Tweet) detalhesTweet.getExtras().get(Tweet.TWEET_INFO);

        autor.setText(tweet.getAutor().getNome());
        email.setText(tweet.getAutor().getEmail());
        telefone.setText(tweet.getAutor().getTelefone());
        conteudo.setText(tweet.getTexto());
        data.setText(tweet.getData().toGMTString());
    }

    /**
     * Volta para a tela anterior
     * @param view
     */
    public void voltar(View view) {
        finish();
    }

    /**
     * Telefona para o número do autor do tweet
     * @param view
     */
    public void telefonar(View view) {
        Intent ligar = new Intent(Intent.ACTION_CALL);
        ligar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        TextView telefone = findViewById(R.id.telefone);
        ligar.setData(Uri.parse("tel:" + telefone.getText()));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            startActivity(ligar);
    }
}
