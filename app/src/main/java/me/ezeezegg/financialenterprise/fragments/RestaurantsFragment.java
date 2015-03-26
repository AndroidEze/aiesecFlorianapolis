package me.ezeezegg.financialenterprise.fragments;

/**
 * Created by egarcia on 3/26/15.
 */
import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.ezeezegg.financialenterprise.R;
import me.ezeezegg.financialenterprise.adapters.RestaurantsAdapter;
import me.ezeezegg.financialenterprise.controllers.AppVolleyController;
import me.ezeezegg.financialenterprise.models.Restaurants;


public class RestaurantsFragment extends Fragment {

    // json object response url

    //private String urlJsonObj = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20local.search%20where%20query%3D%22sushi%22%20and%20location%3D%22san%20francisco%2C%20ca%22%20and%20Rating.AverageRating%3D4&format=json&diagnostics=true&callback=";
    private String urlJsonObj = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20local.search%20where%20zip=%2255429%22%20and%20query=%22lawyer%22&format=json";
    private static String TAG = RestaurantsFragment.class.getSimpleName();
    private Button btnMakeObjectRequest, btnMakeArrayRequest;

    // Progress dialog
    private ProgressDialog pDialog;

    ArrayList<Restaurants> restaurantsAux = new ArrayList<Restaurants>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        //ReclyclerView, Adapter

        restaurantsAux = makeJsonObjectRequest();

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RestaurantsAdapter(restaurantsAux, R.layout.row));


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Por si quieren configurar algom como Grilla solo cambian la linea de arriba por esta:
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //Float Button

        final int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
        final ImageButton imageButton = (ImageButton) getActivity().findViewById(R.id.fab_1);


        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Mejorando.la: Aprende a crear el futuro de la Web",
                        Toast.LENGTH_LONG).show();


            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.restaurants_fragment, container, false);
        return rootView;
    }
    private ArrayList<Restaurants> makeJsonObjectRequest() {
        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, urlJsonObj,(String) null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            JSONObject responseQuery = response.getJSONObject("query");
                            JSONObject jsonResults = responseQuery.getJSONObject("results");
                            //JSONObject jsonResult = jsonResults.getJSONObject("Result");
                            JSONArray jsonResult = jsonResults.getJSONArray("Result");


                            for (int i = 0; i < jsonResult.length(); i++) {
                                Restaurants res = new Restaurants();
                                JSONObject person = (JSONObject) jsonResult.get(i);
                                res.setTitle(person.getString("Title"));
                                res.setAddress(person.getString("Address"));
                                res.setPhone(person.getString("Phone"));
                                //res.setPhone(person.getString("MapUrl"));

                                restaurantsAux.add(res);
                                System.out.println("------*atentiton*------");
                                System.out.println(restaurantsAux);
                            }


                            Toast.makeText(getActivity(),restaurantsAux.toString(), Toast.LENGTH_LONG).show();


                            /*String title = jsonResult.getString("Title");
                            String address = jsonResult.getString("Address");
                            String city = jsonResult.getString("City");
                            Toast.makeText(getActivity(),title+"\n" + address+"\n" + city, Toast.LENGTH_LONG).show();*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        hidepDialog();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(),"Error sorry", Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });
        AppVolleyController.getInstance().addToRequestQueue(jsonObjReq);
        return restaurantsAux;
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