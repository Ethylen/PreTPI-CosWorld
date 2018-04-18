/**
 * Created by tiffany.di-domenico on 13.03.2018.
 * Accueil_CPW
 * Source :
 * https://stackoverflow.com/questions/26129173/insert-radio-button-value-in-to-database
 * https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
 * https://www.youtube.com/watch?v=LHMGamTPEpE
 */

package cpnv.com.accueil_cpw;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    /** DECLARE LES VARIABLES **/

    //Base de donnee
    DatabaseManager cosWorldDB;

    //Titre
    TextView mPseudoText;
    TextView mGenreText;
    TextView mPaysText;
    TextView mDescriptionText;

    //Image
    ImageView mImage1,mImage2, mImage3, mImage4, mImage5, mImage6;
    //BoutonImage
    Button mAddbtn1, mAddbtn2, mAddbtn3, mAddbtn4, mAddbtn5, mAddbtn6;

    //Input
    EditText mPseudoInput;
    EditText mDescriptionInput;

    //Radio
    RadioGroup mGenreGroup;
    RadioButton mFemmeRadio,mHommeRadio;
    String getFinalRadio = "";

    //Spinner (Select)
    Spinner mPaysSpinner;
    String getPays;
    int getPos;

    //BoutonValider
    Button mSubBtn;

    private static final int PICK_IMAGE = 100;
    Uri imageUri1, imageUri2, imageUri3, imageUri4, imageUri5, imageUri6;
    int selectedImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //DATABASE
        cosWorldDB = new DatabaseManager(this);


        //Titre
        mPseudoText = (TextView) findViewById(R.id.tvPseudo);
        mGenreText = (TextView) findViewById(R.id.tvGenre);
        mPaysText = (TextView) findViewById(R.id.tvPays);
        mDescriptionText = (TextView) findViewById(R.id.tvDescription);

        //Image
        mImage1 = (ImageView) findViewById(R.id.ivImage1);
        mImage2 = (ImageView) findViewById(R.id.ivImage2);
        mImage3 = (ImageView) findViewById(R.id.ivImage3);
        mImage4 = (ImageView) findViewById(R.id.ivImage4);
        mImage5 = (ImageView) findViewById(R.id.ivImage5);
        mImage6 = (ImageView) findViewById(R.id.ivImage6);
        //BoutonAdd
        mAddbtn1 = (Button) findViewById(R.id.btnAdd1);
        mAddbtn2 = (Button) findViewById(R.id.btnAdd2);
        mAddbtn3 = (Button) findViewById(R.id.btnAdd3);
        mAddbtn4 = (Button) findViewById(R.id.btnAdd4);
        mAddbtn5 = (Button) findViewById(R.id.btnAdd5);
        mAddbtn6 = (Button) findViewById(R.id.btnAdd6);

        //Champs text
        mPseudoInput = (EditText) findViewById(R.id.etxtPseudo);
        mDescriptionInput = (EditText) findViewById(R.id.etxtDescription);

        //Radio button
        mGenreGroup = (RadioGroup) findViewById(R.id.RadioFH);
        mFemmeRadio = (RadioButton) findViewById(R.id.radioFemme);
        mHommeRadio = (RadioButton) findViewById(R.id.radioHomme);

        //Bouton valider
        mSubBtn = (Button) findViewById(R.id.btnValider);


        /** SPINNER **/
        //Recuperation du spinner declarer
        mPaysSpinner = (Spinner) findViewById(R.id.SpinnerPays);

        //Création d'une liste
        List<String> PaysList = new ArrayList<String>();
        Cursor dPays = cosWorldDB.showPays();
        if (dPays.getCount() == 0){
            Toast.makeText(EditActivity.this, "PAYS IS EMPTY", Toast.LENGTH_SHORT).show();
        }else{
            while (dPays.moveToNext()){
                PaysList.add(dPays.getString(1));
            }
        }


        //Le Spinner a besoin d'un adapter, on lui passe le contexte (this) et un fichier de presentation, ainsi que la liste
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                PaysList
        );

        //On definit la présentation du spinner quand il est deroule
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Adapter du spinner
        mPaysSpinner.setAdapter(adapter);
        /** SPINNER END **/


        mAddbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = 1;
                openGallery();
            }
        });
        mAddbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = 2;
                openGallery();
            }
        });
        mAddbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = 3;
                openGallery();
            }
        });
        mAddbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = 4;
                openGallery();
            }
        });
        mAddbtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = 5;
                openGallery();
            }
        });
        mAddbtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = 6;
                openGallery();
            }
        });

        //Appelle la fonction pour afficher les data de la database
        Cursor data = cosWorldDB.showData();

        //Controle que la database soit vide ou non
        if (data.getCount() == 0){
            AddData(); //Appelle la fonction d'ajout des datas
        }else{ //Si la database n'est pas vide, affiche les datas
            while (data.moveToNext()) { //Cherche dans la database
                mPseudoInput.setText(data.getString(1)); //Le pseudo
                mHommeRadio.setChecked(data.getString(2).equals("♂"));
                mFemmeRadio.setChecked(data.getString(2).equals("♀"));
                mPaysSpinner.setSelection(data.getInt(5));
                mDescriptionInput.setText(data.getString(4));//La description
                mImage1.setImageURI(Uri.parse(data.getString(6)));
                mImage2.setImageURI(Uri.parse(data.getString(7)));
                mImage3.setImageURI(Uri.parse(data.getString(8)));
                mImage4.setImageURI(Uri.parse(data.getString(9)));
                mImage5.setImageURI(Uri.parse(data.getString(10)));
                mImage6.setImageURI(Uri.parse(data.getString(11)));
                //Appelle la fonction d'update en cas de modifications
            }
            UpdateData();
        }

    }



    //Fonction d'ajout des datas
    public void AddData () {
        mSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Au clique du bouton valider

//                Toast.makeText(getApplicationContext(), "AJOUT DE DONNEES ICI", Toast.LENGTH_SHORT).show();

                //Appelle la fonction pour afficher les datas
                Cursor data = cosWorldDB.showData();

               if (mHommeRadio.isChecked()){
                   getFinalRadio = "♂";
               }else if(mFemmeRadio.isChecked()) {
                   getFinalRadio = "♀";
                }

                getPays =  mPaysSpinner.getSelectedItem().toString();
                getPos = mPaysSpinner.getSelectedItemPosition();


                String id = "1"; //définit l'ID à 1

                // Controle que la database soit vide
                if (data.getCount() == 0) {
                    //Insert les datas
                    boolean insertData = cosWorldDB.addData(id,
                            mPseudoInput.getText().toString(),
                            getFinalRadio,
                            getPays,
                            mDescriptionInput.getText().toString(),
                            getPos,
                            imageUri1.toString(),
                            imageUri2.toString(),
                            imageUri3.toString(),
                            imageUri4.toString(),
                            imageUri5.toString(),
                            imageUri6.toString()
                    );

                    //Si return true (voir la base)
                    if (insertData== true) {
                        Toast.makeText(EditActivity.this, "Data ajoutee avec succes", Toast.LENGTH_SHORT).show(); //Affiche un message de reussite
                    }else{
                        Toast.makeText(EditActivity.this, "Data fausses", Toast.LENGTH_SHORT).show(); //Affiche un message d'erreur
                    }
                }

                //Retourne a la page d'accueil
                Intent mainReturnActivity = new Intent(EditActivity.this, MainActivity.class);
                startActivity(mainReturnActivity);
            }
        });
    }

    //Fonction de MAJ
    public void UpdateData() {
        mSubBtn.setOnClickListener(new View.OnClickListener() { //Au clique du bouton valider
            @Override
            public void onClick(View v) {

                if (mHommeRadio.isChecked()){
                    getFinalRadio = "♂";
                }else if(mFemmeRadio.isChecked()) {
                    getFinalRadio = "♀";
                }

                getPays =  mPaysSpinner.getSelectedItem().toString();
                getPos = mPaysSpinner.getSelectedItemPosition();

                //Update les donnee
                boolean update = cosWorldDB.updateData(mPseudoInput.getText().toString(),
                        getFinalRadio,
                        getPays,
                        mDescriptionInput.getText().toString(),
                        getPos);

                //Si retourne true
                if (update == true){
                    //Message de reussite
                    Toast.makeText(EditActivity.this, "Update Data OK!", Toast.LENGTH_SHORT).show();
                }else{
                    //Message d'echec
                    Toast.makeText(EditActivity.this, "Something went wrong with the update", Toast.LENGTH_SHORT).show();
                }

                //Retourne a la page d'accueil
                Intent mainReturnActivity2 = new Intent(EditActivity.this, MainActivity.class);
                startActivity(mainReturnActivity2);
            }
        });
    }


    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent img){
        super.onActivityResult(requestCode, resultCode, img);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            switch (selectedImg){
                case 1:
                    imageUri1 = img.getData();
                    mImage1.setImageURI(imageUri1);
                    break;
                case 2:
                    imageUri2 = img.getData();
                    mImage2.setImageURI(imageUri2);
                    break;
                case 3:
                    imageUri3 = img.getData();
                    mImage3.setImageURI(imageUri3);
                    break;
                case 4:
                    imageUri4 = img.getData();
                    mImage4.setImageURI(imageUri4);
                    break;
                case 5:
                    imageUri5 = img.getData();
                    mImage5.setImageURI(imageUri5);
                    break;
                case 6:
                    imageUri6 = img.getData();
                    mImage6.setImageURI(imageUri6);
                    break;
            }
        }
    }
}


