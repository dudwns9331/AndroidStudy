package yj.p.challenge03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

// 제발
public class MainActivity extends AppCompatActivity {

    ScrollView upview;
    ScrollView downview;

    ImageView imageView;
    ImageView upimage;
    ImageView downimage;

    int count;

    BitmapDrawable bitmapDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upview = findViewById(R.id.upscroll);
        downview = findViewById(R.id.downscroll);

        upview.setHorizontalScrollBarEnabled(true);
        downview.setHorizontalScrollBarEnabled(true);

        imageView = findViewById(R.id.upimageView);
        upimage = findViewById(R.id.upimageView);
        downimage = findViewById(R.id.downimageView);

        Resources res = getResources();
        bitmapDrawable = (BitmapDrawable) res.getDrawable(R.drawable.cat);
        int bitmapWidth = bitmapDrawable.getIntrinsicWidth();
        int bitmapHeight = bitmapDrawable.getIntrinsicHeight();

        imageView.setImageDrawable(bitmapDrawable);
        imageView.getLayoutParams().width = bitmapWidth;
        imageView.getLayoutParams().height = bitmapHeight;


    }

    public void changeImage() {
        Resources res = getResources();
        if(count == 0) {
            bitmapDrawable = (BitmapDrawable) res.getDrawable(R.drawable.cat1);
            count++;
        }
        else if (count == 1) {
            bitmapDrawable = (BitmapDrawable) res.getDrawable(R.drawable.cat2);
            count++;
        }
        else if (count == 2) {
            bitmapDrawable = (BitmapDrawable) res.getDrawable(R.drawable.cat3);
            count++;
        }
        else if (count == 3) {
            bitmapDrawable = (BitmapDrawable) res.getDrawable(R.drawable.cat);
            count = 0;
        }

        int bitmapWidth = bitmapDrawable.getIntrinsicWidth();
        int bitmapHeight = bitmapDrawable.getIntrinsicHeight();

        imageView.setImageDrawable(bitmapDrawable);
        imageView.getLayoutParams().width = bitmapWidth;
        imageView.getLayoutParams().height = bitmapHeight;

    }


    public void UpButton(View view) {
        imageView.setImageDrawable(null);
        imageView = upimage;
        changeImage();
    }

    public void DownButton(View view) {
        imageView.setImageDrawable(null);
        imageView = downimage;
        changeImage();
    }
}