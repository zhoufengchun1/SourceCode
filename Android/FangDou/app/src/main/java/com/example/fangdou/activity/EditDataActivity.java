package com.example.fangdou.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fangdou.JDBC;
import com.example.fangdou.R;
import com.example.fangdou.utils.CircleImageView;
import com.example.fangdou.utils.nav_bar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static butterknife.ButterKnife.bind;

public class EditDataActivity extends AppCompatActivity
{

    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_SMALL_PICTURE = 3;
    @BindView(R.id.h_head)
    CircleImageView hHead;
    @BindView(R.id.changeName)
    nav_bar changeName;
    @BindView(R.id.intro)
    nav_bar intro;
    @BindView(R.id.sex)
    nav_bar sex;
    @BindView(R.id.birth)
    nav_bar birth;
    @BindView(R.id.location)
    nav_bar location;
    private Unbinder bind;
    //调用照相机返回图片文件
    private File tempFile;
    //最后显示的图片文件
    private String mFile;
    private String userName;
    private Connection connection;
    private Statement statement;
    private SharedPreferences sharedPreferences;
    private String baseCode;
    private Bitmap photo;
    private int count;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdata);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("DIY语音合成");
        toolbar.setBackgroundColor(getResources().getColor(R.color.color_default));
        ThemeActivity.setStatusBarColor(EditDataActivity.this, getResources().getColor(R.color.color_default));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        bind(this);
        bind = bind(this);

        sharedPreferences = getSharedPreferences("UserInfo.xml", MODE_PRIVATE);
        handler = new Handler();
        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                hHead.setImageBitmap(photo);
            }
        };
        new Thread()
        {
            @Override
            public void run()
            {
                userName = sharedPreferences.getString("User_Name", "方小逗");
                fetchHeadImg();
                if (photo == null)
                {
                    photo = BitmapFactory.decodeResource(EditDataActivity.this.getResources(), R.drawable.my_user_default);
                }
                handler.post(runnable);
                baseCode = encodeImage(photo);
            }
        }.start();
        Log.d("haha", "3");
        //28041830@


        //fetchHeadImg();
    }

    /**
     * 点击事件
     */


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        bind.unbind();
    }

    @OnClick({R.id.h_head, R.id.changeName, R.id.intro, R.id.sex, R.id.birth, R.id.location})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.h_head:
                Dialog changeHeadDialog = new Dialog(this);
                View changeHeadDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_changehead, null);
                changeHeadDialog.setContentView(changeHeadDialogView);
                TextView changeFromAlbum = changeHeadDialogView.findViewById(R.id.change_from_album);
                TextView changeFromCamera = changeHeadDialogView.findViewById(R.id.change_from_camera);
                TextView cancel = changeHeadDialogView.findViewById(R.id.cancel);
                changeHeadDialog.show();
                changeFromAlbum.setOnClickListener(v ->
                {
                    changeHeadDialog.dismiss();
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
                });
                changeFromCamera.setOnClickListener(v ->
                {
                    changeHeadDialog.dismiss();
                    //用于保存调用相机拍照后所生成的文件
                    getPicFromCamera();

                });
                cancel.setOnClickListener(v -> changeHeadDialog.dismiss());
                break;
            case R.id.changeName:
                break;
            case R.id.intro:
                break;
            case R.id.sex:
                break;
            case R.id.birth:
                break;
            case R.id.location:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode)
        {
            case CAMERA_REQUEST_CODE:   //调用相机后返回
                if (resultCode == RESULT_OK)
                {
                    //用相机返回的照片去调用剪裁也需要对Uri进行处理
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    {
                        Uri contentUri = FileProvider.getUriForFile(this, getPackageName() + ".FileProvider", tempFile);
                        startPhotoZoom(contentUri);//开始对图片进行裁剪处理
                    } else
                    {
                        startPhotoZoom(Uri.fromFile(tempFile));//开始对图片进行裁剪处理
                    }
                }
                break;
            case ALBUM_REQUEST_CODE:    //调用相册后返回
                if (resultCode == RESULT_OK)
                {
                    Uri uri = intent.getData();
                    startPhotoZoom(uri); // 开始对图片进行裁剪处理
                }
                break;
            case CROP_SMALL_PICTURE:  //调用剪裁后返回
                if (intent != null)
                {
                    // 让刚才选择裁剪得到的图片显示在界面上
                    photo = BitmapFactory.decodeFile(mFile);
                    baseCode = encodeImage(photo);
                    if (sharedPreferences.getBoolean("isLogin", false))
                    {
                        new Thread()
                        {
                            public void run()
                            {
                                Looper.prepare();
                                try
                                {
                                    EditDataActivity.this.count = statement.executeUpdate("update user set head_img=" + "'" + baseCode + "'"
                                            + "where user_name=" + "'"
                                            + userName + "'");
                                    if (count == 1)
                                    {
                                        Toast.makeText(EditDataActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                                        handler.post(runnable);
                                    } else
                                        Toast.makeText(EditDataActivity.this, "修改失败。", Toast.LENGTH_SHORT).show();
                                } catch (SQLException e)
                                {
                                    Toast.makeText(EditDataActivity.this, "未找到用户！", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                } finally
                                {
                                    Looper.loop();
                                }
                            }
                        }.start();


                    }
                } else
                {
                    Log.e("data", "data为空");
                }
                break;
        }
    }

    private void getPicFromCamera()
    {
        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".png");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, getPackageName() + ".FileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            Log.e("getPicFromCamera", contentUri.toString());
        } else
        {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    protected void startPhotoZoom(Uri uri)
    {

        if (uri == null)
        {
            Log.i("tag", "The uri is not exist.");
        }
//        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", false);
        File out = new File(getPath());
        if (!out.getParentFile().exists())
        {
            out.getParentFile().mkdirs();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    //裁剪后的地址
    public String getPath()
    {
        //resize image to thumb
        if (mFile == null)
        {
            mFile = Environment.getExternalStorageDirectory() + "/" + "wode/" + "outtemp.png";
        }
        return mFile;
    }


    public String saveImage(String name, Bitmap bmp)
    {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists())
        {
            appDir.mkdir();
        }
        String fileName = name + ".png";
        File file = new File(appDir, fileName);
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String encodeImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //读取文件到流
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        //100即为不压缩
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public void setConnection()
    {
        connection = JDBC.connSql();
        try
        {
            statement = connection.createStatement();
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public synchronized void fetchHeadImg()
    {
        setConnection();
        try
        {
            ResultSet resultSet = statement.executeQuery("select head_img from user where user_name=" + "'" + userName + "'");
            while (resultSet.next())
            {
                baseCode = resultSet.getString("head_img");
                if (baseCode != null)
                {
                    break;
                }
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        if (baseCode == null)
            return;
        byte[] bytes = Base64.decode(baseCode, Base64.DEFAULT);
        photo = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Log.d("haha", "2");

    }


}



