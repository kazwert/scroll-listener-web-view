public class ObservableWebView extends WebView {

    private OnScrollChangedCallback mOnScrollChangedCallback;

    boolean topReached, bottomReached;

    public ObservableWebView(final Context context) {
        super(context);
    }

    public ObservableWebView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableWebView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null) mOnScrollChangedCallback.onScroll(l, t, oldl, oldt);

        Timber.i("onScrollChanged");

        topReached = false;

        bottomReached = false;

        Timber.i("onScrollChanged newLeft : " + l);
        Timber.i("onScrollChanged newTop : " + t);
        Timber.i("onScrollChanged oldLeft : " + oldl);
        Timber.i("onScrollChanged oldTop : " + oldt);

        int readerViewHeight = getMeasuredHeight();

        int contentHeight = getContentHeight();

        if (t == 0) {

            Timber.d("onScrollChanged : Top End");

            topReached = true;

        } else if (t >= contentHeight + readerViewHeight) {

            Timber
                .d("onScrollChanged : Bottom End Content Height : " + (contentHeight+readerViewHeight) + " top : " +
                    t);
            bottomReached = true;
            if (mOnScrollChangedCallback != null && bottomReached)
                mOnScrollChangedCallback.onReachBottom(l, t, oldl, oldt);
        }
    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    /**
     * Impliment in the activity/fragment/view that you want to listen to the webview
     */
    public static interface OnScrollChangedCallback {

        public void onScroll(int l, int t, int oldl, int oldt);

        public void onReachBottom(int l, int t, int oldl, int oldt);
    }
}
