### POP弹出框

```
public class ProductSortPop {
    private Context mContext;
    private View.OnClickListener mOnClickListener;  //创建一个点击事件接口回调数据
    private TextView mAllSort;
    private TextView mNewSort;
    private TextView mCommentSort;
    private View mLeftV;
    private PopupWindow mPopupWindow;


    public ProductSortPop(View.OnClickListener onClickListener, Context context) {
        mOnClickListener = onClickListener;
        mContext = context;
        initView();
    }

    //加载布局
    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_sort_pop_layout, null);
        //找到控件
        mAllSort  = (TextView) view.findViewById(R.id.all_sort);
        mNewSort  = (TextView) view.findViewById(R.id.new_sort);
        mCommentSort  = (TextView) view.findViewById(R.id.comment_sort);
        //设置点击事件
        mAllSort.setOnClickListener(mOnClickListener);
        mNewSort.setOnClickListener(mOnClickListener);
        mCommentSort.setOnClickListener(mOnClickListener);
        view.findViewById(R.id.left_v).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDismiss();
            }
        });
        mPopupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        //设置内部获取焦点
        mPopupWindow.setFocusable(true);
        //设置外部获取焦点
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        //更新当前的状态
        mPopupWindow.update();

    }

    //创建一个显示的方法
    public  void  onShow(View v){
        if (!mPopupWindow.isShowing()){
            mPopupWindow.showAsDropDown(v,0,5);
        }
    }
    //创建一个隐藏的方法
    public void onDismiss(){
        if (mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }
}
```