package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mOriginTextView;
    private TextView mAlsoKnownAsTextView;
    private TextView mIngredientsTextView;
    private TextView mDescriptionTextView;
    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mOriginTextView = findViewById(R.id.origin_tv);
        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .placeholder(R.drawable.noimage)
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        String origin = mSandwich.getPlaceOfOrigin();
        if(origin == null || origin.isEmpty())
            mOriginTextView.setText("---");
        else
            mOriginTextView.setText(mSandwich.getPlaceOfOrigin());

        String description = mSandwich.getDescription();
        if(description == null || description.isEmpty())
            mDescriptionTextView.setText("---");
        else
            mDescriptionTextView.setText(mSandwich.getDescription());

        List<String> alsoKnownAs = mSandwich.getAlsoKnownAs();
        if(alsoKnownAs == null || alsoKnownAs.isEmpty())
            mAlsoKnownAsTextView.setText("---");
        else {
            for(int i = 0; i < alsoKnownAs.size(); i++) {
                if(i == alsoKnownAs.size() - 1)
                    mAlsoKnownAsTextView.append(alsoKnownAs.get(i));
                else
                    mAlsoKnownAsTextView.append(alsoKnownAs.get(i) + ", ");
            }
        }

        List<String> ingredients = mSandwich.getIngredients();
        if(ingredients == null || ingredients.isEmpty())
            mIngredientsTextView.setText("---");
        else {
            for(int j = 0; j < ingredients.size(); j++) {
                if(j == ingredients.size() - 1)
                    mIngredientsTextView.append(ingredients.get(j));
                else
                    mIngredientsTextView.append(ingredients.get(j) + ", ");
            }
        }
    }
}
