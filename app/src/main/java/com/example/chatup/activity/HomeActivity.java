package com.example.chatup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatup.model.Messaggio;
import com.example.chatup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private EditText mimputtext;
    private Button mButtoninvia;
    private ChatListAdapter chatListAdapter;
    private RecyclerView rvChatmsg;

    @Override
    protected void onStart() {
        super.onStart();

        updateUI();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //todo 2 ricevere i dati da intent LoginActivity con il metodo getextras()
        Bundle b = getIntent().getExtras();
        String extra = b.getString("msg");


        /*firebase */
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String displayName = "";
        if (user != null) {
            displayName = user.getDisplayName();
        }

        setTitle(displayName);

        /*firebase scrivere su realdadatabase*/

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        initUI();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvChatmsg.setLayoutManager(linearLayoutManager);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        chatListAdapter = new ChatListAdapter(this, mDatabaseReference, displayName);

        rvChatmsg.setAdapter(chatListAdapter);


        //todo 3 presentare i dati all utente con un toast
        Toast.makeText(this, "Utente :" + extra, Toast.LENGTH_LONG).show();


    }

    private void initUI() {
        mimputtext = (EditText) findViewById(R.id.TestoM);
        mButtoninvia = (Button) findViewById(R.id.textinvia);
        rvChatmsg = (RecyclerView) findViewById(R.id.rv_chat);

        mimputtext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                inviamessaggio();
                return true;
            }
        });
        mButtoninvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inviamessaggio();
            }
        });
    }

    private void inviamessaggio() {
        Log.i(TAG, "invio Messaggio");

        String inputMsg = mimputtext.getText().toString();
        if (!inputMsg.equals("")) {
            Messaggio chat = new Messaggio(inputMsg, mAuth.getCurrentUser().getDisplayName());

            mDatabaseReference.child("messaggi").push().setValue(chat);
            mimputtext.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.layoutmenu, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Logoutitem) {
            Log.i(TAG, "logout selezionato");

            mAuth.signOut();
            updateUI();

            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intlog = new Intent(this, LoginActivity.class);
            startActivity(intlog);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        chatListAdapter.clean();
    }
}

