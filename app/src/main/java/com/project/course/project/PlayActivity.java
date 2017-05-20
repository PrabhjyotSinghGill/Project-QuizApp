package com.project.course.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PlayActivity extends AppCompatActivity {

    private static String TAG = "Play Activity";
    Button mButtonAnswer, mScoreButton;
    TextView mQuestionTextView;
    RadioGroup mAnswerRadioGroup;
    RadioButton mOptionA_RadioButton, mOptionB_RadioButton,
            mOptionC_RadioButton, mOptionD_RadioButton;
    private DatabaseReference mQuestionsReference;
    Iterator<DataSnapshot> mQuestionSet;
    int numQuestions = 0;
    int answer_key = -1;
    int questionIndex = 0;
    int score = 0;

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button_answer_question:
                    int answerGiven = -1;
                    switch (mAnswerRadioGroup.getCheckedRadioButtonId()){
                        case R.id.radio_a_play:
                            answerGiven = 1;
                            break;
                        case R.id.radio_b_play:
                            answerGiven = 2;
                            break;
                        case R.id.radio_c_play:
                            answerGiven = 3;
                            break;
                        case R.id.radio_d_play:
                            answerGiven = 4;
                            break;
                        default:
                            break;
                    }
                    if(answerGiven == answer_key){
                        score += 10;
                        Toast.makeText(getApplicationContext(),"Correct, Score:"+score,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Incorrect Answer, Score:"+score,Toast.LENGTH_SHORT).show();
                    }
                    if(mQuestionSet.hasNext()){
                        updateQuestion();
                    }else{
                        Toast.makeText(getApplicationContext(),"Quiz finished, your score:"+score,Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    mScoreButton.setText("Score : "+score);
                    break;
                default:
                    break;
            }
        }
    };

    ValueEventListener questionListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mQuestionSet = dataSnapshot.getChildren().iterator();
            numQuestions = ((Long) dataSnapshot.getChildrenCount()).intValue();
            if(numQuestions>0){
                updateQuestion();
            }else{
                Toast.makeText(getApplicationContext(),"No questions present in database",Toast.LENGTH_LONG).show();
                finish();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mQuestionTextView = (TextView) findViewById(R.id.question_play);
        mButtonAnswer = (Button) findViewById(R.id.button_answer_question);
        mScoreButton = (Button) findViewById(R.id.score_button);
        mAnswerRadioGroup = (RadioGroup) findViewById(R.id.radio_answer_play);
        mOptionA_RadioButton = (RadioButton) findViewById(R.id.radio_a_play);
        mOptionB_RadioButton = (RadioButton) findViewById(R.id.radio_b_play);
        mOptionC_RadioButton = (RadioButton) findViewById(R.id.radio_c_play);
        mOptionD_RadioButton = (RadioButton) findViewById(R.id.radio_d_play);

        mButtonAnswer.setOnClickListener(mOnClickListener);

        mQuestionsReference = FirebaseDatabase.getInstance().getReference().child(MainActivity.KEY_QUIZ_QUESTIONS);
        mQuestionsReference.addValueEventListener(questionListener);
    }

    public void updateQuestion(){
        DataSnapshot snap = mQuestionSet.next();
        String question = snap.getKey();
        HashMap<String, Object> snapshotData = (HashMap<String, Object>) snap.getValue();
        String option_a = ((ArrayList<String>) snapshotData.get("options")).get(0);
        String option_b = ((ArrayList<String>) snapshotData.get("options")).get(1);
        String option_c = ((ArrayList<String>) snapshotData.get("options")).get(2);
        String option_d = ((ArrayList<String>) snapshotData.get("options")).get(3);
        answer_key = ((Long) snapshotData.get("answer")).intValue();
        mQuestionTextView.setText(question);
        mOptionA_RadioButton.setText(option_a);
        mOptionB_RadioButton.setText(option_b);
        mOptionC_RadioButton.setText(option_c);
        mOptionD_RadioButton.setText(option_d);
    }
}
