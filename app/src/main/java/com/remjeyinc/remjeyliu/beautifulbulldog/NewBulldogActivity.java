package com.remjeyinc.remjeyliu.beautifulbulldog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import io.realm.Realm;

public class NewBulldogActivity extends AppCompatActivity {
    private Realm realm;
    private EditText nameInput;
    private EditText AgeInput;
    private ImageButton newbulldogImage;
    private Button savebutonn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bulldog);

        nameInput = (EditText) findViewById(R.id.nameInput);
        AgeInput = (EditText) findViewById(R.id.ageInput);
        newbulldogImage = (ImageButton) findViewById(R.id.newbulldogImage);
        savebutonn = (Button)  findViewById(R.id.savebuttonn);

        realm = Realm.getDefaultInstance();

        newbulldogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null)
                {
                    startActivityForResult(takePictureIntent,1);
                }
            }
        });

        savebutonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nameInput.getText().toString().matches("")
                        && !AgeInput.getText().toString().matches("")
                        && newbulldogImage.getDrawable() != null)
                {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Bulldog bulldog = new Bulldog();
                            bulldog.setAge(AgeInput.getText().toString());
                            bulldog.setName(nameInput.getText().toString());
                            bulldog.setId(realm.where(Bulldog.class).findAllSorted("id").last().getId() + 1);
                            BitmapDrawable image = (BitmapDrawable) newbulldogImage.getDrawable();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            image.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageInByte = baos.toByteArray();
                            bulldog.setImage(imageInByte);

                            realm.copyToRealm(bulldog);
                            finish();
                        }
                    });
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            newbulldogImage.setImageBitmap(imageBitmap);
        }

    }
}
