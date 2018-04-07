package br.com.marcos.twitter.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import br.com.marcos.twitter.R;
import br.com.marcos.twitter.adapters.TweetAdapter;
import br.com.marcos.twitter.dominio.Tweet;

/**
 * Created by Marcos on 06/04/18.
 */

public class DetalhesTweetActivity extends Activity {

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
        TextView telefone = findViewById(R.id.telefone);
        ligar.setData(Uri.parse("tel:" + telefone.getText()));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(ligar);
    }
}
