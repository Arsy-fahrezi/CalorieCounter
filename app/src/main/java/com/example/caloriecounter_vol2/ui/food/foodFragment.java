package com.example.caloriecounter_vol2.ui.food;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.caloriecounter_vol2.DBAdapter;
import com.example.caloriecounter_vol2.MainActivity;
import com.example.caloriecounter_vol2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link foodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class foodFragment extends Fragment {

    /*- 01 Class Variables -------------------------------------------------------------- */
    private View mainView;
    private Cursor listCursor;

    // Action buttons on toolbar
    private MenuItem menuItemEdit;
    private MenuItem menuItemDelete;

    // Holder for buttons on toolbar
    private String currentId;
    private String currentName;




    /*- 02 Fragment Variables ----------------------------------------------------------- */
    // Nessesary for making fragment run
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /*- 03 Constructur ------------------------------------------------------------------ */
    // Nessesary for having Fragment as class
    public foodFragment() {
        // Required empty public constructor
    }


    /*- 04 Creating Fragment ------------------------------------------------------------- */
    public static foodFragment newInstance(String param1, String param2) {
        foodFragment fragment = new foodFragment();
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
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Food");

        // Populate the list of categories
        populateListFood();

        // Create menu
        setHasOptionsMenu(true);
    } // onActivityCreated


    /*- 06 On create view ---------------------------------------------------------------- */
    // Sets main View variable to the view, so we can change views in fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_food, container, false);
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
        //MenuInflater menuInflater = ((MainActivity)getActivity()).getMenuInflater();
        // inflater.inflate(R.menu.menu_categories, menu);

        ((MainActivity)getActivity()).getMenuInflater().inflate(R.menu.menu_food, menu);

        // Assign menu items to variables
        menuItemEdit = menu.findItem(R.id.menu_action_food_edit);
        menuItemDelete = menu.findItem(R.id.menu_action_food_delete);

        // Hide as default
        menuItemEdit.setVisible(false);
        menuItemDelete.setVisible(false);
    }

    /*- 09 on Options Item Selected ------------------------------------------------------ */
    // Action icon clicked on
    // Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        int id = menuItem.getItemId();
        if (id == R.id.menu_action_food_add) {

        }
        if (id == R.id.menu_action_food_edit) {
            editFood();
        }
        if (id == R.id.menu_action_food_delete) {

        }
        return super.onOptionsItemSelected(menuItem);
    }


    /*- Our own methods -*/


    /*- populate List -------------------------------------------------------------- */
    public void populateListFood(){

        /* Database */
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        // Get categories
        String fields[] = new String[] {
                "_id",
                "food_name",
                "food_manufactor_name",
                "food_description",
                "food_serving_size",
                "food_serving_mesurment",
                "food_serving_name_number",
                "food_serving_name_word",
                "food_energy_calculated"
        };
        listCursor = db.select("food", fields, "", "", "food_name", "ASC");


        // Find ListView to populate
        ListView lvItems = (ListView)getActivity().findViewById(R.id.listViewFood);



        // Setup cursor adapter using cursor from last step
        foodCursorAdapter continentsAdapter = new foodCursorAdapter(getActivity(), listCursor);

        // Attach cursor adapter to the ListView
        lvItems.setAdapter(continentsAdapter); // uses ContinensCursorAdapter

        //On click
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                listItemClicked(arg2);
            }
        });


        // Close db
        db.close();

    }

    /*- List item clicked ------------------------------------------------------------ */
    public void listItemClicked(int listItemIDClicked) {
        /* Change layout */
        int id = R.layout.fragment_food_view;
        setMainView(id);

         //show edit button
        menuItemEdit.setVisible(true);
        menuItemDelete.setVisible(true);

        // Move cursor to ID clicked
        listCursor.moveToPosition(listItemIDClicked);

        // Get ID and name from cursor
        currentId = listCursor.getString(0);
        currentName = listCursor.getString(1);

        // Change title
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(currentName);

        /*Get data from Database*/

        //Open Database
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        String fields[] = new String[] {
                "_id",
                "food_name",
                "food_manufactor_name",
                "food_description",
                "food_serving_size",
                " food_serving_mesurment",
                " food_serving_name_number",
                " food_serving_name_word",
                " food_energy",
                " food_proteins",
                " food_carbohydrates",
                " food_fat",
                " food_energy_calculated",
                " food_proteins_calculated",
                " food_carbohydrates_calculated",
                " food_fat_calculated",
                " food_user_id",
                " food_barcode",
                " food_category_id",
                " food_thumb",
                " food_image_a",
                " food_image_b",
                " food_image_c",
                " food_last_used"
        };
        String currentIdSQL = db.quoteSmart(currentId);
        Cursor foodCursor = db.select("food", fields, "_id", currentIdSQL);

        //Convert cursor to string
        String stringId = foodCursor.getString(0);
        String stringName = foodCursor.getString(1);
        String stringManufactorName = foodCursor.getString(2);
        String stringDescription = foodCursor.getString(3);
        String stringServingSize = foodCursor.getString(4);
        String stringServingMesurment = foodCursor.getString(5);
        String stringServingNameNumber = foodCursor.getString(6);
        String stringServingNameWord = foodCursor.getString(7);
        String stringEnergy = foodCursor.getString(8);
        String stringProteins = foodCursor.getString(9);
        String stringCarbohydrates = foodCursor.getString(10);
        String stringFat = foodCursor.getString(11);
        String stringEnergyCalculated = foodCursor.getString(12);
        String stringProteinsCalculated = foodCursor.getString(13);
        String stringCarbohydratesCalculated = foodCursor.getString(14);
        String stringFatCalculated = foodCursor.getString(15);
        String stringUserId = foodCursor.getString(16);
        String stringBarcode = foodCursor.getString(17);
        String stringCategoryId = foodCursor.getString(18);
        String stringImageA = foodCursor.getString(19);
        String stringImageB = foodCursor.getString(20);
        String stringImageC = foodCursor.getString(21);


        //Headline
        TextView textViewViewFoodName = (TextView) getView().findViewById(R.id.textViewViewFoodName);
        textViewViewFoodName.setText(stringName);

        //Sub Headline
        TextView textViewViewFoodManufactorName = (TextView) getView().findViewById(R.id.textViewViewFoodManufactorName);
        textViewViewFoodManufactorName.setText(stringManufactorName);

        //Image


        //Calculation Line
        TextView textViewViewFoodAbout = (TextView) getView().findViewById(R.id.textViewViewFoodAbout);
        String foodAbout = stringServingSize + "" + stringServingMesurment + "=" +
                stringServingNameNumber + "" + stringServingNameWord + ".";

        textViewViewFoodAbout.setText(foodAbout);

        //Description
        TextView textViewViewFoodDescription = (TextView) getView().findViewById(R.id.textViewViewFoodDescription);
        textViewViewFoodDescription.setText(stringDescription);

        // Calories Table
        TextView textViewViewFoodEnergyPerHundred = (TextView) getView().findViewById(R.id.textViewViewFoodEnergyPerHundred);
        textViewViewFoodEnergyPerHundred.setText(stringEnergy);

        TextView textViewViewFoodProteinsPerHundred = (TextView) getView().findViewById(R.id.textViewViewFoodProteinsPerHundred);
        textViewViewFoodProteinsPerHundred.setText(stringProteins);

        TextView textViewViewFoodCarbsPerHundred = (TextView) getView().findViewById(R.id.textViewViewFoodCarbsPerHundred);
        textViewViewFoodCarbsPerHundred.setText(stringCarbohydrates);

        TextView textViewViewFoodFatPerHundred = (TextView) getView().findViewById(R.id.textViewViewFoodFatPerHundred);
        textViewViewFoodFatPerHundred.setText(stringFat);


        TextView textViewViewFoodEnergyPerN = (TextView) getView().findViewById(R.id.textViewViewFoodEnergyPerN);
        textViewViewFoodEnergyPerN.setText(stringEnergyCalculated);

        TextView textViewViewFoodProteinsN = (TextView) getView().findViewById(R.id.textViewViewFoodProteinsN);
        textViewViewFoodProteinsN.setText(stringProteinsCalculated);

        TextView textViewViewFoodCarbsPerN = (TextView) getView().findViewById(R.id.textViewViewFoodCarbsPerN);
        textViewViewFoodCarbsPerN.setText(stringCarbohydratesCalculated);

        TextView textViewViewFoodFatPerN = (TextView) getView().findViewById(R.id.textViewViewFoodFatPerN);
        textViewViewFoodFatPerN.setText(stringFatCalculated);

        //Close Database
        db.close();
    }

    /*-----Edit Food-----*/
    public void editFood() {
        /* Change layout */
        int id = R.layout.fragment_food_edit;
        setMainView(id);

        // Get ID and name from cursor
        currentId = listCursor.getString(0);
        currentName = listCursor.getString(1);

        // Change title
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Edit" + currentName);

        /*Get data from Database*/

        //Open Database
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        String fields[] = new String[] {
                "_id",
                "food_name",
                "food_manufactor_name",
                "food_description",
                "food_serving_size",
                " food_serving_mesurment",
                " food_serving_name_number",
                " food_serving_name_word",
                " food_energy",
                " food_proteins",
                " food_carbohydrates",
                " food_fat",
                " food_energy_calculated",
                " food_proteins_calculated",
                " food_carbohydrates_calculated",
                " food_fat_calculated",
                " food_user_id",
                " food_barcode",
                " food_category_id",
                " food_thumb",
                " food_image_a",
                " food_image_b",
                " food_image_c",
                " food_last_used"
        };
        String currentIdSQL = db.quoteSmart(currentId);
        Cursor foodCursor = db.select("food", fields, "_id", currentIdSQL);

        //Convert cursor to string
        String stringId = foodCursor.getString(0);
        String stringName = foodCursor.getString(1);
        String stringManufactorName = foodCursor.getString(2);
        String stringDescription = foodCursor.getString(3);
        String stringServingSize = foodCursor.getString(4);
        String stringServingMesurment = foodCursor.getString(5);
        String stringServingNameNumber = foodCursor.getString(6);
        String stringServingNameWord = foodCursor.getString(7);
        String stringEnergy = foodCursor.getString(8);
        String stringProteins = foodCursor.getString(9);
        String stringCarbohydrates = foodCursor.getString(10);
        String stringFat = foodCursor.getString(11);
        String stringEnergyCalculated = foodCursor.getString(12);
        String stringProteinsCalculated = foodCursor.getString(13);
        String stringCarbohydratesCalculated = foodCursor.getString(14);
        String stringFatCalculated = foodCursor.getString(15);
        String stringUserId = foodCursor.getString(16);
        String stringBarcode = foodCursor.getString(17);
        String stringCategoryId = foodCursor.getString(18);
        String stringImageA = foodCursor.getString(19);
        String stringImageB = foodCursor.getString(20);
        String stringImageC = foodCursor.getString(21);

        //Close Database
        db.close();

    }//Edit Food

    /*- Fragment  methods -*/


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}