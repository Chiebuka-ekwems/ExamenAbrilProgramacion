package com.politecnicomalaga.tienda;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.politecnicomalaga.tienda.controller.Controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Controlador.getSingleton(this).listarTodos();
    }


    //Acciones

    //El botón de listar clientes
    public void listCliente(View v) {
        Controlador.getSingleton(this).listarClientes();
    }

    public void listProduct(View v) {
        Controlador.getSingleton(this).listarTodos();
    }

    public void reaccionar(String error) {
        ListView miListaEnPantalla = findViewById(R.id.listaProductos);
        ArrayAdapter<String> miAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        if (error == null || error.isEmpty()) {
            List<Map<String, String>> datos = Controlador.getSingleton(this).getData();

            if (datos != null && !datos.isEmpty()) {
                for (Map<String, String> item : datos) {
                    String resultado = "";

                    // Comprobamos qué tipo de dato estamos leyendo
                    if ("cliente".equals(item.get("tipo"))) {
                        resultado = "DNI: " + item.get("dni") +
                                " | " + item.get("nombre") + " " + item.get("apellidos") +
                                " | Tel: " + item.get("telefono");
                    } else {
                        resultado = "Cod: " + item.get("c") +
                                " | " + item.get("d") +
                                " | Precio: " + item.get("p") + "€" +
                                " | Stock: " + item.get("s");
                    }
                    miAdapter.add(resultado);
                }
            } else {
                miAdapter.add("No hay datos disponibles.");
            }
        } else {
            miAdapter.add("Error: " + error);
        }

        miListaEnPantalla.setAdapter(miAdapter);
    }
}