package nisrinaathallah.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Nisrina Athallah - 1806148813
 * @version 27 - 06 - 2021
 */
public class ApplyJobActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener, TextWatcher
{
    /** radio group untuk opsi jenis pembayaran */
    private RadioGroup group;
    /** job yang ditampilkan */
    private Job job;
    /** posisi index opsi bank payment dari radio group */
    private static final int BANK_ID = 1;
    /** posisi index opsi ewallet payment dari radio group */
    private static final int EWALLET_ID = 0;

    /** metode yang dipanggil saat activity dibangun */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        job = MainActivity.getSelectedjob();

        TextView jname = findViewById(R.id.job_name);
        TextView jcat  = findViewById(R.id.job_category);
        TextView jfee  = findViewById(R.id.job_fee);
        TextView jtot  = findViewById(R.id.total_fee);

        group = findViewById(R.id.radio_group);
        group.setOnCheckedChangeListener(this);

        jname.setText(job.getName());
        jcat.setText(job.getCategory());
        jfee.setText(String.valueOf(job.getFee()));
        jtot.setText("0");

        findViewById(R.id.btnApply).setEnabled(false);
        ((EditText) findViewById(R.id.apply_referral_code)).addTextChangedListener(this);

        findViewById(R.id.btnApply).setOnClickListener(this::onApplyClick);
        findViewById(R.id.btnCount).setOnClickListener(this::onCountClick);
    }

    /** dipanggil saat pemilihan opsi radio group untuk jenis pembayaran */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        TextView textCode = findViewById(R.id.textCode);
        EditText apply_referral_code = findViewById(R.id.apply_referral_code);
        TextView jtot  = findViewById(R.id.total_fee);
        int visibility = getRadioSelection() == EWALLET_ID ? View.VISIBLE : View.INVISIBLE;
        textCode.setVisibility(visibility);
        apply_referral_code.setVisibility(visibility);
        findViewById(R.id.btnApply).setEnabled(false);
        jtot.setText("0");
    }

    /** dipanggil saat button Count di click */
    public void onCountClick(View view) {
        TextView jtot  = findViewById(R.id.total_fee);
        if (getRadioSelection() == EWALLET_ID)
        {
            EditText apply_referral_code = findViewById(R.id.apply_referral_code);
            String refCode = apply_referral_code.getText().toString();
            if (!refCode.isEmpty())
            {
                BonusRequest req = new BonusRequest(refCode, this::onCountResponse);
                RequestQueue queue = Volley.newRequestQueue(this);
                queue.add(req);
                jtot.setText("Calculating...");
                return;
            }
        }
        findViewById(R.id.btnApply).setEnabled(true);
        jtot.setText(String.valueOf(job.getFee()));
    }

    /** dipanggil saat button Apply di click, melakukan request
     * untuk membangun Invoice melalui ApplyJobRequest */
    public void onApplyClick(View view)
    {
        ApplyJobRequest req = null;
        int jobseekerId = MainActivity.getJobSeekerId();

        if (getRadioSelection() == BANK_ID)
        {
            req = new ApplyJobRequest(String.valueOf(job.getId()), String.valueOf(jobseekerId), this::onApplyResponse);
        }
        else
        {
            EditText apply_referral_code = findViewById(R.id.apply_referral_code);
            String referralCode = apply_referral_code.getText().toString();
            req = new ApplyJobRequest(String.valueOf(job.getId()), String.valueOf(jobseekerId), referralCode, this::onApplyResponse);
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(req);
    }

    /** meminta posisi (index) dari radio group opsi jenis pembayaran
     * @return index dari radio group, jika tidak ditemukan return -1 */
    private int getRadioSelection() {
        int checkedId = group.getCheckedRadioButtonId();
        View radioButton = group.findViewById(checkedId);
        return group.indexOfChild(radioButton);
    }


    /** respon terhadap request yang dibentuk oleh button Apply,
     * menambah InvoiceJob kepada HistoryActivity jika invoice berhasil  */
    public void onApplyResponse(String response) {
        String promptMessage = null;
        promptMessage = response.isEmpty() ? "Request failed" : "Applied successfully";
        Toast.makeText(this, promptMessage, Toast.LENGTH_LONG).show();
    }

    /** respon terhadap request yang dibentuk oleh button Count */
    public void onCountResponse(String response) {
        int totalFee = job.getFee();
        String promptMessage = null;
        try {
            if (response.isEmpty())
                throw new Exception("Referral not exists!");
            // get as json object
            JSONObject obj = new JSONObject(response);
            // bonus needs to be active
            if (!obj.getBoolean("active"))
                throw new Exception("Referral not active!");
            // bonus min total fee should be <= totalFee
            if (obj.getInt("minTotalFee") > totalFee)
                throw new Exception("Referral requirements not met!");
            // if all requirements met, increase totalFee
            totalFee += obj.getInt("extraFee");
            promptMessage = "Referral code applied!";
            findViewById(R.id.btnApply).setEnabled(true);
        } catch (JSONException e) {
            promptMessage = "Processing failed!";
        } catch (Exception e) {
            promptMessage = e.getMessage();
        } finally {
            Toast.makeText(this, promptMessage, Toast.LENGTH_LONG).show();
            TextView jtot = findViewById(R.id.total_fee);
            jtot.setText(String.valueOf(totalFee));
        }
    }

    /** not used */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    /** respon terhadap perubahan pada EditText kode referral, jika kode referral berubah
     * maka tombol apply di-non aktifkan karena harus di hitung ulang melalui Count */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        View view = findViewById(R.id.btnApply);
        if (view.isEnabled())
            view.setEnabled(false);
    }

    /** not used */
    @Override
    public void afterTextChanged(Editable s) {}
}