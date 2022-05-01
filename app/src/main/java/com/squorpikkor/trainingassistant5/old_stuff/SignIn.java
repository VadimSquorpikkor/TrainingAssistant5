package com.squorpikkor.trainingassistant5.old_stuff;

import static android.app.Activity.RESULT_OK;
import static com.squorpikkor.trainingassistant5.App.TAG;

import android.accounts.AccountManager;
import android.content.Intent;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;

class SignIn {

   //variant 1
   ActivityResultLauncher<Intent> activityResultLauncher;

   //variant 1
   /*activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
   result -> {
      Log.e(TAG, "onCreateView: ");
      if (result.getResultCode() == RESULT_OK) {
         Intent data = result.getData();
         IdpResponse response = IdpResponse.fromResultIntent(data);
         Log.e(TAG, "onCreateView: "+response.getEmail());
         //mViewModel.checkUserEmail(response.getEmail());
      }
   });*/


   //variant 1
   /*private void signIn() {
        List<AuthUI.IdpConfig> providers = Collections.singletonList( new AuthUI.IdpConfig.GoogleBuilder().build());
        AuthUI.getInstance().signOut(requireActivity());
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build();
        activityResultLauncher.launch(intent);
    }*/



   //variant 2
   /*private void signIn() {
      Intent googlePicker = AccountPicker.newChooseAccountIntent(null, null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, true, null, null, null, null);
      startActivityForResult(googlePicker, REQUEST_CODE);
   }

   public void onActivityResult(final int requestCode, final int resultCode,
                                final Intent data) {
      if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
         String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
         Log.e(TAG, "onActivityResult: "+accountName);
         mViewModel.getDeviceAccountEmail().setValue(accountName);
      }
   }*/



   //variant 2
    /*private void signIn() {
        Log.e(TAG, "signIn: ");
        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.GoogleBuilder().build());
        AuthUI.getInstance().signOut(requireActivity());
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                REQUEST_CODE);
        Log.e(TAG, "signIn: End");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult: REQUEST CODE = "+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                Log.e(TAG, "******************************onActivityResult: OK");
            } else {
                Log.e(TAG, "******************************onActivityResult: NOT OK");
            }
        }
    }*/

}
