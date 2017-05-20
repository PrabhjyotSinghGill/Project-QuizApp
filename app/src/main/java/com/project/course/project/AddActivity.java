package com.project.course.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AddActivity extends AppCompatActivity {
    private DatabaseReference mQuestionsReference, mreference;
    private String TAG = "AddActivity";
    private boolean fromUpdateActivity = false;
    private String question_key, option_a, option_b, option_c, option_d;
    int answer_key ;
    Button mButtonAdd, mButtonDelete;
    EditText mQuestionEditText, mOptionA_EditText,
            mOptionB_EditText, mOptionC_EditText, mOptionD_EditText;
    RadioGroup mAnswerRadioGroup;
    RadioButton mOptionA_RadioButton, mOptionB_RadioButton,
            mOptionC_RadioButton, mOptionD_RadioButton;

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_add_question:
                    if (fromUpdateActivity) {
                        mQuestionsReference.child(question_key).removeValue();
                    }
                    String question = mQuestionEditText.getText().toString();
                    int answer = -1;
                    switch (mAnswerRadioGroup.getCheckedRadioButtonId()) {
                        case R.id.radio_a:
                            answer = 1;
                            break;
                        case R.id.radio_b:
                            answer = 2;
                            break;
                        case R.id.radio_c:
                            answer = 3;
                            break;
                        case R.id.radio_d:
                            answer = 4;
                            break;
                    }
                    String[] options = {mOptionA_EditText.getText().toString(),
                            mOptionB_EditText.getText().toString(), mOptionC_EditText.getText().toString(),
                            mOptionD_EditText.getText().toString()};
                    addQuestions(question, options, answer);
                    break;
                case R.id.button_delete_question:
                    mQuestionsReference.child(question_key).removeValue();
                    break;
                default:
                    break;
            }
            finish();
        }
    };

    ValueEventListener questionListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.w(TAG, "changed");
            HashMap<String, Object> snapshotData = (HashMap<String, Object>) dataSnapshot.getValue();
            option_a = ((ArrayList<String>) snapshotData.get("options")).get(0);
            option_b = ((ArrayList<String>) snapshotData.get("options")).get(1);
            option_c = ((ArrayList<String>) snapshotData.get("options")).get(2);
            option_d = ((ArrayList<String>) snapshotData.get("options")).get(3);
            answer_key = ((Long) snapshotData.get("answer")).intValue();
            fillQuestion();
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
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            question_key = extras.getString(UpdateActivity.KEY_QUESTION, "");
            fromUpdateActivity = extras.getBoolean(UpdateActivity.KEY_FROM_UPDATE, false);
        }

        mQuestionsReference = FirebaseDatabase.getInstance().getReference().child(MainActivity.KEY_QUIZ_QUESTIONS);
        mButtonAdd = (Button) findViewById(R.id.button_add_question);
        mButtonAdd.setOnClickListener(mOnClickListener);
        mButtonDelete = (Button) findViewById(R.id.button_delete_question);
        mButtonDelete.setOnClickListener(mOnClickListener);
        if(fromUpdateActivity){
            mButtonDelete.setVisibility(View.VISIBLE);
        }
        mQuestionEditText = (EditText) findViewById(R.id.et_question);
        mOptionA_EditText = (EditText) findViewById(R.id.et_option_a);
        mOptionB_EditText = (EditText) findViewById(R.id.et_option_b);
        mOptionC_EditText = (EditText) findViewById(R.id.et_option_c);
        mOptionD_EditText = (EditText) findViewById(R.id.et_option_d);
        mAnswerRadioGroup = (RadioGroup) findViewById(R.id.radio_answer);
        mOptionA_RadioButton = (RadioButton) findViewById(R.id.radio_a);
        mOptionB_RadioButton = (RadioButton) findViewById(R.id.radio_b);
        mOptionC_RadioButton = (RadioButton) findViewById(R.id.radio_c);
        mOptionD_RadioButton = (RadioButton) findViewById(R.id.radio_d);
        if (fromUpdateActivity) {
            mreference = mQuestionsReference.child(question_key);
            mreference.addValueEventListener(questionListener);
            mButtonAdd.setText("Update Question");
        }else{
            mButtonAdd.setText("Add Question");
        }
    }

    public void addQuestions(String que, String[] ops, int ans) {
        Options options = new Options();
        options.setOptions(ops);
        options.answer = ans;
        mQuestionsReference.child(que).setValue(options);
    }


    public void fillQuestion() {
        mreference.removeEventListener(questionListener);
        mQuestionEditText.setText(question_key);
        mOptionA_EditText.setText(option_a);
        mOptionB_EditText.setText(option_b);
        mOptionC_EditText.setText(option_c);
        mOptionD_EditText.setText(option_d);
        switch (answer_key){
            case 1:
                mOptionA_RadioButton.setChecked(true);
                break;
            case 2:
                mOptionB_RadioButton.setChecked(true);
                break;
            case 3:
                mOptionC_RadioButton.setChecked(true);
                break;
            case 4:
                mOptionD_RadioButton.setChecked(true);
                break;
        }
    }
}