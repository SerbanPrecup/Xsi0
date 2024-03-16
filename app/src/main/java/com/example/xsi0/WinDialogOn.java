package com.example.xsi0;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class WinDialogOn extends Dialog {

    private final String message;
    private final Xsi0OnActivity xsi0OnActivity;

    public WinDialogOn(@NonNull Context context, String message, Xsi0OnActivity xsi0OnActivity){
        super(context);
        this.message = message;
        this.xsi0OnActivity= xsi0OnActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_dialog_layout);

        final TextView messageTxt = findViewById(R.id.messageTxt);
        final Button startAgain = findViewById(R.id.startAgainBtn);

        messageTxt.setText(message);

        startAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xsi0OnActivity.restartMatch();
                dismiss();
            }
        });



    }
}
