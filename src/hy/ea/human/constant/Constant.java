package hy.ea.human.constant;

public class Constant {
    public static final String ALL = "ALL";
    public static final String CURRENT = "CURRENT";
    public static final String TODAY = "TODAY";
    public static final String YESTERDAY = "YESTERDAY";
    public static final String CURRENT_WEEK = "CURRENT_WEEK";
    public static final String BEFORE_WEEK = "BEFORE_WEEK";
    public static final String CURRENT_MONTH = "CURRENT_MONTH";
    public static final String BEFORE_MONTH = "BEFORE_MONTH";
    public static final String CURRENT_QUARTER = "CURRENT_QUARTER";
    public static final String BEFORE_QUARTER = "BEFORE_QUARTER";
    public static final String CURRENT_YEAR = "CURRENT_YEAR";
    public static final String BEFORE_YEAR = "BEFORE_YEAR";
    public static final String MONTH = "MONTH";

    public static final String LATE = "LATE";
    public static final String LEAVE_EARLY = "LEAVE_EARLY";
    public static final String LEAVE_ABSENCE = "LEAVE_ABSENCE";
    public static final String LEAVE_SICK = "LEAVE_SICK";
    public static final String LEAVE_MARRIAGE = "LEAVE_MARRIAGE";
    public static final String LEAVE_ANNUAL = "LEAVE_ANNUAL";
    public static final String LEAVE_MATERNITY = "LEAVE_MATERNITY";
    public static final String LEAVE_FUNERAL = "LEAVE_FUNERAL";
    public static final String LEAVE_WORKINJURY = "LEAVE_WORKINJURY";
    public static final String LEAVE_ROTATIONREST = "LEAVE_ROTATIONREST";
    public static final String ABSENTEE = "ABSENTEE";
    public static final String LEAVE = "LEAVE";
    public static final String EIGHT_HOUR_OVERTIME = "EIGHT_HOUR_OVERTIME";
    public static final String HOLIDAY_OVERTIME = "HOLIDAY_OVERTIME";
    public static final String WEEKEND_OVERTIME = "WEEKEND_OVERTIME";
    public static final String BIG_HOLIDAY_OVERTIME = "BIG_HOLIDAY_OVERTIME";
    public static final String OUTWORK = "OUTWORK";
    public static final Double OVERTIME_DAYS_PER_MONTH = 30D;
    public static final Double CHECK_ON_DAYS_PER_MONTH = 21.75D;

    public static final String ELDERLY_CARE_RATE = "8";
    public static final String MEDICAL_RATE = "2";
    public static final String UNEMPLOYMENT_RATE = "0.4";


    public static final String Y_VALUE = "Y";
    public static final String N_VALUE = "N";

    public static final String DISCOUNT_TYPE_COMPUTE = "COMPUTE";
    public static final String DISCOUNT_TYPE_SETUP = "SETUP";

    public static final String DEDUCTION_TYPE_COMPUTE = "COMPUTE";
    public static final String DEDUCTION_TYPE_UNIFIED = "UNIFIED";

    public static final String BLANK_CHAR = " ";
    /**
     * 每天工作小时
     */
    public static final Integer HOUR_WORK_DAY = 8;

    public static final String[] END_WEEK_OF_WORK_DATE = {"2024-02-04","2024-02-18","2024-04-07","2024-04-28","2024-05-11"
            ,"2024-09-14","2024-09-29","2024-10-12","2025-01-26","2025-02-08","2025-04-27","2025-09-28","2025-10-11",
            "2026-01-04","2026-02-14","2026-02-28","2026-05-09","2026-09-20","2026-10-10"};
    public static final String[] HOLIDAY_DATE_2024 = {"2024-01-01","2024-02-10","2024-02-11","2024-02-12","2024-02-13","2024-02-14","2024-02-15","2024-02-16"
            ,"2024-02-17","2024-04-04","2024-04-05","2024-04-06","2024-05-01","2024-05-02","2024-05-03","2024-05-04","2024-05-05","2024-06-08","2024-06-09","2024-06-10"
            ,"2024-09-15","2024-09-16","2024-09-17","2024-10-01","2024-10-02","2024-10-03","2024-10-04","2024-10-05","2024-10-06","2024-10-07"};
    public static final String[] HOLIDAY_DATE_2025 = {"2025-01-01","2025-01-28","2025-01-29","2025-01-30","2025-01-31","2025-02-01","2025-02-02","2025-02-03"
            ,"2025-02-04","2025-04-04","2025-04-05","2025-04-06","2025-05-01","2025-05-02","2025-05-03","2025-05-04","2025-05-05","2025-05-31"
            ,"2025-06-01","2025-06-02","2025-10-01","2025-10-02","2025-10-03","2025-10-04","2025-10-05"
            ,"2025-10-06","2025-10-07","2025-10-08",
            "2026-01-01","2026-01-02","2026-02-16","2026-02-17","2026-02-18","2026-02-19","2026-02-20","2026-02-23"
            ,"2026-04-05","2026-04-06","2026-05-01","2026-05-04","2026-05-05","2026-06-19","2026-09-25","2026-10-01","2026-10-02","2026-10-03"
            ,"2026-10-05","2026-10-06","2026-10-07"};

    public static final String[] BIG_HOLIDAY_2024_2025_2026 = {"2024-02-10","2024-02-11","2024-02-12",
            "2024-10-01","2024-10-02","2024-10-03",
            "2025-01-01", "2025-01-28","2025-01-29","2025-01-30","2025-01-31",
            "2025-04-04","2025-05-01","2025-05-02","2025-05-31",
            "2025-10-01","2025-10-02","2025-10-03","2025-10-06",
            "2026-01-01","2026-02-17","2026-02-18","2026-02-19","2026-04-05",
            "2026-05-01","2026-06-19","2026-09-25","2026-10-01","2026-10-02","2026-10-03"
    };

    public static final String[] NO_NEED_SIGN_IN = {"2025-02-06","2025-05-28","2025-05-29","2025-05-30","2025-06-03","2025-06-04",
    "2025-06-05","2025-06-06","2025-06-16","2025-06-30"};

    public static final String TIME_RANGE_1M = "1M";
    public static final String TIME_RANGE_3M = "3M";
    public static final String TIME_RANGE_6M = "6M";
    public static final String TIME_RANGE_1Y = "1Y";
    public static final String TIME_RANGE_2Y = "2Y";
    public static final String TIME_RANGE_3Y = "3Y";

    public static final String MORNING_SIGN_OUT_TIME = "12:00";
    public static final String AFTERNOON_SIGN_IN_TIME = "14:00";
    public static final String CHECKON_MANAGE_ROLE_NAME = "CHECKON_MANAGE_ROLE_NAME";

    public static final String[] NOT_SOCIAL_SECURITY = {"cstaff201509139K6KBSW8TV0000001924"
            ,"cstaff201509139K6KBSW8TV0000001909","cstaff20150908EC75SGDVS90000001105","cstaff2012013044W22TA8840000001615","cstaff20101107w4xvpsrkrx0000002832"
    ,"Staff20251009EMDSW5ZFHZ0000002521","Staff20251014FUYY6BHYJ70000005632","cstaff20190831Z2JC6CS8MF0000061973","Staff20251126XKKBA3YCG80000002315","Staff20251202DACCQIP8BK0000000808"};

    public static final String[] NOT_SOCIAL_SECURITY_1 = {"cstaff201509139K6KBSW8TV0000001924"
            ,"cstaff201509139K6KBSW8TV0000001909","cstaff20150908EC75SGDVS90000001105","cstaff2012013044W22TA8840000001615","cstaff20101107w4xvpsrkrx0000002832"
    ,"Staff20251009EMDSW5ZFHZ0000002521","Staff20251014FUYY6BHYJ70000005632","cstaff20190831Z2JC6CS8MF0000061973","Staff20251126XKKBA3YCG80000002315","Staff20251202DACCQIP8BK0000000808"};
    public static final String SIGN_IN_COUNT_CHANGE_DATE = "2025-06-01";
    public static final String SIGN_IN_COUNT_4 = "4";
    public static final String SIGN_IN_COUNT_2 = "2";

    public static final String SIGN_IN_TYPE_EARLY = "SIGN_IN_TYPE_EARLY";
    public static final String SIGN_IN_TYPE_NIGHT = "SIGN_IN_TYPE_NIGHT";
    public static final String SIGN_IN_TYPE_LATE_NIGHT = "SIGN_IN_TYPE_LATE_NIGHT";
    public static final String SIGN_IN_TYPE_ORDINARY = "SIGN_IN_TYPE_ORDINARY";
    public static final String SIGN_IN_TYPE_MORNING = "SIGN_IN_TYPE_MORNING";
    public static final String SIGN_IN_TYPE_AFTERNOON = "SIGN_IN_TYPE_AFTERNOON";

    public static final String NOT_SIGN_IN_PHONE = "cstaff20200520NWXE25RVN40000000612";

    public static final String COMPANY_ID = "company201009046vxdyzy4wg0000000025";

}
