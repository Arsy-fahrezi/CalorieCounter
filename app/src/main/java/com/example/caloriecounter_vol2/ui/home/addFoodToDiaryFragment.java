package com.example.caloriecounter_vol2.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caloriecounter_vol2.DBAdapter;
import com.example.caloriecounter_vol2.MainActivity;
import com.example.caloriecounter_vol2.R;
import com.example.caloriecounter_vol2.ui.food.foodCursorAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addFoodToDiaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addFoodToDiaryFragment extends Fragment {

    /*- 01 Class Variables -------------------------------------------------------------- */
    private View mainView;
    private Cursor listCursorCategory;
    private Cursor listCursorFood;

    // Action buttons on toolbar
    private MenuItem menuItemEdit;
    private MenuItem menuItemDelete;

    // Holder
    private String currentMealNumber;
    private String currentCategoryId;
    private String currentCategoryName;
    private String currentFoodId;
    private String currentFoodName;

    private boolean lockPortionSizeByPcs;
    private boolean lockPortionSizeByGram;


    /*- 02 Fragment Variables ----------------------------------------------------------- */
    // Nessesary for making fragment run
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    /*- 03 Constructur ------------------------------------------------------------------ */
    // Nessesary for having Fragment as class
    public addFoodToDiaryFragment() {
        // Required empty public constructor
    }

    /*- 04 Creating Fragment ------------------------------------------------------------- */
    public static addFoodToDiaryFragment newInstance(String param1, String param2) {
        addFoodToDiaryFragment fragment = new addFoodToDiaryFragment();
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
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Add food to diary");


        /* Get data from fragment */
        Bundle bundle = this.getArguments();
        if(bundle != null){
            currentMealNumber = bundle.getString("mealNumber");
        }

        // Populate the list of categories
        populateListWithCategories("0", "");


        // Create menu
        // setHasOptionsMenu(true);


    } // onActivityCreated

    /*- 06 On create view ---------------------------------------------------------------- */
    // Sets main View variable to the view, so we can change views in fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_add_food_to_diary, container, false);
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

        ((MainActivity)getActivity()).getMenuInflater().inflate(R.menu.menu_categories, menu);

        // Assign menu items to variables
        menuItemEdit = menu.findItem(R.id.action_edit);
        menuItemDelete = menu.findItem(R.id.action_delete);

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
       /* if (id == R.id.action_add) {
            createNewCategory();
        }*/
        return super.onOptionsItemSelected(menuItem);
    }



    /*- Our own methods -*/

    /*- Poplate list with categories -------------------------------------------------------- */
    public void populateListWithCategories(String stringCategoryParentID, String stringCatgoryName){

        /* Database */
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        // Get categories
        String fields[] = new String[] {
                "_id",
                "category_name",
                "category_parent_id"
        };
        listCursorCategory = db.select("categories", fields, "category_parent_id", stringCategoryParentID, "category_name", "ASC");

        // Createa a array
        ArrayList<String> values = new ArrayList<String>();

        // Convert categories to string
        int categoriesCount = listCursorCategory.getCount();
        for(int x=0;x<categoriesCount;x++){
            values.add(listCursorCategory.getString(listCursorCategory.getColumnIndex("category_name")));

            /* Toast.makeText(getActivity(),
                    "Id: " + categoriesCursor.getString(0) + "\n" +
                            "Name: " + categoriesCursor.getString(1), Toast.LENGTH_SHORT).show();*/
            listCursorCategory.moveToNext();
        }


        // Close cursor
        // categoriesCursor.close();

        // Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);

        // Set Adapter
        ListView lv = (ListView)getActivity().findViewById(R.id.listViewAddFoodToDiary);
        lv.setAdapter(adapter);

        // OnClick
        if(stringCategoryParentID.equals("0")) {
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    categoryListItemClicked(arg2);
                }
            });
        }

        // Close db
        db.close();

    } // populateListWithCategories

    /*- Category list item clicked ---------------------------------------------------- */
    public void categoryListItemClicked(int listItemIndexClicked){

        // Move cursor to ID clicked
        listCursorCategory.moveToPosition(listItemIndexClicked);

        // Get ID and name from cursor
        // Set current name and id
        currentCategoryId = listCursorCategory.getString(0);
        currentCategoryName = listCursorCategory.getString(1);
        String parentCategoryID = listCursorCategory.getString(2);

        // Change title
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Add food from " + currentCategoryName + " to diary");


        // Move to sub class
        populateListWithCategories(currentCategoryId, currentCategoryName);


        // Show food in category
        showFoodInCategory(currentCategoryId, currentCategoryName, parentCategoryID);


    }

    /*- Show food in category ----------------------------------------------------------------- */
    public void showFoodInCategory(String categoryId, String categoryName, String categoryParentID){
        if(!(categoryParentID.equals("0"))) {
            //Toast.makeText(getActivity(), "Cat: + " + categoryName + " + Parent: " + categoryParentID, Toast.LENGTH_SHORT).show();

            /* Change layout */
            int id = R.layout.fragment_food;
            setMainView(id);

            /* Database */
            DBAdapter db = new DBAdapter(getActivity());
            db.open();

            // Get categories
            String fields[] = new String[] {
                    "_id",
                    "food_name",
                    "food_manufactor_name",
                    "food_description",
                    "food_serving_size_gram",
                    "food_serving_size_gram_mesurment",
                    "food_serving_size_pcs",
                    "food_serving_size_pcs_mesurment",
                    "food_energy_calculated"
            };
            listCursorFood = db.select("food", fields, "food_category_id", categoryId, "food_name", "ASC");


            // Find ListView to populate
            ListView lvItems = (ListView)getActivity().findViewById(R.id.listViewFood);



            // Setup cursor adapter using cursor from last step
            foodCursorAdapter continentsAdapter = new foodCursorAdapter(getActivity(), listCursorFood);

            // Attach cursor adapter to the ListView
            try{
                lvItems.setAdapter(continentsAdapter); // uses ContinensCursorAdapter
            }
            catch (Exception e){
                Toast.makeText(getActivity(), "E: " + e.toString(), Toast.LENGTH_LONG).show();
            }


            // OnClick
            lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    foodInCategoryListItemClicked(arg2);
                }
            });


            // Close db
            db.close();

        } //categoryParentID.equals
    } // showFoodInCategory



    /*- Food in category list item clicked ------------------------------------------------ */
    public void foodInCategoryListItemClicked(int listItemFoodIndexClicked){



        /* Change layout */
        int id = R.layout.fragment_add_food_to_diary_view_food;
        setMainView(id);

        // Move cursor to ID clicked
        listCursorFood.moveToPosition(listItemFoodIndexClicked);

        // Get ID and name from cursor
        // Set current name and id
        currentFoodId = listCursorFood.getString(0);
        currentFoodName = listCursorFood.getString(1);

        // Change title
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Add " + currentFoodName);


        /*  Get data from database */

        // Database
        DBAdapter db = new DBAdapter(getActivity());
        db.open();


        String fields[] = new String[] {
                "_id",
                "food_name",
                "food_manufactor_name",
                "food_description",
                "food_serving_size_gram",
                "food_serving_size_gram_mesurment",
                "food_serving_size_pcs",
                "food_serving_size_pcs_mesurment",
                "food_energy",
                "food_proteins",
                "food_carbohydrates",
                "food_fat",
                "food_energy_calculated",
                "food_proteins_calculated",
                "food_carbohydrates_calculated",
                "food_fat_calculated",
                "food_user_id",
                "food_barcode",
                "food_category_id",
                "food_image_a",
                "food_image_b",
                "food_image_c"
        };
        String currentIdSQL = db.quoteSmart(currentFoodId);
        Cursor foodCursor = db.select("food", fields, "_id", currentIdSQL);

        // Convert cursor to strings
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


        // Headline
        TextView textViewViewFoodName = (TextView) getView().findViewById(R.id.textViewViewFoodName);
        textViewViewFoodName.setText(stringName);

        // Sub headline
        TextView textViewViewFoodManufactorName = (TextView) getView().findViewById(R.id.textViewViewFoodManufactorName);
        textViewViewFoodManufactorName.setText(stringManufactorName);


        // Portion size
        EditText editTextPortionSizePcs = (EditText)getActivity().findViewById(R.id.editTextPortionSizePcs);
        editTextPortionSizePcs.setText(stringServingNameNumber);

        TextView textViewPcs = (TextView)getActivity().findViewById(R.id.textViewPcs);
        textViewPcs.setText(stringServingNameWord);

        // Portion gram
        EditText editTextPortionSizeGram = (EditText)getActivity().findViewById(R.id.editTextPortionSizeGram);
        editTextPortionSizeGram.setText(stringServingSize);


        // Image

        // Calculation line
        TextView textViewViewFoodAbout = (TextView) getView().findViewById(R.id.textViewViewFoodAbout);
        String foodAbout = stringServingSize + " " + stringServingMesurment + " = " +
                stringServingNameNumber  + " " + stringServingNameWord + ".";
        textViewViewFoodAbout.setText(foodAbout);

        // Description
        TextView textViewViewFoodDescription = (TextView) getView().findViewById(R.id.textViewViewFoodDescription);
        textViewViewFoodDescription.setText(stringDescription);

        // Calories table
        TextView textViewViewFoodEnergyPerHundred = (TextView) getView().findViewById(R.id.textViewViewFoodEnergyPerHundred);
        TextView textViewViewFoodProteinsPerHundred = (TextView) getView().findViewById(R.id.textViewViewFoodProteinsPerHundred);
        TextView textViewViewFoodCarbsPerHundred = (TextView) getView().findViewById(R.id.textViewViewFoodCarbsPerHundred);
        TextView textViewViewFoodFatPerHundred = (TextView) getView().findViewById(R.id.textViewViewFoodFatPerHundred);

        TextView textViewViewFoodEnergyPerN = (TextView) getView().findViewById(R.id.textViewViewFoodEnergyPerN);
        TextView textViewViewFoodProteinsPerN = (TextView) getView().findViewById(R.id.textViewViewFoodProteinsN);
        TextView textViewViewFoodCarbsPerN = (TextView) getView().findViewById(R.id.textViewViewFoodCarbsPerN);
        TextView textViewViewFoodFatPerN = (TextView) getView().findViewById(R.id.textViewViewFoodFatPerN);

        textViewViewFoodEnergyPerHundred.setText(stringEnergy);
        textViewViewFoodProteinsPerHundred.setText(stringProteins);
        textViewViewFoodCarbsPerHundred.setText(stringCarbohydrates);
        textViewViewFoodFatPerHundred.setText(stringFat);

        textViewViewFoodEnergyPerN.setText(stringEnergyCalculated);
        textViewViewFoodProteinsPerN.setText(stringProteinsCalculated);
        textViewViewFoodCarbsPerN.setText(stringCarbohydratesCalculated);
        textViewViewFoodFatPerN.setText(stringFatCalculated);

        /* Listener for editTextPortionSizePcs */
        editTextPortionSizePcs.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(!(s.toString().equals(""))){
                    editTextPortionSizePcsOnChange();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        editTextPortionSizePcs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                }else {
                    String lock = "portionSizePcs";
                    releaseLock(lock);
                }
            }
        });

        editTextPortionSizeGram.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(!(s.toString().equals(""))){
                    // My code here
                    editTextPortionSizeGramOnChange();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        editTextPortionSizeGram.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                }else {
                    String lock = "portionSizeGram";
                    releaseLock(lock);
                }
            }
        });

        Button buttonSubmit = (Button)getActivity().findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFoodToDiary();
            }


        });

        /* Close db */
        db.close();
    } // foodInCategoryListItemClicked

    private void releaseLock(String lock) {
        if(lock.equals("portionSizeGram")) {

        }else {
            lockPortionSizeByPcs = false;
        }
    }

    /*- editTextPortionSizePcsOnChange ---------------------------------------------------- */
    public void editTextPortionSizePcsOnChange(){
        if(!(lockPortionSizeByGram)) {
            // Lock
            lockPortionSizeByPcs = true;

            // Get value of pcs
            EditText editTextPortionSizePcs = (EditText) getActivity().findViewById(R.id.editTextPortionSizePcs);
            String stringPortionSizePcs = editTextPortionSizePcs.getText().toString();

            double doublePortionSizePcs = 0;

            if (stringPortionSizePcs.equals("")) {
                doublePortionSizePcs = 0;
            } else {
                try {
                    doublePortionSizePcs = Double.parseDouble(stringPortionSizePcs);
                } catch (NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }
            }


            // Database
            DBAdapter db = new DBAdapter(getActivity());
            db.open();

            String fields[] = new String[]{
                    "food_serving_size_gram"
            };
            String currentIdSQL = db.quoteSmart(currentFoodId);
            Cursor foodCursor = db.select("food", fields, "_id", currentIdSQL);

            // Convert cursor to strings
            String stringServingSize = foodCursor.getString(0);
            db.close();


            // Convert cursor to double
            double doubleServingSize = 0;
            try {
                doubleServingSize = Double.parseDouble(stringServingSize);
            } catch (NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }

            // Calculate how much n portionsize is in gram
            // We are changing pcs
            // Update gram
            double doublePortionSizeGram = Math.round(doublePortionSizePcs * doubleServingSize);

            // Update portion size gram
            EditText editTextPortionSizeGram = (EditText) getActivity().findViewById(R.id.editTextPortionSizeGram);
            editTextPortionSizeGram.setText("" + doublePortionSizeGram);
        }
    } // editTextPortionSizePcs

    /*- editTextPortionSizeGramOnChange ---------------------------------------------------- */
    public void editTextPortionSizeGramOnChange(){
        if(!(lockPortionSizeByPcs)) {

            // Lock
            lockPortionSizeByGram = true;

            // Get value of gram
            EditText editTextPortionSizeGram = (EditText) getActivity().findViewById(R.id.editTextPortionSizeGram);
            String stringPortionSizeGram = editTextPortionSizeGram.getText().toString();
            double doublePortionSizeGram = 0;
            try {
                doublePortionSizeGram = Double.parseDouble(stringPortionSizeGram);
            } catch (NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }

            // Database
            DBAdapter db = new DBAdapter(getActivity());
            db.open();

            String fields[] = new String[]{
                    "food_serving_size_gram"
            };
            String currentIdSQL = db.quoteSmart(currentFoodId);
            Cursor foodCursor = db.select("food", fields, "_id", currentIdSQL);

            // Convert cursor to strings
            String stringServingSize = foodCursor.getString(0);
            db.close();


            // Convert cursor to double
            double doubleServingSizeGram = 0;
            try {
                doubleServingSizeGram = Double.parseDouble(stringServingSize);
            } catch (NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }


            // Calculate pcs
            double doublePortionSizePcs = Math.round(doublePortionSizeGram / doubleServingSizeGram);



            // Update
            // Get value of pcs
            EditText editTextPortionSizePcs = (EditText) getActivity().findViewById(R.id.editTextPortionSizePcs);
            editTextPortionSizePcs.setText("" + doublePortionSizePcs);
        }

    } // editTextPortionSizeGramOnChange

    /* Add food to diary ------------------------------------------------------------------- */
    public void addFoodToDiary(){
        // We want to add food
        //Error
        int error = 0;

        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        String fields[] = new String[] {
                "_id",
                "food_name",
                "food_manufactor_name",
                "food_description",
                "food_serving_size_gram",
                "food_serving_size_gram_mesurment",
                "food_serving_size_pcs",
                "food_serving_size_pcs_mesurment",
                "food_energy",
                "food_proteins",
                "food_carbohydrates",
                "food_fat",
                "food_energy_calculated",
                "food_proteins_calculated",
                "food_carbohydrates_calculated",
                "food_fat_calculated",
                "food_user_id",
                "food_barcode",
                "food_category_id",
                "food_image_a",
                "food_image_b",
                "food_image_c"
        };
        String currentIdSQL = db.quoteSmart(currentFoodId);
        Cursor foodCursor = db.select("food", fields, "_id", currentIdSQL);

        // Convert cursor to strings
        String stringId = foodCursor.getString(0);
        String stringName = foodCursor.getString(1);
        String stringManufactorName = foodCursor.getString(2);
        String stringDescription = foodCursor.getString(3);
        String stringServingSizeGram = foodCursor.getString(4);
        String stringServingSizeGramMesurment = foodCursor.getString(5);
        String stringServingSizePcs = foodCursor.getString(6);
        String stringServingSizePcsMesurment = foodCursor.getString(7);
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



        EditText editTextPortionSizeGram = (EditText)getActivity().findViewById(R.id.editTextPortionSizeGram);
        String fdServingSizeGram = editTextPortionSizeGram.getText().toString();
        String fdServingSizeGramSQL = db.quoteSmart(fdServingSizeGram);
        double doubleportionSizeGram = 0;
        try {
            doubleportionSizeGram = Double.parseDouble(fdServingSizeGram);
        }
        catch(NumberFormatException nfe) {
            error = 1;
            Toast.makeText(getActivity(), "Plese fill the gram measurement", Toast.LENGTH_SHORT).show();
        }
        if(fdServingSizeGram.equals("")){
            error = 1;
            Toast.makeText(getActivity(), "empty measurement can not be accepted", Toast.LENGTH_SHORT).show();
        }




        // Date
        Calendar calender = Calendar.getInstance();
        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH);
        int day = calender.get(Calendar.DAY_OF_MONTH);

        String stringMonth;
        month = month+1;
        if(month <10) {
            stringMonth = "0" +month;
        }else {
            stringMonth = "" + month;

        }


        String stringDay = " ";
        if(day <10) {
            stringDay = "0" + day;
        }else {
            stringDay = "" + day;

        }


        String stringFdDate = year  + "-" + stringMonth + "-" + stringDay;
        String stringFdDateSQL = db.quoteSmart(stringFdDate);

        // Meal Number
        String stringFdMealNumber = currentMealNumber;
        String stringFdMealNumberSQL = db.quoteSmart(stringFdMealNumber);

        // Food ID
        String stringFdFoodID = currentFoodId;
        String stringFdFoodIDSQL = db.quoteSmart(stringFdFoodID);

        // Serving Size
        String fdServingSizeGramMesurmentSQL = db.quoteSmart(stringServingSizeGramMesurment);

        // Serving Size Pcs
        double doublePortionSizeGram = 0;
        double doubleServingSizeGram = 0;
        try {
            doubleServingSizeGram = Double.parseDouble(stringServingSizeGram);
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        double doublePortionSizePcs = Math.round(doublePortionSizeGram / doubleServingSizeGram);
        String stringFdServingSizePcs = "" + doublePortionSizePcs;
        String stringFdServingSizePcsSQL = db.quoteSmart(stringFdServingSizePcs);

        // serving size pcs mesurment
        String stringFdServingSizePcsMesurmentSQL = db.quoteSmart(stringServingSizePcsMesurment);

        //Energy Calculated
        double doubleEnergyPerHundred = Double.parseDouble(stringEnergy);

        double doubleFdEnergyCalculated = Math.round((doubleportionSizeGram*doubleEnergyPerHundred)/100);
        String stringFdEnergyCalculated = "" + doubleFdEnergyCalculated;
        String stringFdEnergyCalculatedSQL = db.quoteSmart(stringFdEnergyCalculated);

        //Protein Calculated
        double doubleProteinsPerHundred = Double.parseDouble(stringProteins);

        double doubleFdProteinsCalculated = Math.round((doubleportionSizeGram*doubleProteinsPerHundred)/100);
        String stringFdProteinsCalculated = "" + doubleFdProteinsCalculated;
        String stringFdProteinsCalculatedSQL = db.quoteSmart(stringFdProteinsCalculated);

        //Carbohydrates Calculated
        double doubleCarbohydratesPerHundred = Double.parseDouble(stringCarbohydrates);

        double doubleFdCarbohydratesCalculated = Math.round((doubleportionSizeGram*doubleCarbohydratesPerHundred)/100);
        String stringFdCarbohydratesCalculated = "" + doubleFdCarbohydratesCalculated;
        String stringFdCarbohydratesCalculatedSQL = db.quoteSmart(stringFdCarbohydratesCalculated);

        //Fat Calculated
        double doubleFatPerHundred = Double.parseDouble(stringFat);

        double doubleFdFatCalculated = Math.round((doubleportionSizeGram*doubleFatPerHundred)/100);
        String stringFdFatCalculated = "" + doubleFdFatCalculated;
        String stringFdFatCalculatedSQL = db.quoteSmart(stringFdFatCalculated);

        //Insert to SQL
        if (error == 0) {
            String inpFields = "_id, fd_date, fd_meal_number, fd_food_id," +
                    "fd_serving_size_gram, fd_serving_size_gram_mesurment," +
                    "fd_serving_size_pcs, fd_serving_size_pcs_mesurment," +
                    "fd_energy_calculated, fd_protein_calculated," +
                    "fd_carbohydrates_calculated, fd_fat_calculated";

            String inpValues = "NULL," +stringFdDateSQL + ", " + stringFdMealNumberSQL + ", " + stringFdFoodIDSQL + ", " +
                    fdServingSizeGramSQL + ", " + fdServingSizeGramMesurmentSQL + ", " +
                    stringFdServingSizePcsSQL + ", " + stringFdServingSizePcsMesurmentSQL + ", " +
                    stringFdEnergyCalculatedSQL + ", " + stringFdProteinsCalculatedSQL + ", " +
                    stringFdCarbohydratesCalculatedSQL + ", " + stringFdFatCalculatedSQL;

            db.insert("food_diary", inpFields, inpValues);

            //Toast.makeText(getActivity(), "Food diary updated", Toast.LENGTH_SHORT).show();

            //Change Fragment to Home Fragment
            /* Inialize fragmet */
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = HomeFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        }



        //Close db
        db.close();
    } // addFoodToDiary



    /*- Fragment methods -*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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