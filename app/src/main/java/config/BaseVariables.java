package config;

public class BaseVariables {
    //APP-END-POINTS-JSON
    private static final String BASE_URL = "http://192.168.43.113/skill_rest_api/";
    public static final String URL_SIGNUP = BASE_URL+"signup.php";
    public static final String URL_LOGIN = BASE_URL+"signin.php";
    public static final String URL_REG_SKILLS =BASE_URL+"reg_skills.php";
    public static final String URL_QUERY_SKILLS = BASE_URL+"fetch_skills.php";

    public static final String URL_FETCH_COUNTRIES = BASE_URL+"url_fetch_countries.php";
    public static final String URL_FETCH_COUNTIES =BASE_URL+"url_fetch_counties.php";
    public static final String URL_FETCH_ESTATES = BASE_URL+ "url_fetch_estates.php";
    public static final String URL_FETCH_EXPERT = BASE_URL + "url_fetch_expert.php";

    public static final String URL_FETCH_MY_SKILLS = BASE_URL + "fetch_my_skills.php";

    //SPINNER
    public static final String[] SPINNER_GENDER = {"Male", "Female","Others"};


    //SESSION-STATIC-VARIABLES
    public static final String PREFS_NAME = "skilllite";
    public static final String USER_ID = "user_id";
    public static final String USERNAME ="user_name";
    public static final String NAME ="name";
    public static final String PHONE ="phone";
    public static final String GENDER = "gender";
    public static final String IS_LOGIN = "is_login";





}
