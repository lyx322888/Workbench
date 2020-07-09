package com.zpz.common.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.zpz.common.R;


/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * 确认框
 */
public class ConfirmDialog extends DialogFragment {

    private DialogListener listener;
    private TextView tv_content;
    private String content;


    public static ConfirmDialog newInstance(String content ) {
        ConfirmDialog uploadAccreditationDialog = new ConfirmDialog();
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        uploadAccreditationDialog.setArguments(bundle);
        return uploadAccreditationDialog;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = getArguments().getString("content");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.confirm_dialog, container, false);
        tv_content = view.findViewById(R.id.tv_content);
        tv_content.setText(content);
        view.findViewById(R.id.tv_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.dialogClickListener();
                }
                dismiss();
            }
        });
        view.findViewById(R.id.tv_no_warn_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setCancelable(false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public void setDialogListener(DialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
    }

    public interface DialogListener {
        void dialogClickListener();
    }
}
