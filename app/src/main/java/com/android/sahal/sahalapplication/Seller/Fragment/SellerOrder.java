package com.android.sahal.sahalapplication.Seller.Fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.android.sahal.sahalapplication.Adapters.SellerOrderAdapter;
import com.android.sahal.sahalapplication.Adapters.SellerSoldItemsAdapter;
import com.android.sahal.sahalapplication.Model.ModuleItem;
import com.android.sahal.sahalapplication.R;
import com.android.sahal.sahalapplication.Buyer.Fragment.SignupBuyer;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SellerOrder.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SellerOrder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellerOrder extends Fragment implements SellerSoldItemsAdapter.onItemClickListener, SearchView.OnQueryTextListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private SignupBuyer.OnFragmentInteractionListener mListener;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    final DatabaseReference databaseReference = firebaseDatabase.getInstance().getReference().child("items");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();


    ModuleItem item;
    //    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SellerOrderAdapter sellerOrderAdapter;
    private List<ModuleItem> itemList;



    //____________________________________________

    public static SellerSoldItems newInstance() {
        return new SellerSoldItems();
    }

    @Override

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    //_____________________________________________


    public SellerOrder() {
        // Required empty public constructor
    }


//_______________________________________________



    public static SellerSoldItems newInstance(String param1, String param2) {
        SellerSoldItems fragment = new SellerSoldItems();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //_______________________________________________


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }
//____________________________________________________
private void prepareAlbums() {
//        ModuleItem a = new ModuleItem("hcd","dfv","fdv","adsfv","sdfvd","jhgfd",9,null);
//        itemList.add(a);
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // final FirebaseUser user = mAuth.getCurrentUser();
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                if(ds.child("sellerId").getValue().equals(currentUser.getUid())){
                    //user instead of none
                    if(ds.child("status").getValue().equals("1")) {
                        itemList.add(ds.getValue(ModuleItem.class));
                        Log.d("tesst", "this is size :" + itemList.size());
                        Log.d("tesst", "this is name :" + ds.toString());

//                    sellerHomeAdapter.notifyDataSetChanged();
                    }}}

            if (itemList.equals(null)) {

            }


            sellerOrderAdapter.notifyDataSetChanged();
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
    @Override


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewSold);
        itemList = new ArrayList<>();
        sellerOrderAdapter = new SellerOrderAdapter(this.getContext(), itemList);



//        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sellerOrderAdapter);

        prepareAlbums();


    }


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
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seller_order , container,false);

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
