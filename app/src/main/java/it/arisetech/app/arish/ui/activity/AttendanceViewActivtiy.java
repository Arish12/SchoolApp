package it.arisetech.app.arish.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import it.arisetech.app.arish.R;


public class AttendanceViewActivtiy extends BaseDrawerActivity {
    String[] SPINNERLIST = {"Section A", "Section B", "Section C", "Section D"};
    String[] SPINNERLISTFACULTY = {"Science", "Management"};
    String[] SPINNERLISTGRADE = {"XI", "XII"};
    EditText roll;
    AppCompatButton submit;
    ProgressDialog progressDialog;
    ImageView stuPic;
    String faculty,section,grade,rollno;
  MaterialBetterSpinner sectionSpinner,sectionSpinnerFaculty,sectionSpinnerClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_view);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        sectionSpinner = (MaterialBetterSpinner)
                findViewById(R.id.android_marks_sec);
        sectionSpinner.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapterFaculty = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLISTFACULTY);
        sectionSpinnerFaculty = (MaterialBetterSpinner)
                findViewById(R.id.android_marks_faculty);
        sectionSpinnerFaculty.setAdapter(arrayAdapterFaculty);


        ArrayAdapter<String> arrayAdapterClass = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLISTGRADE);
        sectionSpinnerClass = (MaterialBetterSpinner)
            findViewById(R.id.android_marks_grade);
        sectionSpinnerClass.setAdapter(arrayAdapterClass);

        roll = (EditText) findViewById(R.id.etRoll);
        submit = (AppCompatButton) findViewById(R.id.btn_submit);
//
    sectionSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i){
                case 0:
                    sectionSpinner.setText("a");
                    break;
                case 1:
                    sectionSpinner.setText("b");
                    break;
                case 2:
                    sectionSpinner.setText("c");
                    break;
                case 3:
                    sectionSpinner.setText("d");
                    break;

            }
        }
    });
       sectionSpinnerFaculty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               switch(i) {
                   case 0:
                       sectionSpinnerFaculty.setText("science");
                       break;
                   case 1:
                       sectionSpinnerFaculty.setText("management");
                       break;
               }
           }
       });
        sectionSpinnerClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        sectionSpinnerClass.setText("11");
                        break;
                    case 1:
                        sectionSpinnerClass.setText("12");
                        break;
                }
            }
        });//
submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        rollno = roll.getText().toString();
        faculty =sectionSpinnerFaculty.getText().toString();
        section = sectionSpinner.getText().toString();
        grade = sectionSpinnerClass.getText().toString();

        // Save the text in SharedPreference

        if (TextUtils.isEmpty(roll.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please Write Your Email", Toast.LENGTH_SHORT).show();
        }
        CheckResult(faculty,section,grade,rollno);
    }});

    }


    private void CheckResult(final String faculty,final String section,final String grade, final String rollno) {

        String tag_req_login = "req_login";
        progressDialog.setMessage("Requesting.......");
        progressDialog.show();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.feather-techsolution.com/schol/stdattendance.php",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.hide();
                        try {

                            JSONArray jArray = new JSONArray(response);

                            for(int i = 0; i < jArray.length(); i++) {
                                JSONObject jsonObject = null;

                                jsonObject =jArray.getJSONObject(i);
                                String stuName = jsonObject.getString("student_name");
                                String stuAddress = jsonObject.getString("contact_address");
                                String stuContact = jsonObject.getString("contact_number");
                                String absent = jsonObject.getString("total_absent");

//                                absen = (TextView) findViewById(R.id.absent_days);
//                                absen.setText(tile);
                                String pre = jsonObject.getString("total_present");
//                                present = (TextView)findViewById(R.id.present_days);
//                                present.setText(pre);
                                String total = jsonObject.getString("total_days");
//                                totaldays = (TextView) findViewById(R.id.total_days);
//                                totaldays.setText(total);
                                String total_remarks = jsonObject.getString("teacher_remarks");

//                                totaldays = (TextView) findViewById(R.id.teacher_remarks);
//                                totaldays.setText(total_remaks);
                                String imageUrl = jsonObject.getString("image");
                                Intent intent = new Intent (AttendanceViewActivtiy.this,AttendanceActivity.class);
                                intent.putExtra("studentName",stuName);
                                intent.putExtra("studentAddress",stuAddress);
                                intent.putExtra("studentContact",stuContact);
                                intent.putExtra("absent",absent);
                                intent.putExtra("present",pre);
                                intent.putExtra("total",total);
                                intent.putExtra("teacherRemarks",total_remarks);
                                intent.putExtra("imageUrl",imageUrl);

                                startActivity(intent);
//                                stuPic = (GalleryImages)findViewById(R.id.student_pic);

//                                    stuPic.setImageBitmap(std);

                            }


//                                EventsView geteventAdapter = new EventsView();
//                                JSONObject json = null;

//                                    absen.setText(absent);
//                                String uid = jObj.getString("error_msg");
//                                JSONObject user = jObj.getJSONObject("id");
//                                String name = user.getString("username");
//                                String email = user.getString("password");
//                                Log.d("aaaaa"+name,"aaaaa"+email);
                            Toast.makeText(getApplicationContext(),"Succesfull", Toast.LENGTH_LONG).show();
//                                startActivity(new Intent(LoginPage.this, AdminMapsActivity.class));
//                                finish();
                        } catch (JSONException e) {
                            new MaterialDialog.Builder(AttendanceViewActivtiy.this).title("LOADING FAILED....").content("No data")
                                    .positiveText("OK").callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {

                                }
                            }).show();

//                        }else {
//                                String errorMsg = jsonArray.getString("error_msg");
//                                Toast.makeText(getActivity().getApplicationContext(),
//                                        errorMsg, Toast.LENGTH_LONG).show();


                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },

//                            if (response.trim().equals("false")) {
//                                startActivity(new Intent(LoginPage.this, AdminMapsActivity.class));
//                            } else {
//                                Toast.makeText(LoginPage.this, response, Toast.LENGTH_LONG).show();
//
//                            }
//                        }
//                    },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        new MaterialDialog.Builder(AttendanceViewActivtiy.this).title("LOADING FAILED....").content("Please Check Connection")
                                .positiveText("OK").callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {

                            }
                        }).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("faculty",faculty);
                map.put("class",grade);
                map.put("sec",section);
                map.put("roll",rollno);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    }

