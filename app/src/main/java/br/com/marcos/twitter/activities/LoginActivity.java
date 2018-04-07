package br.com.marcos.twitter.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import br.com.marcos.twitter.R;
import br.com.marcos.twitter.dominio.Tweet;

/**
 * Created by Marcos on 07/04/18.
 */

public class LoginActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        SharedPreferences preferencias = getPreferences(Activity.MODE_PRIVATE);
        EditText login = findViewById(R.id.login);
        login.setText(preferencias.getString("login", ""));
    }

    /**
     * Realiza o login
     * @param view
     */
    public void logar(View view){
        EditText login = findViewById(R.id.login);

        SharedPreferences preferencias = getPreferences(Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString("login", login.getText().toString());

        editor.commit();

        Intent telaPrincipal = new Intent(this, TwitterActivity.class);

        telaPrincipal.putExtra(Tweet.TWEET_INFO, login.getText().toString());

        startActivity(telaPrincipal);
    }
}
