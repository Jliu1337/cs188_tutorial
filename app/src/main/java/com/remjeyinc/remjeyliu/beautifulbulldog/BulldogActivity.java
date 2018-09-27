package com.remjeyinc.remjeyliu.beautifulbulldog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.prefs.Preferences;

import io.realm.Realm;

public class BulldogActivity extends AppCompatActivity{
    private TextView bulldogName;
    private Realm realm;
    private EditText voteamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulldog);

        bulldogName = (TextView) findViewById(R.id.bulldogName);
        realm = Realm.getDefaultInstance();

        String id = (String) getIntent().getStringExtra("bulldog");
        final Bulldog bulldog = realm.where(Bulldog.class).equalTo("id",id).findFirst();
        bulldogName.setText(bulldog.getName());

        voteamount = (EditText) findViewById(R.id.voteAmount);

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
                        newVote.setOwner(getIntent().getSerializableExtra("username"));
                        newVote.setRating(Integer.parseInt(voteamount.getText().toString()));

                        realm.copyToRealmOrUpdate(newVote);
                        finish();
                    }
                    });
            }
        });
    }
}