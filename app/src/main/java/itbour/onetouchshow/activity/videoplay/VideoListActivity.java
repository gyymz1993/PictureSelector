package itbour.onetouchshow.activity.videoplay;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andview.adapter.BaseRecyclerHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.videoplay.helper.video.VideoItem;
import itbour.onetouchshow.base.BaseRefreshAdapter;
import itbour.onetouchshow.mvp.MVPBaseActivity;
import itbour.onetouchshow.utils.JumpUtils;

/**
 * Created by onetouch on 2017/11/16.
 */

public class VideoListActivity extends MVPBaseActivity {
    @BindView(R.id.id_rv)
    RecyclerView idRv;

    @Override
    protected void initView() {
        super.initView();
        setTitleText("视频列表");
       // getToolBarView().getRightImageView().setVisibility(View.VISIBLE);
       // getToolBarView().setRightImageVisible().getRightImageView().setImageResource(R.drawable.__leak_canary_icon);
    }

    @Override
    public void loadSucceed(String result) {

    }

    @Override
    public void loadFaild(String error) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_activity_video_list;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        idRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });
        List<VideoItem> videoItems=new ArrayList<>();
        VideoItem videoItem1 = new VideoItem("playlist1", "http://www.xzcmvideo.cn//masvod/public/2017/01/28/20170128_159e3ae0361_r1_300k.mp4","http://news.hexun.com/2017-01-27/187953027.html");
        VideoItem videoItem2 = new VideoItem("playlist2", "http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear1/prog_index.m3u8","http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear1/prog_index.m3u8");
        VideoItem videoItem3 = new VideoItem("playlist3", "http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear2/prog_index.m3u8","http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear2/prog_index.m3u8");
        VideoItem videoItem4 = new VideoItem("playlist4", "http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear3/prog_index.m3u8","http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear3/prog_index.m3u8");
        VideoItem videoItem5 = new VideoItem("playlist5", "http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_16x9/gear0/prog_index.m3u8","http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_16x9/gear0/prog_index.m3u8");
        videoItems.add(videoItem1);
        videoItems.add(videoItem2);
        videoItems.add(videoItem3);
        videoItems.add(videoItem4);
        videoItems.add(videoItem5);
        ListAdapter listAdapter=new ListAdapter(getApplicationContext(),videoItems,R.layout.support_simple_spinner_dropdown_item);
        idRv.setAdapter(listAdapter);
        //listAdapter.setOnItemClickListener();
    }

    public class ListAdapter extends BaseRefreshAdapter<VideoItem>{

        public ListAdapter(Context context, List<VideoItem> datas, int itemLayoutId) {
            super(context, datas, itemLayoutId);
        }

        @Override
        protected void convert(BaseRecyclerHolder var1, VideoItem var2, int var3) {
            TextView view = var1.getView(android.R.id.text1);
            view.setText(var2.getVideoUrl());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Bundle bundle=new Bundle();
//                    bundle.putSerializable("item",  var2);
//                    openActivity(VideoplayActivity.class,bundle);
                    JumpUtils.goToVideoPlayer(VideoListActivity.this,view,var2);
                }
            });
        }
    }

}
