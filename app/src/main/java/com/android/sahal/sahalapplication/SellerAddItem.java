package com.android.sahal.sahalapplication;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sahal.sahalapplication.Model.Item;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static android.app.Activity.RESULT_OK;

public class SellerAddItem extends Fragment {
   public ArrayList<Item> seller= new ArrayList<>();


    TextView btnImage1 , btnImage2 , btnImage3 , btnImage4 ;
EditText itemName , itemDescr , itemPrice ;
    Spinner itemCompan , itemModel , itemYear , itemCatgory ;
    ImageView image1 , image2 , image3 , image4;

    Button btnDone ;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

btnDone=view.findViewById(R.id.butnDone);

        itemName = view.findViewById(R.id.itemName_input);
        itemDescr = view.findViewById(R.id.itemDescr_input);
        itemPrice = view.findViewById(R.id.itemPrice_input);

        itemCompan = (Spinner) view.findViewById(R.id.itemComp_input);

        String [] comapanys =
                {"الشركة","TOYOTA","HYUANDAY","HONDA"};

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this.getActivity(),R.layout.spinner_item,comapanys){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        itemCompan.setAdapter(spinnerArrayAdapter);


        itemCompan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                String userActivity = selectedItemText;
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }











        // Initializing an ArrayAdapter


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Spinners



        itemModel = view.findViewById(R.id.itemModel_input);

                // when chose car barand show only brand cars

        itemCompan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelectedSring = itemCompan.getSelectedItem().toString().trim();

                if (itemSelectedSring.equals("TOYOTA")) {
                    String [] models =
                            {"CAMRY","COROLLA","AURION"};
                    ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, models);
                    adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    itemModel.setAdapter(adapterModel);
                }
                else if (itemSelectedSring.equals("HYUANDAY")) {
                    String [] models =
                            {"SONATA","ELNTRA","ACCENT"};
                    ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, models);
                    adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    itemModel.setAdapter(adapterModel);
                }

                else if (itemSelectedSring.equals("HONDA")) {
                    String [] models =
                            {"CIVIC","ACORD","CARNAVAL"};
                    ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, models);
                    adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    itemModel.setAdapter(adapterModel);
                }

                else {
                    String [] models = {""} ;

                    ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, models);
                    adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    itemModel.setAdapter(adapterModel);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        itemModel = view.findViewById(R.id.itemModel_input);
        String [] models =
                {"CAMRY","COROLLA","AURION"};
        ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, models);
        adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemModel.setAdapter(adapterModel);


        itemYear = view.findViewById(R.id.itemYear_input);
        String [] years =
                {"2011","2012","2013"};
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, years);
        adapterYear.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemYear.setAdapter(adapterYear);


        itemCatgory = view.findViewById(R.id.itemCatg_input);
        String [] catgs =
                {"هيكل","كهرباء","محرك"};
        ArrayAdapter<String> adapterCatg = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, catgs);
        adapterCatg.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemCatgory.setAdapter(adapterCatg);










        itemModel = view.findViewById(R.id.itemModel_input);
        itemYear = view.findViewById(R.id.itemYear_input);
        itemCatgory = view.findViewById(R.id.itemCatg_input);




        // Add Image
        btnImage1 = view.findViewById(R.id.btnImage1);
        btnImage2 = view.findViewById(R.id.btnImage2);
        btnImage3 = view.findViewById(R.id.btnImage3);
        btnImage4 = view.findViewById(R.id.btnImage4);


        btnImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,100);
            }
        });

        btnImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,98);
            }
        });
        btnImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,96);
            }
        });

        btnImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,94);
            }
        });



 btnDone.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if (!itemName.getText().toString().isEmpty()
                || !itemPrice.getText().toString().isEmpty()
                || !itemDescr.getText().toString().isEmpty())  {

            seller.add( new Item(itemName.getText().toString(),
                    itemDescr.getText().toString(),
                    Double.parseDouble(itemPrice.getText().toString()),
                    itemCompan.getSelectedItem().toString(),
                    itemModel.getSelectedItem().toString(),
                    itemYear.getSelectedItem().toString(),
                    itemCatgory.getSelectedItem().toString()));
            Toast.makeText(getContext(), "jkdfhiud",Toast.LENGTH_SHORT).show();
        } else  {
            Toast.makeText(getContext(), "complete all fields pleas",Toast.LENGTH_SHORT).show();

        }

    }
});
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode==RESULT_OK) {
            Uri uri = data.getData();
            image1.setImageResource(uri.getPort());


        }
        if (requestCode == 98 && resultCode==RESULT_OK) {
            Uri uri2 = data.getData();
            image2.setImageResource(uri2.getPort());
        }
        if (requestCode == 96 && resultCode==RESULT_OK) {
            Uri uri3 = data.getData();
            image3.setImageResource(uri3.getPort());
        }
        if (requestCode == 94 && resultCode==RESULT_OK) {
            Uri uri4 = data.getData();
image4.setImageResource(uri4.getPort());        }
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }




}
