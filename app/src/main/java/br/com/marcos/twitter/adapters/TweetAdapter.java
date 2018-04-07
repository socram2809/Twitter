package br.com.marcos.twitter.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.marcos.twitter.R;
import br.com.marcos.twitter.dominio.Tweet;

/**
 * Created by Marcos on 17/03/18.
 */

public class TweetAdapter extends ArrayAdapter<Tweet>{

    public TweetAdapter(Context context, ArrayList<Tweet> tweets){
        super(context, 0, tweets);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Tweet tweet = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_layout, parent, false);
        }
        TextView autor = convertView.findViewById(R.id.autor);
        TextView texto = convertView.findViewById(R.id.texto);
        TextView data = convertView.findViewById(R.id.data);
        autor.setText("@" + tweet.getAutor().getNome());
        texto.setText(tweet.getTexto());
        data.setText(tweet.getData().toGMTString());
        return convertView;
    }
}
