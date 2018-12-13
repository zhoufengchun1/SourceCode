package com.example.qq1296821114.Fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.qq1296821114.Activity.ShareActivity;
import com.example.qq1296821114.Data.Bean.Letter;
import com.example.qq1296821114.R;
import com.example.qq1296821114.Utils.HttpUtil;
import com.example.qq1296821114.Utils.RealPathFromUriUtils;
import com.example.qq1296821114.Utils.Util;
import com.example.qq1296821114.View.view.KeyboardLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.richeditor.RichEditor;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SendLetterFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.undo)
    Button undo;
    @BindView(R.id.re_undo)
    Button reUndo;
    @BindView(R.id.fore_color)
    Button foreColor;
    @BindView(R.id.illustrate)
    Button illustrate;
    @BindView(R.id.editor)
    RichEditor mEditor;
    Unbinder unbinder;
    @BindView(R.id.view)
    Button view1;
    @BindView(R.id.keyboardLayout)
    KeyboardLayout keyboardLayout;

    @BindView(R.id.send_text)
    Button sendText;
    @BindView(R.id.overstriking)
    RelativeLayout overstriking;
    @BindView(R.id.italic)
    RelativeLayout italic;
    @BindView(R.id.underline)
    RelativeLayout underline;
    @BindView(R.id.flash_left)
    RelativeLayout flashLeft;
    @BindView(R.id.align_center)
    RelativeLayout alignCenter;
    @BindView(R.id.flash_right)
    RelativeLayout flashRight;
    @BindView(R.id._send_text_set)
    LinearLayout SendTextSet;
    @BindView(R.id.send_off_text_set)
    Button sendOffTextSet;

    String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private int scroll_height = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout._send_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);
        init_view();
        init_onClickListener();
        return view;
    }


    private void init_view() {
        //初始化编辑高度
        mEditor.setEditorHeight(200);
        //初始化字体大小
        mEditor.setEditorFontSize(22);
        //初始化字体颜色
        mEditor.setEditorFontColor(Color.BLACK);
        //mEditor.setEditorBackgroundColor(Color.BLUE);

        //初始化内边距
        mEditor.setPadding(10, 10, 10, 10);
        //设置编辑框背景，可以是网络图片
//        mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
//        mEditor.setBackground(getContext().getResources().getDrawable(R.drawable.ic_background));
        // mEditor.setBackgroundColor(Color.BLUE);
        //设置默认显示语句
        mEditor.setPlaceholder("Insert text here...");
        //设置编辑器是否可用
        mEditor.setInputEnabled(true);

        addLayoutListener();

    }

    private void init_onClickListener() {
        undo.setOnClickListener(this);
        reUndo.setOnClickListener(this);
        overstriking.setOnClickListener(this);
        italic.setOnClickListener(this);
        foreColor.setOnClickListener(this);
        flashLeft.setOnClickListener(this);
        alignCenter.setOnClickListener(this);
        flashRight.setOnClickListener(this);
        illustrate.setOnClickListener(this);
        sendText.setOnClickListener(this);
        sendOffTextSet.setOnClickListener(this);
        underline.setOnClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                boolean writeExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0 && writeExternalStorage && readExternalStorage) {
                    getImage();
                } else {
                    Toast.makeText(getContext(), "请设置必要权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void getImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                    888);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 888);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 888:
                    if (data != null) {
                        String realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(getContext(), data.getData());
                        mEditor.insertImage(realPathFromUri, realPathFromUri + "\" style=\"max-width:40%");
//                        mEditor.insertImage(realPathFromUri, realPathFromUri + "\" style=\"max-width:100%;max-height:100%");
                    } else {
                        Toast.makeText(getContext(), "图片损坏，请重新选择", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    public void addLayoutListener() {
        keyboardLayout.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight) {
                if (keyboardHeight != scroll_height && isActive) {
                    view1.setHeight(scroll_height);
                }
                scroll_height = keyboardHeight;
                if (isActive) {
                    view1.setVisibility(View.VISIBLE);
                } else {
                    view1.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.undo:
//                mEditor.undo();
//                mEditor.focusEditor();
                mEditor.setBold();
                break;
            case R.id.re_undo:
//                mEditor.redo();
//                mEditor.focusEditor();
                mEditor.setItalic();
                break;
            case R.id.overstriking:
                mEditor.setBold();
                break;
            case R.id.italic:
                mEditor.setItalic();
                break;
            case R.id.flash_left:
                mEditor.focusEditor();
                mEditor.setAlignLeft();
                break;
            case R.id.underline:
                mEditor.focusEditor();
                mEditor.setUnderline();
                break;
            case R.id.align_center:
                mEditor.focusEditor();
                mEditor.setAlignCenter();
                break;
            case R.id.flash_right:
                mEditor.focusEditor();
                mEditor.setAlignRight();
                break;
            case R.id.fore_color:
                mEditor.clearFocusEditor();
                Toast.makeText(getContext(), "选择颜色", Toast.LENGTH_SHORT).show();
                Log.e("text", mEditor.getHtml() + "\n" + mEditor.getHeight() + " " + mEditor.getContentHeight() + " " +
                        mEditor.getScrollX());

                Bitmap bitmap = Bitmap.createBitmap(mEditor.getWidth(),
                        mEditor.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                mEditor.draw(canvas);
//                new Test_Dialog(getContext(),bitmap).show();
//                Util.getBitmapList(mEditor.getHtml());
                Util.saveBitmapToLocal("bitmap", bitmap,0);
                final Letter letter = new Letter(mEditor.getHtml());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        letter.sendData(getContext());
                    }
                }).start();

                Intent intent = new Intent();
                intent.setClass(getContext(), ShareActivity.class);
                startActivity(intent);
                break;
            case R.id.illustrate:
                mEditor.focusEditor();
                ActivityCompat.requestPermissions(getActivity(), mPermissionList, 100);
                break;
            case R.id.send_text:
                SendTextSet.setVisibility(View.VISIBLE);
                break;
            case R.id.send_off_text_set:
                SendTextSet.setVisibility(View.GONE);
                break;
        }
    }
}
