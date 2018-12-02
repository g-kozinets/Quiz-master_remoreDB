package com.delaroystudios.quiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class UserInfoFragment extends AppCompatDialogFragment {
    private EditText userName;
    private UserNameDialog listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_user_info, null);

        builder.setView(view)
                .setTitle("Please, enter your name")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = "Unknown";
                        listener.applyName(name);
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = userName.getText().toString();
                        listener.applyName(name);
                    }
                });

        userName = view.findViewById(R.id.user_name);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (UserNameDialog)context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString()+"implement listener");
        }
    }

    public interface UserNameDialog{
        void applyName(String name);

    }
}
