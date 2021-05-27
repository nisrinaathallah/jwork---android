/**
 * @author: Nisrina Athallah - 1806148813
 * @version: Modul 9 - Case Study - 27 Mei 2021
 */

package nisrinaathallah.jwork_android;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    private ArrayList<Job> jobIdList = new ArrayList<>();
    private HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();

    Location l1 = new Location("Jakarta", "Jakarta Timur", "Pulomas");
    Recruiter r1 = new Recruiter(1,"Nisrina", "nisrina@gmail.com", "08123456789", l1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    protected void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonResponse = new JSONArray(response);
                    if(jsonResponse != null){
                        for (int i = 0; i < jsonResponse.length(); i++){
                            JSONObject job = jsonResponse.getJSONObject(i);
                            JSONObject recruiter = job.getJSONObject("recruiter");
                            JSONObject location = recruiter.getJSONObject("location");

                            String city = location.getString("city");
                            String province = location.getString("province");
                            String description = location.getString("description");

                            Location l1 = new Location(city, province, description);

                            int recruiterId = recruiter.getInt("id");
                            String rctrName = recruiter.getString("name");
                            String rctrEmail = recruiter.getString("email");
                            String rctrPhoneNumber = recruiter.getString("phoneNumber");

                            Recruiter r1 = new Recruiter(recruiterId, rctrName, rctrEmail, rctrPhoneNumber, l1);
                            if (listRecruiter.size() > 0) {
                                boolean success = true;
                                for (Recruiter rec : listRecruiter)
                                    if (rec.getId() == r1.getId())
                                        success = false;
                                if (success) {
                                    listRecruiter.add(r1);
                                }
                            } else {
                                listRecruiter.add(r1);
                            }

                            int jobId = job.getInt("id");
                            int jobPrice = job.getInt("price");
                            String jobName = job.getString("name");
                            String jobCategory = job.getString("category");

                            Job j1 = new Job(jobId, jobName, r1, jobPrice, jobCategory);
                            jobIdList.add(j1);

                            for (Recruiter rctr : listRecruiter) {
                                ArrayList<Job> tempRecr = new ArrayList<>();
                                for (Job jobs : jobIdList) {
                                    if (jobs.getRecruiter().getName().equals(rctr.getName()) ||
                                            jobs.getRecruiter().getEmail().equals(rctr.getEmail()) ||
                                            jobs.getRecruiter().getPhoneNumber().equals(rctr.getPhoneNumber()))
                                    {
                                        tempRecr.add(jobs);
                                    }
                                }
                                childMapping.put(rctr, tempRecr);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                };
            }
        };
    }
}