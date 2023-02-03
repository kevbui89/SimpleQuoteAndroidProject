package cs.dawson.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

import cs.dawson.myapplication.adapters.MainCustomViewAdapter;

/**
 * Shows the categories from a FireBase database
 *
 * @author Kevin Bui, Hannah Ly
 * @version 1.0
 */

public class MainActivity extends MenuActivity {

    private ArrayList<String> categories;
    // Login information
    protected final String user = "kevinhannah@gmail.com";
    protected final String password = "kevinhannah";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private final static String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
        // Set up Authentication Listener
        setUpAuthenticationListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart() triggered");
        super.onStart();
        // Check if the user is null, if it is, login, else load categories
        if (mFirebaseUser == null)
            automaticLogin();
        else
            loadCategories();
    }

    /**
     * Background login for the database
     */
    private void automaticLogin() {
        mFirebaseAuth.addAuthStateListener(mAuthListener);
        mFirebaseAuth.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadCategories();
                        } else {
                            // Build a dialog to show the user login has failed
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage(getText(R.string.no_user) + "\n" + getText(R.string.no_database)).setTitle(R.string.failure).setPositiveButton(R.string.ok
                                    , null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
    }

    /**
     * Sets up an AuthStateListener
     */
    private void setUpAuthenticationListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                if (mFirebaseUser != null) {
                    Log.i(TAG, "User is signed in - " + mFirebaseUser.getUid());
                } else {
                    Log.i(TAG, "User is signed out");
                }
            }
        };
    }

    /**
     * Loads all the categories from the database
     */
    private void loadCategories() {
        final MainActivity currentActivity = this;
        final ListView lv = (ListView) findViewById(R.id.listViewCate);

        // Query for the category
        Query query = mDatabase.getReference().child("Category");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categories = new ArrayList<String>();
                Log.i(TAG, "Data Snap - Getting the Categories");
                Log.i(TAG, "Data Snap Shot count - " + dataSnapshot.getChildrenCount());

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                //Fetch each category and add to the list
                while (iterator.hasNext()) {
                    String category = iterator.next().getKey();
                    Log.i(TAG, "Category fetched - " + category);

                    categories.add(category);
                }
                Log.i(TAG, "List size - " + categories.size());

                //Set the adaptor
                lv.setAdapter(new MainCustomViewAdapter(currentActivity, categories, dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled called - Unexpected Error ");
                Log.e(TAG, "Code : " + databaseError.getCode()
                        + " - Details : " + databaseError.getDetails()
                        + " - Message : " + databaseError.getMessage()
                );
            }
        });
    }

    /**
     * On stop method
     */
    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * Will log out account
     */
    @Override
    public void onDestroy() {
        // Logs the user out
        mFirebaseAuth.signOut();
        super.onDestroy();
    }

}
