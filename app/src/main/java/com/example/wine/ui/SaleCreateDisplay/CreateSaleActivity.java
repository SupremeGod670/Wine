// Caminho: com.example.wine.ui.SaleCreateDisplay/CreateSaleActivity.java
package com.example.wine.ui.SaleCreateDisplay;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R; // Para acessar os recursos de layout e IDs
import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.data.datasource.client.ClientLocalDataSource;
import com.example.wine.data.datasource.representative.RepresentativeLocalDataSource;
import com.example.wine.data.datasource.sale.SaleLocalDataSource;
import com.example.wine.data.datasource.saleitem.SaleItemLocalDataSource;
import com.example.wine.data.datasource.wine.WineLocalDataSource;
// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
// NOVO IMPORT: Você precisa importar o AppUserLocalDataSource aqui!
import com.example.wine.data.datasource.user.AppUserLocalDataSource;
// CORREÇÃO DOS IMPORTS DE UI: Se seus arquivos estiverem realmente em 'ui.model' e 'ui.adapter', use estes imports.
// Caso contrário, se você manteve 'ui.SaleCreateDisplay' para esses modelos/adapters, DESCOMENTE os imports originais
// e COMENTE estas linhas abaixo:
import com.example.wine.ui.SaleCreateDisplay.SaleItemAdapter; // Exemplo: com.example.wine.ui.adapter
import com.example.wine.ui.SaleCreateDisplay.ClientSpinnerModel;   // Exemplo: com.example.wine.ui.model
import com.example.wine.ui.SaleCreateDisplay.RepresentativeSpinnerModel; // Exemplo: com.example.wine.ui.model
import com.example.wine.ui.SaleCreateDisplay.WineSaleItemModel;      // Exemplo: com.example.wine.ui.model
// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

import com.example.wine.domain.model.Wine; // Note que este já estava correto
import com.example.wine.ui.viewmodel.CreateSaleViewModel; // Note que este já estava correto

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreateSaleActivity extends AppCompatActivity implements SaleItemAdapter.OnItemActionListener {

    private CreateSaleViewModel viewModel;

    private Spinner spinnerClient;
    private Spinner spinnerRepresentative;
    private EditText editTextFreight;
    private EditText editTextDeliveryMethod;
    private RecyclerView recyclerViewSaleItems;
    private TextView textViewSaleTotal;
    private Button buttonAddWineItem;
    private Button buttonSaveSale;
    private ImageButton backButton;

    private SaleItemAdapter saleItemAdapter;
    private List<ClientSpinnerModel> clientsList = new ArrayList<>();
    private List<RepresentativeSpinnerModel> representativesList = new ArrayList<>();
    private List<Wine> availableWines = new ArrayList<>();

    private String selectedClientId;
    private String selectedRepresentativeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sale);

        // 1. Inicialização dos DataSources e ViewModel
        ClientLocalDataSource clientLocalDataSource = new ClientLocalDataSource(AppDatabaseProvider.getDatabase(this).clientDao());
        RepresentativeLocalDataSource representativeLocalDataSource = new RepresentativeLocalDataSource(AppDatabaseProvider.getDatabase(this).representativeDao());
        WineLocalDataSource wineLocalDataSource = new WineLocalDataSource(AppDatabaseProvider.getDatabase(this).wineDao());
        SaleLocalDataSource saleLocalDataSource = new SaleLocalDataSource(AppDatabaseProvider.getDatabase(this).saleDao());
        SaleItemLocalDataSource saleItemLocalDataSource = new SaleItemLocalDataSource(AppDatabaseProvider.getDatabase(this).saleItemDao());
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        // AQUI ESTAVA FALTANDO A INSTANCIAÇÃO DO AppUserLocalDataSource
        AppUserLocalDataSource appUserLocalDataSource = new AppUserLocalDataSource(AppDatabaseProvider.getDatabase(this).appUserDao());
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

        viewModel = new ViewModelProvider(this, new CreateSaleViewModel.Factory(
                clientLocalDataSource,
                representativeLocalDataSource,
                wineLocalDataSource,
                saleLocalDataSource,
                saleItemLocalDataSource,
                appUserLocalDataSource)) // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                // AGORA, PASSAMOS O SEXTO ARGUMENTO (appUserLocalDataSource) PARA A FACTORY
                .get(CreateSaleViewModel.class);

        // 2. Inicialização dos componentes da UI (Views)
        spinnerClient = findViewById(R.id.spinnerClient);
        spinnerRepresentative = findViewById(R.id.spinnerRepresentative);
        editTextFreight = findViewById(R.id.editTextFreight);
        editTextDeliveryMethod = findViewById(R.id.editTextDeliveryMethod);
        recyclerViewSaleItems = findViewById(R.id.recyclerViewSaleItems);
        textViewSaleTotal = findViewById(R.id.textViewSaleTotal);
        buttonAddWineItem = findViewById(R.id.buttonAddWineItem);
        buttonSaveSale = findViewById(R.id.buttonSaveSale);
        backButton = findViewById(R.id.back_button);

        // 3. Configuração da RecyclerView para itens da venda
        // Nota: Garanta que SaleItemAdapter está no pacote correto, senão o import acima precisará ser ajustado.
        saleItemAdapter = new SaleItemAdapter(new ArrayList<>(), this);
        recyclerViewSaleItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSaleItems.setAdapter(saleItemAdapter);

        // 4. Observar dados do ViewModel
        viewModel.clients.observe(this, clients -> {
            clientsList = clients;
            ArrayAdapter<ClientSpinnerModel> clientAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, clientsList);
            clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerClient.setAdapter(clientAdapter);
        });

        viewModel.representatives.observe(this, representatives -> {
            representativesList = representatives;
            ArrayAdapter<RepresentativeSpinnerModel> repAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, representativesList);
            repAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerRepresentative.setAdapter(repAdapter);
            // Opcional: selecione o primeiro item se a lista não for vazia
            if (!representativesList.isEmpty() && selectedRepresentativeId == null) {
                spinnerRepresentative.setSelection(0);
                selectedRepresentativeId = representativesList.get(0).getId();
            }
        });

        viewModel.availableWines.observe(this, wines -> {
            availableWines = wines;
            Log.d("WineDebug", "CreateSaleActivity: Vinhos observados no ViewModel: " + availableWines.size());
            // Opcional: Log para depuração de vinhos, como discutimos anteriormente
            // android.util.Log.d("CreateSaleActivity_Wine", "Vinhos recebidos na Activity: " + availableWines.size());
        });

        viewModel.currentSaleItems.observe(this, items -> {
            saleItemAdapter.updateItems(items);
        });

        viewModel.totalSaleValue.observe(this, total -> {
            // Adiciona o frete ao total exibido
            double freight = 0.0;
            try {
                String freightText = editTextFreight.getText().toString();
                if (!freightText.isEmpty()) {
                    freight = Double.parseDouble(freightText);
                }
            } catch (NumberFormatException e) {
                // Ignora, frete permanece 0.0
            }
            textViewSaleTotal.setText(String.format(Locale.getDefault(), "Total: R$ %.2f", total + freight));
        });

        viewModel.saleSaved.observe(this, isSaved -> {
            if (isSaved) {
                Toast.makeText(this, "Venda salva com sucesso!", Toast.LENGTH_SHORT).show();
                finish(); // Fecha a Activity após salvar
            } else {
                Toast.makeText(this, "Erro ao salvar venda. Verifique os dados.", Toast.LENGTH_LONG).show();
            }
        });

        // 5. Configurar Listeners
        spinnerClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClientId = ((ClientSpinnerModel) parent.getItemAtPosition(position)).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedClientId = null;
            }
        });

        spinnerRepresentative.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRepresentativeId = ((RepresentativeSpinnerModel) parent.getItemAtPosition(position)).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedRepresentativeId = null;
            }
        });

        editTextFreight.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.calculateTotal(); // Recalcula o total ao mudar o frete
            }
        });

        buttonAddWineItem.setOnClickListener(v -> showAddWineDialog());

        buttonSaveSale.setOnClickListener(v -> {
            double freight = 0.0;
            try {
                String freightText = editTextFreight.getText().toString();
                if (!freightText.isEmpty()) {
                    freight = Double.parseDouble(freightText);
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Valor de frete inválido. Usando 0.00.", Toast.LENGTH_SHORT).show();
            }
            String deliveryMethod = editTextDeliveryMethod.getText().toString();

            viewModel.saveSale(selectedClientId, selectedRepresentativeId, freight, deliveryMethod);
        });

        backButton.setOnClickListener(v -> onBackPressed()); // Ação do botão de voltar

        // 6. Carregar dados iniciais
        viewModel.loadInitialData();
    }

    // Implementação da interface SaleItemAdapter.OnItemActionListener
    @Override
    public void onQuantityChanged() {
        viewModel.calculateTotal();
    }

    @Override
    public void onPriceChanged() {
        viewModel.calculateTotal();
    }

    @Override
    public void onItemRemoved(int position) {
        viewModel.removeWineItem(position);
    }

    // Método para mostrar o diálogo de adição de vinho
    private void showAddWineDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicionar Vinho à Venda");

        // Criar um Spinner para os vinhos disponíveis
        final Spinner wineSpinner = new Spinner(this);
        ArrayAdapter<Wine> wineAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, availableWines);
        wineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wineSpinner.setAdapter(wineAdapter);

        builder.setView(wineSpinner);

        builder.setPositiveButton("Adicionar", (dialog, which) -> {
            Wine selectedWine = (Wine) wineSpinner.getSelectedItem();
            if (selectedWine != null) {
                viewModel.addWineItem(selectedWine);
            } else {
                Toast.makeText(this, "Nenhum vinho selecionado ou disponível.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}