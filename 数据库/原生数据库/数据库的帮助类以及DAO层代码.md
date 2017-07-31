### 数据库的帮助类以及DAO层代码

#### 1.帮助类

```
public class BlackNumberHelper extends SQLiteOpenHelper{
    public BlackNumberHelper(Context context) {
        super(context, Constdb.DBNAME, null, Constdb.DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqL) {
        //创建黑名单数据库
        sqL.execSQL("create table "+Constdb.BLACKNUMBERTABLE +" ( "+Constdb._ID+"  integer primary key autoincrement, "+Constdb._PHONE+" varchar(20), "+Constdb._MODE+" varchar(2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
```

```
public class Constdb {

    public static  final  String  DBNAME  = "blacknumber.db";//数据库的名字
    public static  final  int  DBVERSION = 1;   //数据库的版本号

    public  static  final  String  BLACKNUMBERTABLE = "blacknumbertable";
    public  static  final  String  _ID  = "_id";
    public static  final  String  _PHONE = "_phone";
    public  static final  String  _MODE  ="_mode";
}
```

#### 2.DAO层代码

```
public class BlackNumberDao {
    private final BlackNumberHelper mHelper;
    //创建一个黑名单的dao层
    public BlackNumberDao(Context context) {
        mHelper = new BlackNumberHelper(context);
    }

    //创建一个添加的方法
    public boolean inster(String phone, String mode) {
        //获取一个可写的数据库
        SQLiteDatabase dp = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constdb._PHONE, phone);
        values.put(Constdb._MODE, mode);
        long insert = dp.insert(Constdb.BLACKNUMBERTABLE, null, values);
        dp.close();
        if (insert != -1) {
            return true;
        } else {
            return false;
        }

    }

    //创建一个删除的方法
    public boolean delete(String phone) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int delete = db.delete(Constdb.BLACKNUMBERTABLE, Constdb._PHONE + "=?", new String[]{phone});
        db.close();
        if (delete != 0) {
            return true;
        } else {
            return false;
        }
    }

    //修改的方法
    public boolean upDate(String phone, String mode) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constdb._PHONE, phone);
        values.put(Constdb._MODE, mode);
        int update = db.update(Constdb.BLACKNUMBERTABLE, values, Constdb._PHONE + "=?", new String[]{phone});
        db.close();
        if (update != 0) {
            return true;
        } else {
            return false;
        }
    }

    //查询的方法
    public List<BlackNumberBean> query(){
        ArrayList<BlackNumberBean> lists = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor query = db.query(Constdb.BLACKNUMBERTABLE, new String[]{Constdb._PHONE, Constdb._MODE}, null, null, null, null, null);
        while (query.moveToNext()){
            BlackNumberBean bean = new BlackNumberBean();
            //为每个对象赋值
            bean.phone= query.getString(query.getColumnIndex(Constdb._PHONE));
            bean.mode = query.getString(query.getColumnIndex(Constdb._MODE));
            lists.add(bean);
        }
        db.close();
        query.close();
        return lists;
    }

    //创建查询一个的方法
    public String find(String phone){
        String mode = null;
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor query = db.query(Constdb.BLACKNUMBERTABLE, new String[]{Constdb._MODE}, Constdb._PHONE+"=?", new String[]{phone}, null, null, null);
        while (query.moveToNext()){
             mode = query.getString(query.getColumnIndex(Constdb._MODE));

        }
        return mode;
    }

    //分页查询数据库
    public List<BlackNumberBean> findPart(int count ,int startIndex){
        List<BlackNumberBean> lists=new ArrayList<>();
        // 1得到一个可读的数据库,
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select "+Constdb._PHONE+" , "+Constdb._MODE+"  from "+Constdb.BLACKNUMBERTABLE+" order by _id desc limit ? offset ? ", new String[]{String.valueOf(count), String.valueOf(startIndex)});
        while (cursor.moveToNext()){
            BlackNumberBean bean=new BlackNumberBean();
            bean.mode = cursor.getString(cursor.getColumnIndex(Constdb._MODE));
            bean.phone = cursor.getString(cursor.getColumnIndex(Constdb._PHONE));
            lists.add(bean);
        }
        return lists;
    }

    //查询和数据库的个数
    public int getCount(){
        int count=0;
        // 1得到一个可读的数据库
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from "+Constdb.BLACKNUMBERTABLE+" ",null);
        while (cursor.moveToNext()){
            count= cursor.getInt(0);
        }
        return count;

    }
```