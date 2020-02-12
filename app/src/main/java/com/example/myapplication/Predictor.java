package com.example.myapplication;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.widget.TextView;

import org.tensorflow.lite.Interpreter;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

class Predictor extends Activity {

    public List<Float> OUtput(Bitmap image,MappedByteBuffer tfmodel) throws IOException {

        Interpreter interpreter = new Interpreter(tfmodel);
        Bitmap photo = Bitmap.createScaledBitmap(image, 50, 50, false);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        byte[][] photo1 = new byte[50][50];
        int i=0;
        for(int j=0;j<50;j++)
        {
            for(int k=0;k<50;k++)
            {
                photo1[j][k]=byteArray[i];
                i++;
            }
        }
        List<Float> list = null;
        interpreter.run(photo1,list);
        return list;


    }
    public MappedByteBuffer loadModelFile(Activity activity, String MODEL_FILE) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(MODEL_FILE);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }


}