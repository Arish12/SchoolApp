package it.arisetech.app.arish.ui.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

import it.arisetech.app.arish.R;
import it.arisetech.app.arish.Utils;


/**
 * Created by Miroslaw Stanek on 21.02.15.
 */
public class AttendanceActivity extends BaseDrawerActivity {

    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    @BindView(R.id.total_days)
    TextView totaldays;
    @BindView(R.id.total_Present)
    TextView totalPresent;
    @BindView(R.id.total_Absent)
    TextView totalAbsent;
    @BindView(R.id.total_TeacherRemarks)
    TextView techerRemarks;
    @BindView(R.id.studentNAme)
    TextView studentName;
    @BindView(R.id.studentAddress)
    TextView studentAddress;
    @BindView(R.id.studentContact)
    TextView studentContact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        Intent i = getIntent();
        String name = i.getStringExtra("studentName");
        String address = i.getStringExtra("studentAddress");
        String contact = i.getStringExtra("studentContact");
        String totalday = i.getStringExtra("total");
        String present = i.getStringExtra("present");
        String absent = i.getStringExtra("absent");
        String imageUrl = i.getStringExtra("imageUrl");
        String teacherRemarks = i.getStringExtra("teacherRemarks");

        studentName.setText(name);
        studentAddress.setText(address);
        studentContact.setText(contact);
        totaldays.setText(totalday);
        totalPresent.setText(present);
        totalAbsent.setText(absent);
        techerRemarks.setText(teacherRemarks);
        try {

            UrlImageViewHelper.setUrlDrawable(ivPhoto, imageUrl);
        }catch (Exception e) {
            e.printStackTrace();
        }
//                                    stuPic.setImageBitmap(std);

//        intent.putExtra("studentName",stuName);
//        intent.putExtra("absent",absent);
//        intent.putExtra("present",pre);
//        intent.putExtra("total",total);
//        intent.putExtra("teacherRemarks",total_remarks);
//        intent.putExtra("imageUrl",imageUrl);
//

    }



    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(AttendanceActivity.this);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setMessage("Do you want to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        // ToDo get user input here
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })

                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();

    }

}
