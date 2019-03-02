package com.example.moulaye.quizapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import static android.support.constraint.Constraints.TAG;

@SuppressLint("ValidFragment")
public class ExDialog extends AppCompatDialogFragment {
    private EditText editTextUser;
    public  String username;
    public interface OnInputListener{
        void sendInput(String input);
    }
    public OnInputListener mOnInputList;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_diag, null);

        builder.setView(view)
                .setTitle("Entrer votre pseudonyme")
                .setPositiveButton("Jouer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialInterface, int i) {
                        username = editTextUser.getText().toString();
                        if(username != "") {
                            mOnInputList.sendInput(username);
                            dismiss();
                        }
                        else {
                            builder.setTitle("Entrer un Pseudo avant de jouer");

                        }

                    }
                });

        editTextUser = view.findViewById(R.id.edit_usernameQuiz);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputList = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }

}
