package hoangviet.ndhv.com;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AccountAdapter extends BaseAdapter {
    private Context mcontext;
    private int mLayout;
    private List<Account>accountList;

    public AccountAdapter(Context mcontext, int mLayout, List<Account> accountList) {
        this.mcontext = mcontext;
        this.mLayout = mLayout;
        this.accountList = accountList;
    }

    @Override
    public int getCount() {
        return accountList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder{
        ImageView imageHinhAccount;
        TextView txttenAccount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mLayout,null);
            // ánh xạ
            viewHolder.imageHinhAccount = (ImageView)convertView.findViewById(R.id.image_account_hinh);
            viewHolder.txttenAccount = (TextView)convertView.findViewById(R.id.txt_account_tenicon);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // gán giá trị
        viewHolder.txttenAccount.setText(accountList.get(position).getTenAccount());
        viewHolder.imageHinhAccount.setImageResource(accountList.get(position).getHinhAccount());



        return convertView;
    }
}
