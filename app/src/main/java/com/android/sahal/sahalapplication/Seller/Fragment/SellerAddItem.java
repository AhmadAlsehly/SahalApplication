package com.android.sahal.sahalapplication.Seller.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static android.app.Activity.RESULT_OK;

public class SellerAddItem extends Fragment {
    public ArrayList<Item> seller = new ArrayList<>();


    private List<String> mPhotos = new ArrayList<>();

    private FirebaseAuth mAuth;
    int i = 0;

    String itemPackage;
    TextView btnImage1, btnImage2, btnImage3, btnImage4;
    EditText itemName, itemDescr, itemPrice;
    Spinner itemCompan, itemModel, itemYear, itemCatgory;
    ImageView image1, image2, image3, image4;
    Uri uri1, uri2, uri3, uri4;
    FirebaseStorage storage;
    DatabaseReference mDataRef;
    StorageReference mStorageRef;
    int tryies = 0;
    int clearFirst = 0;
    String sellerId;


    Button btnDone;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

         int i = 0;

        super.onViewCreated(view, savedInstanceState);

        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReference();
        mDataRef = FirebaseDatabase.getInstance().getReference();


        btnDone = view.findViewById(R.id.butnDone);
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);

        image3 = view.findViewById(R.id.image3);

        image4 = view.findViewById(R.id.image4);


        itemName = view.findViewById(R.id.itemName_input);
        itemDescr = view.findViewById(R.id.itemDescr_input);
        itemPrice = view.findViewById(R.id.itemPrice_input);

        itemCompan = (Spinner) view.findViewById(R.id.itemComp_input);


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
mPhotos.clear();

                itemPackage = "Items" + "/" + UUID.randomUUID().toString();

//
//                uploadImage(uri1);
//                uploadImage(uri2);
//                uploadImage(uri3);
//                uploadImage(uri4);
                upload(uri1);
                upload(uri2);
                upload(uri3);
                upload(uri4);



                Log.d("looog", mPhotos.toString());


            }

        });


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
                            {"CAMRY", "COROLLA", "AURION"};
                    ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, models);
                    adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    itemModel.setAdapter(adapterModel);
                } else if (itemSelectedSring.equals("HYUANDAY")) {
                    String[] models =
                            {"SONATA", "ELNTRA", "ACCENT"};
                    ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, models);
                    adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    itemModel.setAdapter(adapterModel);
                } else if (itemSelectedSring.equals("HONDA")) {
                    String[] models =
                            {"CIVIC", "ACORD", "CARNAVAL"};
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
                {"CAMRY", "COROLLA", "AURION"};
        ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, models);
        adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemModel.setAdapter(adapterModel);


        itemYear = view.findViewById(R.id.itemYear_input);
        String[] years =
                {"2011", "2012", "2013"};
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, years);
        adapterYear.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemYear.setAdapter(adapterYear);


        itemCatgory = view.findViewById(R.id.itemCatg_input);
        String[] catgs =
                {"هيكل", "كهرباء", "محرك"};
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
                startActivityForResult(intent, 98);
            }
        });
        btnImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();//(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 96);
            }
        });

        btnImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();//(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 94);
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri1 = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri1);
                image1.setImageBitmap(bitmap);
                String u = uri1.getPath();

                Log.d("tesst", u);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (requestCode == 98 && resultCode == RESULT_OK) {
            uri2 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri2);
                image2.setImageBitmap(bitmap);
                String u = uri2.getPath();

                Log.d("tesst", u);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 96 && resultCode == RESULT_OK) {
            uri3 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri3);
                image3.setImageBitmap(bitmap);
                String u = uri3.getPath();

                Log.d("tesst", u);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 94 && resultCode == RESULT_OK) {
            uri4 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri4);
                image4.setImageBitmap(bitmap);
                String u = uri4.getPath();

                Log.d("tesst", u);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }


//    private void uploadImage(Uri uri) {
//        final String imageName = UUID.randomUUID().toString() + ".jpg";
//
//
//        mAuth = FirebaseAuth.getInstance();
//        final FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if (uri != null) {
//            final ProgressDialog progressDialog = new ProgressDialog(getContext());
//            progressDialog.setTitle("Uploading . . . ");
//            progressDialog.show();
//StorageReference storageReference = storage.getReference();
//            StorageReference ref = storage.getReference().child(imageName + "1");
//
//            ref.putFile(uri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            mPhotos.add(taskSnapshot.getStorage().getDownloadUrl().toString());
//                            download(imageName);
//                             progressDialog.dismiss();
//                            Toast.makeText(getContext(), "Upload", Toast.LENGTH_SHORT).show();
//                            i++;
//                            if (i == 4) {
//                                ModuleItem moduleItem = new ModuleItem(itemName.getText().toString(),
//                                        itemDescr.getText().toString(),
//                                        itemCompan.getSelectedItem().toString(),
//                                        itemModel.getSelectedItem().toString(),
//                                        itemCatgory.getSelectedItem().toString(),
//                                        itemYear.getSelectedItem().toString(),
//                                        Double.parseDouble(itemPrice.getText().toString()), mPhotos, currentUser.getUid(), 0);
//
//                                if (!itemName.getText().toString().isEmpty()
//                                        || !itemPrice.getText().toString().isEmpty()
//                                        || !itemDescr.getText().toString().isEmpty()) {
//                                    mDataRef.child("items").child("item" + UUID.randomUUID()).setValue(moduleItem);
//
//                                    Toast.makeText(getContext(), "تم اضافة القطعة ", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(getContext(), "complete all fields pleas", Toast.LENGTH_SHORT).show();
//
//
//                                }
//
//                            }
//
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    progressDialog.dismiss();
//                    Toast.makeText(getContext(), "Faield" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    double prog = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                    progressDialog.setMessage("Upload" + (int) prog + "%");
//
//
//                }
//            });
//
//
//        }
//
//    }

    private void upload(Uri uri) {

        final String imageName = UUID.randomUUID().toString() + ".jpg";
        UploadTask uploadTask = mStorageRef.child(imageName).putFile(uri);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    download(imageName);

                    Toast.makeText(getContext(), "Uplaod Succeed", Toast.LENGTH_SHORT).show();

                } else {
//                        Log.d("3llomi", "upload Failed " + task.getException().getLocalizedMessage());
//                        Toast.makeText(getContext(), "Uplaod Failed :( " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        });

        //if you want to cancel
        //uploadTask.cancel();
    }

    private void download(String imageName) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        progressDialog.setMessage("Downloading...");
        progressDialog.show();
        final File file = new File(getContext().getFilesDir(), imageName);
        FileDownloadTask fileDownloadTask = mStorageRef.child(imageName).getFile(file);
        fileDownloadTask.addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    mPhotos.add(file.getPath());
                    Toast.makeText(getContext(), "file Downloaded to " + file.getPath(), Toast.LENGTH_SHORT).show();
                    Log.d("3llomi", "File Downloaded to " + file.getPath());
                    i++;
                    if (i == 4) {
                        ModuleItem moduleItem = new ModuleItem(itemName.getText().toString(),
                                itemDescr.getText().toString(),
                                itemCompan.getSelectedItem().toString(),
                                itemModel.getSelectedItem().toString(),
                                itemCatgory.getSelectedItem().toString(),
                                itemYear.getSelectedItem().toString(),
                                Double.parseDouble(itemPrice.getText().toString()), mPhotos, currentUser.getUid(), 0);

                        if (!itemName.getText().toString().isEmpty()
                                || !itemPrice.getText().toString().isEmpty()
                                || !itemDescr.getText().toString().isEmpty()) {
                            mDataRef.child("items").child("item" + UUID.randomUUID()).setValue(moduleItem);

                            Toast.makeText(getContext(), "تم اضافة القطعة ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "complete all fields pleas", Toast.LENGTH_SHORT).show();


                        }

                        i=0;

                    }

                } else {
                    Toast.makeText(getContext(), "download Failed " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("3llomi", "Download Failed " + task.getException().getLocalizedMessage());
                }
            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                double progressDouble = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressDialog.setMessage(progressDouble + "");

            }
        });

        //if you want to cancel
        //fileDownloadTask.cancel();
    }


}
