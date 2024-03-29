package com.photogram.inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.photogram.feed.subir_foto;
import com.photogram.moderador.FeedModerador;
import com.photogram.R;
import com.photogram.servicesnetwork.ApiEndPoint;
import com.photogram.servicesnetwork.VolleyS;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuario extends AppCompatActivity {
    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtNombres;
    private EditText txtApellidoPaterno;
    private EditText txtApellidoMaterno;
    private EditText txtCorreo;
    private Button btnAceptar;
    private Button btnCancelar;
    private String estado;
    private String estadoCuenta;

    private static String TAG = "RegistrarUsuario"; //Para comentar los mensajes en las bitácoras


    private VolleyS volley;
    protected RequestQueue fRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        txtUsername = findViewById(R.id.nombreUsuario);
        txtPassword = findViewById(R.id.Contraseña);
        txtNombres = findViewById(R.id.nombres);
        txtApellidoPaterno = findViewById(R.id.apellidoPaterno);
        txtApellidoMaterno = findViewById(R.id.apellidoMaterno);
        txtCorreo = findViewById(R.id.correo);
        btnAceptar = findViewById(R.id.buttonAceptar);
        btnCancelar = findViewById(R.id.buttonCancelarRegistro);
        estado = "Hey! Estoy usando photogram";
        estadoCuenta = "true";



        volley = VolleyS.getInstance(RegistrarUsuario.this);
        fRequestQueue = volley.getRequestQueue();
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuarioRequest();
            }
        });
        SharedPreferences myPreferences = getPreferences(Context.MODE_PRIVATE);
        String token = myPreferences.getString("TOKEN", "unknown");
        if (!token.equals("unknown")) {
            Toast.makeText(RegistrarUsuario.this, "TK: " + token, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegistrarUsuario.this, FeedModerador.class);
            RegistrarUsuario.this.startActivity(intent);

        }
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), Iniciar_Sesion.class);
                startActivityForResult(intent, 0);
                finish();

            }
        });


    }

    private void registrarUsuarioRequest() {
        btnAceptar.setEnabled(false);
        btnAceptar.setVisibility(View.INVISIBLE);
        Map<String, String> param = new HashMap<>();

        param.put("username", txtUsername.getText().toString());
        param.put("password", txtPassword.getText().toString());
        param.put("nombre", txtNombres.getText().toString());
        param.put("apellidoP", txtApellidoPaterno.getText().toString());
        param.put("apellidoM", txtApellidoMaterno.getText().toString());
        param.put("correo", txtCorreo.getText().toString());
        param.put("estado", estado);
        param.put("estadoCuenta", "true");


        JSONObject jsonObject = new JSONObject(param);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiEndPoint.registrarUsuario, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(RegistrarUsuario.this, "Registrado",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistrarUsuario.this, subir_foto.class);
                        Bundle b = new Bundle();
                        b.putBoolean("key", true);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrarUsuario.this, "No se que haces",
                                Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "" + error.getMessage());
                        btnAceptar.setEnabled(true);
                        btnAceptar.setVisibility(View.VISIBLE);
                    }
                });
        volley.addToQueue(jsonObjectRequest);
    }

}