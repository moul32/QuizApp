package com.example.moulaye.quizapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import static android.support.constraint.Constraints.TAG;
import static com.example.moulaye.quizapp.QuizDbHelper.db;


public class QuizActivity extends AppCompatActivity implements ExDialog.OnInputListener {
    private TextView textViewQuestion;
    private Button buttonConfirmNext;
    private ImageView img;
    private List<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private int score;
    String username;
    String text="";
    @Override
    public void sendInput(String input) {
        Log.d(TAG, "sendInput: got the input: " + input);

        //  mInputDisplay.setText(input);
        username = input;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        loadCSV2();
        //Lancement du fragment Username
        ExDialog exampleDialog = new ExDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");

        textViewQuestion = findViewById(R.id.text_view_question);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);
        img = findViewById(R.id.img);



        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);
        showNextQuestion();

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText simpleEditText=(EditText)findViewById(R.id.edit_reponse);
                text = simpleEditText.getText().toString();
            if(text.isEmpty())  Toast.makeText(QuizActivity.this, "Entrer une réponse SVP!", Toast.LENGTH_SHORT).show();
           else {
                simpleEditText.setText("");


                img.setImageResource(0);

                if (text.equals(currentQuestion.getReponse())) {
                    score++;
                    //  System.out.print("ha4i wark :"+currentQuestion.getCapitale()+" text "+text+"\n");

                    Toast.makeText(QuizActivity.this, "Bonne réponse!", Toast.LENGTH_SHORT).show();
                } else {
                    //  System.out.print("ha4i mohou sal7 :"+currentQuestion.getCapitale()+" text "+text+"\n");
                    // img.setImageResource(R.drawable.mr);
                    Toast.makeText(QuizActivity.this, "Mauvaise réponse", Toast.LENGTH_SHORT).show();
                }

                showNextQuestion();
            }

            }
        });
    }

    public void loadCSV2() {

        QuizDbHelper dbHelper = new QuizDbHelper(this);

        String mCSVfile = "datafile.csv";

        AssetManager manager = this.getAssets();
        InputStream inStream = null;
        try {
            Log.i(TAG, "loadCSV2: ");
            inStream = manager.open(mCSVfile);
        } catch (IOException e) {
            Log.i(TAG, "loadCSV2:  ERROR");
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line;
        try {
            while ((line = buffer.readLine()) != null) {
                String[] colums = line.split(",");

                Log.i("CSVParser", "Skipping Bad CSV Row");
                Log.i("CSVParser", "" + colums[1]);
                Question question = new Question();

                String pays = colums[0];
                String capitale = colums[1];
                String lat = colums[2];
                String lon = colums[3];
                String monnaie = colums[4];
                String drapeau = colums[5];

                question.setQuestion(pays);
                question.setCapitale(capitale);
                question.setLat(lat);
                question.setLon(lon);
                question.setMonnaie(monnaie);
                question.setDrapeau(drapeau);
                dbHelper.addQuestion(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showNextQuestion() {



        if (questionCounter < questionCountTotal ) {
            //   System.out.println("count total : "+questionCountTotal);
            // System.out.println("count"+questionCounter);
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQues());
            System.out.println("drapeauu "+currentQuestion.getDrapeau());

             if(currentQuestion.getDrapeau()!=null) {

                 switch (currentQuestion.getDrapeau()) {
                     case "mr":  img.setImageResource(R.drawable.mr);
                         break;
                     case "es":   img.setImageResource(R.drawable.es);
                        break;
                     case "ma":   img.setImageResource(R.drawable.ma);
                         break;
                     case "ch":   img.setImageResource(R.drawable.ch);
                         break;
                     case "be":   img.setImageResource(R.drawable.be);
                         break;

                     default:  img.setImageResource(0);
                         break;
                 }/*
            else if(currentQuestion.getDrapeau().equals("mr")) img.setImageResource(R.drawable.mr);
            else if(currentQuestion.)*/
             }

            questionCounter++;

            buttonConfirmNext.setText("Confirmer");
          //  img.setImageResource(0);

        }


        else {
            finishQuiz();
        }
    }

    private void qImage(){


    }

    private void finishQuiz() {
        SharedPreferences prefs = getSharedPreferences("quizz_score", MODE_PRIVATE);
        int highscore = prefs.getInt("score",0);
        if (score >= highscore) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong("date",System.currentTimeMillis());
            editor.putInt("score", score);
            editor.putString("user",username);
            editor.commit();
        }

        finish();
    }
}
