package yj.p.challenge10;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class customer_info extends Fragment {

    Button birth_button;
    Button save_button;

    EditText name;
    EditText age;

    int init_year, init_month, init_day;
    DatePickerDialog datePickerDialog;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_customer_info, container, false);

        birth_button = rootView.findViewById(R.id.button);
        save_button = rootView.findViewById(R.id.save_button);

        name = rootView.findViewById(R.id.name);
        age = rootView.findViewById(R.id.age);

        Date currentTime = Calendar.getInstance().getTime();

        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일",
                Locale.getDefault()).format(currentTime);

        String week;

        init_year = Integer.parseInt(date_text.substring(0,4));
        init_month = Integer.parseInt(date_text.substring(6,8));
        init_day = Integer.parseInt(date_text.substring(10, 12));
        week = date_text.substring(14, 15);


        birth_button.setText("생년월일 선택 : " + date_text);

        birth_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                birth_button.setText("생년월일 : " + year + "년 " + (month+1) + "월 " + dayOfMonth + "일");
            }
        }, init_year, init_month - 1, init_day);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "이름 : " + name.getText() + "\n나이 : "
                                + age.getText() + "\n"
                                + birth_button.getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}