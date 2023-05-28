package com.example.lab3_4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

public class ThirdActivity extends AppCompatActivity {

    private TextView textViewInfo;
    private Button buttonBack;
    private Button buttonSave;
    private TextView textViewEmail;
    private TextView textViewPhone;
    private ImageView imageViewPhoto;
    private TextView textViewSocialLink;

    private String name;
    private String size;
    private String tag;
    private String description;
    private String Author;
    private String saleRent;
    private String email;
    private String phone;
    private Uri photoUri;
    private String socialLink = "https://www.instagram.com/";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        textViewInfo = findViewById(R.id.textViewInfo);
        buttonBack = findViewById(R.id.buttonBack);
        buttonSave = findViewById(R.id.buttonSave);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewPhone = findViewById(R.id.textViewPhone);
        imageViewPhoto = findViewById(R.id.imageViewPhoto);
        textViewSocialLink = findViewById(R.id.textViewSocialLink);

        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("type");
            size = intent.getStringExtra("area");
            tag = intent.getStringExtra("location");
            description = intent.getStringExtra("price");
            Author = intent.getStringExtra("state");
            saleRent = intent.getStringExtra("saleRent");

            String infoText = "Наименование: " + name + "\n" +
                    "Размер: " + size + "\n" +
                    "Тэг: " + tag + "\n" +
                    "Описание: " + description + "\n" +
                    "Автор: " + Author + "\n" +
                    "Разрешение: " + saleRent + "\n";
            textViewInfo.setText(infoText);
        }

        textViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhoneNumber(phone);
            }
        });

        textViewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail(email);
            }
        });

        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        textViewSocialLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSocialLink(socialLink);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToJson();
            }
        });
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    private void composeEmail(String emailAddress) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + emailAddress));
        startActivity(intent);
    }

    private void openImagePicker() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openImageGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_GALLERY);
    }

    private void openSocialLink(String socialLink) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(socialLink));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageViewPhoto.setImageBitmap(imageBitmap);
            }
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            imageViewPhoto.setImageURI(selectedImage);
            photoUri = selectedImage;
        }
    }

    private void saveDataToJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", name);
            jsonObject.put("area", size);
            jsonObject.put("location", tag);
            jsonObject.put("price", description);
            jsonObject.put("state", Author);
            jsonObject.put("saleRent", saleRent);
            jsonObject.put("email", email);
            jsonObject.put("phone", phone);
            // Save photoUri as a string value
            jsonObject.put("photoUri", photoUri != null ? photoUri.toString() : null);
            jsonObject.put("socialLink", socialLink);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonString = jsonObject.toString();

        // Save jsonString to a .json file
        boolean success = saveJsonToFile(jsonString, "data.json");

        if (success) {
            Toast.makeText(this, "Данные успешно сохранены", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ошибка при сохранении данных", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean saveJsonToFile(String jsonString, String fileName) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}