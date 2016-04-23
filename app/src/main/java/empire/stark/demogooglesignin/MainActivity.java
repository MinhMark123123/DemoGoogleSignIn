package empire.stark.demogooglesignin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, View.OnClickListener {
    public static final String TAG = "MainActivity";


    private GoogleSignInOpen mGoogleSignInOpen;
    private SignInButton mSignInButton;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        mGoogleSignInOpen = new GoogleSignInOpen(this);
        mGoogleSignInOpen.registerConnectionCallBack(this, this);
        //
        initView();
        //
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInResult googleSignInResult = mGoogleSignInOpen.checkUserCacheAvaiable();
        if (googleSignInResult != null) {
            handelSignInResult(googleSignInResult);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void initView() {
        mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
        if (mSignInButton != null) {
            mSignInButton.setSize(SignInButton.SIZE_STANDARD);
            mSignInButton.setScopes(mGoogleSignInOpen.getGoogleSignInOption().getScopeArray());
        }
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        if (mFab != null) {
            mFab.setOnClickListener(this);
        }
        mSignInButton.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        GoogleSignInResult googleSignInResult = mGoogleSignInOpen.detectResultReturn(requestCode, resultCode, data);
        if(googleSignInResult != null){
            handelSignInResult(googleSignInResult);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handelSignInResult(GoogleSignInResult googleSignInResult) {
        if (googleSignInResult.isSuccess()) {
            Log.d(TAG, " login success : " + googleSignInResult.getSignInAccount().getEmail());
            Log.d(TAG, " id auth code : " + googleSignInResult.getSignInAccount().getServerAuthCode());
            Log.d(TAG, " login token : " + googleSignInResult.getSignInAccount().getIdToken());
        } else {
            Log.d(TAG, " login failed");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, " onConnectionFailed");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, " onConnected");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                mGoogleSignInOpen.signIn();
                break;
        }
    }

}
