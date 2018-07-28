package com.android.sahal.sahalapplication.Seller.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.android.sahal.sahalapplication.Model.ModuleItem;
import com.android.sahal.sahalapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SellerAddItem extends Fragment {
    public ArrayList<Item> seller = new ArrayList<>();


    private List<String> mPhotos = new ArrayList<>();
    private List<Uri> uri = new ArrayList<>();

    Uri imagePath;
    String itemPackage;
    TextView btnImage1, btnImage2, btnImage3, btnImage4;
    EditText itemName, itemDescr, itemPrice;
    Spinner itemCompan, itemModel, itemYear, itemCatgory;
    ImageView image1, image2, image3, image4;
    private static int PICK_IMAGE = 100;
    private FirebaseStorage firebaseStorage;
    private FirebaseAuth firebaseAuth;
    Button btnDone;
    public static final int RESULT_OK = -1;
    String filename;
    String sellerId;
    String buyerId = "none";
    private FirebaseAuth mAuth;
    FirebaseStorage storage;
    DatabaseReference mDataRef;
    StorageReference mStorageRef;
    int count=0;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        btnDone = view.findViewById(R.id.butnDone);
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        image4 = view.findViewById(R.id.image4);
        itemName = view.findViewById(R.id.itemName_input);
        itemDescr = view.findViewById(R.id.itemDescr_input);
        itemPrice = view.findViewById(R.id.itemPrice_input);
        firebaseAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReference();
        mDataRef = FirebaseDatabase.getInstance().getReference();

        itemCompan = (Spinner) view.findViewById(R.id.itemComp_input);
        firebaseStorage = FirebaseStorage.getInstance();

        final StorageReference storageReference = firebaseStorage.getReference();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                for (int i = 0; i < mPhotos.size(); i++){
                    StorageReference imageReference = storageReference.child("items").child(mPhotos.get(i));
                    UploadTask uploadTask = imageReference.putFile((uri.get(i)));

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(v.getContext(), "Upload failed!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Toast.makeText(v.getContext(), "Upload successful!", Toast.LENGTH_SHORT).show();
                            count++;

                            if (count==mPhotos.size()){
                                uploadItem();
                            }
                        }
                    });
            }
            }

        });


        init(view);


    }

    void uploadItem (){
        if (!itemName.getText().toString().isEmpty()
                || !itemPrice.getText().toString().isEmpty()
                || !itemDescr.getText().toString().isEmpty()) {

        String itemId = "item" + UUID.randomUUID();
        ModuleItem moduleItem = new ModuleItem(itemName.getText().toString(),
                itemDescr.getText().toString(),
                itemCompan.getSelectedItem().toString(),
                itemModel.getSelectedItem().toString(),
                itemCatgory.getSelectedItem().toString(),
                itemYear.getSelectedItem().toString(),
                itemPrice.getText().toString(), mPhotos, mAuth.getUid(), "0", buyerId, itemId);


            mDataRef.child("items").child(itemId).setValue(moduleItem);

            Toast.makeText(getContext(), "تم اضافة القطعة ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "complete all fields pleas", Toast.LENGTH_SHORT).show();


        }


    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            String path = imagePath.getPath();
            filename = path.substring(path.lastIndexOf("/") + 1);
            mPhotos.add(filename);
            uri.add(imagePath);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), imagePath);
                image1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            String path = imagePath.getPath();
            filename = path.substring(path.lastIndexOf("/") + 1);
            mPhotos.add(filename);
            uri.add(imagePath);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), imagePath);
                image2.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void init(View view) {
        String[] comapanys =
                {"الشركة", "TOYOTA", "HYUANDAY", "HONDA"};

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this.getActivity(), R.layout.spinner_item, comapanys) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
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
                if (position > 0) {
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
                    String[] models =
                            {"", "CAMRY", "COROLLA", "AURION"};
                    ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, models);
                    adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    itemModel.setAdapter(adapterModel);
                } else if (itemSelectedSring.equals("HYUANDAY")) {
                    String[] models =
                            {"", "SONATA", "ELNTRA", "ACCENT"};
                    ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, models);
                    adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    itemModel.setAdapter(adapterModel);
                } else if (itemSelectedSring.equals("HONDA")) {
                    String[] models =
                            {"", "CIVIC", "ACORD", "CARNAVAL"};
                    ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, models);
                    adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    itemModel.setAdapter(adapterModel);
                } else {
                    String[] models = {""};

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
        String[] models =
                {"", "CAMRY", "COROLLA", "AURION"};
        ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, models);
        adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemModel.setAdapter(adapterModel);


        itemYear = view.findViewById(R.id.itemYear_input);
        String[] years =
                {"", "2011", "2012", "2013"};
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, years);
        adapterYear.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemYear.setAdapter(adapterYear);


        itemCatgory = view.findViewById(R.id.itemCatg_input);
        String[] catgs =
                {"", "هيكل", "كهرباء", "محركات", "قطع خارجية"};
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
                Intent intent = new Intent();//(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 100);
            }
        });

        btnImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();//(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 100);
            }
        });
        btnImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();//(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 100);
            }
        });

        btnImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();//(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 100);
            }
        });

    }
}
