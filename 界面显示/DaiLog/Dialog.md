### Dialog的自定义使用和普通使用

1.自定义界面加载弹框的代码模版(模拟输入密码)

```
//第一次进入设置密码界面
private void showSetPasswordDialog() {
    //创建提示框
    final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
    //加载布局
    View view = View.inflate(getApplicationContext(), R.layout.home_setuopassword, null);
    //设置布局
    builder.setView(view);
    //找到控件
    mFirstpassword = (EditText) view.findViewById(R.id.home_setpassword_frist);
    mTwopassword = (EditText) view.findViewById(R.id.home_setpassword_two);
    mCanle = (Button) view.findViewById(R.id.home_setpassword_canle);
    mOk = (Button) view.findViewById(R.id.home_setpassword_ok);
    //设置点击事件
    mCanle.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //点击取消
            mDialog.dismiss();
        }
    });
    //点击确定
    mOk.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //获取到输入的密码
            String password = mFirstpassword.getText().toString().trim();
            String passwords = mTwopassword.getText().toString().trim();
            //判断是否为空
            if (TextUtils.isEmpty(password) || TextUtils.isEmpty(passwords)){
                Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();

            }else {
                //判断是否相等
               if (!password.equals(passwords)){
                   Toast.makeText(HomeActivity.this, "输入的密码不同", 		               Toast.LENGTH_SHORT).show();
                }else {
                   //保存密码
                   SpUitls.setString(getApplicationContext(),Constaints.PASSWORLD,passwords);
                   Toast.makeText(HomeActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                   //跳转页面
                    Intent intent = new Intent(HomeActivity.this,Setup1Activity.class);
                   startActivity(intent);
               }
            }
        }
    });
    mDialog = builder.show();
}


```

2.XML代码

```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:orientation="vertical">

    <TextView
        style="@style/tille"
        android:text="设置密码"/>

    <EditText
        android:id="@+id/home_setpassword_frist"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="10dp"
        android:layout_marginRight="10dp"
        android:layout_marginTop="5dp"
        android:hint="请输入密码"
        android:inputType="textPassword"
        android:textColor="#000"/>

    <EditText
        android:id="@+id/home_setpassword_two"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="10dp"
        android:layout_marginRight="10dp"
        android:hint="请再次输入密码"
        android:inputType="textPassword"
        android:textColor="#000"/>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">
        <Button
            android:id="@+id/home_setpassword_canle"
            android:background="@drawable/buttn_digo"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="取消"
            android:layout_marginLeft="5dp"/>

        <Button
            android:id="@+id/home_setpassword_ok"
            android:layout_marginLeft="5dp"
            android:background="@drawable/buttn_digo"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="确定"
            android:layout_marginRight="5dp"/>
    </LinearLayout>

</LinearLayout>
```

##### 2.普通的弹框

代码实例

```
 //弹出对话框
    AlertDialog.Builder builder = new AlertDialog.Builder(BlackNumberActivity.this);
    builder.setTitle("警告");
    builder.setMessage("是否删除此联系人");
    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
             ..........
                mDialog.dismiss();
            }else {
                Toast.makeText(BlackNumberActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }

        }
    });
    builder.setNeutralButton("取消",null);
    mDialog = builder.show();
}
```