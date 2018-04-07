package br.com.marcos.twitter.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.marcos.twitter.R;
import br.com.marcos.twitter.dominio.Autor;
import br.com.marcos.twitter.dominio.Tweet;

/**
 * Created by Marcos on 07/04/18.
 */

public class EditarTweetActivity extends Activity {

    /**
     * Tweet a ser editado
     */
    Tweet tweet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_tweet);

        EditText autor = findViewById(R.id.autor);
        EditText email = findViewById(R.id.email);
        EditText telefone = findViewById(R.id.telefone);
        EditText conteudo = findViewById(R.id.texto);

        Intent edicaoTweet = getIntent();

        tweet = (Tweet) edicaoTweet.getExtras().get(Tweet.TWEET_EDIT);

        autor.setText(tweet.getAutor().getNome());
        email.setText(tweet.getAutor().getEmail());
        telefone.setText(tweet.getAutor().getTelefone());
        conteudo.setText(tweet.getTexto());
    }

    /**
     * Edita o tweet
     * @param view
     */
    public void editar(View view){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Define o título
        builder.setTitle("Edição");
        //Define a mensagem
        builder.setMessage("Deseja realmente editar esse Tweet?");
        //Define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText autor = findViewById(R.id.autor);
                EditText email = findViewById(R.id.email);
                EditText telefone = findViewById(R.id.telefone);
                EditText conteudo = findViewById(R.id.texto);

                Autor autorTweet = new Autor(autor.getText().toString(), email.getText().toString(), telefone.getText().toString());
                tweet.setAutor(autorTweet);
                tweet.setTexto(conteudo.getText().toString());

                Intent telaPrincipal = new Intent();
                telaPrincipal.putExtra(Tweet.TWEET_EDIT, tweet);
                setResult(RESULT_OK, telaPrincipal);
                finish();
            }
        });
        //Define um botão como negativo
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Não faz nada...
            }
        });
        //Cria o alert dialog
        AlertDialog alert = builder.create();
        //Exibe
        alert.show();
    }

    /**
     * Volta para a tela principal
     * @param view
     */
    public void cancelar(View view){
        finish();
    }
}
