### EdText焦点

```
private void setEd() {
    //获取到资源文件
    mMDrawableLeft = getResources().getDrawable(R.drawable.icon_edit_icon);
    mDrawableBottom = getResources().getDrawable(R.drawable.bg_edit_text);
    mMDrawableLeft.setBounds(0,0,mMDrawableLeft.getIntrinsicWidth(),mMDrawableLeft.getIntrinsicHeight());
    mDrawableBottom.setBounds(0,0,mDrawableBottom.getIntrinsicWidth(),mDrawableBottom.getIntrinsicHeight());
    //设置焦点
    mEtReply.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                //隐藏左边图片,
                mEtReply.setCompoundDrawables(null,null,null,mDrawableBottom);
                mTvReply.setVisibility(View.GONE);
                mTvSendReply.setVisibility(View.VISIBLE);
            }else{
                mEtReply.setCompoundDrawables(mMDrawableLeft,null,null,mDrawableBottom);
                mTvReply.setVisibility(View.VISIBLE);
                mTvSendReply.setVisibility(View.GONE);
            }
        }
    });
```