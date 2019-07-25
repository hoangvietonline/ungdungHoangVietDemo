package hoangviet.ndhv.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentAccount extends ListFragment {
    AccountAdapter adapter;
    ArrayList<Account> accountArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);
        accountArrayList = new ArrayList<>();
        accountArrayList.add(new Account("Lịch sử",R.drawable.icons_histoyr2));
        accountArrayList.add(new Account("Địa chỉ",R.drawable.icons8_address));
        accountArrayList.add(new Account("Thêm bạn",R.drawable.user_add_icon));
        accountArrayList.add(new Account("Đóp góp",R.drawable.feedback_icon));
        accountArrayList.add(new Account("Cài đặt",R.drawable.icons_setting));
        adapter = new AccountAdapter(getActivity(),R.layout.dong_account,accountArrayList);
        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String TenAccount = accountArrayList.get(position).getTenAccount();
        if ( TenAccount == accountArrayList.get(1).getTenAccount()){
            Intent intent = new Intent(getActivity(),DiaChi_activity.class);
            startActivity(intent);
        }
        super.onListItemClick(l, v, position, id);
    }
}
