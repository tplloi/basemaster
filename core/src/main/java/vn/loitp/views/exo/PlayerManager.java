package vn.loitp.views.exo;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.C.ContentType;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;

import androidx.annotation.NonNull;
import loitp.core.R;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.utils.util.AppUtils;

public final class PlayerManager implements AdsMediaSource.MediaSourceFactory {
    private final String TAG = getClass().getSimpleName();
    private ImaAdsLoader adsLoader;
    private DataSource.Factory dataSourceFactory;
    private SimpleExoPlayer player;
    private int screenW;

    public SimpleExoPlayer getPlayer() {
        return player;
    }

    public PlayerManager(@NonNull final Context context) {
        this.adsLoader = null;
        this.screenW = LScreenUtil.getScreenWidth();
        dataSourceFactory = new DefaultDataSourceFactory(context, AppUtils.getAppName());
    }

    public PlayerManager(@NonNull final Context context, @NonNull final String urlIMAAd) {
        if (!urlIMAAd.isEmpty()) {
            adsLoader = new ImaAdsLoader(context, Uri.parse(urlIMAAd));
        }
        this.screenW = LScreenUtil.getScreenWidth();
        dataSourceFactory = new DefaultDataSourceFactory(context, AppUtils.getAppName());
    }

    public void init(@NonNull final Context context, @NonNull final PlayerView playerView, @NonNull final String linkPlay) {
        init(context, playerView, linkPlay, 0);
    }

    public void init(@NonNull final Context context, @NonNull final PlayerView playerView, @NonNull final String linkPlay, final long contentPosition) {
        if (linkPlay.isEmpty()) {
            LLog.e(TAG, "init failed -> return");
            return;
        }
        playerView.setControllerShowTimeoutMs(8000);
        playerView.setControllerHideOnTouch(true);
        // Create a default track selector.
        final TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();
        final TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // Create a player instance.
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        // Bind the player to the view.
        playerView.setPlayer(player);

        // This is the MediaSource representing the content media (i.e. not the ad).
        //String contentUrl = context.getString(R.string.content_url);
        final MediaSource contentMediaSource = buildMediaSource(Uri.parse(linkPlay));

        // Compose the content media source into a new AdsMediaSource with both ads and content.
        MediaSource mediaSourceWithAds = null;
        if (adsLoader != null) {
            mediaSourceWithAds =
                    new AdsMediaSource(
                            contentMediaSource,
                            /* adMediaSourceFactory= */ this,
                            adsLoader,
                            playerView.getOverlayFrameLayout());
        }

        // Prepare the player with the source.
        if (mediaSourceWithAds == null) {
            player.prepare(contentMediaSource);
        } else {
            player.prepare(mediaSourceWithAds);
        }
        player.setPlayWhenReady(true);
        player.seekTo(contentPosition);
        LLog.d(TAG, "seekTo contentPosition: " + contentPosition);
        player.addListener(new Player.EventListener() {
            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                //LLog.d(TAG, "onLoadingChanged " + isLoading);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_BUFFERING:
                        LLog.d(TAG, "onPlayerStateChanged STATE_BUFFERING");
                        if (playerView.getControllerShowTimeoutMs() == 0) {
                            playerView.setControllerShowTimeoutMs(8000);
                        }
                        if (!playerView.getControllerHideOnTouch()) {
                            playerView.setControllerHideOnTouch(true);
                        }
                        break;
                    case Player.STATE_IDLE:
                        LLog.d(TAG, "onPlayerStateChanged STATE_IDLE");
                        break;
                    case Player.STATE_READY:
                        LLog.d(TAG, "onPlayerStateChanged STATE_READY");
                        break;
                    case Player.STATE_ENDED:
                        LLog.d(TAG, "onPlayerStateChanged STATE_ENDED");
                        playerView.showController();
                        playerView.setControllerShowTimeoutMs(0);
                        playerView.setControllerHideOnTouch(false);
                        break;
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            }
        });
        player.addVideoListener(new VideoListener() {
            @Override
            public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
                videoW = width;
                videoH = height;
            }

            @Override
            public void onSurfaceSizeChanged(int width, int height) {
                //LLog.d(TAG, "onSurfaceSizeChanged " + width + "x" + height);
            }

            @Override
            public void onRenderedFirstFrame() {
            }
        });
    }

    private int videoW = 0;
    private int videoH = 0;

    public int getVideoW() {
        return videoW;
    }

    public int getVideoH() {
        return videoH;
    }

    public void reset() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void release() {
        if (player != null) {
            player.release();
            player = null;
        }
        if (adsLoader != null) {
            adsLoader.release();
        }
    }

    // AdsMediaSource.MediaSourceFactory implementation.
    @Override
    public MediaSource createMediaSource(Uri uri) {
        return buildMediaSource(uri);
    }

    @Override
    public int[] getSupportedTypes() {
        // IMA does not support Smooth Streaming ads.
        return new int[]{C.TYPE_DASH, C.TYPE_HLS, C.TYPE_OTHER};
    }

    private MediaSource buildMediaSource(Uri uri) {
        @ContentType int type = Util.inferContentType(uri);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            default:
                throw new IllegalStateException("Unsupported type: " + type);
        }
    }

    public void toggleFullscreen(@NonNull final Activity activity) {
        if (LScreenUtil.isLandscape(activity)) {
            //land -> port
            LScreenUtil.toggleFullscreen(activity, false);
            LActivityUtil.changeScreenPortrait(activity);
        } else {
            //port -> land
            LScreenUtil.toggleFullscreen(activity, true);
            LActivityUtil.changeScreenLandscape(activity);
        }
    }

    //for other sample not UZVideo
    public void updateSizePlayerView(@NonNull final Activity activity, @NonNull final PlayerView playerView, @NonNull final ImageButton exoFullscreen) {
        LLog.d(TAG, "updateSizePlayerView screenW " + screenW);
        if (LScreenUtil.isLandscape(activity)) {
            playerView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            playerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            exoFullscreen.setImageResource(R.drawable.exo_controls_fullscreen_exit);
        } else {
            playerView.getLayoutParams().height = screenW * 9 / 16;
            exoFullscreen.setImageResource(R.drawable.exo_controls_fullscreen_enter);
        }
        playerView.requestLayout();
        LLog.d(TAG, "requestLayout");
    }

    public void pauseVideo() {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }

    public void resumeVideo() {
        if (player != null) {
            player.setPlayWhenReady(true);
        }
    }

    public long getContentPosition() {
        if (player != null) {
            return player.getContentPosition();
        }
        return 0;
    }
}