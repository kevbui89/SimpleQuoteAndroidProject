package cs.dawson.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import cs.dawson.myapplication.adapters.QuoteListCustomViewAdapter;

/**
 * Shows the short quotes of a category from a FireBase database
 *
 * @author Kevin Bui, Hannah Ly
 * @version 1.0
 */

public class QuoteListActivity extends MenuActivity {
    private ArrayList<String> shortQuotes;
    private String category;
    private final static String TAG = QuoteListActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quote_list_activity);
        mFirebaseAuth = FirebaseAuth.getInstance();
        Intent extras = getIntent();

        category = extras.getExtras().getString("categoryChosen");
        Log.i(TAG, "The chosen category is - " + category);
        loadCategoriesShortQuotes();
    }

    /**
     * Will load the short quotes from the category selected
     */
    private void loadCategoriesShortQuotes() {
        final QuoteListActivity currentActivity = this;
        final ListView lv = (ListView) findViewById(R.id.listviewQuotes);
        Query query = mDatabase.getReference().child("Category").child(category);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shortQuotes = new ArrayList<String>();
                Log.i(TAG, "Data Snap - Getting the Short quotes");
                Log.i(TAG, "Data Snap Shot count - " + dataSnapshot.getChildrenCount());

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                //Fetch each short quotes and add to the list
                while (iterator.hasNext()) {
                    String short_quote = iterator.next().getKey();
                    Log.i(TAG, "short quote fetched - " + short_quote);

                    shortQuotes.add(short_quote);
                }

                Log.i(TAG, "List size - " + shortQuotes.size());

                //Set the adaptor
                lv.setAdapter(new QuoteListCustomViewAdapter(currentActivity, shortQuotes, dataSnapshot));
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
}

