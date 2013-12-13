package com.hk.agentsphere;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hari on 12/6/13.
 */
public class SummuryFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER ="section_number" ;
    private int mPageNumber;

    public static  SummuryFragment newInstance(int sectionNumber) {

        SummuryFragment fragment = new SummuryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SummuryFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_SECTION_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        switch(getArguments().getInt(ARG_SECTION_NUMBER))
        {
            case 0:
                View rootView = inflater.inflate(R.layout.fragment_start, container, false);

                return rootView;
            case 1:
                View view1 = inflater.inflate(R.layout.fragment_register,container, false);
                return view1;
            case 2:
                View view2 = inflater.inflate(R.layout.fragment_forgot,container, false);
                return view2;
            default:
                View dview = inflater.inflate(R.layout.fragment_start, container, false);
                return dview;

        }

    }




}
