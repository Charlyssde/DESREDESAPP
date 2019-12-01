package com.photogram.Inicio;

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
import com.photogram.Moderador.FeedModerador;
import com.photogram.R;
import com.photogram.pojo.LoginPOJO;
import com.photogram.servicesnetwork.ApiEndPoint;
import com.photogram.servicesnetwork.JSONAdapter;
import com.photogram.servicesnetwork.VolleyS;
import com.photogram.servicesnetwork.persistence.Default;

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
    private Boolean estadoCuenta;

    private String TAG = "Registrar_Usuario";

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
        btnCancelar = findViewById(R.id.button2);
        estado = "Hey! Estoy usando photogram";
        estadoCuenta = true;



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
            finish();
        }

    }

    private void registrarUsuarioRequest() {
        btnAceptar.setEnabled(false);
        Map<String, String> param = new HashMap<>();
        param.put("username", txtUsername.getText().toString());
        param.put("password", txtPassword.getText().toString());
        param.put("username", txtUsername.getText().toString());
        param.put("password", txtPassword.getText().toString());
        param.put("username", txtUsername.getText().toString());
        param.put("password", txtPassword.getText().toString());
        param.put("username", txtUsername.getText().toString());
        param.put("password", txtPassword.getText().toString());

        JSONObject jsonObject = new JSONObject(param);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiEndPoint.login, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LoginPOJO result = JSONAdapter.loginAdapter(response);
                        Default d = Default.getInstance(RegistrarUsuario.this);
                        d.setToken(result.getToken());

                        SharedPreferences myPreferences = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor myEditor = myPreferences.edit();
                        myEditor.putString("TOKEN", "" + result.getToken());
                        myEditor.commit();

                        Toast.makeText(RegistrarUsuario.this, "TK: " + d.getToken(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegistrarUsuario.this, FeedModerador.class);
                        RegistrarUsuario.this.startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Testing network");
                btnAceptar.setEnabled(true);
            }
        });

        volley.addToQueue(jsonObjectRequest);

    }
}
