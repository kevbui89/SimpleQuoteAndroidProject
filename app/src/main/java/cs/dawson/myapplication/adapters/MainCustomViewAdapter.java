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

import cs.dawson.myapplication.MainActivity;
import cs.dawson.myapplication.QuoteListActivity;
import cs.dawson.myapplication.R;
import cs.dawson.myapplication.beans.Quote;
import java.util.ArrayList;
import java.util.List;

/**
 * An adapter that will load a ListView in the UI and set the appropriate onClick() listener
 * @author Kevin Bui, Hannah Ly
 * @version 1.0
 */

public class MainCustomViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> categories;
    private DataSnapshot snapshot;
    String TAG = MainCustomViewAdapter.class.getName();
    private static LayoutInflater inflater;

    /**
     * Constructor
     * @param ma            The main activity
     * @param categories    The list of categories
     * @param ss            The database snapshot
     */
    public MainCustomViewAdapter(MainActivity ma, ArrayList<String> categories, DataSnapshot ss){
        this.context = ma;
        this.categories = categories;
        this.snapshot = ss;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categories.size();
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

        View rowView = inflater.inflate(R.layout.list_category, null);

        TextView tv = (TextView) rowView.findViewById(R.id.listItem);

        //Displaying the current category in the UI
        tv.setText(categories.get(position));

        //Container of the list of Quotes in the current category
        final List<Quote> quoteList = new ArrayList<>();

        /**
         * Setting an onClickListener on the current row view
         */
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick(View view), view clicked, category is " + categories.get(position));
                Log.i(TAG, "number of quotes in current Category list in onClick(): " + quoteList.size());

                //Setting up intent extra data
                Intent intent = new Intent(context, QuoteListActivity.class);
                // The category name
                intent.putExtra("categoryChosen", categories.get(position));

                //Launching the quoteList activity
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
