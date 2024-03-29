package hoangviet.ndhv.com;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.w3c.dom.Text;

public class second_activity extends AppCompatActivity {
    private static final String TAG = "second_activity";
    private static final int ERROR_DIALOG_REQUEST = 1212;
    String user_name = "";

    ActionBar actionBar;
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (isServicesOK()){
                        fragmentHome();
                    }
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    fragmentNotifications();
                    return true;
                case R.id.navigation_account:
                    fragmentAccount();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activity);
        // khởi tạo toolbar
        khoiTaoToolBar();
        // khởi tạo một bottomNavigationbar
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (isServicesOK()){
            fragmentHome();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void khoiTaoToolBar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    private void fragmentAccount(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FragmentAccount fragmentAccount = new FragmentAccount();
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user");
        Bundle bundle = new Bundle();
        bundle.putString("user1",user_name);
        fragmentAccount.setArguments(bundle);
        fragmentTransaction.replace(R.id.FrameLayout,fragmentAccount,"fragmentAccount");
        fragmentTransaction.commit();
    }

    private void fragmentNotifications(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayout,new FragmentNotifications(),"fragmentNotification");
        fragmentTransaction.commit();
    }

    private void fragmentHome(){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.FrameLayout, new FragmentHome(), "fragmentHome");
            fragmentTransaction.commit();
        }



    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: check lỗi trước khi sử dung google service");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(second_activity.this);
        if (available == ConnectionResult.SUCCESS){
            Log.d(TAG, "isServicesOK: Bạn đang làm việc với google play services");
            return true;
        }else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServicesOK: Lỗi rồi cần fix lỗi google play services");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this, "Bạn không thể làm việc với google map", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

}
