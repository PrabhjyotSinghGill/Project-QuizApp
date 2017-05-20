package com.project.course.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    public static String KEY_QUIZ_QUESTIONS = "quiz_questions";
    private String TAG = "MainActivity";
    private Button mAddButton, mPlayButton, mUpdateButton, mButtonLoad, mButtonDelete;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mIntent;
            switch (view.getId()){
                case R.id.button_play:
                    mIntent = new Intent(getApplicationContext(), PlayActivity.class);
                    startActivity(mIntent);
                    break;
                case R.id.button_add:
                    mIntent = new Intent(getApplicationContext(), AddActivity.class);
                    startActivity(mIntent);
                    break;
                case R.id.button_update:
                    mIntent = new Intent(getApplicationContext(), UpdateActivity.class);
                    startActivity(mIntent);
                    break;
                case R.id.button_delete:
                    FirebaseDatabase.getInstance().getReference().child(MainActivity.KEY_QUIZ_QUESTIONS).removeValue();
                    break;
                case R.id.button_load:
                    new Questions().load(FirebaseDatabase.getInstance().getReference().child(MainActivity.KEY_QUIZ_QUESTIONS));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPlayButton = (Button) findViewById(R.id.button_play);
        mPlayButton.setOnClickListener(mOnClickListener);
        mAddButton = (Button) findViewById(R.id.button_add);
        mAddButton.setOnClickListener(mOnClickListener);
        mUpdateButton = (Button) findViewById(R.id.button_update);
        mUpdateButton.setOnClickListener(mOnClickListener);

        mButtonDelete = (Button) findViewById(R.id.button_delete);
        mButtonDelete.setOnClickListener(mOnClickListener);
        mButtonLoad = (Button) findViewById(R.id.button_load);
        mButtonLoad.setOnClickListener(mOnClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
