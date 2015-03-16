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
import urdu4android.onairm.com.urdu4android.module.Novel;
import urdu4android.onairm.com.urdu4android.module.NovelArrayAdapter;
import urdu4android.onairm.com.urdu4android.framework.TitleController;
import urdu4android.onairm.com.urdu4android.module.NovelData;
import urdu4android.onairm.com.urdu4android.module.NovelManager;
import urdu4android.onairm.com.urdu4android.tool.ScrollView.AutoScrollViewBanner;
import urdu4android.onairm.com.urdu4android.tool.ScrollView.IBanner;

/**
 * Created by apple on 15/3/11.
 */
public class NovelFragment extends Fragment implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView> , Response.Listener<NovelData>, Response.ErrorListener {

    private final String TAG = NovelFragment.class.getSimpleName();
    private View contentView;
    private PullToRefreshListView pullListView;
    private TitleController titleController;
    private NovelArrayAdapter adapter=null;

    AutoScrollViewBanner mBannerView;
    ListView lv;
    ArrayList<IBanner>  listImg = new ArrayList<IBanner>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novel, container, false);
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
//                NovelDescActivity.actionStart(getActivity(), null);
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
            adapter = new NovelArrayAdapter(getActivity(),new NovelData());
            pullListView.setAdapter(adapter);
            // Get the first page
            NovelManager.getInstance().requestGetNovels(this, this, 1);
        }else{
            pullListView.setAdapter(adapter);
//            mBannerView.initData(listImg);
        }

    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        NovelManager.getInstance().requestGetNovels(this, this, 1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        NovelManager.getInstance().requestGetNovels(this, this,adapter.getmNovelData().getPage()+1);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG+"-onItemClick Position>>>>>>>>>>>>>>>", String.valueOf(position));
        Object item = parent.getItemAtPosition(position);
        if (item instanceof Novel) {
            Novel novel = (Novel) item;
            String novelId = novel.getNovelId();
//            NovelDescActivity.actionStart(getActivity(), null);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        titleController = new TitleController(null, contentView.findViewById(R.id.subTitleBar));
        titleController.setTitleText(getString(R.string.novel));
        titleController.setTitleRight(R.drawable.abc_btn_switch_to_on_mtrl_00001,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreActivity.actionStart(getActivity(), null);
            }
        });

    }


    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Log.e(TAG, "Error retrieving additional novels: " + volleyError.getMessage());
    }

    @Override
    public void onResponse(final NovelData response) {
        if(response.getStatusCode()==0){
            adapter.getmNovelData().setPage(response.getPage());
            if(response.getPage()==1){
                adapter.clear();
                listImg.clear();
                IBanner iBanner = new IBanner() {
                    @Override
                    public String getImageUrl() {
                        return response.getNovels().get(0).getImgUrl();
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
                        return response.getNovels().get(1).getImgUrl();
                    }

                    @Override
                    public String getJumpUri() {
                        return null;
                    }
                };
                IBanner iBanner2 = new IBanner() {
                    @Override
                    public String getImageUrl() {
                        return response.getNovels().get(2).getImgUrl();
                    }
                    @Override
                    public String getJumpUri() {
                        return null;
                    }
                };
                listImg.add(iBanner);
                listImg.add(iBanner1);
                listImg.add(iBanner2);
                response.getNovels().remove(0);
                response.getNovels().remove(1);
                response.getNovels().remove(2);
//                mBannerView.initData(listImg);
            }
            adapter.add(response.getNovels());
            adapter.notifyDataSetChanged();
            pullListView.onRefreshComplete();
        }

    }
}
