package me.ezeezegg.aiesecFlorianapolis.fragments;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.ezeezegg.aiesecFlorianapolis.R;
import me.ezeezegg.aiesecFlorianapolis.adapters.infoAdapter;
import me.ezeezegg.aiesecFlorianapolis.controllers.AppVolleyController;
import me.ezeezegg.aiesecFlorianapolis.models.Info;


public class infoFragment extends Fragment {

    // json object response url
    //add Url json AIESEC
    private String urlJsonObj = "http://www.ckl.io/challenge/";
    private static String TAG = infoFragment.class.getSimpleName();
    private Button btnMakeObjectRequest, btnMakeArrayRequest;

    // Progress dialog
    private ProgressDialog pDialog;
    //Arraylist temporal
    ArrayList<Info> infoAux = new ArrayList<Info>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        //ReclyclerView, Adapter
        infoAux = getJsonVolley();

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new infoAdapter(infoAux, R.layout.lawyers_row));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //Float Button

        final int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
        final ImageButton imageButton = (ImageButton) getActivity().findViewById(R.id.fab_1);

        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "other view",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lawyers_fragment, container, false);
        return rootView;
    }
    private ArrayList<Info> getJsonVolley() {
        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, urlJsonObj,(String) null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            //JSONObject responseQuery = response.getJSONObject("query");
                            //JSONObject jsonResults = responseQuery.getJSONObject("results");
                            //JSONObject jsonResult = jsonResults.getJSONObject("Result");
                            //JSONArray jsonResult = jsonResults.getJSONArray("Result");

                            for (int i = 0; i < response.length(); i++) {
                                Info res = new Info();
                                JSONObject jsonObject = response.getJSONObject(String.valueOf(i));
                                res.setTitle(jsonObject.getString("title"));
                                res.setDate(jsonObject.getString("date"));
                                res.setAuthors(jsonObject.getString("authors"));
                                res.setImage(jsonObject.getString("image"));
                                //res.setPhone(person.getString("MapUrl"));

                                infoAux.add(res);
                            }
                            //Toast.makeText(getActivity(),restaurantsAux.toString(), Toast.LENGTH_LONG).show();

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
        return infoAux;
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