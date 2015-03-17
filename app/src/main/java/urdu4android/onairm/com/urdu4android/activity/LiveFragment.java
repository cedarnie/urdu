package urdu4android.onairm.com.urdu4android.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

import urdu4android.onairm.com.urdu4android.R;
import urdu4android.onairm.com.urdu4android.framework.TitleController;
import urdu4android.onairm.com.urdu4android.module.Video;
import urdu4android.onairm.com.urdu4android.module.VideoArrayAdapter;
import urdu4android.onairm.com.urdu4android.module.VideoData;
import urdu4android.onairm.com.urdu4android.module.VideoManager;
import urdu4android.onairm.com.urdu4android.tool.ScrollView.AutoScrollViewBanner;
import urdu4android.onairm.com.urdu4android.tool.ScrollView.IBanner;

/**
 * Created by apple on 15/3/11.
 */
public class LiveFragment extends Fragment implements   Response.Listener<VideoData>, Response.ErrorListener {

    private final String TAG = LiveFragment.class.getSimpleName();
    private View contentView;
    private TitleController titleController;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        contentView=view;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        titleController = new TitleController(null, contentView.findViewById(R.id.subTitleBar));
        titleController.setTitleText(getString(R.string.video));
        titleController.setTitleRight(R.drawable.abc_btn_switch_to_on_mtrl_00001,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreActivity.actionStart(getActivity(), null);
            }
        });

    }


    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Log.e(TAG, "Error retrieving additional videos: " + volleyError.getMessage());
    }

    @Override
    public void onResponse(final VideoData response) {
        if(response.getStatusCode()==0){

        }

    }
}
