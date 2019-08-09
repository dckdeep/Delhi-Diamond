package com.technotion.delhiDiamond.sever_classes;

import java.util.ArrayList;

public class Config {
    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "delhi_diamond";

    //This would be used to store the email of current logged in user
    public static final String USER_SHARED_PREF = "user";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    public static final String AGENT_ID = "1";

    public static final String PLAYER_ID = "1";

    public static final String DEALER_ID = "dealer";


    public static final String TOKENS = "0";
    public static final String GET_TOKENS = "GET_TOKENS";

    public static final String USER_STATUS = "player";



    public static final String BID_ID = "bid_id";

    public static final String CARD_ID = "card_id";

    public static final String BID_Amount = "Bid_Amount";



    public static final String GAME_START_TIME_g1 = "time_start_g1";


    public static final String GAME_START_TIME_g2 = "time_start_g2";


    public static final String GAME_START_TIME_g3 = "time_start_g3";


    public static final String G1_COUNT_OPEN = "one";

    public static final String G2_COUNT_OPEN = "one_";
    public static final String G3_COUNT_OPEN = "_one_";

    public static final String G4_COUNT_OPEN = "_one";

    public static final String G1_SET_TOTAL_AMOUNT = "total_amount";

    public static final String G2_SET_TOTAL_AMOUNT = "g2_total_amount";

    public static final String G3_SET_TOTAL_AMOUNT = "g3_total_amount";

    public static final String G4_SET_TOTAL_AMOUNT = "g4_total_amount";

    public static final String G1_coins = "G1_COINS";

    public static final String G2_coins = "G2_COINS";

    public static final String G3_coins = "G3_COINS";

    public static final String G4_coins = "G4_COINS";

    public static final String G4_cards = "G4_CARDS";

    public static final String G4_ID ="G4_GAME_ID";
    public static final String G5_ID ="G5_GAME_ID";
    public static final String G6_ID ="G6_GAME_ID";
    public static final String G7_ID ="G7_GAME_ID";
    public static final String G8_ID ="G8_GAME_ID";


    public static final String G4__Check_Game_ID ="G4__CHECK_GAME_ID";


    public static final String G3_ID ="G3_id";

    public static final String G3_CHECK_ID ="G3_check_id";

    public static final String Game1_Check_ID ="Game1_check_id";


    public static final String Game2_Check_ID ="Game2_check_id";

    public static final String GAME1_ID = "game1_id";
    public static final String GAME2_ID = "game2_id";

    public static final String Win_player_id = "win_player_id";

    public static final String G1_BID_ID="G1_Bid_id";
    public static final String TRANSACTION_ID_g1 = "Transaction_id_g1";

    public static final String TRANSACTION_ID_g2 = "Transaction_id_g2";
    public static final String TRANSACTION_ID_g3 = "Transaction_id_g3";
    public static final String TRANSACTION_ID_g4 = "Transaction_id_g4";
    public static final String count_G1_trns ="count_g1_trns";
    public static final String count_G2_trns ="count_g2_trns";
    public static final String count_G3_trns ="count_g3_trns";
    public static final String count_G4_trns ="count_g3_trns";

    public static ArrayList<String> get_jantri_coins = new ArrayList<>();
    public static ArrayList<String> get_jantri_numbers = new ArrayList<>();

    public static ArrayList<String> get_numbers_row2 = new ArrayList<>();
    public static ArrayList<String> get_numbers_row1 = new ArrayList<>();

    public static int get_game = 0;


    public static int view_list_admin = 0;
    public static int game_type_admin = 0;

    public static  String first = "first_num";
    public static  int get_game_type = 0;
    public static  int check = -1;

    public static  int final_jantri_check = -1;
    public static int game_report_game_type = -1;


    public static String get_tt = "check_ttl";


    public static int com_patti = 0;

    public static String starting_time="view_jantri_time";




}
