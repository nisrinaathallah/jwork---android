package nisrinaathallah.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static Job job;
    ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    ArrayList<Job> jobIdList = new ArrayList<>();
    HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();

    private MainListAdapter listAdapter;
    ExpandableListView expListView;
    private static int jobSeekerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            jobSeekerId = extras.getInt("jobseekerid");
        }
        Button applied_job = findViewById(R.id.applied_job);
        expListView = findViewById(R.id.lvExpandleListView);
        refreshList();

        applied_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelesaiJobActivity.class);
                intent.putExtra("jobseekerid", jobSeekerId);
                startActivity(intent);
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                job = childMapping.get(listRecruiter.get(groupPosition)).get(childPosition);
                Intent intent = new Intent(MainActivity.this, ApplyJobActivity.class);

                startActivity(intent);
                return true;
            }

        });
    }

    public static int getJobSeekerId(){
        return jobSeekerId;
    }

    public static Job getSelectedjob(){
        return job;
    }

    private void refreshList() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    Log.d("JOSN", jsonResponse.toString());
                    if (jsonResponse != null) {
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject job = jsonResponse.getJSONObject(i);
                            JSONObject recruiter = job.getJSONObject("recruiter");
                            JSONObject location = recruiter.getJSONObject("location");

                            Location location1 = new Location(location.getString("province"),
                                    location.getString("city"),
                                    location.getString("description"));
                            Recruiter recruiter1 = new Recruiter(recruiter.getInt("id"),
                                    recruiter.getString("name"),
                                    recruiter.getString("email"),
                                    recruiter.getString("phoneNumber"),
                                    location1);

                            boolean cond = false;
                            for (Recruiter rec : listRecruiter){
                                if (rec.getId() == recruiter1.getId()){
                                   cond = true;
                                }
                            }
                            if (!cond) {
                                listRecruiter.add(recruiter1);
                            }


                            Job job1 = new Job(job.getInt("id"),
                                    job.getString("name"),
                                    recruiter1,
                                    job.getInt("fee"),
                                    job.getString("category"));

                            jobIdList.add(job1);

                            for(Recruiter rec : listRecruiter) {
                                ArrayList<Job> temp = new ArrayList<>();
                                for (Job job2 : jobIdList) {
                                    if (job2.getRecruiter().getName().equals(rec.getName()) ||
                                            job2.getRecruiter().getEmail().equals(rec.getEmail()) ||
                                            job2.getRecruiter().getPhoneNumber().equals(rec.getPhoneNumber())) {
                                        temp.add(job2);
                                    }
                                }
                                childMapping.put(rec, temp);
                            }
                        }
                    }

                    listAdapter = new MainListAdapter(getApplicationContext(), listRecruiter, childMapping);
                    expListView.setAdapter(listAdapter);
                } catch (JSONException e) {
                    Log.e("Error", "JSON ERROR", e);
                }
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}