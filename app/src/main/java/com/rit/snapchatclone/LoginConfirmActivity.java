package com.rit.snapchatclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class LoginConfirmActivity extends AppCompatActivity {
    TextView textView;
    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_confirm);
        textView = findViewById(R.id.name);
        imageView = findViewById(R.id.avatar);
        button = findViewById(R.id.button);


        String name = getIntent().getExtras().getString("name");
        String avatar = getIntent().getExtras().getString("avatar");

        Glide.with(this).load(avatar.toString()).into(imageView);
        textView.setText(name.toString());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginConfirmActivity.this, BitmojiActivity.class);
                startActivity(intent);
            }
        });


    }
}
