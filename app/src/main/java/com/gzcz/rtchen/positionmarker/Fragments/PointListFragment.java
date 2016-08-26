package com.gzcz.rtchen.positionmarker.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.gzcz.rtchen.positionmarker.ListViewPositionPoint;
import com.gzcz.rtchen.positionmarker.MainActivity;
import com.gzcz.rtchen.positionmarker.MyListViewAdapter;
import com.gzcz.rtchen.positionmarker.PositionPoint;
import com.gzcz.rtchen.positionmarker.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PointListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PointListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PointListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    View mView = null;

    ArrayList<PositionPoint> mPointsList = null;
    ArrayList<ListViewPositionPoint> mList = null;
    ListView mListView = null;
    MyListViewAdapter mAdapter = null;

    public PointListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PointListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PointListFragment newInstance(String param1, String param2) {
        PointListFragment fragment = new PointListFragment();
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

    public ArrayList<ListViewPositionPoint> convertList(ArrayList<PositionPoint> src) {
        if (null == src) return null;

        ArrayList<ListViewPositionPoint> ret = new ArrayList<ListViewPositionPoint>();
        if (src.isEmpty()) return ret;

        int currentNum = 0;
        int currentDotNum = 0;
        String currentDotName = src.get(0).getDotName();

        for (PositionPoint p : src) {
            if (!p.getDotName().equals(currentDotName)) {
                currentDotNum = 0;
                currentDotName = p.getDotName();
            }
            ret.add(new ListViewPositionPoint(++currentNum, ++currentDotNum, false, p));
        }

        // TODO:出错处理
        //return null;
        return ret;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.pointlist_fragment, container, false);
        mListView = (ListView) mView.findViewById(R.id.list);

        TextView mTV = (TextView) mView.findViewById(R.id.tv_current_project_name);
        mTV.setText(MainActivity.dm.getCurrentProjectName());

        CheckBox mCB = (CheckBox) mView.findViewById(R.id.mcb);
        mCB.setVisibility(CheckBox.INVISIBLE);

        mPointsList = MainActivity.dm.getPointsList();

        mList = new ArrayList<ListViewPositionPoint>();
        mList = convertList(mPointsList);

        mAdapter = new MyListViewAdapter(getContext(), mList);
        mListView.setAdapter(mAdapter);

        // TODO:简化步骤
        mList = convertList(mPointsList);
        mAdapter.refresh(mList);

        /*
         * 注意不能使用上面那句默认返回语句！否则自定义的ListView不会显示。
         * 参考文章：http://blog.csdn.net/mldan/article/details/39896765
         * */
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.pointlist_fragment, container, false);
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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