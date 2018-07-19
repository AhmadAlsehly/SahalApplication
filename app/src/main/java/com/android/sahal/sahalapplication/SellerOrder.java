package com.android.sahal.sahalapplication;

import android.content.Context;
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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

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
public class SellerOrder extends Fragment implements SallerSoldItemsAdapter.onItemClickListener, SearchView.OnQueryTextListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private SignupBuyer.OnFragmentInteractionListener mListener;

    ModuleItem item;
    //    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SallerOrderAdapter sallerOrderAdapter;
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
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        ModuleItem a = new ModuleItem("سوبر تشارج", "تجربة للوصف ", "FORD", "GT", "Electric", "2017", 2059.95,covers[0]);
        itemList.add(a);

        a = new ModuleItem("سوبر تشارج", "تجربة للوصف ", "FORD", "GT", "Electric", "2017", 2059.95,covers[0]);
        itemList.add(a);

        a = new ModuleItem("سوبر تشارج", "تجربة للوصف ", "FORD", "GT", "Electric", "2017", 2059.95,covers[1]);
        itemList.add(a);

        a = new ModuleItem("سوبر تشارج", "تجربة للوصف ", "FORD", "GT", "Electric", "2017", 2059.95,covers[2]);
        itemList.add(a);

        a = new ModuleItem("سوبر تشارج", "تجربة للوصف ", "FORD", "GT", "Electric", "2017", 2059.95,covers[3]);
        itemList.add(a);

        a = new ModuleItem("سوبر تشارج", "تجربة للوصف ", "FORD", "GT", "Electric", "2017", 2059.95,covers[4]);
        itemList.add(a);

        a = new ModuleItem("سوبر تشارج", "تجربة للوصف ", "FORD", "GT", "Electric", "2017", 2059.95,covers[5]);
        itemList.add(a);

        a = new ModuleItem("سوبر تشارج", "تجربة للوصف ", "FORD", "GT", "Electric", "2017", 2059.95,covers[6]);
        itemList.add(a);

        a = new ModuleItem("سوبر تشارج", "تجربة للوصف ", "FORD", "GT", "Electric", "2017", 2059.95,covers[7]);
        itemList.add(a);

        a = new ModuleItem("سوبر تشارج", "تجربة للوصف ", "FORD", "GT", "Electric", "2017", 2059.95,covers[8]);
        itemList.add(a);

        sallerOrderAdapter.notifyDataSetChanged();
    }

    //____________________________________________________________
    @Override


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewSold);
        itemList = new ArrayList<>();
        sallerOrderAdapter = new SallerOrderAdapter(this.getContext(), itemList);



//        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sallerOrderAdapter);

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
