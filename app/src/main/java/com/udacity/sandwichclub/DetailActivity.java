package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
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
    private TextView also_known;
    private ImageView image;
    private TextView originTv;
    private TextView descriptionTv;
    private TextView originLabel;
    private TextView Also_knownLabel;
    private TextView ingredientsTv;
    private TextView ingredientsLabel;
    private TextView descriptionLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        also_known = findViewById(R.id.also_known_tv);
        originTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        Also_knownLabel = findViewById(R.id.also_known_label);
        originLabel = findViewById(R.id.place_of_origin_label);
        image = findViewById(R.id.image_iv);
        ingredientsLabel = findViewById(R.id.ingredients_label);
        descriptionLabel = findViewById(R.id.description_label);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        setTitle(sandwich.getMainName());
        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (!sandwich.getImage().equals("null") && !sandwich.getImage().isEmpty() && sandwich.getImage() != null) {
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(image);
        } else {
            image.setVisibility(View.GONE);
        }

        //check for also known as textview
        int akaSize = sandwich.getAlsoKnownAs().size();
        if (akaSize != 0) {
            for (int i = 0; i < akaSize; i++) {
                also_known.append(sandwich.getAlsoKnownAs().get(i));
                if (i != akaSize - 1) {
                    also_known.append("\n");
                }
            }
        } else {
            also_known.setVisibility(View.GONE);
            Also_knownLabel.setVisibility(View.GONE);
        }
        //check for ingredients
        int ingredientsSize = sandwich.getIngredients().size();
        if (ingredientsSize != 0) {
            for (int i = 0; i < ingredientsSize; i++) {
                ingredientsTv.append(sandwich.getIngredients().get(i));
                if (i != ingredientsSize - 1) {
                    ingredientsTv.append(", ");
                }
            }
        } else {
            ingredientsLabel.setVisibility(View.GONE);
            ingredientsTv.setVisibility(View.GONE);
        }
        //check for origin place
        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().equals("null") && !sandwich.getPlaceOfOrigin().isEmpty()) {
            originTv.setText(sandwich.getPlaceOfOrigin());
        } else {
            originLabel.setVisibility(View.GONE);
            originLabel.setVisibility(View.GONE);
        }
        //check for description
        if (!sandwich.getDescription().equals("null") && !sandwich.getDescription().isEmpty() && sandwich.getDescription() != null) {
            descriptionTv.setText(sandwich.getDescription());
        } else {
            descriptionLabel.setVisibility(View.GONE);
            descriptionTv.setVisibility(View.GONE);
        }

    }
}
