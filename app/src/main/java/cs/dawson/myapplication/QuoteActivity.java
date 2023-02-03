package cs.dawson.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Shows the quote from a FireBase database
 *
 * @author Kevin Bui, Hannah Ly
 * @version 1.0
 */

public class QuoteActivity extends MenuActivity {

    private static final String TAG = QuoteActivity.class.getName();
    String blurb;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quote_activity);
        String last;

        // Get the extras from quoteListActivity
        Intent extras = getIntent();
        String attributed;
        String blurb;
        String date;
        String dateOfBirth;
        String quoteFull;
        String quoteShort;
        String reference;

        // Get reference to textviews
        TextView attributeTv = (TextView) findViewById(R.id.attrText);
        TextView dobText = (TextView) findViewById(R.id.dobText);
        TextView sQuoteTv = (TextView) findViewById(R.id.quoteSText);
        TextView fQuoteTv = (TextView) findViewById(R.id.quoteFText);
        TextView refTv = (TextView) findViewById(R.id.urlText);
        TextView dateTv = (TextView) findViewById(R.id.dateText);

        last = extras.getExtras().getString("last");

        //Checks is the quote is coming from the user clicking on a quote or clicking on the Last menu option
        if(last.equals("last")) {
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);

            attributeTv.setText(prefs.getString("attribute", null));
            dobText.setText(prefs.getString("dob", null));
            sQuoteTv.setText(prefs.getString("quoteShort", null));
            fQuoteTv.setText(prefs.getString("quoteFull", null));
            refTv.setText(prefs.getString("ref", null));
            dateTv.setText(prefs.getString("date", null));
            blurb = prefs.getString("blurb", null);
            category = prefs.getString("category", null);
        } else {
            attributed = extras.getExtras().getString("Attributed");
            blurb = extras.getExtras().getString("Blurb");
            date = extras.getExtras().getString("Date");
            dateOfBirth = extras.getExtras().getString("DateofBirth");
            quoteFull = extras.getExtras().getString("QuoteFull");
            quoteShort = extras.getExtras().getString("QuoteShort");
            reference = extras.getExtras().getString("Reference");
            category = extras.getExtras().getString("Category");

            Log.i(TAG, "Received attributed - " + attributed);
            Log.i(TAG, "Received blurb - " + blurb);
            Log.i(TAG, "Received date - " + date);
            Log.i(TAG, "Received dateOfBirth - " + dateOfBirth);
            Log.i(TAG, "Received QuoteFull - " + quoteFull);
            Log.i(TAG, "Received quoteShort - " + quoteShort);
            Log.i(TAG, "Received reference - " + reference);

            // Display
            attributeTv.setText(attributed);
            dobText.setText(dateOfBirth);
            sQuoteTv.setText(quoteShort);
            fQuoteTv.setText(quoteFull);
            refTv.setText(reference);
            dateTv.setText(date);
        }

        getCategoryImage(category);

        /**
         * Sets the onclick listener for the attributed, displays the blurb
         */
        final Context context = this;
        final String displayedBlurb = blurb;
        attributeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Blurb").setMessage(displayedBlurb);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // Make links with references
        Linkify.addLinks(refTv, Linkify.WEB_URLS);
    }

    /**
     * Sets up the SharedPreferences
     */
    @Override
    public void onPause() {
        super.onPause();

        TextView attributeTv = (TextView) findViewById(R.id.attrText);
        TextView dobText = (TextView) findViewById(R.id.dobText);
        TextView sQuoteTv = (TextView) findViewById(R.id.quoteSText);
        TextView fQuoteTv = (TextView) findViewById(R.id.quoteFText);
        TextView refTv = (TextView) findViewById(R.id.urlText);
        TextView dateTv = (TextView) findViewById(R.id.dateText);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor e = prefs.edit();

        e.putString("attribute", attributeTv.getText().toString());
        e.putString("dob", dobText.getText().toString());
        e.putString("quoteShort", sQuoteTv.getText().toString());
        e.putString("quoteFull", fQuoteTv.getText().toString());
        e.putString("ref", refTv.getText().toString());
        e.putString("date", dateTv.getText().toString());
        e.putString("blurb", blurb);
        e.putString("category", category);

        e.commit();
    }

    /**
     * Gets the image for the correct category for each quote
     *
     * @param category The category of the quote
     */
    private void getCategoryImage(String category) {
        // Get the imageview
        ImageView cat = (ImageView) findViewById(R.id.category);

        // Location of the images
        String simpsons = "https://firebasestorage.googleapis.com/v0/b/quoteskevinbuihannahly.appspot.com/o/homer50.jpg?alt=media&token=062733fd-4429-49cb-9a42-46d68da20679";
        String celebs = "https://firebasestorage.googleapis.com/v0/b/quoteskevinbuihannahly.appspot.com/o/holly50.jpg?alt=media&token=24e4c5c7-8cd6-40b4-9d1b-832c287d15b6";
        String futurama = "https://firebasestorage.googleapis.com/v0/b/quoteskevinbuihannahly.appspot.com/o/bender50.jpg?alt=media&token=282f8254-b251-4b9a-abb5-4727191347ae";
        String politicians = "https://firebasestorage.googleapis.com/v0/b/quoteskevinbuihannahly.appspot.com/o/politics50.jpg?alt=media&token=d46e9dba-5f28-4850-bab8-275c64bc3cbc";
        String inventors = "https://firebasestorage.googleapis.com/v0/b/quoteskevinbuihannahly.appspot.com/o/inventor50.jpg?alt=media&token=ce62ed0f-bf83-4604-8b45-f07c69ff1fd8";

        // Selects the right image according to the quote object
        if (category.equals("Celebrities")) {
            Glide.with(getApplicationContext()).load(celebs).into(cat);
        } else if (category.equals("Futurama")) {
            Glide.with(getApplicationContext()).load(futurama).into(cat);
        } else if (category.equals("The Simpsons")) {
            Glide.with(getApplicationContext()).load(simpsons).into(cat);
        } else if (category.equals("Politicians")) {
            Glide.with(getApplicationContext()).load(politicians).into(cat);
        } else {
            Glide.with(getApplicationContext()).load(inventors).into(cat);
        }
    }
}
