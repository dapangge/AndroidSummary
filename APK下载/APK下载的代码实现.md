### APK下载的代码实现

```
//下载APK
private void downloadNEWAPK(Versioninfo info) {
    HttpUtils httpUtils = new HttpUtils();
    final File file = new File(Environment.getExternalStorageDirectory(),"xx.apk");
    Log.i("TAG",info.downloadurl);
    httpUtils.download(info.downloadurl, file.getAbsolutePath(), new RequestCallBack<File>() {
        @Override
        public void onSuccess(ResponseInfo<File> responseInfo) {
                    //下载成功
                   //安装软件
                   Intent intent = new Intent();
                   intent.setAction("android.intent.action.VIEW");
                   intent.addCategory("android.intent.category.DEFAULT");
                   intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                   startActivityForResult(intent, 1);
        }

        @Override
        public void onFailure(HttpException e, String s) {
                //下载失败
            Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
            startHomeActivity();
        }
    });
```