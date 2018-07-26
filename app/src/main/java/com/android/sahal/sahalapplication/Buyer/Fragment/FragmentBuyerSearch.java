package com.android.sahal.sahalapplication.Buyer.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sahal.sahalapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentBuyerSearch.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBuyerSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBuyerSearch extends Fragment {

    Spinner itemCompan, itemModel, itemYear, itemCatgory;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText serchText ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentBuyerSearch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBuyerSearch.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBuyerSearch newInstance(String param1, String param2) {
        FragmentBuyerSearch fragment = new FragmentBuyerSearch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        itemModel = view.findViewById(R.id.itemModel_input);
        itemYear = view.findViewById(R.id.itemYear_input);
        serchText = view.findViewById(R.id.search_edit_text);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                }});
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        itemCompan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelectedSring = itemCompan.getSelectedItem().toString().trim();

                if (itemSelectedSring.equals("TOYOTA")) {
                    String[] models =
                            {"أي","CAMRY", "COROLLA", "AURION"};
                    ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, models);
                    adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    itemModel.setAdapter(adapterModel);
                } else if (itemSelectedSring.equals("HYUANDAY")) {
                    String[] models =
                            {"أي","SONATA", "ELNTRA", "ACCENT"};
                    ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, models);
                    adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    itemModel.setAdapter(adapterModel);
                } else if (itemSelectedSring.equals("HONDA")) {
                    String[] models =
                            {"أي","CIVIC", "ACORD", "CARNAVAL"};
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
                {"أي","CAMRY", "COROLLA", "AURION"};
        ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, models);
        adapterModel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemModel.setAdapter(adapterModel);


        itemYear = view.findViewById(R.id.itemYear_input);
        String[] years =
                {"أي","2011", "2012", "2013"};
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, years);
        adapterYear.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemYear.setAdapter(adapterYear);


        itemCatgory = view.findViewById(R.id.itemCatg_input);
        String[] catgs =
                {"أي","هيكل", "كهرباء", "محرك"};
        ArrayAdapter<String> adapterCatg = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, catgs);
        adapterCatg.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemCatgory.setAdapter(adapterCatg);

        serchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });



    }






                /**
                 * This interface must be implemented by activities that contain this
                 * fragment to allow an interaction in this fragment to be communicated
                 * to the activity and potentially other fragments contained in that
                 * activity.
                 * <p>
                 * See the Android Training lesson <a href=
                 * "http://developer.android.com/training/basics/fragments/communicating.html"
                 * >Communicating with Other Fragments</a> for more information.
                 */
        public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            void onFragmentInteraction(Uri uri);
        }
    }
