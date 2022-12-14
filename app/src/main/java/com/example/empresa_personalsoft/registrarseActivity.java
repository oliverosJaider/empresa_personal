package com.example.empresa_personalsoft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class registrarseActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    EditText jetusuario, jetcorreo, jetnombre, jetclave;
    CheckBox jcbactivo;
    String usuario, correo, nombre, clave, url;
    RequestQueue rq;
    JsonRequest jrq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        getSupportActionBar().hide();
        jetusuario=findViewById(R.id.etusuario);
        jetnombre=findViewById(R.id.etnombre);
        jetcorreo=findViewById(R.id.etcorreo);
        jcbactivo=findViewById(R.id.cbactivo);
        jetclave=findViewById(R.id.etclave);
        rq = Volley.newRequestQueue(this);//conexion a internet
    }
    public void Consultar(View View){
        usuario=jetusuario.getText().toString();
        if (usuario.isEmpty()){
            Toast.makeText(this, "Usuario requerido", Toast.LENGTH_SHORT).show();
            jetusuario.requestFocus();
        }
        else {
            url = "http://172.16.62.255:80/webServices/consulta.php?usr='" + usuario;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);

    }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
        jetusuario.requestFocus();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);//posicion 0 del arreglo....
            jetnombre.setText(jsonObject.optString("nombre"));
            jetcorreo.setText(jsonObject.optString("correo"));
            jetclave.setText(jsonObject.optString("clave"));
            if (jsonObject.optString("activo").equals("si"))
                jcbactivo.setChecked(true);
            else
                jcbactivo.setChecked(false);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
