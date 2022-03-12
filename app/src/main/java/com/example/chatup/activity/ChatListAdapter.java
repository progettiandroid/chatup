package com.example.chatup.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chatup.model.Messaggio;
import com.example.chatup.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

    private Activity mActivity;
    private static DatabaseReference mDatabaseReference;
    private String mDisplayname;
    private ArrayList<DataSnapshot> mDataSnapshot;
    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
            mDataSnapshot.add(snapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

        }

        @Override
        public void onChildRemoved(DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

        }

        @Override
        public void onCancelled(DatabaseError error) {

        }
    };


    public class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView autore;
        TextView messaggio;

        public ChatViewHolder(View itemView) {
            super(itemView);

            autore = (TextView) itemView.findViewById(R.id.tv_autore);
            messaggio = (TextView) itemView.findViewById(R.id.tv_messaggio);
        }
    }

    public ChatListAdapter(Activity activity, DatabaseReference ref, String name) {
        mActivity = activity;
        mDatabaseReference = ref.child("messaggi");
        mDisplayname = name;
        mDataSnapshot = new ArrayList<>();

        mDatabaseReference.addChildEventListener(mListener);


    }


    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.chat_msg_row, parent, false);
        ChatViewHolder vh = new ChatViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        DataSnapshot snapshot = mDataSnapshot.get(position);
        Messaggio msg = snapshot.getValue(Messaggio.class);
        holder.autore.setText(msg.getAutore());
        holder.messaggio.setText(msg.getMessaggio());
        boolean sonoIO = msg.getAutore().equals(mDisplayname);
        setChatItemStyle(sonoIO, holder);

    }

    private void setChatItemStyle(Boolean sonoIO, ChatViewHolder holder) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.autore.getLayoutParams();
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) holder.messaggio.getLayoutParams();
        if (sonoIO) {
            params.gravity = Gravity.END;
            params2.gravity = Gravity.END;
            holder.autore.setTextColor(Color.BLUE);
            holder.messaggio.setBackgroundResource(R.drawable.msg_uno);
        }
        else {
            params.gravity = Gravity.START;
            params2.gravity = Gravity.START;
            holder.autore.setTextColor(Color.MAGENTA);
        }
        holder.autore.setLayoutParams(params);
        holder.messaggio.setTextColor(Color.WHITE);
    }


    @Override
    public int getItemCount() {
        return mDataSnapshot.size();
    }

    public void clean() {

        mDatabaseReference.removeEventListener(mListener);
    }
}

