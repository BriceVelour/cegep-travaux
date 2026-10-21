package com.example.batpasta_débutant;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    public static ArrayList<Commande> commandes = new ArrayList<>();
    private String employeActuel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        employeActuel = getIntent().getStringExtra("employe");
        listView = findViewById(R.id.listViewCommandes);
        if (commandes.isEmpty()) {
            commandes.add(new Commande("Spaghetti", "Carbonara", "Petit", true, false, employeActuel));
        }
        updateList();
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Commande c = commandes.get(position);
            showDetails(c);
        });
        listView.setOnItemLongClickListener((adapterView, view, position, id) -> {
            confirmSuppression(position);
            return true;
        });
    }
    private void updateList() {
        ArrayList<String> affichage = new ArrayList<>();
        for (Commande c : commandes) {
            affichage.add("Commande #" + c.getId() +
                    " | Total: " + String.format("%.2f", c.getTotal()) +
                    "$ | Commis: " + c.getEmploye());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, affichage);
        listView.setAdapter(adapter);
    }
    private void showDetails(Commande c) {
        String message = "Pâtes: " + c.getTypePate() +
                "\nSauce: " + c.getSauce() +
                "\nFormat: " + c.getFormat() +
                "\nBreuvage: " + (c.hasBreuvage() ? "Oui" : "Non") +
                "\nDessert: " + (c.hasDessert() ? "Oui" : "Non") +
                "\nTotal: " + String.format("%.2f", c.getTotal()) + "$";
        new AlertDialog.Builder(this)
                .setTitle("Détails de la commande")
                .setMessage(message)
                .setPositiveButton("Fermer", null)
                .show();
    }
    private void confirmSuppression(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Supprimer la commande")
                .setMessage("Confirmer la suppression ?")
                .setPositiveButton("Oui", (dialog, which) -> {
                    commandes.remove(position);
                    updateList();
                    Toast.makeText(this, "Commande supprimée", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Non", null)
                .show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add) {
            Intent i = new Intent(this, AjoutCommandeActivity.class);
            i.putExtra("employe", employeActuel);
            startActivity(i);
            return true;
        }
        if (id == R.id.menu_logout) {
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onResume() {
        super.onResume();
        updateList();
    }
}
