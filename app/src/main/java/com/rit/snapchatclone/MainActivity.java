package com.rit.snapchatclone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.snapchat.kit.sdk.SnapLogin;
import com.snapchat.kit.sdk.core.controller.LoginStateController;
import com.snapchat.kit.sdk.login.models.UserDataResponse;
import com.snapchat.kit.sdk.login.networking.FetchUserDataCallback;

import static com.snapchat.kit.sdk.SnapLogin.fetchUserData;

public class MainActivity extends AppCompatActivity implements LoginStateController.OnLoginStateChangedListener, FetchUserDataCallback {
    TextView label;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        label = findViewById(R.id.textView);
        button = findViewById(R.id.button2);
        label.setText("Pemoji");
        SnapLogin.getLoginStateController(this).addOnLoginStateChangedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnapLogin.getAuthTokenManager(getApplicationContext()).startTokenGrant();

            }
        });
    }

    @Override
    public void onLoginSucceeded() {
        Log.d("login", " login success");
        getUserData();

    }

    @Override
    public void onLoginFailed() {
        Log.d("login faied", " login Failed");

    }

    @Override
    public void onLogout() {
        Log.d("logout", " logout");
        SnapLogin.getAuthTokenManager(this).revokeToken();

    }

    @Override
    public void onSuccess(@Nullable UserDataResponse userDataResponse) {
        String name = userDataResponse.getData().getMe().getDisplayName();
        String avatar = userDataResponse.getData().getMe().getBitmojiData().getAvatar();

        goToNext(name, avatar);


    }


    private void goToNext(String name, String avatar) {
        Intent intent = new Intent(this, LoginConfirmActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("avatar", avatar);
        startActivity(intent);

    }

    @Override
    public void onFailure(boolean b, int i) {

    }

    private void getUserData() {
        String query = "{me{bitmoji{avatar},displayName}}";
        fetchUserData(this, query, null, this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        SnapLogin.getLoginStateController(this).addOnLoginStateChangedListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SnapLogin.getLoginStateController(this).removeOnLoginStateChangedListener(this);
    }
}

