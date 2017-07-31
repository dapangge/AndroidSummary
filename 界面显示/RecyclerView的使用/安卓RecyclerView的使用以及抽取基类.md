

#### 安卓RecyclerView的使用以及抽取基类

**1.引入:**

在moudle中的build.gradle文件里，找到dependencies，添加关于recyclerView的引用：

```xml
compile 'com.android.support:recyclerview-v7:24.2.0'  //这里的版本需要和V7包相对
```

其实design包中含有Material Design相关的很多控件，其中也包括了recyclerView的代码，所以你也可以通过引用design包来使用recyclerView：

```xml
compile 'com.android.support:design:23.2.0'   //同样的版本需要一样
```

**2.在Xml中的使用:**

```
<android.support.v7.widget.RecyclerView
    android:id="@+id/rv"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    >
```

**3.在代码中的使用**:

* RecyclerView需要设置样式才能显示出数据:

```
new LinearLayoutManager(getApplicationContext()); //显示为listView样式
new GridLayoutManager(getApplicationContext(),3); //显示为Grid样式
//显示为横向的Grid  true为是否逆转
new GridLayoutManager(getApplicationContext(), 3, GridLayoutManager.HORIZONTAL, false); 
//显示为瀑布流 可以设置方向
new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
```

```
mRv = (RecyclerView) findViewById(R.id.rv); //找到控件
 //必须要设置layout样式才能使用
 mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
 mRv.setLayoutManager(mLinearLayoutManager);
```
* 可以给每个Item设置动画效果,可也自定义可以使用第三方:

我这里使用的是第三方,自定义的比较麻烦,下面是依赖地址:

```
compile 'jp.wasabeef:recyclerview-animators:2.2.6'
```

下面是代码中的使用:

```
 //可以给item设置动画   使用的是第三方动画库
mRv.setItemAnimator(new FadeInRightAnimator());
```

* RecyclerView必须自定义分割线,这里也是使用的第三方

  ```
  compile 'com.yqritc:recyclerview-flexibledivider:1.4.0'
  ```

```
//手动设置分割线    使用的第三方
mRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
        .color(Color.RED)  //颜色
        .size(2)		//大小
        .margin(0,2)	//距离
        .build());
```

**5.Adpater类代码**

 RecyclerView中的Adapter与ListView中的Adapter不同:这里直接加上了样式的选择.

```
public class myAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  
  //确定数据个数
    @Override
    public int getItemCount() {
        return mData.size();
    }
    
    @Override
     public int getItemViewType(int position) {
            //可能根据数据的字段来指定对应的type
            if(position%2==0){
                return TYPE_ONE;
            }else{
                return TYPE_TWO;
            }
    }
    //确定布局
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //当有多个Item样式时先进行判断
        if (viewType == TYPE_ONE){
            //加载布局
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            //创建ViewHolder对象
            MyViewHolder myHolederView = new MyViewHolder(inflate);
            //查找控件
            myHolederView.tv = (TextView) inflate.findViewById(tv);
            return myHolederView;
        }
        //显示不同的布局
        if (viewType == TYPE_TWO){
            //创建不同的ViewHolder
           return null;
        }
        return  null;
    }
    //设置值
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //有可能会有多种Item的显示样式,所有在设置值之前要进行判断
        if (getItemViewType(position) == TYPE_ONE){
            //设置值
            MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.tv.setText(mData.get(position));
        }
        if (getItemViewType(position) == position){
            //强转成不同的ViewHolder进行赋值
        }
        //设置Item的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件回调到Activity
                if (mListerent != null){
                    mListerent.onitemLister(v,position);
                }
            }
        });

    }
}
```

* RecyclerView中不提供Item的点击事件,这是只能在Adapter中设置,但为了更好的编辑代码,需要把点击事件回调到Activity中进行处理

  创建一个接口回调点击事件:

  ```
  //设置一个回调的接口
  interface  IRecyclerItemChangeListerent {
       void onitemLister(View v,int postion);
  }
  ```

**6.上面介绍是对RecyclerView的初步使用,在项目中为了减低耦合,需要对Adapter类进行抽取,下面代码进行了简单的抽取,可供参考使用:**

```
public abstract class BaseAdapterss<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<T> mData;                       //数据
    IRecyclerItemChangeListerent mListerent;  //回调接口

    //设置数据的方法
    public  void setData(ArrayList<T>  data ){
        this.mData = data;
    }
	//获取显示数量
    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }
    
    //设置点击事件借口回调
    public  void  setListerent(IRecyclerItemChangeListerent listerent){
        mListerent = listerent;
    }
    //设置一个回调的接口
    interface  IRecyclerItemChangeListerent {
        void onitemLister(View v, int postion);
    }
	//返回Item显示的不同布局
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //抽象方法,子类出具体实现
        return getLayout(parent,viewType);
    }
    //对Item的数据进行赋值
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            //抽象方法子类去具体实现
            setItemData(holder,position);
            //设置Item的点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件回调到Activity
                if (mListerent != null){
                    mListerent.onitemLister(v,position);
                }
            }
        });
    }
    //抽象方法,子类实现具体操作
    protected abstract void setItemData(RecyclerView.ViewHolder holder, int position);
    protected  abstract   RecyclerView.ViewHolder getLayout(ViewGroup parent, int viewType);
}
```

* 模拟子类继承

  ```
  //具体操作
  public class MyAdapters extends BaseAdapterss<String> {
      
      private static final int TYPE_ONE = 0;   //item的显示样式
      private static final int TYPE_TWO = 1;   //item的显示样式
      
      @Override
      public int getItemViewType(int position) {
          //可能根据数据的字段来指定对应的type
          if(position%2==0){
              return TYPE_ONE;
          }else{
              return TYPE_TWO;
          }
      }

      @Override
      protected void setItemData(RecyclerView.ViewHolder holder, int position) {
          //有可能会有多种Item的显示样式,所有在设置值之前要进行判断
          if (getItemViewType(position) == TYPE_ONE){
              //设置值
              MyViewHolder viewHolder = (MyViewHolder) holder;
              viewHolder.tv.setText(mData.get(position));
          }
          if (getItemViewType(position) == position){
              //强转成不同的ViewHolder进行赋值
          }
      }

      @Override
      protected RecyclerView.ViewHolder getLayout(ViewGroup parent, int viewType) {
          //当有多个Item样式时先进行判断
          if (viewType == TYPE_ONE){
              //加载布局
              View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, 								parent, false);
              //创建ViewHolder对象
              MyViewHolder myHolederView = new MyViewHolder(inflate);
              //查找控件
              myHolederView.tv = (TextView) inflate.findViewById(tv);
              return myHolederView;
          }
          //显示不同的布局
          if (viewType == TYPE_TWO){
              //创建不同的ViewHolder
              return null;
          }
          return  null;
      }
  }
  ```

如有不懂的可以下载Dome看看,地址:https://github.com/MSHAO1/RecyclerViewDome