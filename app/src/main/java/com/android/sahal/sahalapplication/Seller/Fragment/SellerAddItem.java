package com.android.sahal.sahalapplication.Seller.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private List<String> linkPhotos = new ArrayList<>();

    private List<Uri> uri = new ArrayList<>();
    List<String> carsCompany = new ArrayList<>();
    List<String> carsName = new ArrayList<>();
    String selectedItem;


    Uri imagePath;
    String itemPackage;
    TextView btnImage1, btnImage2, btnImage3, btnImage4;
    EditText itemName, itemDescr, itemPrice;
    Spinner itemCompan, itemCarName, itemYear, itemCatgory, itemType;
    ImageView image1, image2, image3, image4;
    private static int PICK_IMAGE = 100;
    private static int PICK_IMAGE_2 = 98;
    private static int PICK_IMAGE_3 = 96;
    private static int PICK_IMAGE_4 = 94;

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
    int count = 0;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        init(view);
        companySpinner();

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
        itemType = view.findViewById(R.id.itemType_input);
        itemCarName = view.findViewById(R.id.carName_input);


        firebaseAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();

        mDataRef = FirebaseDatabase.getInstance().getReference();

        firebaseStorage = FirebaseStorage.getInstance();


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference companysReference = firebaseDatabase.getInstance().getReference().child("carsCompany");
        DatabaseReference carsReferenc = firebaseDatabase.getInstance().getReference().child("cars");


        companysReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        carsCompany.add(ds.getValue().toString());
                        Log.d("comp", carsCompany.toString());


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final StorageReference storageReference = firebaseStorage.getReference();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                for (int i = 0; i < mPhotos.size(); i++) {
                    Log.d("photoname", mPhotos.get(i));
                    StorageReference imageReference = storageReference.child("items").child(mPhotos.get(i));
                    UploadTask uploadTask = imageReference.putFile((uri.get(i)));
                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

                    StorageReference storageReference = firebaseStorage.getReference();

                    storageReference.child("items").child(mPhotos.get(i)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            linkPhotos.add(uri.toString());
                        }
                    });

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(v.getContext(), "Upload failed!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            //linkPhotos.add(task.getResult().getUploadSessionUri().toString());
                            task.getResult().getStorage().getDownloadUrl();
                            Toast.makeText(v.getContext(), "Upload successful!", Toast.LENGTH_SHORT).show();

                            count++;

                            if (count == mPhotos.size()) {
                                uploadItem();
                            }
                        }
                    });


                }
            }

        });


//        init(view);


    }

    void uploadItem() {
        if (!itemName.getText().toString().isEmpty()
                || !itemPrice.getText().toString().isEmpty()
                || !itemDescr.getText().toString().isEmpty()) {

            String itemId = "item" + UUID.randomUUID();
            ModuleItem moduleItem = new ModuleItem(itemName.getText().toString(),
                    itemCarName.getSelectedItem().toString(),
                    itemDescr.getText().toString(),
                    itemCompan.getSelectedItem().toString(),
                    itemType.getSelectedItem().toString(),
                    itemCatgory.getSelectedItem().toString(),
                    itemYear.getSelectedItem().toString(),
                    itemPrice.getText().toString(), linkPhotos, mAuth.getUid(), "0", buyerId, itemId);


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
        if (requestCode == PICK_IMAGE_2 && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            String path = imagePath.getPath();
            filename = path.substring(path.lastIndexOf("/") + 2);
            mPhotos.add(filename);
            uri.add(imagePath);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), imagePath);
                image2.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (requestCode == PICK_IMAGE_3 && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            String path = imagePath.getPath();
            filename = path.substring(path.lastIndexOf("/") + 2);
            mPhotos.add(filename);
            uri.add(imagePath);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), imagePath);
                image3.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == PICK_IMAGE_4 && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            String path = imagePath.getPath();
            filename = path.substring(path.lastIndexOf("/") + 2);
            mPhotos.add(filename);
            uri.add(imagePath);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), imagePath);
                image4.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void companySpinner() {
        itemCompan = getView().findViewById(R.id.itemComp_input);
        carsCompany = new ArrayList<>();
        carsCompany.add(0, "اختر شركة");
        ArrayAdapter<String> companysAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, carsCompany);
        companysAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemCompan.setAdapter(companysAdapter);
        // Initializing an ArrayAdapter


        itemCompan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = itemCompan.getSelectedItem().toString();
                carsSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void carsSpinner() {
        if (selectedItem != null) {
            carsName.clear();
                                    carsName.add(0, "اختر سيارة");

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference carsReferenc = firebaseDatabase.getInstance().getReference().child("cars").child(selectedItem);
            carsReferenc.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            carsName.add(ds.getValue().toString());
                        }
                    }
                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            itemCarName = this.getActivity().findViewById(R.id.carName_input);
            ArrayAdapter<String> companysAdapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, carsName);
            companysAdapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            itemCarName.setAdapter(companysAdapter2);
        }


//        ArrayAdapter<String> carsAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, carsName);
//        carsAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        itemCarName.setAdapter(carsAdapter);

    }

    void init(View view) {


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

        itemType = view.findViewById(R.id.itemType_input);

        String[] type =
                {"", "اضاءة", "ابواب", "مقاعد", "مضخة "};
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, type);
        adapterType.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemType.setAdapter(adapterType);


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
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        btnImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();//(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_2);
            }
        });
        btnImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();//(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_3);
            }
        });

        btnImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();//(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_4);
            }
        });

    }
}
