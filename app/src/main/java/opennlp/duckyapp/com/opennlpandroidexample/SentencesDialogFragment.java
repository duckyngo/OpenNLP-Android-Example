package opennlp.duckyapp.com.opennlpandroidexample;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import opennlp.tools.util.Span;

/**
 * Created by DK on 7/13/2017.
 */

public class SentencesDialogFragment extends DialogFragment {

    private static final String TAG = SentencesDialogFragment.class.getName();
    TextView mTitle;
    public ArrayList<Span> spanArrayList;
    ListView listView;
    ProgressBar progressBar;
    private AnswerAdapter mAnswerAdapter;
    private AlertDialog alertDialog;
    String mParagraph = "";
    TextView calTimeTv;
    long consumeTime = 0;


    public SentencesDialogFragment() {
    }

    public SentencesDialogFragment(ArrayList<Span> spanArrayList, String inputText, long time) {
        this.spanArrayList = spanArrayList;
        consumeTime = time;
        mParagraph = inputText;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_answer_dialog, container);
        mTitle = (TextView) view.findViewById(R.id.answer_title);
        listView = (ListView) view.findViewById(R.id.expandable_listview);
        progressBar = (ProgressBar) view.findViewById(R.id.loading_empty_pb);
        calTimeTv = (TextView) view.findViewById(R.id.cal_point);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        // Fetch arguments from bundle and set title

        mAnswerAdapter = new AnswerAdapter(getActivity(), spanArrayList);
        listView.setAdapter(mAnswerAdapter);
        listView.setEmptyView(progressBar);


        calTimeTv.setText("Sentences count: " + spanArrayList.size() +  " - Time: " + consumeTime);

    }

    public class AnswerAdapter extends BaseAdapter {

        ArrayList<Span> dataLists;
        Context context;

        public AnswerAdapter(Context context, ArrayList<Span> dataList) {
            this.dataLists = dataList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return dataLists.size();
        }

        @Override
        public Object getItem(int position) {
            return dataLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            Span span = (Span) getItem(position);
            if (convertView == null) {
                view = inflater.inflate(R.layout.sentence_list_item_layout, null);
            } else {
                view = convertView;
            }

            TextView sentenceTv = (TextView) view.findViewById(R.id.sentence);
            sentenceTv.setText(span.toString() + "|: " + span.getCoveredText(mParagraph));
            return view;
        }
    }





}
