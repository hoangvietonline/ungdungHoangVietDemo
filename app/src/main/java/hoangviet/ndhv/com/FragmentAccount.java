package hoangviet.ndhv.com;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentAccount extends ListFragment {
    int REQUEST_CODE_CAMERA =123;
    TextView txt_user ;
    ImageView img_avata;
    AccountAdapter adapter;
    ArrayList<Account> accountArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);
        txt_user = view.findViewById(R.id.txt_Ten_TaiKhoan);
        img_avata  = view.findViewById(R.id.avarta_user);
        Bundle bundle = getArguments();
        if (bundle != null){
            txt_user.setText(bundle.getString("user1"));
        }

        img_avata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                startActivityForResult(intent,REQUEST_CODE_CAMERA);
            }
        });


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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == getActivity().RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img_avata.setImageBitmap(bitmap);
        }

        super.onActivityResult(requestCode, resultCode, data);
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
    public void ganuserName(String username){
        txt_user.setText(username);
    }
}
