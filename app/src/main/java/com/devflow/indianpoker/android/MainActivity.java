package com.devflow.indianpoker.android;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.Strategy;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    private Message mActiveMessage;
    private MessageListener mMessageListener;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int TTL_IN_SECONDS = 3 * 60;
    private ArrayList<String> mMessageList;
    private int hello = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        mMessageListener = new MessageListener() {
            @Override
            public void onFound(Message message) {
                String messageAsString = new String(message.getContent());
                mMessageList.add(messageAsString);
                Log.i(TAG, "Found message: " + messageAsString);
            }
            @Override
            public void onLost(Message message) {
                String messageAsString = new String(message.getContent());
                mMessageList.add(messageAsString);
                Log.i(TAG, "Lost sight of message: " + messageAsString);
            }
        };

        final EditText text = (EditText) findViewById(R.id.content);
        final TextView textView = (TextView) findViewById(R.id.text);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_hi = text.getText().toString();
                if(mGoogleApiClient != null && mGoogleApiClient.isConnected() && hello%2 == 0){
                    mActiveMessage = new Message(text_hi.getBytes());
                    publish();
                }
                else if(mGoogleApiClient != null && mGoogleApiClient.isConnected() && hello%2 == 1){
                    subscribe();
                    if(mMessageList != null)
                        textView.setText(mMessageList.get(0));
                }
                hello++;
            }
        });
        buildGoogleApiClient();
    }

    private void buildGoogleApiClient() {
        if (mGoogleApiClient != null) {
            return;
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        return;
    }

    @Override
    public void onConnectionSuspended(int i) {
        return;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        return;
    }

    private void publish(){
        Log.i(TAG, "Publishing");
        Nearby.Messages.publish(mGoogleApiClient, mActiveMessage)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()){
                            Log.i(TAG, "published success");
                        } else {
                            // Show a snackbar with a publish failed message
                            Log.i(TAG, "published fail");
                        }
                    }
                });
    }

    private void unpublish() {
        if (mActiveMessage != null) {
            Nearby.Messages.unpublish(mGoogleApiClient, mActiveMessage);
            mActiveMessage = null;
        }
    }

    private void subscribe() {
        Log.i(TAG, "Subscribing");

        Nearby.Messages.subscribe(mGoogleApiClient, mMessageListener)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            // Subscribed successfully, notify the user.
                            Log.i(TAG, "subscribe success");
                        } else {
                            Log.i(TAG, "subscribe fail");
                        }
                    }
                });
    }

    private void unsubscribe() {
        Nearby.Messages.unsubscribe(mGoogleApiClient, mMessageListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }
}
