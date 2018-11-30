package com.example.nihit.googlesignin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CameraFragment extends Fragment implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    private LinearLayout qrCameraLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_camera, container, false);
        qrCameraLayout = (LinearLayout) v.findViewById(R.id.ll_qrcamera);
        mScannerView = new ZXingScannerView(getActivity().getApplicationContext());
        mScannerView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        qrCameraLayout.addView(mScannerView);
        return v;
    }
    public void onStart() {
        super.onStart();
        /*mScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result rawResult) {
                //Toast.makeText(getActivity(), "Contents = " + rawResult.getText() + ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
            }
        }); // Register ourselves as a handler for scan results.*/
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onResume() {
        super.onResume();
        /*mScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result rawResult) {
                //Toast.makeText(getActivity(), "Contents = " + rawResult.getText() + ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
            }
        }); // Register ourselves as a handler for scan results.*/
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }


    @Override
    public void handleResult(Result result) {
        MainActivity.userid=result.getText();
        //onBackPressed();
    }
}


