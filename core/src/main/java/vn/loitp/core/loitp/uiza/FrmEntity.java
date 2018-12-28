package vn.loitp.core.loitp.uiza;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import loitp.core.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.uiza.UZRestClient;
import vn.loitp.restapi.uiza.UZService;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;
import vn.loitp.restapi.uiza.model.v3.videoondeman.listallentity.ResultListEntity;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;
import vn.loitp.views.recyclerview.animator.adapters.ScaleInAnimationAdapter;
import vn.loitp.views.recyclerview.animator.animators.SlideInRightAnimator;

public class FrmEntity extends BaseFragment {
    private final String TAG = "TAG" + getClass().getSimpleName();
    private Data metadata;
    private boolean isMetadataHome;
    private AVLoadingIndicatorView avl;
    private TextView tvMsg;
    private List<Data> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EntityAdapter entityAdapter;
    private String admobBaner = null;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            admobBaner = bundle.getString(Constants.AD_UNIT_ID_BANNER);
        }
        LLog.d(TAG, "admobBaner " + admobBaner);
        metadata = UZD.getInstance().getMetadata();
        if (metadata == null) {
            return;
        }
        if (metadata.getName().equals(UZCons.HOME)) {
            isMetadataHome = true;
        } else {
            isMetadataHome = false;
        }
        avl = (AVLoadingIndicatorView) view.findViewById(R.id.avl);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        tvMsg = (TextView) view.findViewById(R.id.tv_msg);
        SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(300);
        recyclerView.setItemAnimator(animator);
        entityAdapter = new EntityAdapter(getActivity(), dataList, new EntityAdapter.Callback() {
            @Override
            public void onClick(Data data, int position) {
                LUIUtil.goToUZPlayerActivity(getActivity(), data, admobBaner);
            }

            @Override
            public void onLongClick(Data data, int position) {
            }

            @Override
            public void onLoadMore() {
                getListEntity();
            }

            @Override
            public void onScrollUp() {
                //LLog.d(TAG, "onScrollUp -> show");
            }

            @Override
            public void onScrollDown() {
                //LLog.d(TAG, "onScrollDown -> hide");
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(entityAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);
        LUIUtil.setPullLikeIOSVertical(recyclerView);
        getListEntity();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.uiza_frm_entity;
    }

    private void getListEntity() {
        if (metadata == null) {
            return;
        }
        if (!isCanLoadMore) {
            LLog.d(TAG, "Cannot loadmore because this is last page!");
            return;
        }
        page++;
        if (isMetadataHome) {
            listAllEntity();
        } else {
            listAllEntityMetadata();
        }
    }

    private int page = 0;
    private double totalPage = Integer.MAX_VALUE;
    private boolean isCanLoadMore = true;

    private void listAllEntity() {
        tvMsg.setVisibility(View.GONE);
        UZService service = UZRestClient.createService(UZService.class);
        String metadataId = "";
        int limit = 50;
        String orderBy = "createdAt";
        String orderType = "DESC";
        subscribe(service.getListAllEntity(metadataId, limit, page, orderBy, orderType, "success"), new ApiSubscriber<ResultListEntity>() {
            @Override
            public void onSuccess(ResultListEntity result) {
                if (result == null || result.getData() == null || result.getData().isEmpty()) {
                    if (page == 1) {
                        tvMsg.setVisibility(View.VISIBLE);
                    } else {
                        LLog.d(TAG, "last page");
                        isCanLoadMore = false;
                    }
                } else {
                    if (dataList.size() > 500) {
                        dataList.clear();
                    }
                    dataList.addAll(result.getData());
                    totalPage = result.getMetadata().getTotal();
                    LLog.d(TAG, "totalPage " + totalPage);
                    refreshAllViews();
                }
                avl.smoothToHide();
            }

            @Override
            public void onFail(Throwable e) {
                LLog.e(TAG, "getListAllEntity onFail " + e.getMessage());
                if (page == 0) {
                    tvMsg.setVisibility(View.VISIBLE);
                }
                avl.smoothToHide();
            }
        });
    }

    private void listAllEntityMetadata() {
        tvMsg.setVisibility(View.GONE);
        UZService service = UZRestClient.createService(UZService.class);
        int limit = 50;
        String orderBy = "createdAt";
        String orderType = "DESC";
        subscribe(service.getListAllEntity(metadata.getId(), limit, page, orderBy, orderType, "success"), new ApiSubscriber<ResultListEntity>() {
            @Override
            public void onSuccess(ResultListEntity result) {
                if (result == null || result.getData() == null || result.getData().isEmpty()) {
                    if (page == 1) {
                        tvMsg.setVisibility(View.VISIBLE);
                    } else {
                        LLog.d(TAG, "last page");
                        isCanLoadMore = false;
                    }
                } else {
                    if (dataList.size() > 500) {
                        dataList.clear();
                    }
                    dataList.addAll(result.getData());
                    totalPage = result.getMetadata().getTotal();
                    LLog.d(TAG, "totalPage " + totalPage);
                    refreshAllViews();
                }
                avl.smoothToHide();
            }

            @Override
            public void onFail(Throwable e) {
                LLog.e(TAG, "listAllEntityMetadata onFail " + e.getMessage());
                if (page == 0) {
                    tvMsg.setVisibility(View.VISIBLE);
                }
                avl.smoothToHide();
            }
        });
    }

    private void refreshAllViews() {
        LLog.d(TAG, "refreshAllViews size: " + dataList.size());
        if (entityAdapter != null) {
            entityAdapter.notifyDataSetChanged();
        }
    }
}