package cpnv.com.accueil_cpw;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    /**Déclare les variables**/
    ViewPager viewPager;

    TextView Pseudo;
    TextView Gender;
    TextView Coma;
    TextView Geo;
    TextView Description;
    TextView Country;
    Button Edit;

    ImageView Home;
    TextView Accueil;
    ImageView Group;

    /**TOAST**/
    String stHome = "HOME HERE";
    String stGroup = "GROUPE HERE";
    String sEdit = "EDIT PAGE HERE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** IMAGE SLIDE **/
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);


        //Metadata
        Pseudo = (TextView) findViewById(R.id.txtPseudo);
        Gender = (TextView) findViewById(R.id.txtGender);
        Coma = (TextView) findViewById(R.id.txtComa);
        Geo = (TextView) findViewById(R.id.txtGeo);
        Description = (TextView) findViewById(R.id.txtDescription);
        Country = (TextView) findViewById(R.id.txtCountry);
        Edit = (Button) findViewById(R.id.btnEdit);

        Home = (ImageView) findViewById(R.id.imgHome);
        Accueil = (TextView) findViewById(R.id.txtAccueil);
        Group = (ImageView) findViewById(R.id.imgGroup);

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),stHome,Toast.LENGTH_SHORT).show();
            }
        });
        Group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),stGroup,Toast.LENGTH_SHORT).show();
            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),sEdit,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
