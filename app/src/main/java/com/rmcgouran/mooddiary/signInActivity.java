package com.rmcgouran.mooddiary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hanks.passcodeview.PasscodeView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class signInActivity extends AppCompatActivity {

    private KeyStore keyStore;
    private static final String KEY_NAME = "AndroidKey";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        try {
            keyStore.load(null);
        } catch (CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException("Failed to load KeyStore", e);
        }

        SharedPreferences prefs = getSharedPreferences("PASS_PREF", Context.MODE_PRIVATE);
        String thePassCode = prefs.getString("Digits", "");
        PasscodeView passcodeView = findViewById(R.id.passcode_view);
        TextView mParaLabel = findViewById(R.id.paraLabel);
        passcodeView.setPasscodeLength(4)
                .setLocalPasscode(thePassCode)
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {
                        Toast.makeText(getApplicationContext(), "Password is Incorrect", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String number) {
                        Intent intent = new Intent(signInActivity.this, createMoodEntry.class);
                        startActivity(intent);
                    }
                });
    }

    private boolean initCipher(Cipher cipher) {
        try {
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | UnrecoverableKeyException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }
}
