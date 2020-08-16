package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button next, login, back, setting;
    EditText username, password;
    View usernameL, passwordL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        G.context = this;
        G.currentActivity = this;

        G.database = new DBHelper(this);

        G.settings = getSharedPreferences("UserInfo", 0);
        G.editor = G.settings.edit();

        String setUser = G.settings.getString("setUser", "NotSet");

        next = (Button) findViewById(R.id.nextb);
        login = (Button) findViewById(R.id.login);
        back = (Button) findViewById(R.id.back);
        setting = (Button) findViewById(R.id.setting);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        usernameL = (View) findViewById(R.id.usernamel);
        passwordL = (View) findViewById(R.id.passwordl);

        passwordL.setVisibility(View.GONE);
        back.setVisibility(View.GONE);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameL.setVisibility(View.GONE);
                setting.setVisibility(View.GONE);
                passwordL.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordL.setVisibility(View.GONE);
                back.setVisibility(View.GONE);
                usernameL.setVisibility(View.VISIBLE);
                setting.setVisibility(View.VISIBLE);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password.getText().toString();
                String user = username.getText().toString();

//                Toast.makeText(G.context, "username = " + user + "\n password = " + pass, Toast.LENGTH_SHORT).show();

                if (user.compareTo(G.UserName) == 0 && pass.compareTo(G.PassWord) == 0){
                    Intent intent = new Intent(MainActivity.this, LNote.class);
                    MainActivity.this.startActivity(intent);
                }
                else
                    Toast.makeText(G.context, "UserName or PassWord is not true", Toast.LENGTH_LONG).show();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReSetUser();
            }
        });

        if (setUser.compareTo("NotSet") == 0)
            SetUser();
        else {
            G.PassWord = G.settings.getString("PassWord", "NotSet");
            G.UserName = G.settings.getString("Username", "NotSet");
        }
    }

    public void SetUser(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_setting_password);

        Button ok = (Button) dialog.findViewById(R.id.ok);
        Button close = (Button) dialog.findViewById(R.id.close);
        final EditText username, password, repassword, usernameerror, passworderror, repassworderror;

        username = (EditText) dialog.findViewById(R.id.username);
        password = (EditText) dialog.findViewById(R.id.password);
        repassword = (EditText) dialog.findViewById(R.id.repassword);

        usernameerror = (EditText) dialog.findViewById(R.id.usernameerror);
        passworderror = (EditText) dialog.findViewById(R.id.passworderror);
        repassworderror = (EditText) dialog.findViewById(R.id.repassworderror);

        usernameerror.setVisibility(View.GONE);
        passworderror.setVisibility(View.GONE);
        repassworderror.setVisibility(View.GONE);

        final boolean[] f = {true, true};

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String P1, P2, U;
                if (f[0]){
                    P1 = password.getText().toString();
                    P2 = repassword.getText().toString();
                } else {
                    P1 = passworderror.getText().toString();
                    P2 = repassworderror.getText().toString();
                }
                if (f[1]){
                    U = username.getText().toString();
                } else {
                    U = usernameerror.getText().toString();
                }
                if (U.compareTo("") != 0) {
                    if (P1.compareTo(P2) == 0 && P1.compareTo("") != 0){
                        G.editor.putString("Username",U);
                        G.editor.putString("PassWord",P1);
                        G.editor.putString("setUser","set");
                        G.UserName = U;
                        G.PassWord = P1;
                        G.editor.commit();
                        dialog.dismiss();
                    } else {
                        f[0] = false;
                        repassword.setVisibility(View.GONE);
                        password.setVisibility(View.GONE);
                        passworderror.setVisibility(View.VISIBLE);
                        repassworderror.setVisibility(View.VISIBLE);
                    }
                } else {
                    username.setVisibility(View.GONE);
                    usernameerror.setVisibility(View.VISIBLE);
                    f[1] = false;
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void ReSetUser() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_resetting_password);

        Button ok = (Button) dialog.findViewById(R.id.ok);
        Button close = (Button) dialog.findViewById(R.id.close);
        final EditText username, password, repassword, usernameerror, passworderror, repassworderror, oldusername, oldpassword, oldusernameerror, oldpassworderror;

        username = (EditText) dialog.findViewById(R.id.username);
        password = (EditText) dialog.findViewById(R.id.password);
        repassword = (EditText) dialog.findViewById(R.id.repassword);

        usernameerror = (EditText) dialog.findViewById(R.id.usernameerror);
        passworderror = (EditText) dialog.findViewById(R.id.passworderror);
        repassworderror = (EditText) dialog.findViewById(R.id.repassworderror);

        oldusernameerror = (EditText) dialog.findViewById(R.id.oldusernameerror);
        oldpassworderror = (EditText) dialog.findViewById(R.id.oldpassworderror);
        oldusername = (EditText) dialog.findViewById(R.id.oldusername);
        oldpassword = (EditText) dialog.findViewById(R.id.oldpassword);

        usernameerror.setVisibility(View.GONE);
        passworderror.setVisibility(View.GONE);
        repassworderror.setVisibility(View.GONE);

        oldusernameerror.setVisibility(View.GONE);
        oldpassworderror.setVisibility(View.GONE);

        final boolean[] f = {true, true, true, true};

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String P1, P2, U, OU, OP;
                if (f[0]){
                    P1 = password.getText().toString();
                    P2 = repassword.getText().toString();
                } else {
                    P1 = passworderror.getText().toString();
                    P2 = repassworderror.getText().toString();
                }
                if (f[1]){
                    U = username.getText().toString();
                } else {
                    U = usernameerror.getText().toString();
                }
                if (f[2]){
                    OU = oldusername.getText().toString();
                } else {
                    OU = oldusernameerror.getText().toString();
                }
                if (f[3]){
                    OP = oldpassword.getText().toString();
                } else {
                    OP = oldpassworderror.getText().toString();
                }
                if (OU.compareTo(G.UserName) == 0) {
                    if (OP.compareTo(G.PassWord) == 0) {
                        if (U.compareTo("") != 0) {
                            if (P1.compareTo(P2) == 0 && P1.compareTo("") != 0){
                                G.editor.putString("Username",U);
                                G.editor.putString("PassWord",P1);
                                G.editor.putString("setUser","set");
                                G.UserName = U;
                                G.PassWord = P1;
                                G.editor.commit();
                                dialog.dismiss();
                            } else {
                                f[0] = false;
                                repassword.setVisibility(View.GONE);
                                password.setVisibility(View.GONE);
                                passworderror.setVisibility(View.VISIBLE);
                                repassworderror.setVisibility(View.VISIBLE);
                            }
                        } else {
                            username.setVisibility(View.GONE);
                            usernameerror.setVisibility(View.VISIBLE);
                            f[1] = false;
                        }
                    } else {
                        oldpassword.setVisibility(View.GONE);
                        oldpassworderror.setVisibility(View.VISIBLE);
                        f[3] = false;
                    }
                } else {
                    f[2] = false;
                    oldusername.setVisibility(View.GONE);
                    oldusernameerror.setVisibility(View.VISIBLE);
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}