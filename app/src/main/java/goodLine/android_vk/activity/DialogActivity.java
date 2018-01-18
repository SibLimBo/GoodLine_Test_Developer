package goodLine.android_vk.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import goodLine.android_vk.R;

public class DialogActivity  extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Context context;
    private Toolbar toolbar;
    private Intent intent;
    private int userID;
    private String photoid="s";
    private TextView first_name;
    private TextView last_name;
    private TextView screenname;
    private TextView sextxt;
    private TextView relationtxt;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        ImageView img;

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        intent = getIntent();
        long time = intent.getLongExtra(getString(R.string.last_seen), 0);
        toolbar.setTitle(intent.getCharSequenceExtra(getString(R.string.first_last_name)));


        first_name = (TextView) findViewById(R.id.first_name_person);
        first_name.setText(intent.getCharSequenceExtra("first_name_person"));

        last_name = (TextView) findViewById(R.id.last_name_person);
        last_name.setText(intent.getCharSequenceExtra("last_name_person"));

        screenname = (TextView) findViewById(R.id.screenname);
        screenname.setText(intent.getCharSequenceExtra("screenname"));

        int sex = intent.getIntExtra("sex", 0);
        switch (sex){
            case 1:
                  sextxt = (TextView) findViewById(R.id.sex);
                  sextxt.setText("Жен.");
                break;
            case 2:
                sextxt = (TextView) findViewById(R.id.sex);
                sextxt.setText("Муж.");
                break;
            case 0:
                sextxt = (TextView) findViewById(R.id.sex);
                sextxt.setText("-");
                break;
        }

        Log.d("sex1", String.valueOf(sex));

        int relation = intent.getIntExtra("relation", 0);
        switch (relation){
            case 1:
                relationtxt = (TextView) findViewById(R.id.relation);
                relationtxt.setText("не женат/не замужем");
                break;
            case 2:
                relationtxt = (TextView) findViewById(R.id.relation);
                relationtxt.setText("встречается");
                break;
            case 3:
                relationtxt = (TextView) findViewById(R.id.relation);
                relationtxt.setText("помолвлен(-на)");
                break;
            case 4:
                relationtxt = (TextView) findViewById(R.id.relation);
                relationtxt.setText("женат/замужем");
                break;
            case 5:
                relationtxt = (TextView) findViewById(R.id.relation);
                relationtxt.setText("Все сложно");
                break;
            case 6:
                relationtxt = (TextView) findViewById(R.id.relation);
                relationtxt.setText("В активном поиске");
                break;
            case 7:
                relationtxt = (TextView) findViewById(R.id.relation);
                relationtxt.setText("влюблен(-на)");
                break;
            case 8:
                relationtxt = (TextView) findViewById(R.id.relation);
                relationtxt.setText("в гражданском браке");
                break;
        }

        Log.d("relation", String.valueOf(relation));

        userID = intent.getIntExtra(getString(R.string.user_id), 0);
        photoid = intent.getStringExtra(getString(R.string.avatarPhoto));

        img = (ImageView) findViewById(R.id.Avatarphoto);
        Picasso.with(context).load(photoid).into(img);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Log.d("photoid", photoid);
         }

}