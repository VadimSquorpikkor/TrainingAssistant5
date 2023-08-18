package com.squorpikkor.trainingassistant5.dialog;

import static com.squorpikkor.trainingassistant5.MainViewModel.NO_ACTION;
import static com.squorpikkor.trainingassistant5.MainViewModel.REGISTER_FAILURE;
import static com.squorpikkor.trainingassistant5.MainViewModel.REGISTER_SUCCESS;
import static com.squorpikkor.trainingassistant5.MainViewModel.SIGN_IN_FAILURE;
import static com.squorpikkor.trainingassistant5.MainViewModel.SIGN_IN_SUCCESS;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.squorpikkor.trainingassistant5.R;

/**
 *
 * */
public class SignInDialog extends BaseDialog {

   // TODO: 18.08.2023 сделать вариант отображения, когда при открытии диалога пользователь уже зарегистрирован: тогда будет кнопка "Выйти из аккаунта"

   public static SignInDialog newInstance() {
      return new SignInDialog();
   }
   private TextView loginText, registerText;
   private EditText login, password;
   private View setRegisterLayout, setSignInLayout;
   private View setRegisterButton, setSignInButton;
   private Button cancel, register, signIn;
   private ProgressBar progressBar;

   @NonNull
   @Override
   public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      super.onCreateDialog(savedInstanceState);
      initializeWithVM(R.layout.dialog_sign_in);


      loginText = view.findViewById(R.id.login_text);
      registerText = view.findViewById(R.id.register_text);
      login = view.findViewById(R.id.editEmail);
      password = view.findViewById(R.id.editPass);
      setRegisterLayout = view.findViewById(R.id.set_register_layout);
      setSignInLayout = view.findViewById(R.id.set_sign_in_layout);
      setRegisterButton = view.findViewById(R.id.set_register);
      setSignInButton = view.findViewById(R.id.set_sign_in);
      cancel = view.findViewById(R.id.button_cancel);
      register = view.findViewById(R.id.button_register);
      signIn = view.findViewById(R.id.button_sign_in);

      mViewModel.getLogin().observe(this, s -> login.setText(s));
      mViewModel.getPassword().observe(this, s -> password.setText(s));
      mViewModel.getLoginState().observe(this, this::setState);

      setAsSignIn();

      setRegisterButton.setOnClickListener(v->setAsRegister());
      setSignInButton.setOnClickListener(v->setAsSignIn());

      progressBar = view.findViewById(R.id.progressBar);

      cancel.setOnClickListener(v->dismiss());
      signIn.setOnClickListener(v->{
         mViewModel.signIn(login.getText().toString(), password.getText().toString());
         progressBar.setIndeterminate(true);
      });
      register.setOnClickListener(v-> {
         mViewModel.signUp(login.getText().toString(), password.getText().toString());
         progressBar.setIndeterminate(true);
      });



      return dialog;
   }

   private void setState(int state) {
      switch (state) {
         case REGISTER_SUCCESS: {
            mViewModel.getLoginState().postValue(NO_ACTION);
            dismiss();
         } break;
         case REGISTER_FAILURE: {
            progressBar.setIndeterminate(false);
            Toast.makeText(mContext, "Ошибка регистрации", Toast.LENGTH_SHORT).show();// TODO: 18.08.2023 текст вместо тоста
         } break;
         case SIGN_IN_SUCCESS: {
            mViewModel.getLoginState().postValue(NO_ACTION);
            dismiss();
         } break;
         case SIGN_IN_FAILURE: {
            progressBar.setIndeterminate(false);
            Toast.makeText(mContext, "Не удалось войти в аккаунт", Toast.LENGTH_SHORT).show();// TODO: 18.08.2023 текст вместо тоста
         } break;
         default: {

         }
      }

   }

   private void setAsRegister() {
      loginText.setVisibility(View.GONE);
      registerText.setVisibility(View.VISIBLE);
      setRegisterLayout.setVisibility(View.GONE);
      setSignInLayout.setVisibility(View.VISIBLE);
      register.setVisibility(View.VISIBLE);
      signIn.setVisibility(View.GONE);
   }

   private void setAsSignIn() {
      loginText.setVisibility(View.VISIBLE);
      registerText.setVisibility(View.GONE);
      setRegisterLayout.setVisibility(View.VISIBLE);
      setSignInLayout.setVisibility(View.GONE);
      register.setVisibility(View.GONE);
      signIn.setVisibility(View.VISIBLE);
   }
}
