package com.travel.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.App;
import com.travel.Database.UserDAO;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.GooogleCloudStorageHelper;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityPersonalInfoBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PersonalInfoActivity extends AppCompatActivity implements GooogleCloudStorageHelper.UploadImageListener {
    ActivityPersonalInfoBinding binding;
    private static final int REQUEST_IMAGE_PICK = 1;
    GooogleCloudStorageHelper cloudStorageHelper;
    UserDAO userDAO = new UserDAO();
    UserModel user = user = userDAO.getUser(1);
    Calendar myCalendar = Calendar.getInstance();
    UserModel userModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cloudStorageHelper = new GooogleCloudStorageHelper();
        userModel = SharePreferencesHelper.getInstance().get("user", UserModel.class);
        this.handleBottomNavigation();

        binding.editAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start image picker intent
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });
        setUser(user);
        hideEditText();
        binding.editNameImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUsername();
            }
        });
        binding.editDobImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDateOfBirth();
            }
        });
        binding.editPwImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });
        binding.editEmailImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmail();
            }
        });
        binding.editPhoneImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePhoneNumber();
            }
        });
        binding.editAreaImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddress();
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void hideEditText() {
        binding.name.setEnabled(false);
        binding.dob.setEnabled(false);
        binding.pw.setEnabled(false);
        binding.email.setEnabled(false);
        binding.phoneNum.setEnabled(false);
        binding.area.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            try {
                byte[] imageData = getImageData(selectedImageUri);
                GooogleCloudStorageHelper.uploadImageToStorage(this, imageData, this);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private byte[] getImageData(Uri imageUri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(imageUri);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private void saveImageUrlToDatabase(String imageUrl) {
        user.setUserId(userModel.getUserId());
        user.setAvatar(imageUrl);
        Glide.with(this).load(user.getAvatar()).error(R.drawable.profile_user).into(binding.editAvt);
        userDAO.updateImageProfile(user);
    }

    @Override
    public void onUploadSuccess(String imageUrl) {
    }

    @Override
    public void onUploadFailed() {
    }

    public void setUser(UserModel user) {
        binding.name.setText(user.getUsername());
        if (user.getDob() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dobString = dateFormat.format(user.getDob());
            binding.dob.setText(dobString);
        } else {
            binding.dob.setText("");
        }
        binding.pw.setText(user.getPassword());
        binding.email.setText(user.getEmail());
        binding.phoneNum.setText(user.getPhoneNumber());
        binding.area.setText(user.getAddress());
    }

    public void updateUsername() {
        final Dialog dialog = new Dialog(PersonalInfoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_name);

        EditText edtUsername = dialog.findViewById(R.id.edtUsername);
        Button btnLuu = dialog.findViewById(R.id.btnLuu);

        edtUsername.setText(user.getUsername());

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = edtUsername.getText().toString();
                user.setUsername(newUsername);
                userDAO.updateUserName(user);
                setUser(user);
                dialog.dismiss();
            }
        });
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void updateDateOfBirth() {
        final Dialog dialog = new Dialog(PersonalInfoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_dob);

        EditText edtDob = dialog.findViewById(R.id.edtDob);
        Button btnLuu = dialog.findViewById(R.id.btnLuu);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (user.getDob() != null) {
            String dobString = dateFormat.format(user.getDob());
            edtDob.setText(dobString);
        } else {
            edtDob.setText(dateFormat.format(Calendar.getInstance().getTime()));
        }
        ImageView imgCalendar=dialog.findViewById(R.id.calendar);
        DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String formattedDate = dateFormat.format(myCalendar.getTime());
                edtDob.setText(formattedDate);

            }
        };
        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PersonalInfoActivity.this, d,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dobString = String.valueOf(edtDob.getText());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date dob = dateFormat.parse(dobString);
                    user.setDob(dob);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                userDAO.updateDob(user);
                setUser(user);
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void updatePassword() {
        final Dialog dialog = new Dialog(PersonalInfoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_password);
        EditText edtPassword = dialog.findViewById(R.id.edtPassword);
        EditText edtPasswordAgain = dialog.findViewById(R.id.edtPasswordAgain);
        Button btnLuu = dialog.findViewById(R.id.btnLuu);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = edtPassword.getText().toString();
                String passwordAgain = edtPasswordAgain.getText().toString();
                if (!password.isEmpty() && !passwordAgain.isEmpty() && password.equals(passwordAgain)) {
                    btnLuu.setEnabled(true);
                } else {
                    btnLuu.setEnabled(false);
                }
            }
        };

        edtPassword.addTextChangedListener(textWatcher);
        edtPasswordAgain.addTextChangedListener(textWatcher);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = edtPassword.getText().toString();
                user.setPassword(newPassword);
                userDAO.updatePassword(user);
                setUser(user);
                dialog.dismiss();
            }
        });
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void updateEmail() {
        final Dialog dialog = new Dialog(PersonalInfoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_email);
        EditText edtEmail = dialog.findViewById(R.id.edtEmail);
        Button btnLuu = dialog.findViewById(R.id.btnLuu);
        btnLuu.setEnabled(false);
        edtEmail.setText(user.getEmail());
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = edtEmail.getText().toString();
                if (!email.isEmpty() && isValidEmail(email)) {
                    btnLuu.setEnabled(true);
                } else {
                    btnLuu.setEnabled(false);
                }
            }
        };
        edtEmail.addTextChangedListener(textWatcher);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = edtEmail.getText().toString();
                user.setEmail(newEmail);
                userDAO.updateEmail(user);
                setUser(user);
                dialog.dismiss();
            }
        });
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.com+";
        return email.matches(emailPattern);
    }


    public void updatePhoneNumber() {
        final Dialog dialog = new Dialog(PersonalInfoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_phone_number);
        EditText edtPhoneNumber = dialog.findViewById(R.id.edtPhoneNumber);
        Button btnLuu = dialog.findViewById(R.id.btnLuu);
        btnLuu.setEnabled(false);
        edtPhoneNumber.setText(user.getPhoneNumber());
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String phoneNumber = edtPhoneNumber.getText().toString();
                if (!phoneNumber.isEmpty() && isValidPhone(phoneNumber)) {
                    btnLuu.setEnabled(true);
                } else {
                    btnLuu.setEnabled(false);
                }
            }
        };
        edtPhoneNumber.addTextChangedListener(textWatcher);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPhoneNumber = edtPhoneNumber.getText().toString();
                user.setPhoneNumber(newPhoneNumber);
                userDAO.updatePhoneNumber(user);
                setUser(user);
                dialog.dismiss();
            }
        });
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private boolean isValidPhone(String phone) {
        String phonePattern = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
        return phone.matches(phonePattern);
    }
    public void updateAddress() {
        final Dialog dialog = new Dialog(PersonalInfoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_area);
        EditText edtAddress = dialog.findViewById(R.id.edtAddress);
        Button btnLuu = dialog.findViewById(R.id.btnLuu);
        edtAddress.setText(user.getAddress());
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String address = edtAddress.getText().toString();
                if (!address.isEmpty()) {
                    btnLuu.setEnabled(true);
                } else {
                    btnLuu.setEnabled(false);
                }
            }
        };
        edtAddress.addTextChangedListener(textWatcher);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newAddress = edtAddress.getText().toString();
                user.setAddress(newAddress);
                userDAO.updateAddress(user);
                setUser(user);
                dialog.dismiss();
            }
        });
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    private void handleBottomNavigation() {
        binding.navigation.setItemIconTintList(null);
        binding.navigation.setSelectedItemId(R.id.navigation_profile);
        binding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    intent = new Intent(PersonalInfoActivity.this, HomeActivity.class);
                } else if (id == R.id.navigation_favorite) {
                    intent = new Intent(PersonalInfoActivity.this, FavoriteActivity.class);
                } else if (id == R.id.navigation_map) {
                    intent = new Intent(PersonalInfoActivity.this, DestinationActivity.class);
                }else if (id == R.id.navigation_translate) {
                    intent = new Intent(PersonalInfoActivity.this, MapsActivity2.class);
                }
                else if (id == R.id.navigation_profile) {
                    return true;
                }
                if (intent != null) {
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });
    }
}

