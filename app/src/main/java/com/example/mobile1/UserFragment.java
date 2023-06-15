package com.example.mobile1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

public class UserFragment extends Fragment {

    private Button btnLogout;
    private ImageView imgFoto;
    private File photoFile;

    private Button btnConta;

    private ActivityResultLauncher<Uri> cameraLauncher;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);

        btnLogout = view.findViewById(R.id.btn_logout);
        imgFoto = view.findViewById(R.id.img_foto);
        btnConta = view.findViewById(R.id.btn_conta);

        // Configura o clique do botão de logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

                getActivity().finish();
            }
        });


        // Configura o clique da imagem de foto
        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // Permissão concedida, inicia a câmera
                    photoFile = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photo.jpg");
                    Uri photoUri = FileProvider.getUriForFile(requireContext(), "com.example.mobile1.fileprovider", photoFile);
                    cameraLauncher.launch(photoUri);
                } else {
                    // Permissão não concedida, solicita a permissão
                    ActivityCompat.requestPermissions(requireActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                }
            }
        });

        // Defina o ActivityResultLauncher
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
            if (result) {
                Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                imgFoto.setImageBitmap(bitmap);
            } else {
                // O usuário cancelou a captura da imagem
            }
        });

        btnConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), alterarDadosActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, inicia a câmera
                photoFile = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photo.jpg");
                Uri photoUri = FileProvider.getUriForFile(requireContext(), "com.example.mobile1.fileprovider", photoFile);
                cameraLauncher.launch(photoUri);
            } else {
                // Permissão não concedida, exibe uma mensagem de erro
                Toast.makeText(requireContext(), "Permissão de câmera não concedida", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
