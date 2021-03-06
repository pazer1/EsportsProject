package com.dipdoo.esportsproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dipdoo.esportsproject.ChatMessage.MessageItem;
import com.dipdoo.esportsproject.FirebaseBoard.FirebaseConnect;
import com.dipdoo.esportsproject.R;
import com.dipdoo.esportsproject.Util.UtcToLocal;

public class WriteDialog extends AlertDialog.Builder implements View.OnClickListener {

    AlertDialog alertDialog;
    private boolean isTitle,isContent;
    EditText titleEt,contentEt,nickNameEt;
    String game_id;
    Toast myToast;

    public WriteDialog(@NonNull Context context, int themeResId,String game_id) {
        super(context, themeResId);
        this.game_id = game_id;
        View view = LayoutInflater.from(getContext()).inflate(R.layout.write_fragment,null);
        setView(view);
    }

    @Override
    public AlertDialog.Builder setView(View view) {
        setViewContent(view);
        return super.setView(view);
    }

    public void setViewContent(View view){
        ImageView backbutton = view.findViewById(R.id.write_back);
        View postView = view.findViewById(R.id.write_post_text);
        final TextView watcher_et = view.findViewById(R.id.wrtie_text_watcher);
        titleEt = view.findViewById(R.id.wrtie_title_et);
        contentEt = view.findViewById(R.id.write_content_et);
        nickNameEt = view.findViewById(R.id.wirte_nickname);
        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                watcher_et.setText(s.length()+"/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        backbutton.setOnClickListener(this);
        postView.setOnClickListener(this);
    }

    @Override
    public AlertDialog create() {
        alertDialog = super.create();
        alertDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        return alertDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.write_back:
                alertDialog.dismiss();
                break;
            case R.id.write_post_text:
                if(titleEt.getText().toString().equals("") || contentEt.getText().toString().equals("") || nickNameEt.getText().toString().equals("")){
                    myToast = Toast.makeText(getContext(), "빈 항목을 채워주세용", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                MessageItem messageItem = new MessageItem(game_id,nickNameEt.getText().toString(),titleEt.getText().toString(),contentEt.getText().toString(), UtcToLocal.getUtcToLocal().getTimeMDHM());
                FirebaseConnect.getFirebaseConnect().writeToBoard(messageItem,getContext());
                alertDialog.dismiss();
                break;
        }
    }
}
