package me.ezeezegg.financialenterprise;

import android.app.Activity;
import android.os.Bundle;
import me.ezeezegg.financialenterprise.fragments.RestaurantsFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new RestaurantsFragment())
                    .commit();
        }

    }


}