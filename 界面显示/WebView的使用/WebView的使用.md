### WebView的使用

```
 mAd_detail = (WebView) findViewById(R.id.addetails_wv);
 //给mWebView配置,让其能够加载javascript
mAd_detail.getSettings().setJavaScriptEnabled(true);
 //碰到重定向时,会默认打开其他的应用来展示网页
 mAd_detail.setWebViewClient(new WebViewClient(){
     @Override
     public boolean shouldOverrideUrlLoading(WebView view, String url) {
         return super.shouldOverrideUrlLoading(view, url);
     }
 });
 //加载界面
 mAd_detail.loadUrl(mAd_detail_url);
```

```
//复写返回的方法
@Override
public void onBackPressed() {
    //判断是否有显示的网页
    if (mAd_detail.canGoBack()){
        mAd_detail.goBack();
    }else {
        super.onBackPressed();
    }
```
在javaSc中调用java方法

```
//设置web
mWebView.getSettings().setJavaScriptEnabled(true);
mWebView.addJavascriptInterface(NewsDetailsActivity.this,"test");
```

```
//web界面点击图片是调用
@JavascriptInterface
public void showPic(int index){
```