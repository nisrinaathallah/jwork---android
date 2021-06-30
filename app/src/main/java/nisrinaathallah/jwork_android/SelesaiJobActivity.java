package nisrinaathallah.jwork_android;
/**
 * @author Nisrina Athallah - 1806148813
 * @version 27-06-2021
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class SelesaiJobActivity extends AppCompatActivity {
    private int jobSeekerId;
    private int jobSeekerInvoiceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_job);
        ConstraintLayout layout = findViewById(R.id.selesai_layout);
        ConstraintLayout layout2 = findViewById(R.id.empty_layout);
        TextView jobseeker_name = findViewById(R.id.jobseeker_name);
        TextView invoice_date = findViewById(R.id.invoice_date);
        TextView payment_type = findViewById(R.id.payment_type);
        TextView invoice_status = findViewById(R.id.invoice_status);
        TextView referral_code = findViewById(R.id.referral_code);
        TextView staticJob_Name = findViewById(R.id.staticJob_Name);
        TextView jobname = findViewById(R.id.jobname);
        TextView fee = findViewById(R.id.fee);
        TextView totalfee = findViewById(R.id.totalfee);
        Button btnFinish = findViewById(R.id.btnFinish);
        Button btnCancel = findViewById(R.id.btnCancel);
        jobSeekerId = getIntent().getExtras().getInt("jobseekerid");

        layout.setVisibility(View.INVISIBLE);
        layout2.setVisibility(View.VISIBLE);

    fetchJob();
    buttonSelect();
    }

    private void fetchJob() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ConstraintLayout layout = findViewById(R.id.selesai_layout);
                ConstraintLayout layout2 = findViewById(R.id.empty_layout);
                TextView jobseeker_name = findViewById(R.id.jobseeker_name);
                TextView invoice_date = findViewById(R.id.invoice_date);
                TextView payment_type = findViewById(R.id.payment_type);
                TextView invoice_status = findViewById(R.id.invoice_status);
                TextView referral_code = findViewById(R.id.referral_code);
                TextView jobname = findViewById(R.id.jobname);
                TextView fee = findViewById(R.id.fee);
                TextView totalfee = findViewById(R.id.totalfee);
                Button btnFinish = findViewById(R.id.btnFinish);
                Button btnCancel = findViewById(R.id.btnCancel);

                try {
                    JSONArray jsonresponse = new JSONArray(response);
                    JSONObject jsoncustomer = jsonresponse.getJSONObject(jsonresponse.length() - 1);
                    JSONObject job = jsoncustomer.getJSONArray("jobs").getJSONObject(0);
                    JSONObject jobseeker = jsoncustomer.getJSONObject("jobseeker");
                    if (!jsoncustomer.isNull("bonus")){
                        JSONObject bonus = jsoncustomer.getJSONObject("bonus");
                        referral_code.setText(bonus.getString("referralCode"));
                    }
                    jobSeekerInvoiceId = jsoncustomer.getInt("id");
                    jobseeker_name.setText(jobseeker.getString("name"));
                    jobname.setText(job.getString("name"));
                    payment_type.setText(jsoncustomer.getString("paymentType"));
                    invoice_date.setText(jsoncustomer.getString("date"));
                    invoice_status.setText(jsoncustomer.getString("invoiceStatus"));
                    fee.setText(job.getString("fee"));
                    totalfee.setText(jsoncustomer.getString("totalFee"));
                    layout.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.INVISIBLE);
                }
                catch (JSONException e ){
                    Toast.makeText(SelesaiJobActivity.this, "No job applied!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SelesaiJobActivity.this, MainActivity.class);
                    startActivity(intent);
                    e.printStackTrace();
                }
            }
        };

        JobFetchRequest fetchRequest = new JobFetchRequest(String.valueOf(jobSeekerId), responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
        queue.add(fetchRequest);
    }

    private void buttonSelect() {
        Button btnFinish = findViewById(R.id.btnFinish);
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject invoice = new JSONObject(response);
                            //Akan memeriksa status invoice
                            if (invoice.getString("invoiceStatus").equals("Cancelled")) {
                                Toast.makeText(SelesaiJobActivity.this, "Invoice berhasil di cancel", Toast.LENGTH_SHORT).show();
                                SelesaiJobActivity.this.finish();
                            }
                            else {
                                Toast.makeText(SelesaiJobActivity.this, "Invoice telah dicancel", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(SelesaiJobActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                };
                JobBatalRequest jbr = new JobBatalRequest(String.valueOf(jobSeekerInvoiceId), responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
                queue.add(jbr);
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject invoice = new JSONObject(response);
                            if (invoice.getString("invoiceStatus").equals("Finished")) {
                                Toast.makeText(SelesaiJobActivity.this, "Invoice berhasil diselesaikan", Toast.LENGTH_SHORT).show();
                                SelesaiJobActivity.this.finish();
                            }
                            else {
                                Toast.makeText(SelesaiJobActivity.this, "Invoice telah selesai", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(SelesaiJobActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                };
                JobSelesaiRequest jsr = new JobSelesaiRequest(String.valueOf(jobSeekerInvoiceId), responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
                queue.add(jsr);
            }
        });


    }
    }



