package com.example.xsi0;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class WaitingRoom extends Dialog {

    private final String message;
    private final Room1Activity room1Activity;

    public WaitingRoom(@NonNull Context context, String message, Room1Activity room1Activity){
        super(context);
        this.message = message;
        this.room1Activity= room1Activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_room_layout);

        final TextView messageTxt = findViewById(R.id.messageTxt);
        final Button cancelBtn = findViewById(R.id.cancelBtn);

        messageTxt.setText(message);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

//        startAgain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                room1Activity.restartMatch();
//                dismiss();
//            }
//        });


    }}
