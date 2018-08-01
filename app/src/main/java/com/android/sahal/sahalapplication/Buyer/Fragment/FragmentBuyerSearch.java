package com.android.sahal.sahalapplication.Buyer.Fragment;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sahal.sahalapplication.Adapters.BuyerSearchAdapter;
import com.android.sahal.sahalapplication.Model.ModuleItem;
import com.android.sahal.sahalapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentBuyerSearch.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBuyerSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBuyerSearch extends Fragment implements BuyerSearchAdapter.onItemClickListener, SearchView.OnQueryTextListener {

    Spinner itemCompan, itemModel, itemYear, itemCatgory;
    Button count;
    //int i=0,i2=0;

    boolean filter = false,factory=false,category=false,model=false,year=false;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText serchText;
    List<ModuleItem> itemMoudelList;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    final DatabaseReference databaseReference = firebaseDatabase.getInstance().getReference().child("items");
    ModuleItem moduleItem;
    private OnFragmentInteractionListener mListener;
    private List<ModuleItem> itemList;
    Query query;

    ModuleItem item;
    RecyclerView recyclerView;

    private BuyerSearchAdapter buyerSearchAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


//    public static FragmentBuyerSearch newInstance() {
//        return new FragmentBuyerSearch();
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buyer_search, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemCompan = view.findViewById(R.id.itemComp_input);
        itemCatgory = view.findViewById(R.id.itemCatg_input);
        itemModel = view.findViewById(R.id.itemType_input);
        itemYear = view.findViewById(R.id.itemYear_input);
        serchText = view.findViewById(R.id.search_edit_text);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //Recycler
        recyclerView = view.findViewById(R.id.search_recyclerView);
        itemList = new ArrayList<>();
        buyerSearchAdapter = new BuyerSearchAdapter(this.getContext(), itemList);


        LinearLayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(buyerSearchAdapter);

        prepareAlbums();

        itemCatgory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
                

                String workRequestType = arg0.getItemAtPosition(pos)
                        .toString();
               // final int[] i = {0};

                if (pos != 0) {

                    if (category == false){

                        category=true;
                    }

                    query = databaseReference.orderByChild("category")
                            .equalTo(workRequestType);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                itemList.clear();

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    if (ds.child("status").getValue().equals("0")) {


                                        //Year
                                        //----------------------------------------------------------
                                        if(year==true){
                                            if((ds.child("year").getValue().equals(itemYear.getSelectedItem().toString()))&& ! itemYear.getSelectedItem().equals(0)){

                                        itemList.add(ds.getValue(ModuleItem.class));
                                        //i++;



                                    }}else{
                                        //----------------------------------------------------------


                                                itemList.add(ds.getValue(ModuleItem.class));


                                            }

                                        }
                                    }
                                    buyerSearchAdapter.notifyDataSetChanged();
                                }
                            else {
                                itemList.clear();
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                }else{
                    itemList.clear();
                   prepareAlbums();
                    buyerSearchAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                prepareAlbums();

            }
        });
//
//
//        itemModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int pos, long id) {
//
//                String workRequestType = arg0.getItemAtPosition(pos)
//                        .toString();
//
//                if (pos != 0) {
//                    if (filter == false){
//
//                        itemList.clear();
//                        filter=true;
//                    }
//                    query = databaseReference.orderByChild("type")
//                            .equalTo(workRequestType);
//
//                    query.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                itemList.clear();
//
//                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    if (ds.child("status").getValue().equals("0")) {
//
//                                        itemList.add(ds.getValue(ModuleItem.class));
//
//
//
//                                    }
//                                    buyerSearchAdapter.notifyDataSetChanged();
//                                }
//                            }
//                        }
//
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//
//
//
//                }
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                prepareAlbums();
//
//            }
//        });
//
        itemYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {

                final String workRequestType = arg0.getItemAtPosition(pos)
                        .toString();
                //final int[] i = {0};

                if (pos != 0) {
                    if (year == false){

                       // itemList.clear();
                        year=true;
                       // Log.d(workRequestType, "onItemSelected: "+year);

                    }
                    query = databaseReference.orderByChild("year")
                            .equalTo(workRequestType);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                itemList.clear();

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    if (ds.child("status").getValue().equals("0")) {

                                        //Category
//                                        //---------------------------------------------------------
                                        if(category==true){
                                                if((ds.child("category").getValue().equals(itemCatgory.getSelectedItem().toString()))&& !itemCatgory.getSelectedItem().equals(0)){

                                                    itemList.add(ds.getValue(ModuleItem.class));




                                                }}else{
                                            //------------------------------------------------------


                                                itemList.add(ds.getValue(ModuleItem.class));


                                            }

                                        }

                                    }
                                  //  year=true;

                                    buyerSearchAdapter.notifyDataSetChanged();
                                }
                            else{

                                itemList.clear();

                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                }else{
                   // itemCompan.getSelectedItem();
                    itemList.clear();
                    query = databaseReference.orderByChild("category")
                            .equalTo(itemCatgory.getSelectedItem().toString());

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                itemList.clear();

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    if (ds.child("status").getValue().equals("0")) {

                                        //Category
//                                        //---------------------------------------------------------

                                        itemList.add(ds.getValue(ModuleItem.class));


                                    }

                                }
                                //  year=true;

                                buyerSearchAdapter.notifyDataSetChanged();
                            }
                            else{

                                itemList.clear();

                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                    buyerSearchAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                prepareAlbums();

            }
        });


//        itemCompan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int pos, long id) {
//
//                String workRequestType = arg0.getItemAtPosition(pos)
//                        .toString();
//
//                if (pos != 0) {
//
//                    if (filter == false){
//
//                        itemList.clear();
//                        filter=true;
//                    }
//                    query = databaseReference.orderByChild("factoryName")
//                            .equalTo(workRequestType);
//
//                    query.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                itemList.clear();
//
//                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    if (ds.child("status").getValue().equals("0")) {
//
//                                        itemList.add(ds.getValue(ModuleItem.class));
//
//
//
//                                    }
//                                    buyerSearchAdapter.notifyDataSetChanged();
//                                }
//                            }
//                        }
//
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//
//
//
//                }else {
//                    //TODO chick if another filter selected
//                    prepareAlbums();
//                }
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                prepareAlbums();
//
//            }
//        });




        serchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                itemList.clear();

                query = databaseReference.orderByChild("name")
                        .startAt(serchText.getText().toString())
                .endAt(serchText.getText().toString()+"\uf8ff");
                if(!serchText.getText().toString().equals(""))
                {

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            itemList.clear();

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                if (ds.child("status").getValue().equals("0")) {

                                itemList.add(ds.getValue(ModuleItem.class));

//                                    if (!ds.child("factoryName").getValue().equals(null) && ds.child("factoryName").getValue().equals(itemCompan.getSelectedItem().toString()))
//                                    {
//                                        if (!ds.child("category").getValue().equals(null) && ds.child("category").getValue().equals(itemCatgory.getSelectedItem().toString()))
//                                        {
//                                            if (!ds.child("year").getValue().equals(null) && ds.child("year").getValue().equals(itemYear.getSelectedItem().toString())) {
//
//                                                if (!ds.child("type").getValue().equals(null) && ds.child("type").getValue().equals(itemModel.getSelectedItem().toString())) {
//
//                                                    itemList.add(ds.getValue(ModuleItem.class));
//                                                }else{
//                                                    itemList.add(ds.getValue(ModuleItem.class));
//
//                                                }
//                                            }else{
//                                                itemList.add(ds.getValue(ModuleItem.class));
//
//                                            }
//                                }else{
//                                            itemList.add(ds.getValue(ModuleItem.class));
//
//                                        }
//
//                                    }else {
//
//                                    }
                            }

                                buyerSearchAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                }else{
                    itemList.clear();

                    prepareAlbums();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        String[] comapanys =
                {"أي", "TOYOTA", "HYUANDAY", "HONDA"};

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


//        itemCompan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItemText = (String) parent.getItemAtPosition(position);
//                String userActivity = selectedItemText;
//                // If user change the default selection
//                // First item is disable and it is used for hint
//                if (position > 0) {
//                    // Notify the selected item text
//                    Toast.makeText
//                            (getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
//                            .show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        itemCompan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelectedSring = itemCompan.getSelectedItem().toString().trim();

                if (itemSelectedSring.equals("TOYOTA")) {
                    String[] models =
                            {"أي", "CAMRY", "COROLLA", "AURION"};
                    ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, models);
                    adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    itemModel.setAdapter(adapterModel);
                } else if (itemSelectedSring.equals("HYUANDAY")) {
                    String[] models =
                            {"أي", "SONATA", "ELNTRA", "ACCENT"};
                    ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, models);
                    adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    itemModel.setAdapter(adapterModel);
                } else if (itemSelectedSring.equals("HONDA")) {
                    String[] models =
                            {"أي", "CIVIC", "ACORD", "CARNAVAL"};
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


        itemModel = view.findViewById(R.id.itemType_input);
        String[] models =
                {"أي", "CAMRY", "COROLLA", "AURION"};
        ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, models);
        adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemModel.setAdapter(adapterModel);


        itemYear = view.findViewById(R.id.itemYear_input);
        String[] years =
                {"أي", "2011", "2012", "2013"};
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, years);
        adapterYear.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemYear.setAdapter(adapterYear);


        itemCatgory = view.findViewById(R.id.itemCatg_input);
        String[] catgs =
                {"أي", "بودي", "كهرباء", "محركات - وقود", "جير - شاسيه"};
        ArrayAdapter<String> adapterCatg = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, catgs);
        adapterCatg.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemCatgory.setAdapter(adapterCatg);

        serchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });




//
//        count = view.findViewById(R.id.count);
//
//        count.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                    MainBuyerActivity.cartList.add(moduleItem.getId());
//                    Toast.makeText(getActivity().getBaseContext(), "تمت الاضافة للسلة", Toast.LENGTH_SHORT).show();
//
//                }
//            }  );



















    }

    @Override

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    public FragmentBuyerSearch() {
        // Required empty public constructor
    }

    public static FragmentBuyerSearch newInstance(String param1, String param2) {
        FragmentBuyerSearch fragment = new FragmentBuyerSearch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private void prepareAlbums() {
//        ModuleItem a = new ModuleItem("hcd","dfv","fdv","adsfv","sdfvd","jhgfd",9,null);
//        itemList.add(a);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("tesst", "this is size :" + dataSnapshot.toString());
                    if (ds.child("status").getValue().equals("0"))
                    {
                        itemList.add(ds.getValue(ModuleItem.class));
                    }




//                    sellerHomeAdapter.notifyDataSetChanged();
                }
                if (itemList.equals(null)) {
                    Toast.makeText(getContext(), "no Items Yet", Toast.LENGTH_LONG).show();
                }


                buyerSearchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);


//
//
//
    }


    //**************************************************************************************************
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void itemDetailClick(ModuleItem item) {

    }
//**************************************************************************************************


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}

