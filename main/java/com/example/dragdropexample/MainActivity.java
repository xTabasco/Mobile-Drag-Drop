package com.example.dragdropexample;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewDrag, imageViewDrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewDrag = findViewById(R.id.imageViewDrag);
        imageViewDrop = findViewById(R.id.imageViewDrop);

        imageViewDrag.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(imageViewDrag);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            imageViewDrag.setVisibility(View.INVISIBLE); // Bild verstecken beim ziehen
            return true;
        });

        imageViewDrop.setOnDragListener((v, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(0x30000000);
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(0xFFFFFFFF);
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(0xFFFFFFFF);
                    if (!event.getResult()) {
                        imageViewDrag.setVisibility(View.VISIBLE); // sichtbar machen bei Fehlschlag
                    }
                    return true;
                case DragEvent.ACTION_DROP:
                    View view = (View) event.getLocalState();
                    if (view == imageViewDrag) {
                        imageViewDrop.setImageResource(R.drawable.bild3filled); // Bild ersetzen
                        Toast.makeText(MainActivity.this, "Dropped", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                default:
                    break;
            }
            return false;
        });
    }
}
