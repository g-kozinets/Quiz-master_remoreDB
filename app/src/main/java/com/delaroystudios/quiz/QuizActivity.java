package com.delaroystudios.quiz;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.delaroystudios.quiz.data.DbHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity  implements UserInfoFragment.UserNameDialog{
	Question currentQuest;
	List<Question> quesList;
	List<Question> downloadedQuest;
	int score=0;
	int qid=0;
	TextView txtQuestion;
	RadioButton rda, rdb, rdc;
	Button butNext, btnFb, btnChangeName;
    DbHelper db;
	String userName = "default";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		db = new DbHelper(this);
		quesList = db.getAllQuestions();

		downloadedQuest = new ArrayList<>();
		txtQuestion=(TextView)findViewById(R.id.textView1);
		rda=(RadioButton)findViewById(R.id.radio0);
		rdb=(RadioButton)findViewById(R.id.radio1);
		rdc=(RadioButton)findViewById(R.id.radio2);
		butNext=(Button)findViewById(R.id.button1);
		btnFb = findViewById(R.id.buttonFb);
		btnChangeName = findViewById(R.id.menu_settings);

		// Get a reference to our posts
		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference ref = database.getReference();
        // Attach a listener to read the data at our posts reference
		ref.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

				downloadedQuest.clear();

				for (DataSnapshot dataSnapshotquest : dataSnapshot.getChildren()){
					Question question = dataSnapshotquest.getValue(Question.class);
					downloadedQuest.add(question);
					}

					currentQuest = downloadedQuest.get(3);
				   setQuestionView();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				System.out.println("The read failed: " + databaseError.getCode());
			}
		});

		btnFb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				FirebaseDatabase database = FirebaseDatabase.getInstance();
				DatabaseReference myRef = database.getReference();
				for(int i=0; i<10; i++){
					myRef.child("quest"+i).setValue(quesList.get(i));

				}
			}
		});

		butNext.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				RadioGroup grp=(RadioGroup)findViewById(R.id.radioGroup1);
				RadioButton answer=(RadioButton)findViewById(grp.getCheckedRadioButtonId());
				grp.clearCheck();
				if(currentQuest.getAnswer().equals(answer.getText()))
				{
					score++;
				}
				if(qid<10){
					currentQuest = downloadedQuest.get(qid);
					setQuestionView();
				}else{
					Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
					Bundle b = new Bundle();
					b.putInt("score", score);
					b.putString("userName", userName);
					intent.putExtras(b);
					startActivity(intent);
					finish();
				}
			}
		});

		openUserInfoFragment();
	}

    private void openUserInfoFragment() {
        UserInfoFragment userInfoFragment = new UserInfoFragment();
        userInfoFragment.show(getSupportFragmentManager(), "dialog");
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_quiz, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.action_settings:
				openUserInfoFragment();
				return true;
			default:
				openUserInfoFragment();
				return super.onOptionsItemSelected(item);
		}
	}

	private void setQuestionView()
	{
		txtQuestion.setText(currentQuest.getQuestion());
		rda.setText(currentQuest.getOpta());
		rdb.setText(currentQuest.getOptb());
		rdc.setText(currentQuest.getOptc());
		qid++;
	}

	@Override
	public void applyName(String name) {
		userName = name;
	}
}
