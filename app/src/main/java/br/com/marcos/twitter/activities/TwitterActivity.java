package br.com.marcos.twitter.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import br.com.marcos.twitter.R;
import br.com.marcos.twitter.adapters.TweetAdapter;
import br.com.marcos.twitter.dominio.Autor;
import br.com.marcos.twitter.dominio.Tweet;

/**
 * Created by Marcos on 17/03/18.
 */

public class TwitterActivity extends Activity {

    /**
     * Constante que identifica a subActivity de edição de tweets
     */
    private static final int EDITAR_TWEET = 1;

    /**
     * Constante que identifica a subActivity de tirar foto
     */
    private static final int TIRAR_FOTO = 2;

    /**
     * Constante que identifica a opção de aparecer um toast
     */
    private static final int REFRESH = 3;

    /**
     * Constante que identifica a opção de finalizar a activity
     */
    private static final int LOGOUT = 4;

    /**
     * Lista do histórico de Tweets
     */
    private ArrayList<Tweet> tweets;

    /**
     * Adapter para o ListView de Tweets
     */
    private TweetAdapter tweetsAdapter;

    /**
     * Posição do Tweet na lista para ser editado
     */
    private int posicaoTweet;

    /**
     * Tweet selecionado na listagem
     */
    private Tweet tweetSelecionado;

    /**
     * Usuário logado na aplicação
     */
    private String login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);

        Intent telaLogin = getIntent();

        login = telaLogin.getExtras().get(Tweet.TWEET_INFO).toString();

        if(savedInstanceState == null) {
            tweets = new ArrayList<>();
        }else{
            tweets = (ArrayList<Tweet>) savedInstanceState.getSerializable("tweets");
        }
        ListView historicoTweets = findViewById(R.id.historicoTweets);
        tweetsAdapter = new TweetAdapter(this, tweets);
        historicoTweets.setAdapter(tweetsAdapter);

        registerForContextMenu(historicoTweets);

        historicoTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Tweet tweetSelecionado = tweets.get(i);

                Intent detalhesTweet = new Intent(TwitterActivity.this, DetalhesTweetActivity.class);

                detalhesTweet.putExtra(Tweet.TWEET_INFO, tweetSelecionado);

                startActivity(detalhesTweet);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tweet, menu);
    }

    /**
     * Edita o Tweet
     */
    private void editaTweet(Tweet tweet){

        Intent editarTweet = new Intent(TwitterActivity.this, EditarTweetActivity.class);

        editarTweet.putExtra(Tweet.TWEET_EDIT, tweet);

        startActivityForResult(editarTweet, EDITAR_TWEET);
    }

    /**
     * Remove o Tweet
     */
    private void removeTweet(Tweet tweet){

        tweetSelecionado = tweet;

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Define o título
        builder.setTitle("Remoção");
        //Define a mensagem
        builder.setMessage("Deseja realmente remover esse Tweet?");
        //Define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                tweets.remove(tweetSelecionado);

                tweetsAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.editar:
                posicaoTweet = info.position;
                editaTweet(tweets.get(info.position));
                return true;
            case R.id.remover:
                removeTweet(tweets.get(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                Toast.makeText(this, "Atualizado", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Recebe o resultado de subActivities
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == this.RESULT_OK && requestCode == EDITAR_TWEET){
            Tweet tweetEditado = (Tweet) data.getExtras().get(Tweet.TWEET_EDIT);

            tweets.set(posicaoTweet, tweetEditado);

            tweetsAdapter.notifyDataSetChanged();
        }else if(resultCode == this.RESULT_OK && requestCode == TIRAR_FOTO){
            ImageView foto = findViewById(R.id.fotoPerfil);

            foto.setImageBitmap((Bitmap) data.getExtras().get("data"));
        }
    }

    /**
     * Insere o tweet dentro do histórico
     * @param view
     */
    public void tweetar(View view){
        EditText tweetText = findViewById(R.id.tweet);
        Autor autor = new Autor(login, "email@email.com", "123456");
        Tweet tweet = new Tweet(tweetText.getText().toString(), autor, new Date());
        tweets.add(tweet);
        tweetsAdapter.notifyDataSetChanged();
    }

    /**
     * Modifica a foto de perfil
     * @param view
     */
    public void mudaFoto(View view){
        Intent tirarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(tirarFoto, TIRAR_FOTO);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tweets",tweets);
    }
}
