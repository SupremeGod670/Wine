package com.example.wine.ui.wine;


import androidx.appcompat.app.AppCompatActivity;

public class WineFormActivity extends AppCompatActivity {
/*
    private EditText edtName, edtHarvest, edtType, edtNotes, edtPairing;
    private Button btnSave;
    private WineRepository repository;
    private int wineId = -1;
    private WineEntity wine;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_clientes1);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        open = findViewById(R.id.open);

        edtName = findViewById(R.id.edtName);
        edtHarvest = findViewById(R.id.edtHarvest);
        edtType = findViewById(R.id.edtType);
        edtNotes = findViewById(R.id.edtNotes);
        edtPairing = findViewById(R.id.edtPairing);
        btnSave = findViewById(R.id.btnSave);
        repository = new WineRepository(this);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.vinhos) {
                    Intent intent = new Intent(WineFormActivity.this, WineListActivity.class);
                    startActivity(intent);
                }

                drawerLayout.close();

                return false;
            }
        });

        wineId = getIntent().getIntExtra("wine_id", -1);
        if (wineId != -1) {
            wine = repository.getById(wineId);
            edtName.setText(wine.name);
            edtHarvest.setText(wine.harvest);
            edtType.setText(wine.type);
            edtNotes.setText(wine.notes);
            edtPairing.setText(wine.pairing);
        } else {
            wine = new WineEntity();
        }

        btnSave.setOnClickListener(v -> {
            wine.name = edtName.getText().toString();
            wine.harvest = edtHarvest.getText().toString();
            wine.type = edtType.getText().toString();
            wine.notes = edtNotes.getText().toString();
            wine.pairing = edtPairing.getText().toString();

            if (wineId == -1) {
                repository.insert(wine);
                Toast.makeText(this, "Vinho cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                repository.update(wine);
                Toast.makeText(this, "Vinho atualizado com sucesso", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }

 */
}