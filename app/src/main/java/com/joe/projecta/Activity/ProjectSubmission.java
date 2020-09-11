package com.joe.projecta.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joe.projecta.R;
import com.joe.projecta.retrofit.Client;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectSubmission extends AppCompatActivity {
    private ImageButton returnButton, cancelSubmission;
    private ImageView questionMark, confirmImage, unconfirmImage;
    private TextView textConfirmation, confirmText, unconfirmText;
    private Button submitConfirmation, submitProject;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private EditText firstname,lastname,emailaddresss,linkgithub;

    private static Retrofit.Builder reBuilder = new Retrofit.Builder()
            .baseUrl(" https://docs.google.com/forms/d/e/")
            .addConverterFactory(GsonConverterFactory.create());

    public static Retrofit retrofit = reBuilder.build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_submission);

        submitProject = findViewById(R.id.submitProjectButton);
        cancelSubmission = findViewById(R.id.cancelSubmissionButton);
        returnButton = findViewById(R.id.returnButton);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        emailaddresss = findViewById(R.id.emailaddress);
        linkgithub = findViewById(R.id.linkgithub);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectSubmission.this, MainActivity.class));
                finish();
            }
        });

        submitProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!firstname.getText().toString().isEmpty()
                && !lastname.getText().toString().isEmpty()
                && !emailaddresss.getText().toString().isEmpty()
                && !linkgithub.getText().toString().isEmpty()){
                createPopDialog();
                }else {
                    Toast.makeText(ProjectSubmission.this, "Empty field not allowed", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void createPopDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.confirmcard, null);

        CardView cardView = view.findViewById(R.id.confirmSubmitCardView);
        cancelSubmission = view.findViewById(R.id.cancelSubmissionButton);
        questionMark = view.findViewById(R.id.questionMark);
        textConfirmation = view.findViewById(R.id.textConfirmation);
        submitConfirmation = view.findViewById(R.id.confirmationButton);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        cancelSubmission = view.findViewById(R.id.cancelSubmissionButton);
        cancelSubmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


        submitConfirmation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = emailaddresss.getText().toString().trim();
                String firstName = firstname.getText().toString().trim();
                String lastName = lastname.getText().toString().trim();
                String projectLink = linkgithub.getText().toString().trim();

                executeSendFeedback(firstName, lastName, email, projectLink);

            }





        });

    }

    private void executeSendFeedback(String firstName, String lastName, String email, String linkGitHub){
        Client client = retrofit.create(Client.class);

        Call<ResponseBody> call = client.sendFeedback(
                firstName,
                lastName,
                email,
                linkGitHub
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                Toast.makeText(ProjectSubmission.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                Toast.makeText(ProjectSubmission.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}