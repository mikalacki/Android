package com.example.mmreviews;


import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;


import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    EditText search;
    Button btnCard;

    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        search = view.findViewById(R.id.search);
        btnCard = view.findViewById(R.id.btnCard);


        Places.initialize(getContext(), "GOOGLE_API_KEY");

        search.setFocusable(false);
        search.setOnClickListener(v -> {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.NAME);

            // ovaj forResult je deprecated a i koristis ga iz fragmenta
            // logiku bi trebalo da prebaacis u activity pa da activity za koji je vezan
            // ovaj fragment radi posao. Evo jednog primera:
            // https://github.com/jocelin09/FragmentCommunication/tree/master/app/src/main/java/com/jocelinthomas/fragmentcommunication
            // ima ih more na internetu svakako pa proguglaaj
            // ono sto je sad glavna stvar i sto google gura jeste da koristis ViewModel klasu
            // koja radi logiku i koristis je u okviru fragmenta i activitya. Pogledaj i to malo
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(getActivity());
            startActivityForResult(intent, 100);

        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            assert data != null;
            Place place = Autocomplete.getPlaceFromIntent(data);

            search.setText(place.getAddress());
            btnCard.setText(place.getName());

            // ove stvari je lepse kad izbacis u poseban metod
            // pa mu prosledis parametar koji ce da metne u intent :)
            btnCard.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), PlaceReviewsActivity.class);
                intent.putExtra("placeName", place.getName());
                startActivity(intent);
            });
            btnCard.setVisibility(View.VISIBLE);

        } else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getContext(),status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
