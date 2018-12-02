package com.delaroystudios.quiz;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.delaroystudios.quiz.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResultActivity extends AppCompatActivity{
	User user;
	int score;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		//get rating bar object
		RatingBar bar=(RatingBar)findViewById(R.id.ratingBar1); 
		bar.setNumStars(5);
		bar.setStepSize(0.5f);
		//get text view
		TextView t=(TextView)findViewById(R.id.textResult);
		//get score
		Bundle b = getIntent().getExtras();
		score= b.getInt("score");
		//display score
		bar.setRating(score/2);
		switch (score)
		{
			case 0: t.setText("0 баллов, всё");
				break;
		default: t.setText("Вы набрали " + score + " балл(а/ов)");
			break;
		}

		addUser();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_result, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent settingsIntent = new Intent(this, QuizActivity.class);
			startActivity(settingsIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	void addUser(){
        Bundle b = getIntent().getExtras();
        String userName = b.getString("userName");
        user = new User(userName, score);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users");
        // Generate a reference to a new location and add some data using push()
        String key = myRef.push().getKey();

        myRef.child(key).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.getMessage());

                    Toast.makeText(getBaseContext(), "Can't save user",
                            Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Data saved successfully.");

                    Toast.makeText(getBaseContext(), "User saved!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
