package com.remjeyinc.remjeyliu.beautifulbulldog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.prefs.Preferences;

import io.realm.Realm;

public class BulldogActivity extends AppCompatActivity{
    private TextView bulldogName;
    private Realm realm;
    private EditText voteamount;
    private ImageView bulldogImage;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulldog);

        bulldogName = (TextView) findViewById(R.id.bulldogName);
        voteamount = (EditText) findViewById(R.id.voteAmount);
        bulldogImage = (ImageView) findViewById(R.id.bulldogImage);

        realm = Realm.getDefaultInstance();

        String username = (String) getIntent().getStringExtra("username");
        user = realm.where(User.class).equalTo("username", username).findFirst();

        String id = (String) getIntent().getStringExtra("bulldog");
        final Bulldog bulldog = realm.where(Bulldog.class).equalTo("id",id).findFirst();
        bulldogName.setText(bulldog.getName());

        if(bulldog.getImage() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(bulldog.getImage(), 0, bulldog.getImage().length);
            bulldogImage.setImageBitmap(bmp);
        }

        Button saveButton = (Button) findViewById(R.id.savebutton);
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Vote newVote = new Vote();

                        newVote.setBulldog(bulldog);
                        newVote.setOwner(user);
                        newVote.setRating(Integer.parseInt(voteamount.getText().toString()));

                        realm.copyToRealmOrUpdate(newVote);
                        finish();
                    }
                    });
            }
        });
    }
}