package com.example.batpasta_pro;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;
public class AjoutCommandeActivity extends Activity {
    private Spinner spinnerPate, spinnerSauce, spinnerFormat;
    private CheckBox cbBreuvage, cbDessert;
    private TextView txtTotal;
    private Button btnAjouter;
    private String employeActuel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_commande);
        spinnerPate = findViewById(R.id.spinnerPate);
        spinnerSauce = findViewById(R.id.spinnerSauce);
        spinnerFormat = findViewById(R.id.spinnerFormat);
        cbBreuvage = findViewById(R.id.cbBreuvage);
        cbDessert = findViewById(R.id.cbDessert);
        txtTotal = findViewById(R.id.txtTotal);
        btnAjouter = findViewById(R.id.btnAjouter);
        employeActuel = getIntent().getStringExtra("employe");
        ArrayAdapter<String> adapterPates = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Prix.getPates());
        adapterPates.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPate.setAdapter(adapterPates);
        ArrayAdapter<String> adapterSauces = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Prix.getSauces());
        adapterSauces.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSauce.setAdapter(adapterSauces);
        ArrayAdapter<String> adapterFormats = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Prix.getFormats());
        adapterFormats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFormat.setAdapter(adapterFormats);
        AdapterView.OnItemSelectedListener recalcul = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calculerTotal();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };
        spinnerPate.setOnItemSelectedListener(recalcul);
        spinnerSauce.setOnItemSelectedListener(recalcul);
        spinnerFormat.setOnItemSelectedListener(recalcul);
        cbBreuvage.setOnCheckedChangeListener((b, c) -> calculerTotal());
        cbDessert.setOnCheckedChangeListener((b, c) -> calculerTotal());
        btnAjouter.setOnClickListener(v -> {
            String pate = (String) spinnerPate.getSelectedItem();
            String sauce = (String) spinnerSauce.getSelectedItem();
            String format = (String) spinnerFormat.getSelectedItem();
            boolean breuvage = cbBreuvage.isChecked();
            boolean dessert = cbDessert.isChecked();
            if (pate == null || sauce == null || format == null) {
                Toast.makeText(this, "Veuillez choisir une pâte, une sauce et un format", Toast.LENGTH_SHORT).show();
                return;
            }
            Commande c = new Commande(pate, sauce, format, breuvage, dessert, employeActuel);
            MainActivity.commandes.add(c);
            Toast.makeText(this, "Commande ajoutée : " + String.format("%.2f", c.getTotal()) + " $", Toast.LENGTH_SHORT).show();
            finish();
        });
        calculerTotal();
    }
    private void calculerTotal() {
        String pate = (String) spinnerPate.getSelectedItem();
        String sauce = (String) spinnerSauce.getSelectedItem();
        String format = (String) spinnerFormat.getSelectedItem();
        if (pate == null || sauce == null || format == null) {
            txtTotal.setText("Total: --");
            return;
        }
        double total = Prix.getPrixPate(pate) + Prix.getPrixSauce(sauce) + Prix.getPrixFormat(format);
        if (cbBreuvage.isChecked()) total += Prix.BREUVAGE;
        if (cbDessert.isChecked()) total += Prix.DESSERT;
        total *= 1.15; // Taxes
        txtTotal.setText("Total: " + String.format("%.2f", total) + " $");
    }
}
