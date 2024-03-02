package org.chinh.file;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dataset.IShapeManager;
import dataset.ShapeManager;
import data.model.Circle;
import data.model.Shape;
import data.model.Rect;
import data.model.Triangle;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public Button  btnDisplay;
    public Button btnSortByPerimeter;
    public Button btnFindLargestArea;
    EditText myInputText;
    TextView responseText;

    private String filename = "input.txt";
    private String filepath = "vncoder.vm";
    File myInternalFile;

    private IShapeManager shapeManager = new ShapeManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        ContextWrapper contextWrapper = new ContextWrapper(
                getApplicationContext());
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, filename);

    }
    private void initView() {
        myInputText = (EditText) findViewById(R.id.myInputText);
        responseText = (TextView) findViewById(R.id.responseText);
        btnDisplay = (Button) findViewById(R.id.btnDisplay);
        btnSortByPerimeter = (Button) findViewById(R.id.btnSortByPerimeter);
        btnFindLargestArea = (Button) findViewById(R.id.btnFindLargestArea);
        btnDisplay.setOnClickListener(this);
        btnSortByPerimeter.setOnClickListener(this);
        btnFindLargestArea.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDisplay) {
            displayData();
        } else if (v.getId() == R.id.btnSortByPerimeter) {
            sortShapesByPerimeter();
        } else if (v.getId() == R.id.btnFindLargestArea) {
            findShapeWithLargestArea();
        }
        readShapeList();
    }

    private void sortShapesByPerimeter() {
        shapeManager.sort();
        responseText.setText("Đã sắp xếp các hình theo thứ tự tăng dần của chu vi!");
    }

    private void findShapeWithLargestArea() {
        Shape largestShape = shapeManager.findMax();
        if (largestShape != null) {
            String result = "Hình có diện tích lớn nhất là: ";
            if (largestShape instanceof Circle) {
                result += "Hình tròn, diện tích = " + largestShape.dienTich();
            } else if (largestShape instanceof Rect) {
                result += "Hình chữ nhật, diện tích = " + largestShape.dienTich();
            } else if (largestShape instanceof Triangle) {
                result += "Hình tam giác, diện tích = " + largestShape.dienTich();
            }
            responseText.setText(result);
        } else {
            responseText.setText("Không có hình nào được tìm thấy.");
        }
    }


    private void saveData() {
        try {
            FileOutputStream fos = new FileOutputStream(myInternalFile);
            fos.write(myInputText.getText().toString().getBytes());
            fos.close();
            myInputText.setText("");
            responseText.setText("Đã được lưu vào bộ nhớ trong");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayData() {
        String myData = "";
        try {
            FileInputStream fis = new FileInputStream(myInternalFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                myData += strLine;
            }
            fis.close();
            myInputText.setText(myData);
            responseText.setText("Lấy dữ liệu từ bộ nhớ trong");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readShapeList() {
        AssetManager assetManager = getAssets();

        try {
            InputStream inputStream = assetManager.open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.startsWith("#rect")){
                    readRect(line);
                }
                if(line.startsWith("#circle")){
                    readCircle(line);
                }
                if(line.startsWith("#triangle")){
                    readTriangle(line);
                }
            }
            inputStream.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readCircle(String line) {
        String arr[] = line.split(" ");
        double radius = Double.parseDouble(arr[1]);
        shapeManager.add(new Circle(radius));
    }

    private void readRect(String line) {
        String arr[] = line.split(" ");
        double a = Double.parseDouble(arr[1]);
        double b = Double.parseDouble(arr[2]);
        shapeManager.add(new Rect(a, b));
    }
    private void readTriangle(String line) {
        String arr[] = line.split(" ");
        double a = Double.parseDouble(arr[1]);
        double b = Double.parseDouble(arr[2]);
        double c = Double.parseDouble(arr[3]);
        shapeManager.add(new Triangle(a, b, c));

    }

}