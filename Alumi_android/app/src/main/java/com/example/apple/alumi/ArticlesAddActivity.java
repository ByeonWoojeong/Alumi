package com.example.apple.alumi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import cz.msebera.android.httpclient.Header;

public class ArticlesAddActivity extends AppCompatActivity {
    private EditText mEditAddSubject;
    private EditText mEditAddDescription;
    private Button mBtnSend;

    private ImageView mImagePhoto;

    private static final int REQUEST_TAKE_ALBUM = 1;
    private static final int REQUEST_TAKE_PHOTO = 2;

    public static String SERVER_HOST = ConstValues.SERVER_IP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_add);

        //바인딩
        mEditAddSubject = (EditText)findViewById(R.id.addSubject);
        mEditAddDescription = (EditText)findViewById(R.id.addDescription);
        mBtnSend = (Button)findViewById(R.id.btnSend);
        mImagePhoto = (ImageView)findViewById(R.id.photo);
    }

    public void mOnClickBackground(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditAddSubject.getWindowToken(), 0);

    }

    public void mOnClickSendArticle(View view){

        //put(서버 파라미터, )
        RequestParams params = new RequestParams();
        params.put("title", mEditAddSubject.getText().toString());
        params.put("userName", "tom");
        params.put("content", mEditAddDescription.getText().toString());

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(SERVER_HOST+"/article_add", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Intent intent = new Intent(ArticlesAddActivity.this, ArticlesListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                ArticlesAddActivity.this.finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    //갤러리
    public void mOnClickChooseImage(View view){
        this.getAlbum();
    }

    public void getAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_TAKE_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {

                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    mImagePhoto.setVisibility(View.VISIBLE);
                    mImagePhoto.setImageBitmap(imageBitmap);

                    this.uploadPhoto(imageBitmap);
                } catch (Exception e) {

                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mImagePhoto.setVisibility(View.VISIBLE);
                mImagePhoto.setImageBitmap(imageBitmap);

                this.uploadPhoto(imageBitmap);
            }
        }
    }

    private void uploadPhoto(Bitmap bitmap){

        File photoFile = this.bitmapToFile(bitmap);

        RequestParams params = new RequestParams();
        try {
            params.put("photo", photoFile);
        } catch (Exception e) {

        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ConstValues.SERVER_IP + "/upload", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // String body = new String(responseBody);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    //카메라
    public void mOnClickCamera(View view){
        this.takePhoto();
    }

    public void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    private File bitmapToFile(Bitmap bitmap){
        //File photoFile = new File("");

        File fileCacheItem = new File("temp.jpg");
        OutputStream out = null;

        try
        {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return fileCacheItem;
    }
}
