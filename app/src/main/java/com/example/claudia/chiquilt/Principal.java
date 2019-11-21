package com.example.claudia.chiquilt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Principal extends AppCompatActivity implements View.OnClickListener {
    private Button btnRemoveUser,
            camera, signOut;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    //menu
    private CardView tipsCard,camara,galeria,agenda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        tipsCard = (CardView) findViewById(R.id.tip);
        camara=(CardView)findViewById(R.id.cama);
       // galeria=(CardView)findViewById(R.id.gale);
        agenda=(CardView)findViewById(R.id.age);
        tipsCard.setOnClickListener(this);
        signOut = (Button) findViewById(R.id.sign_out);
        camara.setOnClickListener(this);
        //galeria.setOnClickListener(this);
        agenda.setOnClickListener(this);
        signOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.tip : i = new Intent (this,Foro.class);startActivity(i); break;
            case R.id.cama : i = new Intent (this,Foto.class);startActivity(i); break;
          //  case R.id.gale : i = new Intent (this,Galeria.class);startActivity(i); break;
            case R.id.age : i = new Intent (this,MainActivity.class);startActivity(i); break;
            default:break;
            case R.id.sign_out:
                signOut();
        }

    }
    private void signOut() {
        auth.signOut();
        startActivity(new Intent(Principal.this, Login.class));
        finish();
    }
}
