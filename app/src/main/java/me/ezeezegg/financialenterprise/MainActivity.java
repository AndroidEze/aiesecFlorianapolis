package me.ezeezegg.financialenterprise;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import me.ezeezegg.financialenterprise.controllers.AppVolleyController;


public class MainActivity extends Activity {

    // json object response url
    private String urlJsonObj = "http://query.yahooapis.com/v1/public/yql?q=select%20MarketCapitalization,EBITDA,Name,PERatio,PEGRatio,Symbol%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%27AAPL%27,%20%27GOOG%27%20)%20order%20byMarketCapitalization&format=json&env=http://datatables.org/alltables.env";

    // json array response url
    private String urlJsonArry = "http://api.androidhive.info/volley/person_array.json";

    private static String TAG = MainActivity.class.getSimpleName();
    private Button btnMakeObjectRequest, btnMakeArrayRequest;

    // Progress dialog
    private ProgressDialog pDialog;

    // temporary string to show the parsed response
    private String jsonResponse;

    private TextView txtResponse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnMakeObjectRequest = (Button) findViewById(R.id.btnObjRequest);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        btnMakeObjectRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json object request
                makeJsonObjectRequest();
            }
        });

    }

    /**
     * Method to make json object request where json response starts wtih {
     * */
    private void makeJsonObjectRequest() {
        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, urlJsonObj,(String) null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            String name = response.getString("Name");
                            String symbol = response.getString("Symbol");

                            jsonResponse = "";
                            jsonResponse += "Name: " + name + "\n\n";
                            jsonResponse += "Email: " + symbol + "\n\n";

                            //txtResponse.setText(jsonResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        hidepDialog();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });
        AppVolleyController.getInstance().addToRequestQueue(jsonObjReq);
    }



    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}