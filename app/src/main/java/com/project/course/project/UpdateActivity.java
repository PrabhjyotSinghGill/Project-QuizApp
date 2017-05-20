package com.project.course.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateActivity extends AppCompatActivity {
    private static String TAG = "UpdateActivity";
    public static String KEY_FROM_UPDATE = "from_update";
    public static String KEY_QUESTION = "question";
    private DatabaseReference mQuestionsReference;
    DataSnapshot mData;
    LinearLayout mContainer;

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView tv = (TextView) view;
            String question = tv.getText().toString();
            Intent intent = new Intent(getApplicationContext(),AddActivity.class);
            intent.putExtra(KEY_QUESTION, question);
            intent.putExtra(KEY_FROM_UPDATE, true);
            startActivity(intent);
        }
    };

    ValueEventListener questionListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mData = dataSnapshot;
            Log.w(TAG, "changed");
            mContainer.removeAllViews();
            if(mData.getChildrenCount()==0){
                Toast.makeText(getApplicationContext(),
                        "No more questions in the database, please load questions first.", Toast.LENGTH_LONG).show();
                finish();
            }
            for (DataSnapshot imageSnapshot: mData.getChildren()) {
                TextView tv = new TextView(getApplicationContext());
                tv.setPadding(10,10,10,10);
                tv.setText(imageSnapshot.getKey());
                tv.generateViewId();
                tv.setOnClickListener(mOnClickListener);
                tv.setBackgroundResource(R.drawable.back);
                mContainer.addView(tv);
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

        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContainer = (LinearLayout) findViewById(R.id.container);
        mQuestionsReference = FirebaseDatabase.getInstance().getReference().child(MainActivity.KEY_QUIZ_QUESTIONS);
        mQuestionsReference.addValueEventListener(questionListener);
    }
}
