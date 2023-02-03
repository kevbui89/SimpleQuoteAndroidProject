package cs.dawson.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cs.dawson.myapplication.beans.Quote;

/**
 * MenuActivity to give basic functionality to all Activities.
 * Will give activities that extends this Activity class a common menu
 *
 * @author Kevin Bui, Hannah Ly
 * @version 1.0
 */

public class MenuActivity extends AppCompatActivity {

    private final static String TAG = MenuActivity.class.getName();
    // Database settings
    protected FirebaseAuth mFirebaseAuth;
    protected FirebaseUser mFirebaseUser;
    protected FirebaseDatabase mDatabase;
    private ArrayList<String> categorieList;
    private String category;
    private int position = (int) (Math.random() * 4);
    private List<Quote> quoteList;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        getRandomCategory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(getApplicationContext(), "About Selected", Toast.LENGTH_LONG).show();
                //Connect the AboutActivity
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                return true;
            case R.id.random:
                Toast.makeText(getApplicationContext(), "Random Selected", Toast.LENGTH_LONG).show();
                //Select a random quote
                getRandomCategory();
                Log.i(TAG, "Random number: " + position);
                Intent intentRan = new Intent(this, QuoteActivity.class);
                intentRan.putExtra("Attributed", quoteList.get(position).getAttributed());
                intentRan.putExtra("Blurb", quoteList.get(position).getBlurb());
                intentRan.putExtra("Date", quoteList.get(position).getDate());
                intentRan.putExtra("DateofBirth", quoteList.get(position).getDateOfBirth());
                intentRan.putExtra("QuoteFull", quoteList.get(position).getQuoteFull());
                intentRan.putExtra("QuoteShort", quoteList.get(position).getQuoteShort());
                intentRan.putExtra("Reference", quoteList.get(position).getReference());
                intentRan.putExtra("Category", quoteList.get(position).getCategory());
                intentRan.putExtra("last", "notlast");
                intentRan.putExtra("Category", quoteList.get(position).getCategory());
                startActivity(intentRan);
                return true;
            case R.id.last:
                Toast.makeText(getApplicationContext(), "Last Selected", Toast.LENGTH_LONG).show();
                //SharedPreferences
                Intent intentLast = new Intent(this, QuoteActivity.class);
                intentLast.putExtra("last", "last");
                startActivity(intentLast);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Gets the random category
     */
    public void getRandomCategory() {
        // Query for the category
        Query query = mDatabase.getReference().child("Category");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categorieList = new ArrayList<String>();
                Log.i(TAG, "Data Snap - Getting the Categories");
                Log.i(TAG, "Data Snap Shot count - " + dataSnapshot.getChildrenCount());

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                //Fetch each category and add to the list
                while (iterator.hasNext()) {
                    String category = iterator.next().getKey();
                    Log.i(TAG, "Category fetched - " + category);

                    categorieList.add(category);
                }
                Log.i(TAG, "List size - " + categorieList.size());

                int random = (int) (Math.random() * categorieList.size());
                category = categorieList.get(random);
                Log.i(TAG, "Category is: " + category);
                getQuotes(category);
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
     * Gets all the quotes from the random category
     *
     * @param category The random category
     */
    public void getQuotes(String category) {
        quoteList = new ArrayList<>();
        getRandomCategory();
        Log.i(TAG, "Category is: " + category);
        Query query = mDatabase.getReference().child("Category").child(category);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "Data Snap Shot for Random: " + dataSnapshot.getChildrenCount());

                Iterator<DataSnapshot> quotes = dataSnapshot.getChildren().iterator();
                while (quotes.hasNext()) {
                    DataSnapshot quoteSnap = quotes.next();
                    Quote quote = new Quote();
                    Log.i(TAG, "Writing a quote: " + quoteSnap.child("Attributed").getValue());
                    quote.setAttributed((String) quoteSnap.child("Attributed").getValue());
                    quote.setBlurb((String) quoteSnap.child("Blurb").getValue());
                    quote.setDate((String) quoteSnap.child("Date").getValue());
                    quote.setDateOfBirth((String) quoteSnap.child("DateofBirth").getValue());
                    quote.setQuoteFull((String) quoteSnap.child("QuoteFull").getValue());
                    quote.setQuoteShort((String) quoteSnap.child("QuoteShort").getValue());
                    quote.setReference((String) quoteSnap.child("Reference").getValue());
                    quote.setCategory((String) quoteSnap.child("Category").getValue());
                    quoteList.add(quote);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

