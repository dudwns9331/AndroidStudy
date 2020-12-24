package yj.p.fragmentexample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // 리턴 무조건 필요함 View 형식으로
        // Inflate the layout for this fragment

        ViewGroup rootView = (ViewGroup) inflater.inflate
                (R.layout.fragment_main, container, false);
        // 최상위 레이아웃은 ViewGroup 이다.

        Button button = rootView.findViewById(R.id.button); // 해당 뷰그룹에서 버튼을 찾고
        button.setOnClickListener(new View.OnClickListener() { // 버튼이 눌렸을 때,
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity(); // getActivity()를 통해서 객체 참조
                assert activity != null;
                activity.onFragmentChanged(0); // 엑티비티의 onFragmentChanged 호출
            }
        });
        return rootView;
    }
    // 리턴타입 ViewGroup
}