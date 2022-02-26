package topica.edu.vn.karaoke;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import topica.edu.vn.adapter.MuisicAdapter;
import topica.edu.vn.model.Music;

public class MainActivity extends AppCompatActivity {

    ListView lvDanhSachBaiHat;
    ArrayList<Music> dsBaiHat;
    MuisicAdapter adapterBaiHatGoc;

    ListView lvBaiHatYeuThich;
    ArrayList<Music> dsBaiHatYeuThich;
    MuisicAdapter adapterBaiHatYeuThich;
    TabHost tabHost;

    public static String DATABASE_NAME="Arirang.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    public  static SQLiteDatabase database=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xuLySaoChepCoSoDuLieuTuAssetsVaoHeThong();
        addControls();
        addEvents();

    }

    private void xuLySaoChepCoSoDuLieuTuAssetsVaoHeThong() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try
            {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }     catch (Exception e)       {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }

    private void CopyDataBaseFromAsset() {
        try {
            InputStream inputStream;
            inputStream=getAssets().open(DATABASE_NAME);
            String outFileName=layDuongDanLuuChu();
            File f=new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if(!f.exists()) {
                f.mkdir();
            }
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte []buffer=new byte[1024];
            int lenth;
            while ((lenth= inputStream.read(buffer))>0)
            {
                myOutput.write(buffer,0,lenth);
            }
            myOutput.flush();
            myOutput.close();
            inputStream.close();

        }
        catch (Exception ex)
        {
            Log.e("Loi Sao Chep",ex.toString());
        }

    }
    private String layDuongDanLuuChu()
    {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }

    private void addEvents() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equalsIgnoreCase("t1"))
                {
                    xuLiBaiHatGoc();
                }
                else if(tabId.equalsIgnoreCase("t2"));
                {
                    xuLiBayHatYeuThich();
                }

            }
        });
    }

    private void xuLiBayHatYeuThich() {
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor=database.query("ArirangSongList",null,
                "YEUTHICH=?",new String[]{"1"},null,
                null,null);
        dsBaiHatYeuThich.clear();
        while (cursor.moveToNext())
        {
            String mabh=cursor.getString(0);
            String tenbh=cursor.getString(1);
            String casi=cursor.getString(3);
            int yeuThich=cursor.getInt(5);
            Music music=new Music();
            music.setMa(mabh);
            music.setTen(tenbh);
            music.setCaSi(casi);
            music.setThich(yeuThich==1);
            dsBaiHatYeuThich.add(music);

        }
        cursor.close();
    adapterBaiHatYeuThich.notifyDataSetChanged();

    }

    private void xuLiBaiHatGoc() {
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor=database.query("ArirangSongList",null,null,null,null,
           null,null);
        dsBaiHat.clear();
        while (cursor.moveToNext())
        {
            String mabh=cursor.getString(0);
            String tenbh=cursor.getString(1);
            String casi=cursor.getString(3);
            int yeuThich=cursor.getInt(5);
            Music music=new Music();
            music.setMa(mabh);
            music.setTen(tenbh);
            music.setCaSi(casi);
            music.setThich(yeuThich==1);
            dsBaiHat.add(music);

        }
        cursor.close();
        adapterBaiHatGoc.notifyDataSetChanged();
    }

    private void addControls() {

         tabHost= findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1=tabHost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Bài Hát Gốc");
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2=tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Bai Hát yêu thích");
        tabHost.addTab(tab2);




       dsBaiHat=new ArrayList<Music>();
       lvDanhSachBaiHat=findViewById(R.id.lvDanhSachBaiHat);
        adapterBaiHatGoc=new MuisicAdapter(MainActivity.this,R.layout.item,dsBaiHat);
        lvDanhSachBaiHat.setAdapter(adapterBaiHatGoc);

        dsBaiHatYeuThich=new ArrayList<Music>();
       lvBaiHatYeuThich=findViewById(R.id.lvDanhSachBaiHatYeuThich);
       adapterBaiHatYeuThich=new MuisicAdapter(MainActivity.this,R.layout.item,dsBaiHatYeuThich);
       lvBaiHatYeuThich.setAdapter(adapterBaiHatYeuThich);
    }


}
