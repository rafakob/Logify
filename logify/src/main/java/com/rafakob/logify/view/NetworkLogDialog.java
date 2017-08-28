package com.rafakob.logify.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rafakob.logify.R;
import com.rafakob.logify.repository.entity.NetworkLog;

public class NetworkLogDialog extends DialogFragment {
    private TextView method, path, duration, timestamp, host, status,
            requestParams, requestHeaders, requestBody, responseHeaders, responseBody;

    public static NetworkLogDialog newInstance(NetworkLog networkLog) {
        NetworkLogDialog fragment = new NetworkLogDialog();
        Bundle args = new Bundle();
        args.putSerializable("log", networkLog);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = (getActivity()).getLayoutInflater().inflate(R.layout.fragment_network_log, null);
        NetworkLog log = (NetworkLog) getArguments().getSerializable("log");

        method = view.findViewById(R.id.method);
        timestamp = view.findViewById(R.id.timestamp);
        path = view.findViewById(R.id.path);
        host = view.findViewById(R.id.host);
        duration = view.findViewById(R.id.duration);
        status = view.findViewById(R.id.status);
        requestHeaders = view.findViewById(R.id.request_headers);
        requestBody = view.findViewById(R.id.request_body);
        requestParams = view.findViewById(R.id.request_params);
        responseHeaders = view.findViewById(R.id.response_headers);
        responseBody = view.findViewById(R.id.response_body);

        if (log != null) {
            method.setText(log.getRequestMethod());
            timestamp.setText(log.getTimestamp().toString());
            path.setText(log.getPath());
            host.setText(log.getHost());
            duration.setText(log.getDuration().toString() + "ms");
            status.setText(log.getResponseCode() + " " + log.getResponseMessage());
            requestHeaders.setText(log.getRequestHeaders().toString());
            requestBody.setText(log.getRequestBody());
            requestParams.setText(log.getRequestParams().toString());
            responseHeaders.setText(log.getResponseHeaders().toString());
            responseBody.setText(log.getResponseBody());


        }
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
    }
}
