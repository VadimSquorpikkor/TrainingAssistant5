package com.squorpikkor.trainingassistant5.dialog;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;


import com.squorpikkor.trainingassistant5.App;
import com.squorpikkor.trainingassistant5.MainActivity;
import com.squorpikkor.trainingassistant5.MainViewModel;
import com.squorpikkor.trainingassistant5.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**Базовый класс для диалогов. Есть варианты с ViewModel и без
 * */
@SuppressWarnings("unused")
public
class BaseDialog extends DialogFragment {

    Context mContext;
    MainViewModel mViewModel;
    View view;
    AlertDialog dialog;
    /**Лаунчер для внутреннего проводника. Метод getFolder или getFile открывает проводник,
     * который возвращает путь выбранного в проводнике файла. Для назначения действия
     * переопределить метод pathReturnListener

     * (@code @Override)
     *     public void pathReturnListener(String path) {
     *         pathToFolder.setText(path);
     *     }

     * Для открытия проводника запустить getFolder или getFile
     * */
    ActivityResultLauncher<Intent> getFileLauncher;

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    /**Добавляет диалогу лэйаут и задаёт параметры*/
    public void initialize(int layout) {
        dialog = new AlertDialog.Builder(mContext).create();
//        Window window = dialog.getWindow();
//        if (window != null) window.setBackgroundDrawableResource(R.drawable.main_gradient);
///
        view = requireActivity().getLayoutInflater().inflate(layout, null);
        dialog.setView(view, 0, 0, 0, 0);
    }

    /**Дать диалогу лэйаут и задать параметры. Добавление ViewModel*/
    public void initializeWithVM(int layout) {
        dialog = new AlertDialog.Builder(mContext).create();
//        Window window = dialog.getWindow();
//        if (window != null) window.setBackgroundDrawableResource(R.drawable.main_gradient);
///
        view = requireActivity().getLayoutInflater().inflate(layout, null);
        dialog.setView(view, 0, 0, 0, 0);
        mViewModel = new ViewModelProvider((MainActivity) mContext).get(MainViewModel.class);
    }



    public void pathReturnListener(String path) {

    }

    /**
     * Проверка введённого пароля. Пароль — это время без ":"
     * @param input EditText в который вводится пароль
     * @param errorText TextView, в который будет выводится сообщение об ошибке ("Неверный пароль"
     *                  или "Введите пароль" — если в input ничего не введено)
     * @return если пароль введен (input не пустой) и он правильный, возвращает true
     */
     public boolean checkPassword(String input, TextView errorText) {
        String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());//19:50
        String password = time.split(":")[0] + time.split(":")[1];//1950

        if (input.trim().length() > 0) {
            if (input.equals(password)) {
                return true;
            } else {
                if (errorText!=null) errorText.setText(getString(R.string.wrong_pass));
                return false;
            }
        } else {
            if (errorText!=null) errorText.setText(getString(R.string.enter_password));
        }
        return false;
    }

    /**Очищает errorText при изменении EditText, которому привязан этот TextWatcher.
     * Нужен для: когда введен неверный пароль, то в TextView записывается сообщение об ошибке.
     * Теперь если начинать стирать символы в EditText или добавлять новые, то сообщение об ошибке
     * будет удалено из TextView
     * @param errorText TextView, который очищается при изменении в EditText
     * @return TextWatcher для EditText
     */
    public TextWatcher getTextWatcher(TextView errorText) {
        return new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                errorText.setText("");
            }
            @Override public void afterTextChanged(Editable editable) {
                    errorText.setText("");
                }
        };
    }

    /**Если необходимо чтобы при открытии диалога автоматом открывалась клавиатура,
     * то добавить этот метод в onCreateDialog*/
    void setKeyword() {
        Window window = dialog.getWindow();
        if (window != null) window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void makeToast(int res) {
        Toast.makeText(App.getContext(), getString(res), Toast.LENGTH_SHORT).show();
    }

    public void makeToastLong(int res) {
        Toast.makeText(App.getContext(), getString(res), Toast.LENGTH_LONG).show();
    }

    static abstract class CheckPassword {
        void check(TextView input) {
            String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());//19:50
            String password = time.split(":")[0] + time.split(":")[1];//1950
            String s = input.getText().toString();

            if (s.trim().length() > 0) {
                if (s.equals(password)) {
                    rightPassword();
                } else {
                    //Toast.makeText(App.getContext(), getString(R.string.wrong_password), Toast.LENGTH_LONG).show();
                    wrongPassword();
                }
            } else {
                emptyField();
                //Toast.makeText(App.getContext(), getString(R.string.empty_password), Toast.LENGTH_LONG).show();
            }
        }

        abstract void rightPassword();
        abstract void wrongPassword();
        abstract void emptyField();
    }



    public abstract class BaseAlert {
        public void show(int message, int okBtn, int cancelBtn) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());
            dialog.setMessage(message);
            dialog.setPositiveButton(okBtn, (paramDialogInterface, paramInt) -> {
                ok();
                dismiss();
            });
            dialog.setNegativeButton(cancelBtn, (paramDialogInterface, paramInt) -> dismiss());
            dialog.show();
        }
        public abstract void ok();
    }

}
