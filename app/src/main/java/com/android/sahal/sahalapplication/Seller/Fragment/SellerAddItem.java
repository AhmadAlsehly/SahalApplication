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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static android.app.Activity.RESULT_OK;

public class SellerAddItem extends Fragment {
    public ArrayList<Item> seller = new ArrayList<>();


    private List<String> mPhotos = new ArrayList<>();

    private FirebaseAuth mAuth;

    String itemPackage;
    TextView btnImage1, btnImage2, btnImage3, btnImage4;
    EditText itemName, itemDescr, itemPrice;
    Spinner itemCompan, itemModel, itemYear, itemCatgory;
    ImageView image1, image2, image3, image4;
    Uri uri1, uri2, uri3, uri4;
    FirebaseStorage storage;
    DatabaseReference mDataRef;
    StorageReference mStorageRef;
    int tryies = 0 ;
int clearFirst = 0;


    Button btnDone;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
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

                itemPackage = "Items" + "/" + UUID.randomUUID().toString();

if(clearFirst==0) {
    mPhotos.clear();
    clearFirst++;
}
if (clearFirst==1) {
    uploadImage();
    uploadImage2();
    uploadImage3();
    uploadImage4();
    Log.d("looog",mPhotos.toString());
}


                if (mPhotos!=null) {

                        ModuleItem moduleItem = new ModuleItem(itemName.getText().toString(),
                                itemDescr.getText().toString(),
                                itemCompan.getSelectedItem().toString(),
                                itemModel.getSelectedItem().toString(),
                                itemCatgory.getSelectedItem().toString(),
                                itemYear.getSelectedItem().toString(),
                                Double.parseDouble(itemPrice.getText().toString()), mPhotos);

                        if (!itemName.getText().toString().isEmpty()
                                || !itemPrice.getText().toString().isEmpty()
                                || !itemDescr.getText().toString().isEmpty()) {
                            mDataRef.child("items").child("item" + UUID.randomUUID()).setValue(moduleItem);

                            Toast.makeText(getContext(), "تم اضافة القطعة ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "complete all fields pleas", Toast.LENGTH_SHORT).show();


                }


                };



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

    private void uploadImage() {

        if (uri1 != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading . . . ");
            progressDialog.show();

            StorageReference ref = storage.getReference().child(itemPackage + "1");

            ref.putFile(uri1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            mPhotos.add(taskSnapshot.getStorage().getDownloadUrl().toString());
                            Log.d("looog",taskSnapshot.getUploadSessionUri().toString());


                            progressDialog.dismiss();
                            tryies++;


                            Toast.makeText(getContext(), "Upload", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Faield" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double prog = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Upload" + (int) prog + "%");


                }
            });


        }

    }

    private void uploadImage2() {
        if (uri2 != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading . . . ");
            progressDialog.show();

            StorageReference ref = storage.getReference().child(itemPackage + "2");

            ref.putFile(uri2)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            mPhotos.add(taskSnapshot.getStorage().getDownloadUrl().toString());
                            progressDialog.dismiss();
                            //tryies++;


                            Toast.makeText(getContext(), "Upload", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Faield" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double prog = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Upload" + (int) prog + "%");


                }
            });


        }
    }

    private void uploadImage3() {
        if (uri3 != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading . . . ");
            progressDialog.show();

            StorageReference ref = storage.getReference().child(itemPackage + "3");

            ref.putFile(uri3)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mPhotos.add(taskSnapshot.getStorage().getDownloadUrl().toString());
                          //  tryies++;

                            progressDialog.dismiss();

                            Toast.makeText(getContext(), "Upload", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Faield" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double prog = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Upload" + (int) prog + "%");


                }
            });


        }
    }

    private void uploadImage4() {
        if (uri4 != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading . . . ");
            progressDialog.show();

            StorageReference ref = storage.getReference().child(itemPackage + "4");

            ref.putFile(uri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mPhotos.add(taskSnapshot.getStorage().getDownloadUrl().toString());

                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Upload", Toast.LENGTH_SHORT).show();
                   // tryies++;

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Faield" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double prog = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());

                    progressDialog.setMessage("Upload" + (int) prog + "%");

                }
            });


        }
    }

    private void imageUploader() {

    }


}
