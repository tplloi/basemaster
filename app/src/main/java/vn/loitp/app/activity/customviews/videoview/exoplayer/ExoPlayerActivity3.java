package vn.loitp.app.activity.customviews.videoview.exoplayer;

import android.os.Bundle;

import com.google.android.exoplayer2.ui.PlayerView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.views.exo.PlayerManager;

public class ExoPlayerActivity3 extends BaseFontActivity {
    private PlayerView playerView0;
    private PlayerView playerView1;
    private PlayerManager playerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerView0 = findViewById(R.id.player_view_0);
        playerView1 = findViewById(R.id.player_view_1);
        final String linkPlay = "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd";
        playerManager = new PlayerManager(activity);
        playerManager.init(this, playerView0, linkPlay);

        findViewById(R.id.bt_0_1).setOnClickListener(view -> PlayerView.switchTargetView(playerView0.getPlayer(), playerView0, playerView1));
        findViewById(R.id.bt_1_0).setOnClickListener(view -> PlayerView.switchTargetView(playerView1.getPlayer(), playerView1, playerView0));
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_exo_player3;
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerManager.resumeVideo();
    }

    @Override
    public void onPause() {
        super.onPause();
        playerManager.pauseVideo();
    }

    @Override
    public void onDestroy() {
        playerManager.release();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (LScreenUtil.isLandscape(activity)) {
            playerManager.toggleFullscreen(activity);
        } else {
            super.onBackPressed();
        }
    }
}
