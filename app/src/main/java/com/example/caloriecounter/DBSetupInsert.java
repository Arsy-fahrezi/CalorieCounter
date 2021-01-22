package com.example.caloriecounter;

import android.content.Context;

public class DBSetupInsert {

    //Variables
    private final Context context;

    //Public Class
    public DBSetupInsert(Context context) {
        this.context = context;
    }

    //Setup insert to table categories
    public void setupInsertToCategories(String values) {
        DBAdapter db = new DBAdapter(context);
        db.open();
        db.insert("categories", "category_id, category_name, category_parent_id, category_icon, category_note",
                values);
        db.close();
    }

    public void insertAllCategories() {
        setupInsertToCategories("NULL, 'Grain & Cereal', '1', NULL, NULL");
    }

    //Setup insert to table food
    public void setupInsertToFood(String values){
        DBAdapter db = new DBAdapter(context);
        db.open();
        db.insert("food",
                "food_id, food_name, food_manufactor, food_serving_size, food_serving_measurement, food_serving_name_number, " +
                        "food_serving_name_word, food_energy, food_proteins, food_carbohydrates, food_fat, food_energy_calculated, " +
                        "food_proteins_calculated, food_carbohydrates_calculated, food_fat_calculated, food_user_id, food_barcode , " +
                        "food_category_id, food_thumb, food_image_a, food_image_b, food_image_c, food_note",
                values);
    }

    // Insert all food into the database
    public void insertAllFood() {
        setupInsertToFood("NULL, 'Cooked White Rice, Regular', 'Prior', '158', 'gram', '1', 'cup', '242', '4.4', '0.4', '0.4', '405', '4.7', '53.2', '0.4', NULL, NULL, 'NULL', NULL, NULL, NULL, NULL, 'Cooked white rice counted per cup'");
        setupInsertToFood("NULL, 'Cooked White Red, Regular', 'Prior', '195', 'gram', '1', 'cup', '216', '5.0', '44.8', '1.8', '405', '5.0', '44.8', '1.8', NULL, NULL, 'NULL', NULL, NULL, NULL, NULL, 'Cooked red rice counted per cup'");
    }
}
