package topica.edu.vn.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import topica.edu.vn.karaoke.MainActivity;
import topica.edu.vn.karaoke.R;
import topica.edu.vn.model.Music;


public class MuisicAdapter extends ArrayAdapter<Music> {
    Activity context;
    int resource;
    @NonNull List<Music> objects;
    public MuisicAdapter(@NonNull Activity context, int resource, @NonNull List<Music> objects) {
        super(context, resource, objects);
        this.objects=objects;
        this.context=context;
        this.resource=resource;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View row=layoutInflater.inflate(this.resource,null);
        TextView txtMa=row.findViewById(R.id.txtMa);
        TextView txtTenBaiHat=row.findViewById(R.id.txtTenBaiHat);
        TextView txtCasi=row.findViewById(R.id.txtCasi);
        ImageButton btnLike=row.findViewById(R.id.btnLike);
        ImageButton btndisLike=row.findViewById(R.id.btndisLike);
         final Music music =this.objects.get(position);
        txtMa.setText(music.getMa());
        txtTenBaiHat.setText(music.getTenBaiHat());
        txtCasi.setText(music.getCaSi());
        if(music.isThich())
        {
            btnLike.setVisibility(View.INVISIBLE);
            btndisLike.setVisibility(View.VISIBLE);
        }
        else
        {
            btnLike.setVisibility(View.VISIBLE);
            btndisLike.setVisibility(View.INVISIBLE);
        }

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               xuLiThich(music);
            }
        });
        btndisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLiKHongThich(music);
            }
        });
        return  row;

    }

    private void xuLiKHongThich(Music music) {
        this.remove(music);
        ContentValues row=new ContentValues();
        row.put("YEUTHICH",0);
        MainActivity.database.update("ArirangSongList",
                row,"MABH=?",new String[]{music.getMa()});
    }

    private void xuLiThich(Music music) {
        ContentValues row=new ContentValues();
        row.put("YEUTHICH",1);
        MainActivity.database.update("ArirangSongList",
                row,"MABH=?",new String[]{music.getMa()});
    }


}
