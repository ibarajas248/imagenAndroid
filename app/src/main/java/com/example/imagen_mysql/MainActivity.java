package com.example.imagen_mysql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnCamara;
    ImageView imgView;
    String rutaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnCamara= (Button) findViewById(R.id.btncamara);
        imgView=(ImageView) findViewById(R.id.imageView);

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();

            }
        });
    }

    private void abrirCamara(){
        //para capturar la imagen
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(intent,1);
            File imagenArchivo = null;
            try{
                imagenArchivo = crearImagen();
            }catch (IOException ex){
                Log.e("error",ex.toString());

            }
            if (imagenArchivo != null){
                Uri fotoUri = FileProvider(this, "com.example.imagen_mysql.fileprovider", imagenArchivo);
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // El método onActivityResult se llama cuando una actividad que has iniciado devuelve un resultado.
        // requestCode es el código que se utiliza para identificar la solicitud.
        // resultCode es el código que indica el resultado de la operación.
        // data contiene la intención original que fue enviada.
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Comprueba si el requestCode es igual a 1 y si el resultCode es RESULT_OK.
            // Esto significa que la actividad que iniciaste con el requestCode 1 ha finalizado con éxito.
            Bundle extras = data.getExtras();
            // Recupera los extras del Intent data. Los extras suelen contener datos adicionales o resultados de la actividad.
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            // Recupera un objeto Bitmap del Bundle extras. En este caso, se espera que "data" sea la clave que almacena el Bitmap.

            imgView.setImageBitmap(imgBitmap);
            // Establece el Bitmap recuperado en un ImageView llamado imgView para mostrar la imagen capturada en la pantalla.

        }
    }

    private File crearImagen() throws IOException {
        String nombreImagen ="foto_";
        File directorio =getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen=File.createTempFile(nombreImagen,".jpg",directorio);
        rutaImagen = imagen.getAbsolutePath();
        return imagen;
    }


}