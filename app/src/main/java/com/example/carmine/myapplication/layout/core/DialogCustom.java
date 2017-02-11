package com.example.carmine.myapplication.layout.core;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.example.carmine.myapplication.R;

/**
 * Created by carmine on 02/02/2017.
 */

public class DialogCustom {
    private Button buttonUno;
    private Button buttonDue;

    public DialogCustom(Context context, String titolo, String button1) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setTitle(titolo);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(titolo);
        buttonUno = (Button) dialog.findViewById(R.id.DialogButtonOk);
        buttonUno.setText(button1);
        dialog.show();
    }

    public DialogCustom(Context context, String titolo, String button1, String button2)

    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_custom_due_bottoni);
        dialog.setTitle(titolo);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(titolo);
        buttonUno = (Button) dialog.findViewById(R.id.DialogButton1);
        buttonUno.setText(button1);
        buttonDue = (Button) dialog.findViewById(R.id.DialogButton2);
        buttonDue.setText(button2);
        dialog.show();
    }


    public Button getButtonUno() {
        return buttonUno;
    }

    public Button getButtonDue() {
        return buttonDue;
    }
}
