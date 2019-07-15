package com.example.bbddaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_codigo, et_nombre, et_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.et_codigo = (EditText)findViewById(R.id.txt_cod);
        this.et_nombre = (EditText)findViewById(R.id.txt_nombre);
        this.et_precio = (EditText)findViewById(R.id.txt_precio);
    }

    public void registrar (View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bbdd = admin.getWritableDatabase();

        String codigo = this.et_codigo.getText().toString();
        String nombre = this.et_nombre.getText().toString();
        String precio = this.et_precio.getText().toString();

        if(!codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty()) {
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("precio", precio);

            bbdd.insert("articulos", null, registro);
            bbdd.close();

            this.et_codigo.setText("");
            this.et_nombre.setText("");
            this.et_precio.setText("");

            Toast.makeText(this,
                    "Producto registrado con exito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,
                    "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bbdd = admin.getWritableDatabase();

        String codigo = this.et_codigo.getText().toString();

        if(!codigo.isEmpty()) {
            Cursor fila = bbdd.rawQuery("select nombre, precio from articulos where codigo = "
                            + codigo, null);

            if(fila.moveToFirst()) {
                this.et_nombre.setText(fila.getString(0));
                this.et_precio.setText(fila.getString(1));
            } else {
                Toast.makeText(this, "No existe el articulo",
                        Toast.LENGTH_SHORT).show();
            }

            bbdd.close();
        } else {
            Toast.makeText(this, "Debes introducir el codigo del articulo",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bbdd = admin.getWritableDatabase();

        String codigo = this.et_codigo.getText().toString();

        if(!codigo.isEmpty()) {
            int cantidad = bbdd.delete("articulos", "codigo="
                    + codigo, null);
            bbdd.close();

            this.et_codigo.setText("");
            this.et_precio.setText("");
            this.et_nombre.setText("");

            if(cantidad == 1) {
                Toast.makeText(this, "Articulo eliminado exitosamente",
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "El articulo no existe",
                        Toast.LENGTH_SHORT).show();
            }
        } else  {
            Toast.makeText(this, "Debes introducir el codigo del articulo",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void modificar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bbdd = admin.getWritableDatabase();

        String codigo =  this.et_codigo.getText().toString();
        String nombre = this.et_nombre.getText().toString();
        String precio = this.et_precio.getText().toString();

        if(!codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty()) {
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("precio", precio);

            int cantidad = bbdd.update("articulos", registro,
                    "codigo= " + codigo, null);
            bbdd.close();

            if(cantidad == 1) {
                Toast.makeText(this, "El registro se ha modificado correctamente",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El articulo no existe",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes de llenar todos los campos",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
