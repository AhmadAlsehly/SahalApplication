package com.android.sahal.sahalapplication.Buyer.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sahal.sahalapplication.Adapters.BuyerCartAdapter;
import com.android.sahal.sahalapplication.Adapters.BuyerPartsAdapter;
import com.android.sahal.sahalapplication.Buyer.Activity.MainBuyerActivity;
import com.android.sahal.sahalapplication.MainFirstActivity;
import com.android.sahal.sahalapplication.Model.ModuleItem;
import com.android.sahal.sahalapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;


public class FragmentCart extends Fragment implements BuyerCartAdapter.onItemClickListener, SearchView.OnQueryTextListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<ModuleItem> itemMoudelList;
    public int sum = 0;
    public int sold = 0;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    final DatabaseReference databaseReference = firebaseDatabase.getInstance().getReference().child("items");
    ModuleItem moduleItem;
    private OnFragmentInteractionListener mListener;
    private List<ModuleItem> itemList;
    private TextView txtSum = null;
    Button btnPurchase = null;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


//--------------------------------------------------------------------------------


    ModuleItem item;
    //    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BuyerCartAdapter buyerCartAdapter;


//____________________________________________

    public static FragmentCart newInstance() {
        return new FragmentCart();
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        recyclerView = view.findViewById(R.id.cart_recyclerView);
        itemList = new ArrayList<>();
        buyerCartAdapter = new BuyerCartAdapter(this.getContext(), itemList);

        btnPurchase = view.findViewById(R.id.btnPurchase);

        txtSum=view.findViewById(R.id.txtSum);

        LinearLayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(buyerCartAdapter);

        prepareAlbums();


        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                final FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null){ AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(getContext());
                    }
                    builder.setTitle("عملية شراء")
                            .setMessage("هل انت متاكد من رغبتك في شراء عدد قطع ؟  "+MainBuyerActivity.cartList.size())
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    for(int i=0 ;i < MainBuyerActivity.cartList.size() ; i++){

                                        mDatabase = FirebaseDatabase.getInstance().getReference().child("items").child(MainBuyerActivity.cartList.get(i));
                                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataS) {
                                                ModuleItem item = dataS.getValue(ModuleItem.class);
                                                if(item.getStatus().equals("0")){
                                                item.setStatus("1");
                                                item.setBuyerId(currentUser.getUid().toString());
                                                mDatabase.setValue(item);}
                                                else {
                                                    sold++;
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                        //add the buyer ID and change the status to 1
                                    }
                                    if(sold > 0){
                                    Toast.makeText(getContext(),"قطع مباعة تم ازالتها"+sold,Toast.LENGTH_SHORT).show();}
                                     MainBuyerActivity.cartList.clear();
                                    itemList.clear();
                                    buyerCartAdapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    //        ModuleItem a = new ModuleItem("hcd","dfv","fdv","adsfv","sdfvd","jhgfd",9,null);
//        itemList.add(a);












//
//
//
//        sellerHomeAdapter.notifyDataSetChanged();
                }
                else {

                    //should be loged in
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(getContext());
                    }
                    builder.setTitle("سجل الدخول")
                            .setMessage("قم بتسجيل الدخول لاتمام عملية الشراء")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete

                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

    }


    @Override

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


//_____________________________________________


    public FragmentCart() {
        // Required empty public constructor
    }


//_______________________________________________


    public static FragmentCart newInstance(String param1, String param2) {
        FragmentCart fragment = new FragmentCart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    //_______________________________________________


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//---------------------------------------------------------------------------

        setHasOptionsMenu(true);


    }


//____________________________________________________


    private void prepareAlbums() {
        sum = 0;
//        ModuleItem a = new ModuleItem("hcd","dfv","fdv","adsfv","sdfvd","jhgfd",9,null);
//        itemList.add(a);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for(int i=0 ;i < MainBuyerActivity.cartList.size() ; i++){
                        if(MainBuyerActivity.cartList.get(i).equals(ds.child("id").getValue())) {

                            itemList.add(ds.getValue(ModuleItem.class));
                            sum += Integer.parseInt(ds.child("price").getValue().toString());
                            Log.d("tesst", "this is size :" + itemList.size());
                            Log.d("tesst", "this is name :" + ds.toString());
                        }}
//                    sellerHomeAdapter.notifyDataSetChanged();
                }
                txtSum.setText(""+sum);

                buyerCartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);


//
//
//
//        sellerHomeAdapter.notifyDataSetChanged();
    }

//____________________________________________________________

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


    //________________________________________________________


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_cart, container, false);
    }


    //____________________________________________________


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    //______________________________________________________


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    //_______________________________________________


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    //_______________________________________________

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    //_______________________________________________

    @Override
    public void itemDetailClick(ModuleItem item) {

    }

    //_______________________________________________


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

//_______________________________________________


}
