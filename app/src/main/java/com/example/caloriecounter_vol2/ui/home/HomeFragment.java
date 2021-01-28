package com.example.caloriecounter_vol2.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.caloriecounter_vol2.DBAdapter;
import com.example.caloriecounter_vol2.MainActivity;
import com.example.caloriecounter_vol2.R;

import org.w3c.dom.Text;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    /*- 01 Class Variables -------------------------------------------------------------- */
    private View mainView;
    private Cursor listCursor;

    // Action buttons on toolbar
    private MenuItem menuItemEdit;
    private MenuItem menuItemDelete;

    // Holder for buttons on toolbar
    private String currentId;
    private String currentName;

    private String currentDateYear = "";
    private String currentDateMonth = "";
    private String currentDateDay = "";


    /*- 02 Fragment Variables ----------------------------------------------------------- */
    // Nessesary for making fragment run
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /*- 03 Constructur ------------------------------------------------------------------ */
    // Nessesary for having Fragment as class
    public HomeFragment() {
        // Required empty public constructor
    }

    /*- 04 Creating Fragment ------------------------------------------------------------- */
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*- 05 on Activity Created ---------------------------------------------------------- */
    // Run methods when started
    // Set toolbar menu items
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /* Set title */
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Home");

        // getDataFromDbAndDisplay
        initializeHome();

        // Create menu
        // setHasOptionsMenu(true);
    } // onActivityCreated


    /*- 06 On create view ---------------------------------------------------------------- */
    // Sets main View variable to the view, so we can change views in fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // mainView = inflater.inflate(R.layout.fragment_home, container, false);
        mainView = inflater.inflate(R.layout.fragment_home, container, false);
        return mainView;
    }


    /*- 07 set main view ----------------------------------------------------------------- */
    // Changing view method in fragmetn
    private void setMainView(int id){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainView = inflater.inflate(id, null);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(mainView);
    }

    /*- 08 on Create Options Menu -------------------------------------------------------- */
    // Creating action icon on toolbar
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate menu
        MenuInflater menuInflater = ((MainActivity)getActivity()).getMenuInflater();
        inflater.inflate(R.menu.menu_goal, menu);

        //((MainActivity)getActivity()).getMenuInflater().inflate(R.menu.menu_food, menu);

        // Assign menu items to variables
        menuItemEdit = menu.findItem(R.id.menu_action_food_edit);
        //menuItemDelete = menu.findItem(R.id.menu_action_food_delete);

        // Hide as default
        // menuItemEdit.setVisible(false);
        //menuItemDelete.setVisible(false);
    }

    /*- 09 on Options Item Selected ------------------------------------------------------ */
    // Action icon clicked on
    // Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        int id = menuItem.getItemId();
        //if (id == R.id.menu_action_goal_edit) {

        //}
        return super.onOptionsItemSelected(menuItem);
    }
    /*- Our own methods -*/

    /*- Initialize home ------------------------------------------------------------ */
    private void initializeHome(){


        if(currentDateYear.equals("") || currentDateMonth.equals("") || currentDateDay.equals("")) {
            Calendar calender = Calendar.getInstance();
            int year = calender.get(Calendar.YEAR);
            int month = calender.get(Calendar.MONTH);
            int day = calender.get(Calendar.DAY_OF_MONTH);


            currentDateYear = "" + year;

            month = month+1;
            String stringMonth;
            if(month <10) {
                currentDateMonth = "0" +month;
            }else {
                currentDateMonth = "" + month;

            }

            if(day <10) {
                currentDateDay = "0" + day;
            }else {
                currentDateDay = "" + day;
            }

        }
            String stringFdDate = currentDateYear  + "-" + currentDateMonth + "-" + currentDateDay;

            updateTable(stringFdDate, "0");







        /* Breakfast listener */
        ImageView imageViewAddBreakfast = (ImageView)getActivity().findViewById(R.id.imageViewAddBreakfast);
        imageViewAddBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood(0); // 0 == Breakfast
            }
        });


    } // initalizeHome


    private  void updateTable(String stringDate,String stringMealNumber) {
        //Toast.makeText(getActivity(), "Update Table date = " +stringDate, Toast.LENGTH_SHORT).show();

        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        Cursor allCategories;
        String fields[] = new String[] {
                "_id",
                "fd_food_id",
                "fd_serving_size_gram",
                "fd_serving_size_gram_mesurment",
                "fd_serving_size_pcs",
                "fd_serving_size_pcs_mesurment",
                "fd_energy_calculated",
                "fd_protein_calculated",
                "fd_carbohydrates_calculated",
                "fd_fat_calculated"
        };
        String stringDateSQL = db.quoteSmart(stringDate);
        Cursor cursorFd = db.select("food_diary", fields, "fd_date", stringDateSQL);

        String fieldsFood[] = new String[] {
                "_id",
                "food_name",
                "food_manufactor_name",
        };
        Cursor cursorFood;

        String stringFdFoodId = "";
        String stringFdFoodIdSQL = "";

        String stringFdServingSizeGram = "";
        String stringFdServingSizeGramMesurment = "";
        String stringFdServingSizePcs = "";
        String stringFdServingSizePcsMesurment = "";
        String stringFdEnergyCalculated = "";

        String stringFoodID = "";
        String stringFoodName = "";
        String stringFoodManufactorName  = "";

        int intCursorFdCount = cursorFd.getCount();
        for(int x=0;x<intCursorFdCount;x++) {
            String stringFdID = cursorFd.getString(0);
            //Toast.makeText(getActivity(), "ID: " + stringFdID, Toast.LENGTH_SHORT).show();

            String food_id = cursorFd.getString(1);
            String food_idSQL = db.quoteSmart(food_id);

            String fdServingSizeGram = cursorFd.getString(2);
            String fdServingSizeGramMeasurement = cursorFd.getString(3);
            String fdServingSizepcs = cursorFd.getString(4);
            String fdServingSizepcsMeasurement = cursorFd.getString(5);
            String fdEnergyCalculated= cursorFd.getString(6);


            cursorFood = db.select("food", fieldsFood, "_id", food_idSQL);

            String foodID = cursorFood.getString(0);
            String foodName = cursorFood.getString(1);
            String foodManufactorName = cursorFood.getString(2);


            String subLine = foodManufactorName + ", " +
                    fdServingSizeGram + " " +
                    fdServingSizeGramMeasurement + ", " +
                    fdServingSizepcs + " " +
                    fdServingSizepcsMeasurement;

            TableLayout t1 = (TableLayout)getActivity().findViewById(R.id.tableLayoutBreakfastItem);
            TableRow tr1 = new TableRow(getActivity());
            tr1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TableRow tr2 = new TableRow(getActivity());
            tr2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView textViewName = new TextView(getActivity());
            textViewName.setText(foodName);
            tr1.addView(textViewName);

            TextView textViewEnergy = new TextView(getActivity());
            textViewEnergy.setText(fdEnergyCalculated);
            tr1.addView(textViewEnergy);

            TextView textViewSubLine = new TextView(getActivity()); // Add textview
            textViewSubLine.setText(subLine);
            tr2.addView(textViewSubLine);

            t1.addView(tr1, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)); /* Add row to TableLayout. */
            t1.addView(tr2, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)); /* Add row to TableLayout. */




            cursorFd.moveToNext();
        }




        db.close();
    }

    // AddFood Method
    private void addFood(int mealNumber){

        /* Inialize fragmet */
        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = addFoodToDiaryFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Send variable
        Bundle bundle = new Bundle();
        bundle.putString("mealNumber", ""+mealNumber); // Put anything what you want
        fragment.setArguments(bundle);

        //
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


    } // initalizeHome

    /*----------On Create----------*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        /*-----Set Title-----*/
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Home");
    }


    public interface OnFragmentInteractionListener {
    }
}