package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.RenderScript;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button btnlogin;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String Password = etPassword.getText().toString();

                if(email.isEmpty()){
                    Toast.makeText( MainActivity.this, "Email Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                if(Password.isEmpty()){
                    Toast.makeText( MainActivity.this, "Email Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                AndroidNetworking.post("http://192.168.43.55/api.php")
                        .addBodyParameter("Email", email)
                        .addBodyParameter("Password", Password)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject json = response.getJSONObject("hasil");
                                    boolean sukses = json.getBoolean("sukses");
                                    if(sukses) {
                                        Toast.makeText(MainActivity.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(MainActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                            }
                        });






            }
        });
    }
}
