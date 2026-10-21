package com.example.batpasta_pro;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class LoginActivity extends Activity {
    EditText editNumero, editMotDePasse;
    Button btnConnexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editNumero = findViewById(R.id.editNumero);
        editMotDePasse = findViewById(R.id.editMotDePasse);
        btnConnexion = findViewById(R.id.btnConnexion);
        btnConnexion.setOnClickListener(v -> {
            String numero = editNumero.getText().toString();
            String mdp = editMotDePasse.getText().toString();
            Employe employe = EmployeRepository.authentifier(numero, mdp);
            if (employe != null) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("employe", employe.getNumeroEmploye());
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Erreur d'identification", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
