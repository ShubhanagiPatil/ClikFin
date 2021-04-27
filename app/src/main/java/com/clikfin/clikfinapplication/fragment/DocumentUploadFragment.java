package com.clikfin.clikfinapplication.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clikfin.clikfinapplication.BuildConfig;
import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;
import com.clikfin.clikfinapplication.constants.Constants;
import com.clikfin.clikfinapplication.externalRequests.Response.UploadDocName;
import com.clikfin.clikfinapplication.externalRequests.Response.UploadDocumentErrorResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.UploadDocumentResponse;
import com.clikfin.clikfinapplication.network.APICallbackInterface;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.Common;
import com.clikfin.clikfinapplication.util.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;

public class DocumentUploadFragment extends Fragment {
    LinearLayout layBankStatements;
    Button btnUploadDocDone;
    TextView tvPanUpload, tvAadharUpload, tvResidencyProofUpload, tvBankStatementUpload, tvPaySlip, tvPhotoUpload, tvCompanyIDUpload;
    final int PERMISSION_REQUES_CODE = 101;
    TextView tvIsPanMandatory, tvIsAadharFrontMandatory, tvIsAadharBackMandatory, tvIsResidencyProofMandatory, tvIs1MonthsBankStateMandatory, tvIs2MonthsBankStateMandatory, tvIs3MonthsBankStateMandatory, tvIs1MonthPaySlipMandatory, tvIs2MonthPaySlipMandatory, tvIs3MonthPaySlipMandatory, tvIsPhotoUploadMandatory, tvIsCompanyIDUploadMandatory;
    TextView tvUploadDocName, tvUploadDocMsg;
    Button btnSelectFileTOUpload, btnCancelFileToUpload, btnUploadFile;
    ImageView imgCamera;
    static Uri fileUri;
    File file;
    CheckBox chkFilePassword;

    EditText edFilePassword;
    Dialog dialog;
    static String uploadDocName, uploadDocType;
    public static UploadDocumentResponse documentResponse;
    ImageView imgPanUpload, imgAadharFrontUpload, imgAadharBackUpload, imgResidencyProofUpload, img_1MonthsBankStateUpload, img_2MonthsBankStateUpload, img_3MonthsBankStateUpload, img1MonthPaySlip, img2MonthPaySlip, img3MonthPaySlip, imgPhotoUpload, imgCompanyIDUpload;
    static FragmentActivity activity;
    static Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof FragmentActivity) {
            activity = (FragmentActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_documents, container, false);
        // layBankStatements = view.findViewById(R.id.layBankStatements);
        btnUploadDocDone = view.findViewById(R.id.btnUploadDocDone);
        tvPanUpload = view.findViewById(R.id.tvPanUpload);
        tvAadharUpload = view.findViewById(R.id.tvAadharUpload);
        tvResidencyProofUpload = view.findViewById(R.id.tvResidencyProofUpload);
        tvBankStatementUpload = view.findViewById(R.id.tvBankStatementUpload);
        tvPaySlip = view.findViewById(R.id.tvPaySlip);
        tvPhotoUpload = view.findViewById(R.id.tvPhotoUpload);
        tvCompanyIDUpload = view.findViewById(R.id.tvCompanyIDUpload);

        imgPanUpload = view.findViewById(R.id.imgPanUpload);
        imgAadharFrontUpload = view.findViewById(R.id.imgAadharFrontUpload);
        imgAadharBackUpload = view.findViewById(R.id.imgAadharBackUpload);
        imgResidencyProofUpload = view.findViewById(R.id.imgResidencyProofUpload);
        //tv3MonthsBankStateUpload = view.findViewById(R.id.tv3MonthsBankStateUpload);
        img_1MonthsBankStateUpload = view.findViewById(R.id.img_1MonthsBankStateUpload);
        img_2MonthsBankStateUpload = view.findViewById(R.id.img_2MonthsBankStateUpload);
        img_3MonthsBankStateUpload = view.findViewById(R.id.img_3MonthsBankStateUpload);
        img1MonthPaySlip = view.findViewById(R.id.img1MonthPaySlip);
        img2MonthPaySlip = view.findViewById(R.id.img2MonthPaySlip);
        img3MonthPaySlip = view.findViewById(R.id.img3MonthPaySlip);
        imgPhotoUpload = view.findViewById(R.id.imgPhotoUpload);
        imgCompanyIDUpload = view.findViewById(R.id.imgCompanyIDUpload);

        tvIsPanMandatory = view.findViewById(R.id.tvIsPanMandatory);
        tvIsAadharFrontMandatory = view.findViewById(R.id.tvIsAadharFrontMandatory);
        tvIsAadharBackMandatory = view.findViewById(R.id.tvIsAadharBackMandatory);
        tvIsResidencyProofMandatory = view.findViewById(R.id.tvIsResidencyProofMandatory);
        tvIs1MonthsBankStateMandatory = view.findViewById(R.id.tvIs1MonthsBankStateMandatory);
        tvIs2MonthsBankStateMandatory = view.findViewById(R.id.tvIs2MonthsBankStateMandatory);
        tvIs3MonthsBankStateMandatory = view.findViewById(R.id.tvIs3MonthsBankStateMandatory);
        tvIs1MonthPaySlipMandatory = view.findViewById(R.id.tvIs1MonthPaySlipMandatory);
        tvIs2MonthPaySlipMandatory = view.findViewById(R.id.tvIs2MonthPaySlipMandatory);
        tvIs3MonthPaySlipMandatory = view.findViewById(R.id.tvIs3MonthPaySlipMandatory);
        tvIsPhotoUploadMandatory = view.findViewById(R.id.tvIsPhotoUploadMandatory);
        tvIsCompanyIDUploadMandatory = view.findViewById(R.id.tvIsCompanyIDUploadMandatory);

        checkAndRequestPermissions();
        if (Common.isNetworkConnected(context)) {
            getUploadDocument();
        } else {
            Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);

        imgPanUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_upload_pan_card), false)) {
                    uploadDocType = getString(R.string.doc_upload_pan_card);
                    showUploadDialog(tvPanUpload.getText().toString());
                }
            }
        });
        imgAadharFrontUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_aadhar_front), false)) {
                    uploadDocType = getString(R.string.doc_aadhar_front);
                    showUploadDialog(tvAadharUpload.getText().toString());
                }
            }
        });
        imgAadharBackUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_aadhar_back), false)) {
                    uploadDocType = getString(R.string.doc_aadhar_back);
                    showUploadDialog(tvAadharUpload.getText().toString());
                }

            }
        });
        imgResidencyProofUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_current_residency_proof), false)) {
                    uploadDocType = getString(R.string.doc_current_residency_proof);
                    showUploadDialog(tvResidencyProofUpload.getText().toString());
                }
            }
        });

        img_1MonthsBankStateUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_1), false)) {
                    uploadDocType = getString(R.string.doc_bank_statement_1);
                    showUploadDialog(tvBankStatementUpload.getText().toString());
                }
            }
        });
        img_2MonthsBankStateUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_2), false)) {
                    uploadDocType = getString(R.string.doc_bank_statement_2);
                    showUploadDialog(tvBankStatementUpload.getText().toString());
                }
            }
        });
        img_3MonthsBankStateUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_3), false)) {
                    uploadDocType = getString(R.string.doc_bank_statement_3);
                    showUploadDialog(tvBankStatementUpload.getText().toString());
                }
            }
        });
        img1MonthPaySlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_1), false)) {
                    uploadDocType = getString(R.string.doc_pay_slip_1);
                    showUploadDialog(tvPaySlip.getText().toString());
                }
            }
        });
        img2MonthPaySlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_2), false)) {
                    uploadDocType = getString(R.string.doc_pay_slip_2);
                    showUploadDialog(tvPaySlip.getText().toString());
                }
            }
        });
        img3MonthPaySlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_3), false)) {
                    uploadDocType = getString(R.string.doc_pay_slip_3);
                    showUploadDialog(tvPaySlip.getText().toString());
                }
            }
        });
        imgPhotoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_photo), false)) {
                    uploadDocType = getString(R.string.doc_photo);
                    showUploadDialog(tvPhotoUpload.getText().toString());
                }
            }
        });
        imgCompanyIDUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_company_id), false)) {
                    uploadDocType = getString(R.string.doc_company_id);
                    showUploadDialog(tvCompanyIDUpload.getText().toString());
                }
            }
        });

        btnUploadDocDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAllMandatoryDocUploaded()) {
                    postAllDocumentUploadStatus();
                } else {
                    Toast.makeText(context, getString(R.string.upload_doc_msg), Toast.LENGTH_LONG).show();
                }
            }
        });
        ((DashboardActivity) context).setNavigationTitle(getString(R.string.title_upload_documents));
        return view;

    }

    private void postAllDocumentUploadStatus() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        String loanApplicationId = sharedPreferences.getString(getString(R.string.loan_application_id), "");
        String url = APIClient.BASE_URL + "/application/" + loanApplicationId + "/" + getString(R.string.under_review);
        Call<Void> call = APIClient.getClient(APIClient.type.JSON).postAllDocumentUploadStatus(url, authToken);
        call.enqueue(new APICallbackInterface<Void>(context) {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                super.onResponse(call, response);
                if (response.code() == 200) {
                    SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(activity);
                    editor.putString(getString(R.string.loan_application_status), getString(R.string.under_review));
                    editor.apply();
                    replaceFragment(new LoanApplicationStatusFragment());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, "" + getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        //+++++++++++++ fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        int write = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (write != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_REQUES_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUES_CODE: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for  permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        //permissions are granted already
                    } else {
                        //ask permissions
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage Permission required for this app OR you will exit from app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    int pid = android.os.Process.myPid();
                                                    android.os.Process.killProcess(pid);
                                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                                    intent.addCategory(Intent.CATEGORY_HOME);
                                                    startActivity(intent);
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(context, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Close App", okListener)
                .create()
                .show();
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStorageDirectory(),
                "ClikFin");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Toast.makeText(context, "Oops! Failed create "
                        + "ClikFin" + " directory", Toast.LENGTH_LONG).show();
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == Constants.MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }


    private void setIsDocumentUploadedImg() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_upload_pan_card), false)) {
            imgPanUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_current_residency_proof), false)) {
            imgResidencyProofUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_1), false)) {
            img1MonthPaySlip.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_2), false)) {
            img2MonthPaySlip.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_3), false)) {
            img3MonthPaySlip.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_1), false)) {
            img_1MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_2), false)) {
            img_2MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_3), false)) {
            img_3MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_aadhar_front), false)) {
            imgAadharFrontUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_aadhar_back), false)) {
            imgAadharBackUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_photo), false)) {
            imgPhotoUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_company_id), false)) {
            imgCompanyIDUpload.setImageResource(R.drawable.ic_ok);
        }
    }


    private void setMandatoryDocuments() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_upload_pan_card), true)) {
            tvIsPanMandatory.setText(tvIsPanMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_current_residency_proof), true)) {
            tvIsResidencyProofMandatory.setText(tvIsResidencyProofMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_pay_slip_1), true)) {
            tvIs1MonthPaySlipMandatory.setText(tvIs1MonthPaySlipMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_pay_slip_2), true)) {
            tvIs2MonthPaySlipMandatory.setText(tvIs2MonthPaySlipMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_pay_slip_3), true)) {
            tvIs3MonthPaySlipMandatory.setText(tvIs3MonthPaySlipMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_bank_statement_1), true)) {
            tvIs1MonthsBankStateMandatory.setText(tvIs1MonthsBankStateMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_bank_statement_2), true)) {
            tvIs2MonthsBankStateMandatory.setText(tvIs2MonthsBankStateMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_bank_statement_3), true)) {
            tvIs3MonthsBankStateMandatory.setText(tvIs3MonthsBankStateMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_aadhar_front), true)) {
            tvIsAadharFrontMandatory.setText(tvIsAadharFrontMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_aadhar_back), true)) {
            tvIsAadharBackMandatory.setText(tvIsAadharBackMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_photo), true)) {
            tvIsPhotoUploadMandatory.setText(tvIsPhotoUploadMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_company_id), true)) {
            tvIsCompanyIDUploadMandatory.setText(tvIsCompanyIDUploadMandatory.getText() + " *");
        }
    }

    private boolean checkAllMandatoryDocUploaded() {
        boolean returnValue = true;
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_upload_pan_card), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_upload_pan_card), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_current_residency_proof), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_current_residency_proof), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_pay_slip_1), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_1), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_pay_slip_2), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_2), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_pay_slip_3), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_3), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_bank_statement_1), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_1), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_bank_statement_2), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_2), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_bank_statement_3), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_3), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_aadhar_front), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_aadhar_front), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_aadhar_back), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_aadhar_back), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_photo), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_photo), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_company_id), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_company_id), false)) {
            return false;
        }
        return returnValue;
    }


    private void postDocumentUpload(String url) throws IOException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(activity);
        RequestBody requestFile = null;
        if (file != null)
            requestFile = RequestBody.create(MediaType.parse(context.getContentResolver().getType(fileUri)), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("document", URLEncoder.encode(file.getName(), "utf-8"), requestFile);
        String uploadFileName = tvUploadDocName.getText().toString().replace("*", "");
        String uploadFilePassword = edFilePassword.getText().toString();

        Call<UploadDocumentResponse> call = APIClient.getClient(APIClient.type.JSON).putDocumentUpload(filePart, url, authToken, uploadFileName, uploadFilePassword);
        call.enqueue(new APICallbackInterface<UploadDocumentResponse>(context) {
            @Override
            public void onResponse(Call<UploadDocumentResponse> call, Response<UploadDocumentResponse> response) {
                super.onResponse(call, response);

                switch (response.code()) {
                    case 201:
                        dialog.dismiss();
                        if (response.body() != null) {
                            UploadDocumentResponse uploadDocumentResponse = response.body();
                            editor.putBoolean(getString(R.string.isUpload_upload_pan_card), uploadDocumentResponse.getPAN_CARD().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_current_residency_proof), uploadDocumentResponse.getCURRENT_ADDRESS_PROOF().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_pay_slip_1), uploadDocumentResponse.getPAY_SLIP_1().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_pay_slip_2), uploadDocumentResponse.getPAY_SLIP_2().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_pay_slip_3), uploadDocumentResponse.getPAY_SLIP_3().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_bank_statement_1), uploadDocumentResponse.getBANK_STATEMENT_1().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_bank_statement_2), uploadDocumentResponse.getBANK_STATEMENT_2().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_bank_statement_3), uploadDocumentResponse.getBANK_STATEMENT_3().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_aadhar_front), uploadDocumentResponse.getAADHAAR_CARD_FRONT().isUploaded());
                           editor.putBoolean(getString(R.string.isUpload_aadhar_back), uploadDocumentResponse.getAADHAAR_CARD_BACK().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_photo), uploadDocumentResponse.getPHOTO().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_company_id), uploadDocumentResponse.getCOMPANY_ID().isUploaded());

                            editor.putBoolean(getString(R.string.isMandatory_upload_pan_card), uploadDocumentResponse.getPAN_CARD().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_current_residency_proof), uploadDocumentResponse.getCURRENT_ADDRESS_PROOF().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_pay_slip_1), uploadDocumentResponse.getPAY_SLIP_1().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_pay_slip_2), uploadDocumentResponse.getPAY_SLIP_2().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_pay_slip_3), uploadDocumentResponse.getPAY_SLIP_3().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_bank_statement_1), uploadDocumentResponse.getBANK_STATEMENT_1().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_bank_statement_2), uploadDocumentResponse.getBANK_STATEMENT_2().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_bank_statement_3), uploadDocumentResponse.getBANK_STATEMENT_3().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_aadhar_front), uploadDocumentResponse.getAADHAAR_CARD_FRONT().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_aadhar_back), uploadDocumentResponse.getAADHAAR_CARD_BACK().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_photo), uploadDocumentResponse.getPHOTO().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_company_id), uploadDocumentResponse.getCOMPANY_ID().isMandatory());


                            editor.apply();

                            documentResponse = response.body();

                            if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_current_residency_proof))) {
                                if (uploadDocumentResponse.getCURRENT_ADDRESS_PROOF().isUploaded()) {

                                    imgResidencyProofUpload.setImageResource(R.drawable.ic_ok);


                                    Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                                }
                            } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_aadhar_front))) {
                                if (uploadDocumentResponse.getAADHAAR_CARD_FRONT().isUploaded()) {
                                    Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_LONG).show();
                                    imgAadharFrontUpload.setImageResource(R.drawable.ic_ok);

                                } else {
                                    Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                                }
                            } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_aadhar_back))) {
                                if (uploadDocumentResponse.getAADHAAR_CARD_BACK().isUploaded()) {
                                    Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_LONG).show();

                                    imgAadharBackUpload.setImageResource(R.drawable.ic_ok);


                                } else {
                                    Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                                }
                            } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_upload_pan_card))) {
                                if (uploadDocumentResponse.getPAN_CARD().isUploaded()) {

                                    imgPanUpload.setImageResource(R.drawable.ic_ok);

                                    Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                                }
                            } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_1))) {
                                if (uploadDocumentResponse.getBANK_STATEMENT_1().isUploaded()) {

                                    img_1MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);

                                    Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                                }
                            } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_2))) {
                                if (uploadDocumentResponse.getBANK_STATEMENT_2().isUploaded()) {

                                    img_2MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);

                                    Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                                }
                            } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_3))) {
                                if (uploadDocumentResponse.getBANK_STATEMENT_3().isUploaded()) {

                                    img_3MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);

                                    Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                                }
                            } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_1))) {
                                if (uploadDocumentResponse.getPAY_SLIP_1().isUploaded()) {

                                    img1MonthPaySlip.setImageResource(R.drawable.ic_ok);

                                    Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                                }
                            } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_2))) {
                                if (uploadDocumentResponse.getPAY_SLIP_2().isUploaded()) {

                                    img2MonthPaySlip.setImageResource(R.drawable.ic_ok);

                                    Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                                }
                            } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_3))) {
                                if (uploadDocumentResponse.getPAY_SLIP_3().isUploaded()) {

                                    img3MonthPaySlip.setImageResource(R.drawable.ic_ok);

                                    Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                                }
                            } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_photo))) {
                                if (uploadDocumentResponse.getPHOTO().isUploaded()) {

                                    imgPhotoUpload.setImageResource(R.drawable.ic_ok);

                                    Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                                }
                            } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_company_id))) {
                                if (uploadDocumentResponse.getCOMPANY_ID().isUploaded()) {


                                    imgCompanyIDUpload.setImageResource(R.drawable.ic_ok);

                                    Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        break;
                    case 403:
                    case 401:
                        dialog.dismiss();
                        Toast.makeText(context, getString(R.string.logged_out), Toast.LENGTH_LONG).show();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentManager.popBackStack();
                        fragmentTransaction.replace(R.id.content_frame, new LoginFragment());
                        fragmentTransaction.commitAllowingStateLoss();
                        break;
                    case 400:
                    case 413:
                        dialog.dismiss();
                        if (response.errorBody() != null) {
                            Converter<ResponseBody, UploadDocumentErrorResponse> PersonalDetailsConverter = APIClient.getRetrofit().responseBodyConverter(UploadDocumentErrorResponse.class, new Annotation[0]);
                            try {
                                Toast.makeText(context, PersonalDetailsConverter.convert(response.errorBody()).getError(), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Common.logExceptionToCrashlaytics(e);
                            }
                        }
                        break;
                    case 409:
                        dialog.dismiss();
                        Toast.makeText(context, uploadDocName + " already uploaded", Toast.LENGTH_LONG).show();
                        if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_current_residency_proof))) {
                            imgResidencyProofUpload.setImageResource(R.drawable.ic_ok);
                            editor.putBoolean(getString(R.string.isUpload_current_residency_proof), true);
                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_aadhar_front))) {
                            imgAadharFrontUpload.setImageResource(R.drawable.ic_ok);
                            editor.putBoolean(getString(R.string.isUpload_aadhar_front), true);
                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_aadhar_back))) {
                            editor.putBoolean(getString(R.string.isUpload_aadhar_back), true);
                            imgAadharBackUpload.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_upload_pan_card))) {
                            editor.putBoolean(getString(R.string.isUpload_upload_pan_card), true);
                            imgPanUpload.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_1))) {
                            editor.putBoolean(getString(R.string.isUpload_bank_statement_1), true);
                            img_1MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_2))) {
                            editor.putBoolean(getString(R.string.isUpload_bank_statement_2), true);
                            img_2MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_3))) {
                            editor.putBoolean(getString(R.string.isUpload_bank_statement_3), true);
                            img_3MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_1))) {
                            editor.putBoolean(getString(R.string.isUpload_pay_slip_1), true);
                            img1MonthPaySlip.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_2))) {
                            editor.putBoolean(getString(R.string.isUpload_pay_slip_2), true);
                            img2MonthPaySlip.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_3))) {
                            editor.putBoolean(getString(R.string.isUpload_pay_slip_3), true);
                            img3MonthPaySlip.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_photo))) {
                            editor.putBoolean(getString(R.string.isUpload_photo), true);
                            imgPhotoUpload.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_company_id))) {
                            editor.putBoolean(getString(R.string.isUpload_company_id), true);
                            imgCompanyIDUpload.setImageResource(R.drawable.ic_ok);

                        }
                        editor.apply();
                        break;
                    case 500:
                        dialog.dismiss();
                        Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        break;

                }
            }

            @Override
            public void onFailure(Call<UploadDocumentResponse> call, Throwable t) {
                super.onFailure(call, t);
                dialog.dismiss();
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                Common.logAPIFailureToCrashlyatics(t);

            }
        });
    }

    private void showUploadDialog(String docName) {
        uploadDocName = docName;

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_upload_doc);
        tvUploadDocName = dialog.findViewById(R.id.tvUploadDocName);
        tvUploadDocMsg = dialog.findViewById(R.id.tvUploadDocMsg);
        btnSelectFileTOUpload = dialog.findViewById(R.id.btnSelectFileToUpload);
        btnCancelFileToUpload = dialog.findViewById(R.id.btnCancelFileToUpload);
        btnUploadFile = dialog.findViewById(R.id.btnUploadFile);
        imgCamera = dialog.findViewById(R.id.imgCamera);
        chkFilePassword = dialog.findViewById(R.id.chkFilePassword);
        edFilePassword = dialog.findViewById(R.id.edFilePassword);
        tvUploadDocName.setText(docName);
        dialog.setCancelable(false);
        dialog.show();
        Intent picIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT, null);
        if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_1)) || uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_2)) || uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_3)) || uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_1)) || uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_2)) || uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_3))) {
            imgCamera.setVisibility(View.GONE);
            picIntent.setType("application/pdf");
            tvUploadDocMsg.setText(getString(R.string.tv_upload_doc_msg));
        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_photo)) || uploadDocType.equalsIgnoreCase(getString(R.string.doc_company_id))) {
            picIntent.setType("image/*");
            tvUploadDocMsg.setText(getString(R.string.tv_upload_image_doc_msg));
        } else {
            String[] mimeTypes = {"image/*","application/pdf"};
            imgCamera.setVisibility(View.VISIBLE);
            tvUploadDocMsg.setText("");
            picIntent.setType("*/*");
            picIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        }
        chkFilePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edFilePassword.setVisibility(View.VISIBLE);
                } else {
                    edFilePassword.setVisibility(View.GONE);
                }
            }
        });
        btnSelectFileTOUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 picIntent.putExtra("return_data", true);
                startActivityForResult(picIntent, Constants.SELECT_DOC_FILE_CODE);
            }
        });
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    captureImage();
                } catch (IOException e) {
                    Common.logExceptionToCrashlaytics(e);
                }
            }
        });
        btnCancelFileToUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
                String loanApplicationId = sharedPreferences.getString(getString(R.string.loan_application_id), "");
                String url = APIClient.BASE_URL + "/application/" + loanApplicationId + "/upload/" + uploadDocType;
                try {
                    if (file != null) {

                        if (Common.isNetworkConnected(context)) {
                            postDocumentUpload(url);
                        } else {
                            Common.networkDisconnectionDialog(context);
                        }

                    } else {
                        Toast.makeText(context, "Please select file to upload", Toast.LENGTH_LONG).show();
                    }

                } catch (IOException e) {
                    Common.logExceptionToCrashlaytics(e);
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;
        if (requestCode == Constants.SELECT_DOC_FILE_CODE) {
            if (data != null) {
                try {

                    fileUri = data.getData();
                    file = new File(FileUtils.getRealPath(context, fileUri));
                    String fileType = context.getContentResolver().getType(fileUri);
                    int size = Integer.parseInt(String.valueOf(file.length() / 1024));
                    if (size > 5000) {
                        tvUploadDocMsg.setText(getString(R.string.file_size_error));
                    } else if (!(fileType.contains("pdf") || fileType.contains("png") || fileType.contains("jpg") || fileType.contains("jpeg"))) {
                        tvUploadDocMsg.setText(getString(R.string.file_format_error));
                    } else {
                        btnSelectFileTOUpload.setVisibility(View.GONE);
                        btnUploadFile.setVisibility(View.VISIBLE);
                        tvUploadDocMsg.setText(file.getPath());

                    }
                    if (fileType.contains("pdf")) {
                        chkFilePassword.setVisibility(View.VISIBLE);
                    } else {
                        chkFilePassword.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Common.logExceptionToCrashlaytics(e);
                }
            }

        } else if (requestCode == Constants.CAMERA_REQUEST_CODE) {
            chkFilePassword.setVisibility(View.GONE);
            edFilePassword.setVisibility(View.GONE);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), fileUri);
            } catch (IOException e) {
                Common.logExceptionToCrashlaytics(e);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                fileUri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "attachment", null));
                file = new File(getRealPathFromURI(fileUri));
                String fileType = context.getContentResolver().getType(fileUri);
                int size = Integer.parseInt(String.valueOf(file.length() / 1024));
                if (size > 5000) {
                    tvUploadDocMsg.setText(getString(R.string.file_size_error));
                } else if (!(fileType.contains("png") || fileType.contains("jpg") || fileType.contains("jpeg"))) {
                    tvUploadDocMsg.setText(getString(R.string.file_format_error));
                } else {
                    btnSelectFileTOUpload.setVisibility(View.GONE);
                    btnUploadFile.setVisibility(View.VISIBLE);
                    tvUploadDocMsg.setText(file.getPath());
                }

            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cur = context.getContentResolver().query(uri, null, null, null, null);
        cur.moveToFirst();
        return cur.getString(cur.getColumnIndex("_data"));
    }

    public void captureImage() throws IOException {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        fileUri = FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider",
                getOutputMediaFile(Constants.MEDIA_TYPE_IMAGE));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, Constants.CAMERA_REQUEST_CODE);
    }


    private void getUploadDocument() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        String loanApplicationId = sharedPreferences.getString(getString(R.string.loan_application_id), "");
        String url = APIClient.BASE_URL + "/application/" + loanApplicationId + "/uploadDocument/type";
        Call<UploadDocName> call = APIClient.getClient(APIClient.type.JSON).getUploadDocument(url, authToken);
        call.enqueue(new APICallbackInterface<UploadDocName>(context) {
            @Override
            public void onResponse(Call<UploadDocName> call, Response<UploadDocName> response) {
                super.onResponse(call, response);
                if (response.body() != null) {
                    tvResidencyProofUpload.setText(response.body().getCURRENT_ADDRESS_PROOF());
                    setIsDocumentUploadedImg();
                    setMandatoryDocuments();
                }
            }

            @Override
            public void onFailure(Call<UploadDocName> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();
                Common.logAPIFailureToCrashlyatics(t);
            }
        });
    }


}
