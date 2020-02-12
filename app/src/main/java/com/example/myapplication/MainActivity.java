package com.example.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.Interpreter;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystem;


public class MainActivity extends AppCompatActivity
         {
             public static TextView t;
             public static Button b;
             protected Interpreter tflite;

             @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ////////////////////////////////


                 t = (TextView) findViewById(R.id.textView);
                 b =  (Button) findViewById(R.id.button);

                 b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    hello();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
                //////////////////////////////////
            }

        });



    }
             ImageView i;
             Bitmap bitmap;

             Uri selectedfile;
             @Override
             protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                 super.onActivityResult(requestCode, resultCode, data);
                 if(requestCode == 123 && resultCode == RESULT_OK) {
                     selectedfile = data.getData(); //The uri with the location of the file
                     i = (ImageView) findViewById(R.id.imageView);

                     i.setImageURI( selectedfile);

                     try {
                         bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedfile);
                         i.setImageURI(selectedfile);

                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
             }
             /** Memory-map the model file in Assets. */




             public void hello() throws IOException {
                 Predictor p = new Predictor();

                 MappedByteBuffer tfmodel = p.loadModelFile(MainActivity.this,"C:\\Users\\Divyanshu\\AndroidStudioProjects\\MyApplication\\app\\src\\main\\res\\assets\\model.tflite");

                 try {

                     t.setText(p.OUtput(bitmap,tfmodel).toString());
                 } catch (IOException e) {
                     e.printStackTrace();
                 }



    }


}
