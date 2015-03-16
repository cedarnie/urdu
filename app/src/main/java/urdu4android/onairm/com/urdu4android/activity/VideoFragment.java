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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import urdu4android.onairm.com.urdu4android.R;
import urdu4android.onairm.com.urdu4android.module.Video;
import urdu4android.onairm.com.urdu4android.module.VideoArrayAdapter;
import urdu4android.onairm.com.urdu4android.framework.TitleController;
import urdu4android.onairm.com.urdu4android.module.VideoData;
import urdu4android.onairm.com.urdu4android.module.VideoManager;
import urdu4android.onairm.com.urdu4android.tool.ScrollView.AutoScrollViewBanner;
import urdu4android.onairm.com.urdu4android.tool.ScrollView.IBanner;

/**
 * Created by apple on 15/3/11.
 */
public class VideoFragment extends Fragment implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView> , Response.Listener<VideoData>, Response.ErrorListener {

    private final String TAG = VideoFragment.class.getSimpleName();
    private View contentView;
    private PullToRefreshListView pullListView;
    private TitleController titleController;
    private VideoArrayAdapter adapter=null;

    AutoScrollViewBanner mBannerView;
    ListView lv;
    ArrayList<IBanner>  listImg = new ArrayList<IBanner>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        contentView=view;

        pullListView = (PullToRefreshListView) contentView.findViewById(R.id.refreshListView);
        pullListView.setOnRefreshListener(this);
        pullListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullListView.setOnItemClickListener(this);
        pullListView.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));

        mBannerView = new AutoScrollViewBanner(getActivity(), 640, 300);
//        mSwipRefreshLayout = (TsSwipeRefreshLayout)findViewById(R.id.swiperefreshlayout);
        mBannerView.setOnItemClickListener(new AutoScrollViewBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int index, String imgAction) {
                VideoDescActivity.actionStart(getActivity(), null);
//                TS2Act.toUri(getActivity(), imgAction);
//                Toast.makeText(MainActivity.this,"累死小爷了"+imgAction,Toast.LENGTH_SHORT).show();
            }
        });
        //解决手势冲突
//        mSwipRefreshLayout.setVp(mBannerView.getViewFlipper());
        lv  = pullListView.getRefreshableView();
        lv.addHeaderView(mBannerView);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(adapter == null) {
            adapter = new VideoArrayAdapter(getActivity(),new VideoData());
            pullListView.setAdapter(adapter);
            // Get the first page
            VideoManager.getInstance().requestGetVideos(this, this, 1);
        }else{
            pullListView.setAdapter(adapter);
            mBannerView.initData(listImg);
        }

    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        VideoManager.getInstance().requestGetVideos(this, this, 1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        VideoManager.getInstance().requestGetVideos(this, this,adapter.getmVideoData().getPage()+1);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG+"-onItemClick Position>>>>>>>>>>>>>>>", String.valueOf(position));
        Object item = parent.getItemAtPosition(position);
        if (item instanceof Video) {
            Video video = (Video) item;
            String videoId = video.getNewsId();
            VideoDescActivity.actionStart(getActivity(), null);
        }
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
            adapter.getmVideoData().setPage(response.getPage());
            if(response.getPage()==1){
                adapter.clear();
                listImg.clear();
                IBanner iBanner = new IBanner() {
                    @Override
                    public String getImageUrl() {
                        return response.getVideos().get(0).getImgUrl();
//                        return "http://7vznzz.com1.z0.glb.clouddn.com/1.jpg";
                    }
                    @Override
                    public String getJumpUri() {
                        return null;
                    }
                };
                IBanner iBanner1 = new IBanner() {
                    @Override
                    public String getImageUrl() {
                        return response.getVideos().get(1).getImgUrl();
                    }

                    @Override
                    public String getJumpUri() {
                        return null;
                    }
                };
                IBanner iBanner2 = new IBanner() {
                    @Override
                    public String getImageUrl() {
                        return response.getVideos().get(2).getImgUrl();
                    }
                    @Override
                    public String getJumpUri() {
                        return null;
                    }
                };
                listImg.add(iBanner);
                listImg.add(iBanner1);
                listImg.add(iBanner2);
                response.getVideos().remove(0);
                response.getVideos().remove(1);
                response.getVideos().remove(2);
                mBannerView.initData(listImg);
            }
            adapter.add(response.getVideos());
            adapter.notifyDataSetChanged();
            pullListView.onRefreshComplete();
        }

    }
}
