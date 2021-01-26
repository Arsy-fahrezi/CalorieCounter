package com.example.caloriecounter_vol2;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

public class DBSetupInsert {
    /* Variables */
    private final Context context;

    /* Public Class ------------------------------------------------------ */
    public DBSetupInsert(Context ctx){
        this.context = ctx;
    }



    /* Setup Insert To Categories ----------------------------------------- */
    // To insert to category table
    public void setupInsertToCategories(String values){
        try{
            DBAdapter db = new DBAdapter(context);
            db.open();
            db.insert("categories",
                    "_id, category_name, category_parent_id, category_icon, category_note",
                    values);
            db.close();
        }
        catch (SQLiteException e){
            // Toast.makeText(context, "Error; Could not insert categories.", Toast.LENGTH_SHORT).show();
        }
    }
    public void insertAllCategories(){
        setupInsertToCategories("NULL, 'Bread', '0', '', NULL");
        setupInsertToCategories("NULL, 'Bread', '1', '', NULL");
        setupInsertToCategories("NULL, 'Cereals', '1', '', NULL");
        setupInsertToCategories("NULL, 'Frozen bread and rolls', '1', '', NULL");
        setupInsertToCategories("NULL, 'Crispbread', '1', '', NULL");

        //Parent id 6
        setupInsertToCategories("NULL, 'Dessert and baking', '0', '', NULL");
        setupInsertToCategories("NULL, 'Baking', '6', '', NULL");
        setupInsertToCategories("NULL, 'Biscuit', '6', '', NULL");


        setupInsertToCategories("NULL, 'Drinks', '0', '', NULL");
        setupInsertToCategories("NULL, 'Soda', '9', '', NULL");


        setupInsertToCategories("NULL, 'Fruit and vegetables', '0', '', NULL");
        setupInsertToCategories("NULL, 'Frozen fruits and vegetables', '11', '', NULL");
        setupInsertToCategories("NULL, 'Fruit', '11', '', NULL");
        setupInsertToCategories("NULL, 'Vegetables', '11', '', NULL");
        setupInsertToCategories("NULL, 'Canned fruits and vegetables', '11', '', NULL");


        setupInsertToCategories("NULL, 'Health', '0', '', NULL");
        setupInsertToCategories("NULL, 'Meal substitutes', '16', '', NULL");
        setupInsertToCategories("NULL, 'Protein bars', '16', '', NULL");
        setupInsertToCategories("NULL, 'Protein powder', '16', '', NULL");


        setupInsertToCategories("NULL, 'Meat, chicken and fish', '0', '', NULL");
        setupInsertToCategories("NULL, 'Meat', '20', '', NULL");
        setupInsertToCategories("NULL, 'Chicken', '20', '', NULL");
        setupInsertToCategories("NULL, 'Seafood', '20', '', NULL");


        setupInsertToCategories("NULL, 'Dairy and eggs', '0', '', NULL");
        setupInsertToCategories("NULL, 'Eggs', '24', '', NULL");
        setupInsertToCategories("NULL, 'Cream and sour cream', '24', '', NULL");
        setupInsertToCategories("NULL, 'Yogurt', '24', '', NULL");


        setupInsertToCategories("NULL, 'Dinner', '0', '', NULL");
        setupInsertToCategories("NULL, 'Ready dinner dishes', '28', '', NULL");
        setupInsertToCategories("NULL, 'Pizza', '28', '', NULL");
        setupInsertToCategories("NULL, 'Noodle', '28', '', NULL");
        setupInsertToCategories("NULL, 'Pasta', '28', '', NULL");
        setupInsertToCategories("NULL, 'Rice', '28', '', NULL");
        setupInsertToCategories("NULL, 'Taco', '28', '', NULL");


        setupInsertToCategories("NULL, 'Cheese', '0', '', NULL");
        setupInsertToCategories("NULL, 'Cream cheese', '35', '', NULL");


        setupInsertToCategories("NULL, 'On bread', '0', '', NULL");
        setupInsertToCategories("NULL, 'Cold meats', '37', '', NULL");
        setupInsertToCategories("NULL, 'Sweet spreads', '37', '', NULL");
        setupInsertToCategories("NULL, 'Jam', '37', '', NULL");


        setupInsertToCategories("NULL, 'Snacks', '0', '', NULL");
        setupInsertToCategories("NULL, 'Nuts', '41', '', NULL");
        setupInsertToCategories("NULL, 'Potato chips', '41', '', NULL");
    }




    /* Setup Insert To Food ----------------------------------------------- */
    // To insert food to food table
    public void setupInsertToFood(String values){

        try {
            DBAdapter db = new DBAdapter(context);
            db.open();
            db.insert("food",
                    "_id, food_name, food_manufactor_name, food_serving_size_gram, food_serving_size_gram_mesurment, food_serving_size_pcs, food_serving_size_pcs_mesurment, food_energy, food_proteins, food_carbohydrates, food_fat, food_energy_calculated, food_proteins_calculated, food_carbohydrates_calculated, food_fat_calculated, food_user_id, food_barcode, food_category_id, food_thumb, food_image_a, food_image_b, food_image_c, food_notes",
                    values);
            db.close();
        }
        catch (SQLiteException e){
            // Toast.makeText(context, "Error; Could not insert food.", Toast.LENGTH_SHORT).show();
        }

    }
    // Insert all food into food database
    public void insertAllFood(){
        setupInsertToFood("NULL, 'Wheat Bread', 'Sari Roti', '35', 'gram', '1', 'pcs', '190', '6', '31', '4', '190', '6', '31', '4', NULL, NULL, '2', 'sari_roti_roti_gandum.jpg', 'sari_roti_roti_gandum_a.jpg', 'sari_roti_roti_gandum_b.jpg', 'sari_roti_roti_gandum_c.jpg', NULL");
        setupInsertToFood("NULL, 'Rolled Oats, Cooked', 'Quaker Oats', '38', 'gram', '1', 'cup', '140', '5', '20', '3.5', '140', '5', '20', '3.5', NULL, NULL, '3', 'quacker_oats_rolledoats.jpg', 'quacker_oats_rolledoats_a.jpg', 'aquacker_oats_rolledoats_b.jpg', 'quacker_oats_rolledoats_c.jpg', NULL");
        setupInsertToFood("NULL, 'Instant Oatmeal', 'Quaker', '35', 'gram', '1', 'cup', '140', '5', '24', '3.5', '140', '5', '24', '3.5', NULL, NULL, '3', 'axa_havregryn_store_thumb.jpg', 'axa_havregryn_store_a.jpg', 'axa_havregryn_store_b.jpg', 'axa_havregryn_store_c.jpg', NULL");

        setupInsertToFood("NULL, 'Pita Bread', 'Khubs', '60', 'gram', '1', 'pcs', '165', '5.5', '33', '0.7', '165', '5.5', '33, '0.7', NULL, NULL, '4', 'hatting_frosne_pitabrod_thumb.jpg', 'hatting_frosne_pitabrod_a.jpg', 'hatting_frosne_pitabrod_b.jpg', 'hatting_frosne_pitabrod_c.jpg', NULL");

        setupInsertToFood("NULL, 'Crisp Bread', 'Obst', '5', 'gram', '1', 'pcs', '90', '2', '18', '0', '90', '2', '18', '0', NULL, NULL, '5', 'wasa_fiber_balance_thumb.jpg', 'wasa_fiber_balance_a.jpg', 'wasa_fiber_balance_b.jpg', 'wasa_fiber_balance_c.jpg', NULL");
        setupInsertToFood("NULL, 'Sport +', 'Wasa', '16', 'gram', '1', 'stk', '338', '10.5', '55.5', '3.5', '54', '2', '9', '1', NULL, NULL, '5', 'wasa_sport_pluss_thumb.jpg', 'wasa_sport_pluss_a.jpg', 'wasa_sport_pluss_b.jpg', 'wasa_sport_pluss_c.jpg', NULL");

        setupInsertToFood("NULL, 'Sugar', 'Gulaku', '4.2', 'gram', '1', 'tsp', '16', '0', '4.2', '0', '16', '0', '4.2', '0', NULL, NULL, '7', 'dan_sukker_sukker_thumb.jpg', 'dan_sukker_sukker_a.jpg', 'dan_sukker_sukker_b.jpg', 'dan_sukker_sukker_c.jpg', NULL");
        setupInsertToFood("NULL, 'Maple Syrup', 'Traders Joe', '20', 'gram', '1', 'tsp', '52', '0', '13.4', '0.04', '52', '0', '13.4', '0.04', NULL, NULL, '7', 'moollerens_siktet_hvetemel_thumb.jpg', 'moollerens_siktet_hvetemel_a.jpg', 'moollerens_siktet_hvetemel_b.jpg', 'moollerens_siktet_hvetemel_c.jpg', NULL");

        setupInsertToFood("NULL, 'Ritz Crackers', 'Nabisco', '200', 'gram', '1', 'Pack', '490', '7.8', '61', '23', '980', '16', '122', '46', NULL, NULL, '8', 'mondelez_norge_ritz_crackers_thumb.jpg', 'mondelez_norge_ritz_crackers_a.jpg', 'mondelez_norge_ritz_crackers_b.jpg', 'mondelez_norge_ritz_crackers_c.jpg', NULL");

        setupInsertToFood("NULL, 'Kratingdaeng', 'Kratingdaeng', '150', 'ml', '1', 'can', '100', '1.5', '25', '0', '100', '1.5', '25', '0', NULL, NULL, '10', 'ringnes_battery_energy_drink_50cl_thumb.jpg', 'ringnes_battery_energy_drink_50cl_a.jpg', 'ringnes_battery_energy_drink_50cl_b.jpg', 'ringnes_battery_energy_drink_50cl_c.jpg', NULL");

        setupInsertToFood("NULL, 'Broccoli, Raw', 'Prior', '100', 'gram', '1', 'cup', '31', '2.57', '6.04', '0.3', '31', '2.57', '6.04', '0.3', NULL, NULL, '12', 'eldorado_frossen_brokkoliblanding_thumb.jpg', 'eldorado_frossen_brokkoliblanding_a.jpg', 'eldorado_frossen_brokkoliblanding_b.jpg', 'eldorado_frossen_brokkoliblanding_c.jpg', NULL");
        setupInsertToFood("NULL, 'Broccoli, Coooked', 'Prior', '100', 'gram', '1', 'cup', '34', '2.3', '6.93', '0.4', '34', '2.3', '6.93', '0.4', NULL, NULL, '12', 'rema_1000_frosne_brokkolitopper_thumb.jpg', 'rema_1000_frosne_brokkolitopper_a.jpg', 'rema_1000_frosne_brokkolitopper_b.jpg', 'rema_1000_frosne_brokkolitopper_c.jpg', NULL");

        setupInsertToFood("NULL, 'Cantaloupe Melons', 'Prior', '100', 'gram', '1', 'cup', '60', '1.5', '14.4', '0.4', '60', '1.5', '14.4', '0.4', NULL, NULL, '13', 'bama_rode_druer_thumb.jpg', 'bama_rode_druer_a.jpg', 'bama_rode_druer_b.jpg', 'bama_rode_druer_c.jpg', NULL");

        setupInsertToFood("NULL, 'Carrots', 'Prior', '100', 'gram', '100', 'gram, '52', '1.2', '12.2', '0.3', '52', '1.2', '12.2', '0.3', NULL, NULL, '14', 'bama_brokkoli_thumb.jpg', 'bama_brokkoli_a.jpg', 'bama_brokkoli_b.jpg', 'bama_brokkoli_c.jpg', NULL");
        setupInsertToFood("NULL, 'Carrots, Boiled', 'Priot', '46', 'gram', '1', 'pcs', '3.3', '0.2', '3.0', '0.1', '3.3', '0.2', '3.0', '0.1', NULL, NULL, '14', 'bama_gulrot_thumb.jpg', 'bama_gulrot_a.jpg', 'bama_gulrot_b.jpg', 'bama_gulrot_c.jpg', NULL");
        setupInsertToFood("NULL, 'Green Lettuce, Raw', 'Prior', '24', 'gram', '1', 'leaf', '3.6', '0.8', '2.5', '0.4', '3.6', '0.8', '2.5', '0.4', NULL, NULL, '14', 'bama_isberg_mix_thumb.jpg', 'bama_isberg_mix_a.jpg', 'bama_isberg_mix_b.jpg', 'bama_isberg_mix_c.jpg', NULL");
        setupInsertToFood("NULL, 'Red Lettuce, Raw', 'Prior', '17', 'gram', '1', 'leaf', '2.7', '0.9', '1.5', '0.3', '2.7', '0.9', '1.5', '0.3', NULL, NULL, '14', 'bama_isbergsalat_thumb.jpg', 'bama_isbergsalat_a.jpg', 'bama_isbergsalat_b.jpg', 'bama_isbergsalat_c.jpg', NULL");
        setupInsertToFood("NULL, 'Salad', 'McDONALD', '87', 'gram', '1', 'portion', '17.4', '0.9', '3.7', '0.2', '17.4', '3.6', '12.3', '1.5', NULL, NULL, '14', 'bama_meksikansk_salat_thumb.jpg', 'bama_meksikansk_salat_a.jpg', 'bama_meksikansk_salat_b.jpg', 'bama_meksikansk_salat_c.jpg', NULL");

        setupInsertToFood("NULL, 'Baked beans', 'Coop', '420', 'gram', '1', 'boks', '116', '5', '19', '0.5', '487', '21', '80', '2', NULL, NULL, '15', 'coop_baked_beans_thumb.jpg', 'coop_baked_beans_a.jpg', 'coop_baked_beans_b.jpg', 'coop_baked_beans_c.jpg', NULL");
        setupInsertToFood("NULL, 'Canned Fruit Cocktail', 'Del Monte', '214', 'gram', '1', 'cup', '150', '1', '40', '0.2', '150', '3.4', '145', '1.8', NULL, NULL, '15', 'eldorado_kokosmelk_thumb.jpg', 'eldorado_kokosmelk_a.jpg', 'eldorado_kokosmelk_b.jpg', 'eldorado_kokosmelk_c.jpg', NULL");
        setupInsertToFood("NULL, 'Canned Vegetable', 'Del Monte', '163', 'gram', '1', 'cup', '79.9', '4.2', '15.1', '0.4', '79.9', '14.7', '61.8', '3.4', NULL, NULL, '15', 'eldorado_lett_kokosmelk_thumb.jpg', 'eldorado_lett_kokosmelk_a.jpg', 'eldorado_lett_kokosmelk_b.jpg', 'eldorado_lett_kokosmelk_c.jpg', NULL");
        setupInsertToFood("NULL, 'Canned Crushed Tomato', 'Del Monte', '28', 'gram', '1', 'ounces', '9.0', '0.5', '2.0', '0.1', '9', '1.1', '7.2', '0.7', NULL, NULL, '15', 'eldorado_maiskorn_thumb.jpg', 'eldorado_maiskorn_a.jpg', 'eldorado_maiskorn_b.jpg', 'eldorado_maiskorn_c.jpg', NULL");

        setupInsertToFood("NULL, 'Chocolate & Almond', 'Soy Joy', '30', 'gram', '1', 'bar', '160', '10', '12', '10', '160', '10', '12', '10', NULL, NULL, '18', 'atkins_chocolate_peanut_thumb.jpg', 'atkins_chocolate_peanut_a.jpg', 'atkins_chocolate_peanut_b.jpg', 'atkins_chocolate_peanut_c.jpg', NULL");
        setupInsertToFood("NULL, 'Chocolate bar', 'Snickers', '57', 'gram', '1', 'bar', '328', '41', '27', '9.2', '164', '21', '14', '5', NULL, NULL, '18', 'maxim_protein_bar_almond_and_caramel_flavour_thumb.jpg', 'maxim_protein_bar_almond_and_caramel_flavour_a.jpg', 'maxim_protein_bar_almond_and_caramel_flavour_b.jpg', 'maxim_protein_bar_clmond_cnd_caramel_flavour_c.jpg', NULL");
        setupInsertToFood("NULL, 'Caramel Delight', 'Fitbar', '22', 'gram', '1', 'bar', '90', '3', '15', '3.5', '90', '3', '15', '3.5', NULL, NULL, '18', 'tine_yt_1_oppladning_for_trening_sot_og_salt_med_sjokolade_thumb.jpg', 'tine_yt_1_oppladning_for_trening_sot_og_salt_med_sjokolade_a.jpg', 'tine_yt_1_oppladning_for_trening_sot_og_salt_med_sjokolade_b.jpg', 'tine_yt_1_oppladning_for_trening_sot_og_salt_med_sjokolade_c.jpg', NULL");

        setupInsertToFood("NULL, 'Protein Bar', 'Herbalife', '25', 'gram', '1', 'glass', '90', '9', '13', '1', '90', '9', '13', '1', NULL, NULL, '19', 'gymgrossisten_100_lean_whey_chocolate_milkshake_thumb.jpg', 'gymgrossisten_100_lean_whey_chocolate_milkshake_a.jpg', 'gymgrossisten_100_lean_whey_chocolate_milkshake_b.jpg', 'gymgrossisten_100_lean_whey_chocolate_milkshake_c.jpg', NULL");

        setupInsertToFood("NULL, 'Corned Beef', 'Pronas', '50', 'gram', '1', 'portion', '80', '5', '6', '4', '80', '5', '6', '4', NULL, NULL, '21', 'gilde_stjernebacon_thumb.jpg', 'gilde_stjernebacon_a.jpg', 'gilde_stjernebacon_b.jpg', 'gilde_stjernebacon_c.jpg', NULL");
        setupInsertToFood("NULL, 'Beef Patty ', 'USDA', '64', 'gram', '1', 'pcs', '159', '14.7', '0.6', '10.5', '159', '62.8', '2.1', '94.5', NULL, NULL, '21', 'nordfjord_kvernet_deig_av_storfe_thumb.jpg', 'nordfjord_kvernet_deig_av_storfe_a.jpg', 'nordfjord_kvernet_deig_av_storfe_b.jpg', 'nordfjord_kvernet_deig_cv_storfe_c.jpg', NULL");

        setupInsertToFood("NULL, 'Fried Chicken, Drumstick', 'Prior', '29', 'gram', '1, 'pcs', '71', '7.8', '0.5', '4.0', '71', '33.4', '1.8', '35.9', NULL, NULL, '22', 'den_stolte_hane_frossen_god_helg_kylling_bbq_thumb.jpg', 'den_stolte_hane_frossen_god_helg_kylling_bbq_a.jpg', 'den_stolte_hane_frossen_god_helg_kylling_bbq_b.jpg', 'den_stolte_hane_frossen_god_helg_kylling_bbq_c.jpg', NULL");
        setupInsertToFood("NULL, 'Boiled Chicken, Breast', 'Prior', '86', 'gram', '1', 'pcs', '142', '21', '0.2', '0.9', '186', '42', '0', '2', NULL, NULL, '22', 'first_price_frossen_kyllingfilet_thumb.jpg', 'first_price_frossen_kyllingfilet_a.jpg', 'first_price_frossen_kyllingfilet_b.jpg', 'first_price_frossen_kyllingfilet_c.jpg', NULL");

        setupInsertToFood("NULL, 'Sardine, Canned', 'Del Monte', '149', 'gram', '1', 'can', '310', '36.7', '0', '17.1', '310', '156', '0', '154', NULL, NULL, '23', 'fiskemannen_reker_i_lake_thumb.jpg', 'fiskemannen_reker_i_lake_a.jpg', 'fiskemannen_reker_i_lake_b.jpg', 'fiskemannen_reker_i_lake_c.jpg', NULL");

        setupInsertToFood("NULL, 'Egg white', 'Prior', '30', 'gram', '1', 'pcs', '42', '10.2', '0.4', '0', '13', '3', '0', '0', NULL, NULL, '25', 'first_price_egg_eggehvite_thumb.jpg', 'first_price_egg_eggehvite_a.jpg', 'first_price_egg_eggehvite_b.jpg', 'first_price_egg_eggehvite_c.jpg', NULL");
        setupInsertToFood("NULL, 'Egg yolk', 'Prior', '15', 'gram', '1', 'pcs', '308', '15.8', '0.2', '27.1', '46', '2', '0', '4', NULL, NULL, '25', 'first_price_egg_eggeplomme_thumb.jpg', 'first_price_egg_eggeplomme_a.jpg', 'first_price_egg_eggeplomme_b.jpg', 'first_price_egg_eggeplomme_c.jpg', NULL");
        setupInsertToFood("NULL, 'Egg, Boiled', 'Prior', '63', 'gram', '1', 'pcs', '143', '12.4', '0.3', '10.2', '90', '8', '0', '6', NULL, NULL, '25', 'first_price_egg_kokt_thumb.jpg', 'first_price_egg_kokt_a.jpg', 'first_price_egg_kokt_b.jpg', 'first_price_egg_kokt_c.jpg', NULL");
        setupInsertToFood("NULL, 'Egg, fried', 'Prior', '63', 'gram', '1', 'pcs', '142', '12.4', '0.3', '10.1', '89', '8', '0', '6', NULL, NULL, '25', 'flemming_egg_thumb.jpg', 'flemming_egg_a.jpg', 'flemming_egg_b.jpg', 'flemming_egg_c.jpg', NULL");

        setupInsertToFood("NULL, 'Cottage Cheese original', 'Tine', '250', 'gram', '1', 'boks', '96', '13', '1.5', '4.3', '240', '33', '4', '11', NULL, NULL, '26', 'tine_cottage_cheese_thumb.jpg', 'tine_cottage_cheese_a.jpg', 'tine_cottage_cheese_b.jpg', 'tine_cottage_cheese_c.jpg', NULL");
        setupInsertToFood("NULL, 'Mager Cottage Cheese 400g', 'Tine', '200', 'gram', '0.5', 'boks', '79', '13', '2.1', '2', '158', '26', '4', '4', NULL, NULL, '26', 'tine_mager_cottage_cheese_400g_thumb.jpg', 'tine_mager_cottage_cheese_400g_a.jpg', 'tine_mager_cottage_cheese_400g_b.jpg', 'tine_mager_cottage_cheese_400g_c.jpg', NULL");
        setupInsertToFood("NULL, 'Cheddar, ', 'Kraft', '170', 'gram', '1', 'sheets', '50', '3', '1', '3.5', '50', '3', '1', '3.5', NULL, NULL, '26', 'tine_mager_cottage_cheese_eple_paere_og_vanilje_thumb.jpg', 'tine_mager_cottage_cheese_eple_paere_og_vanilje_a.jpg', 'tine_mager_cottage_cheese_eple_paere_og_vanilje_b.jpg', 'tine_mager_cottage_cheese_eple_paere_og_vanilje_c.jpg', NULL");

        setupInsertToFood("NULL, 'Yogurt', 'Cimory', '70', 'gram', '1', 'cup', '60', '2', '9', '2', '60', '2', '9', '2', NULL, NULL, '27', 'q_meieriene_liten_skyr_blabaer_thumb.jpg', 'q_meieriene_liten_skyr_blabaer_a.jpg', 'q_meieriene_liten_skyr_blabaer_b.jpg', 'q_meieriene_liten_skyr_blabaer_c.jpg', NULL");
        setupInsertToFood("NULL, 'Squeeze Yogurt', 'Cimory', '120', 'gram', '1', 'cup', '120', '2', '12', '1.5', '120', '2', '12', '1.5', NULL, NULL, '27', 'q_meieriene_liten_skyr_jordbaer_og_lime_thumb.jpg', 'q_meieriene_liten_skyr_jordbaer_og_lime_a.jpg', 'q_meieriene_liten_skyr_jordbaer_og_lime_b.jpg', 'q_meieriene_liten_skyr_jordbaer_og_lime_c.jpg', NULL");

        setupInsertToFood("NULL, 'Pepperoni, Big slices', 'Pizza Hut', '106', 'gram', '1', 'slices', '299', '12', '34', '13', '299', '12', '34', '13', NULL, NULL, '30', 'dr_oetker_ristorante_pizza_speciale_thumb.jpg', 'dr_oetker_ristorante_pizza_speciale_a.jpg', 'dr_oetker_ristorante_pizza_speciale_b.jpg', 'dr_oetker_ristorante_pizza_speciale_c.jpg', NULL");

        setupInsertToFood("NULL, 'Mie Goreng', 'Indomie', '80', 'gram', '1', 'pack', '349', '8', '52', '12', '349', '8', '52', '12', NULL, NULL, '31', 'first_price_nudler_med_kjootsmak_thumb.jpg', 'first_price_nudler_med_kjootsmak_a.jpg', 'first_price_nudler_med_kjootsmak_b.jpg', 'first_price_nudler_med_kjootsmak_c.jpg', NULL");

        setupInsertToFood("NULL, 'Spaghetti', 'La fonte', '60', 'gram', '1', 'portion', '220', '7', '45', '1', '220', '7', '45', '1', NULL, NULL, '32', 'sopps_fullkornspasta_fusilli_thumb.jpg', 'sopps_fullkornspasta_fusilli_a.jpg', 'sopps_fullkornspasta_fusilli_b.jpg', 'sopps_fullkornspasta_fusilli_c.jpg', NULL");
        setupInsertToFood("NULL, 'Macaroni', 'La Fonte', '60', 'gram', '1', 'portion', '230', '8', '45', '3', '230', '8', '45', '3', NULL, NULL, '32', 'sopps_fullkornspasta_penne_thumb.jpg', 'sopps_fullkornspasta_penne_a.jpg', 'sopps_fullkornspasta_penne_b.jpg', 'sopps_fullkornspasta_penne_c.jpg', NULL");

        setupInsertToFood("NULL, 'Cooked White Rice, Regular', 'Prior', '158', 'gram', '1', 'cup', '205', '16.2', '44.5', '0.4', '205', '16.2', '44.5', '0.4', NULL, NULL, 'NULL', NULL, NULL, NULL, NULL, 'Cooked white rice counted per cup'");
        setupInsertToFood("NULL, 'Cooked red Rice, Regular', 'Prior', '195', 'gram', '1', 'cup', '216', '17.2', '44.8', '0.4', '216', '17.2', '44.8', '0.4', NULL, NULL, 'NULL', NULL, NULL, NULL, NULL, 'Cooked red rice counted per cup'");
        setupInsertToFood("NULL, 'Cooked Yellow Rice, Regular', 'Prior', '163', 'gram', '1', 'cup', '252', '3.7', '43', '6.7', '252', '3.7', '43', '6.7', NULL, NULL, 'NULL', NULL, NULL, NULL, NULL, 'Cooked yellow rice counted per cup'");


        setupInsertToFood("NULL, 'Cheese Spread', 'Kraft', '100', 'gram', '100', 'g', '296', '7.1', '3.5', '28.6', '295', '28.4', '9.2', '257', NULL, NULL, '36', 'kraft_philadelphia_naturell_light_thumb.jpg', 'kraft_philadelphia_naturell_light_a.jpg', 'kraft_philadelphia_naturell_light_b.jpg', 'kraft_philadelphia_naturell_light_c.jpg', NULL");

        setupInsertToFood("NULL, 'Chocolate hazelnut spread', 'Nutella', '37', 'gram', '1', 'serving', '200', '2', '23', '11', '200', '8', '93.2', '99', NULL, NULL, '39', 'kavli_hapaa_thumb.jpg', 'kavli_hapaa_a.jpg', 'kavli_hapaa_b.jpg', 'kavli_hapaa_c.jpg', NULL");

        setupInsertToFood("NULL, 'Srikaya Jam', 'Mariza', '20', 'gram', '1', 'tsp', '50', '1', '11', '1', '50', '1', '11', '1', NULL, NULL, '40', 'first_price_bringaebaersyltetooy_thumb.jpg', 'first_price_bringaebaersyltetooy_a.jpg', 'first_price_bringaebaersyltetooy_b.jpg', 'first_price_bringaebaersyltetooy_c.jpg', NULL");
        setupInsertToFood("NULL, 'Strawberry Jam', 'Morin', '14', 'gram', '1', 'tsp', '40', '0', '10', '0', '40', '0', '10', '0', NULL, NULL, '40', 'lerums_utvalde_kirsebear_thumb.jpg', 'lerums_utvalde_kirsebear_a.jpg', 'lerums_utvalde_kirsebear_b.jpg', 'lerums_utvalde_kirsebear_c.jpg', NULL");
        setupInsertToFood("NULL, 'Blueberry Jam', 'Morin', '600', 'gram', '1', 'pakke', '40', '0', '10', '0', '40', '0', '10', '0'', NULL, NULL, '42', 'first_price_noottemiks_thumb.jpg', 'first_price_noottemiks_a.jpg', 'first_price_noottemiks_b.jpg', 'first_price_noottemiks_c.jpg', NULL");

        setupInsertToFood("NULL, 'Potato Chip', 'Chitato', '20', 'gram', '1', 'bag', '100', '2', '11', '6', '100', '2', '11', '6', NULL, NULL, '43', 'eldorado_micropop_thumb.jpg', 'eldorado_micropop_a.jpg', 'eldorado_micropop_b.jpg', 'eldorado_micropop_c.jpg', NULL");
        setupInsertToFood("NULL, 'Tortilla Chips', 'Doritos', '20', 'gram', '1', 'bag', '110', '1', '13', '5', '110', '1', '13', '5', NULL, NULL, '43', 'soorlandschips_spansk_paprika_med_persille_thumb.jpg', 'soorlandschips_spansk_paprika_med_persille_a.jpg', 'soorlandschips_spansk_paprika_med_persille_b.jpg', 'soorlandschips_spansk_paprika_med_persille_c.jpg', NULL");
    }
}
