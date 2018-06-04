package com.example.sergi.app1personaltrainer;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergi.app1personaltrainer.db.AppDatabase;
import com.example.sergi.app1personaltrainer.db.Ejercicios;
import com.example.sergi.app1personaltrainer.db.Rutina;

import java.util.ArrayList;
import java.util.List;

public class RutUser extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private static AppDatabase mDb;
    static ArrayList<Rutina> rutinasUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rut_user);


        mDb = AppDatabase.getDatabase(getApplicationContext());

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        String user = extras.getString("USER");
        rutinasUser = (ArrayList) mDb.rutModel().getRutinasFromUser(user);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    public ArrayList getDatosRutina(){
        return rutinasUser;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rut_user, menu);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        static int contador_ejer = 0;
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, contador_ejer);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_rut_user, container, false);


                TextView textView = (TextView) rootView.findViewById(R.id.descripcion);
                TextView repeticiones = (TextView) rootView.findViewById(R.id.num_repeticiones);
                TextView series = (TextView) rootView.findViewById(R.id.num_series);
                ImageView image = (ImageView) rootView.findViewById(R.id.image_ej);

                Rutina rut_aux = rutinasUser.get(contador_ejer);

                String image_name = mDb.ejerModel().getImageName(rut_aux.id_ejercicio);
                String description = mDb.ejerModel().getDescriptionEjer(rut_aux.id_ejercicio);

                int id = getResources().getIdentifier(image_name, "mipmap", getActivity().getPackageName());
                image.setImageResource(id);
                textView.setText(description);
                repeticiones.setText(rut_aux.repeticiones);
                series.setText(rut_aux.series);

            if(contador_ejer < rutinasUser.size()-1){
                contador_ejer++;
            }


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            int num = getDatosRutina().size();

            return num;
        }
    }
}
