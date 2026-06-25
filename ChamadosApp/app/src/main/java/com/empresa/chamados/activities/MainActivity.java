package com.empresa.chamados.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.empresa.chamados.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_novo_chamado) {
                startActivity(new Intent(this, NovoChamadoActivity.class));
            } else if (id == R.id.nav_listagem) {
                startActivity(new Intent(this, ListaChamadosActivity.class));
            } else if (id == R.id.nav_estatisticas) {
                startActivity(new Intent(this, EstatisticasActivity.class));
            } else if (id == R.id.nav_sobre) {
                startActivity(new Intent(this, SobreActivity.class));
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        findViewById(R.id.btn_novo_chamado).setOnClickListener(v -> {
            startActivity(new Intent(this, NovoChamadoActivity.class));
        });

        findViewById(R.id.btn_lista_chamados).setOnClickListener(v -> {
            startActivity(new Intent(this, ListaChamadosActivity.class));
        });

        findViewById(R.id.btn_estatisticas).setOnClickListener(v -> {
            startActivity(new Intent(this, EstatisticasActivity.class));
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
