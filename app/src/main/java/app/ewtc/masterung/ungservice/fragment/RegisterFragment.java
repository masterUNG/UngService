package app.ewtc.masterung.ungservice.fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.ewtc.masterung.ungservice.MainActivity;
import app.ewtc.masterung.ungservice.R;
import app.ewtc.masterung.ungservice.model.User;
import app.ewtc.masterung.ungservice.utility.MyAlert;

/**
 * Created by masterung on 4/11/2017 AD.
 */

public class RegisterFragment extends Fragment {

    private String nameString, emailString, passwordString;
    private String tag = "14novV1";
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();


    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(getString(R.string.new_register));

        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        ImageView imageView = getView().findViewById(R.id.imvSave);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nameEditText = getView().findViewById(R.id.edtName);
                EditText emailEditText = getView().findViewById(R.id.edtEmail);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);

                nameString = nameEditText.getText().toString().trim();
                emailString = emailEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                if (!nameString.isEmpty() && !emailString.isEmpty() && !passwordString.isEmpty()) {
//                    No Space
                    saveValueToFirebase();
                } else {
//                    Have Space
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog(getString(R.string.title_have_space),
                            getString(R.string.message_have_space));
                }


            }
        });

    }

    private void saveValueToFirebase() {

//        Show ProgressDialog
        showProgressDialog();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            //Success
                            Toast.makeText(getActivity(), "Register Success",
                                    Toast.LENGTH_SHORT).show();

//                            Storing Register User To Firebase
                            storeRegister();

//                            Back To MainFragment
                            getActivity().getSupportFragmentManager().popBackStack();

                        } else {
                            //Have Error
                            MyAlert myAlert = new MyAlert(getActivity());
                            myAlert.myDialog("Cannot Register",
                                    "Please Try Again Register False Because " +
                                            task.getException().getMessage());
                        }
                    }
                });

    }   // saveValueToFirebase

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please Wait ...");
        progressDialog.show();
    }

    private void storeRegister() {

        firebaseUser = firebaseAuth.getCurrentUser();

        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(nameString)
                .setPhotoUri(Uri.parse("http://androidthai.in.th/rmuts/Image/avata_masterUNG.jpg"))
                .build();

        firebaseUser.updateProfile(userProfileChangeRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                User user = new User(
                        firebaseUser.getDisplayName(),
                        firebaseUser.getEmail(),
                        firebaseUser.getPhotoUrl().toString(),
                        firebaseUser.getUid());

                databaseReference = FirebaseDatabase.getInstance()
                        .getReference().child("Users");
                databaseReference.child(firebaseUser.getUid()).setValue(user);

                showLog(firebaseUser);

            }
        });



    }   // storeRegister

    private void showLog(FirebaseUser firebaseUser) {

        Log.d(tag, "displayName ==> " + firebaseUser.getDisplayName());
        Log.d(tag, "urlImage ==> " + firebaseUser.getPhotoUrl().toString());
        Log.d(tag, "eMail ==> " + firebaseUser.getEmail());
        Log.d(tag, "userID ==> " + firebaseUser.getUid());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }
}
