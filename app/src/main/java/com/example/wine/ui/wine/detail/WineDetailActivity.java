package com.example.wine.ui.wine.detail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wine.R;
import com.example.wine.adapter.WineModel;

public class WineDetailActivity extends AppCompatActivity {

    private TextView nome, varietal, doc, safra, vinicola;
    private ImageView imageWine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_detail);

        // Referência dos elementos
        ImageView imageWine = findViewById(R.id.wine);
        TextView nome = findViewById(R.id.nome);
        TextView varietal = findViewById(R.id.varietal);
        TextView doc = findViewById(R.id.doc);
        TextView safra = findViewById(R.id.safra);
        TextView vinicola = findViewById(R.id.vinicola);

        // Recupera o objeto inteiro
        WineModel wine = (WineModel) getIntent().getSerializableExtra("wine");

        if (wine != null) {
            nome.setText(wine.getNome());
            varietal.setText(wine.getVarietal());
            doc.setText(wine.getDoc());
            safra.setText(wine.getSafra());
            vinicola.setText(wine.getVinicola());

            imageWine.setImageResource(R.drawable.wine);
        }
    }
}
