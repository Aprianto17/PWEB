package com.example.aprianto.pwebchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference("chats");

    ArrayList<Chat> chats = new ArrayList<>();

    EditText etKetik;
    ImageView btSend;

    RecyclerView rvChats;
    ChatListAdapter adapter;

    User user;
    SharedPreferences mylocaldata;

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        //getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    //Daftar menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sign_out) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else if (item.getItemId() == R.id.user) {
            startActivity(new Intent(MainActivity.this, UsersListActivity.class));
        }else if (item.getItemId() == R.id.profil) {
            Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mylocaldata = getSharedPreferences("mylocaldata", MODE_PRIVATE);

        user = getIntent().getParcelableExtra("user");

//        if ( user == null){
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intent);
//        }
        // PEMBACAAN DATA DARI FIREBASE
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chats.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Chat chat = postSnapshot.getValue( Chat.class );
                    chats.add(chat);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        // PENGAKTIFAN RecyclerView MENGGUNAKAN ChatListAdapter
        rvChats = (RecyclerView)findViewById(R.id.rvChats);
        rvChats.setHasFixedSize(true);
        rvChats.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatListAdapter(this, chats);
        rvChats.setAdapter(adapter);

        etKetik = (EditText)findViewById(R.id.etKetik);
        btSend = (ImageView) findViewById(R.id.btSend);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chat chat = new Chat();
                chat.setPesan( etKetik.getText().toString() );
                chat.setTanggal( new Date().getTime() );
                chat.setSender( user );

                chat.send();
                etKetik.setText("");
            }
        });
    }
}
