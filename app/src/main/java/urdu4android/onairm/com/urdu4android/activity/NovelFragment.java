package urdu4android.onairm.com.urdu4android.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import urdu4android.onairm.com.urdu4android.R;
import urdu4android.onairm.com.urdu4android.framework.TitleController;


/**
 * 小说页面
 */
public class NovelFragment  extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {

    private static final String TAG = NovelFragment.class.getSimpleName();

    private View contentView;

    private boolean busy;

    private PullToRefreshListView pullListView;
    private BaseAdapter adapter;
    private TitleController titleController;



    // 用于解决onActivityResult问题
    private int waitingRequest;
    private String title;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novel, container, false);
        contentView=view;

        pullListView = (PullToRefreshListView) contentView.findViewById(R.id.refreshListView);
        pullListView.setOnRefreshListener(this);
        pullListView.getRefreshableView().setDivider(null);
        pullListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullListView.setOnItemClickListener(this);
        pullListView.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        pullListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (!busy) {
                    //loadData();
                }
            }
        });

//        if (adapter == null)
//            adapter = createArrayAdapter();

        return view;
    }


    @Override
    public void onDestroyView() {
        unbindAdapter();
        super.onDestroyView();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        titleController = new TitleController(getActivity(), contentView.findViewById(R.id.subTitleBar));
        titleController.setTitleText(getString(R.string.novel));

    }



    @Override
    public void onClick(final View v) {
        if (busy) return;
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object item = parent.getItemAtPosition(position);
//        if (item instanceof WorkPosition) {
//            WorkPosition positionSearchResponse = (WorkPosition) item;
//            String positionId = positionSearchResponse._id;
//            switch (type) {
//                case OFFERED:
//                    String applyId = positionIdToAllyIdMap.get(positionId);
//                    WorkDetailActivity.actionShowDetailAsOffered(getActivity(), positionId, positionSearchResponse, applyId != null ? applyId : "");
//                    break;
//                default:
//                    WorkDetailActivity.actionShowDetail(getActivity(), positionId, positionSearchResponse.position_name);
//                    break;
//            }
//        }
    }

    void onLoadFinish() {
        pullListView.onRefreshComplete();
    }

    /**
     * called when header is set
     */
    protected void bindAdapter() {
        pullListView.setAdapter(adapter);
    }

    // region helpers

    protected void unbindAdapter() {
        pullListView.setAdapter(null);
    }

    public PullToRefreshListView getPullListView() {
        return pullListView;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        //reloadData(false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
       //loadData();
    }

//    @Override
//    public TitleController getTitleController() {
//        return titleController;
//    }



}
