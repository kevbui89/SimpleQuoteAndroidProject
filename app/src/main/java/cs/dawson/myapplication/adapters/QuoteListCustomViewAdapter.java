package cs.dawson.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import cs.dawson.myapplication.QuoteActivity;
import cs.dawson.myapplication.QuoteListActivity;
import cs.dawson.myapplication.R;
import cs.dawson.myapplication.beans.Quote;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An adapter that will load a ListView in the UI and set the appropriate onClick() listener
 * @author Kevin Bui, Hannah Ly
 * @version 1.0
 */

public class QuoteListCustomViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> shortQuotes;
    private DataSnapshot snapshot;
    String TAG = QuoteListCustomViewAdapter.class.getName();
    private static LayoutInflater inflater;

    /**
     * Constructor
     * @param qa            The quote list activity
     * @param shortQuotes   The list of short quotes
     * @param ss            The database snapshot
     */
    public QuoteListCustomViewAdapter(QuoteListActivity qa, List<String> shortQuotes, DataSnapshot ss){
        this.context = qa;
        this.shortQuotes = shortQuotes;
        this.snapshot = ss;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return shortQuotes.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = inflater.inflate(R.layout.list_quotes, null);

        TextView tv = (TextView) rowView.findViewById(R.id.listItem);

        //Displaying the current category in the UI
        tv.setText(shortQuotes.get(position));

        //Container of the list of Quotes in the current category
        final List<Quote> quoteList = new ArrayList<>();

        /**
         * Setting an onClickListener on the current row view
         */
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick(View view), view clicked, short quote is " + shortQuotes.get(position));

                //Setting up intent extra data
                Intent intent = new Intent(context, QuoteActivity.class);
                intent.putExtra("Attributed", quoteList.get(position).getAttributed());
                intent.putExtra("Blurb", quoteList.get(position).getBlurb());
                intent.putExtra("Date", quoteList.get(position).getDate());
                intent.putExtra("DateofBirth", quoteList.get(position).getDateOfBirth());
                intent.putExtra("QuoteFull", quoteList.get(position).getQuoteFull());
                intent.putExtra("QuoteShort", quoteList.get(position).getQuoteShort());
                intent.putExtra("Reference", quoteList.get(position).getReference());
                intent.putExtra("last", "notlast");
                intent.putExtra("Category", quoteList.get(position).getCategory());

                //Launching the quote activity
                context.startActivity(intent);
            }
        });

        /**
         * Querying the list of quotes in the current short quote and updating the list of Quote
         */
        final Query query = snapshot.getRef();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG,"Short Quote length for " + shortQuotes.get(position) + " is: "+ dataSnapshot.getChildrenCount());

                Iterator<DataSnapshot> quotes = dataSnapshot.getChildren().iterator();
                while(quotes.hasNext()){
                    DataSnapshot quoteSnap = quotes.next();
                    Quote quote = new Quote();
                    Log.i(TAG, "Attributed: " + quoteSnap.child("Attributed").getValue());
                    quote.setAttributed((String)quoteSnap.child("Attributed").getValue());
                    quote.setBlurb((String)quoteSnap.child("Blurb").getValue());
                    quote.setDate((String)quoteSnap.child("Date").getValue());
                    quote.setDateOfBirth((String)quoteSnap.child("DateofBirth").getValue());
                    quote.setQuoteFull((String)quoteSnap.child("QuoteFull").getValue());
                    quote.setQuoteShort((String)quoteSnap.child("QuoteShort").getValue());
                    quote.setReference((String)quoteSnap.child("Reference").getValue());
                    quote.setCategory((String)quoteSnap.child("Category").getValue());
                    quoteList.add(quote);
                }

                Log.i(TAG,"Attribute for " + shortQuotes.get(position) + " is " + quoteList.get(position).getAttributed());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rowView;
    }
}