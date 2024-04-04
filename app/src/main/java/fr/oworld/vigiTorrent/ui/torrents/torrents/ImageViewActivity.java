package fr.oworld.vigiTorrent.ui.torrents.torrents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.os.Bundle;
import android.widget.ImageView;

import fr.oworld.vigiTorrent.R;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        ImageView imageView = findViewById(R.id.imageView);

        imageView.setImageDrawable(AppCompatResources.getDrawable(this, getIntent().getIntExtra("image",0)));
    }
}