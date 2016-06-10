package de.zudeick.galacticclash;

public class ADBJob {

    public static void galaxy(String activity) throws Exception {
        ADBAction.tap(ADBPoint.START_GALAXY);
        Thread.sleep(3000);

        switch (activity) {

            case "com.papaya.galaxyclash": {
                int start_at = 1;
                ADBAction.tap(ADBPoint.GALAXY_SEARCH);
                Thread.sleep(200);
                ADBAction.tap(ADBPoint.GALAXY_SEARCH_GALAXY);
                Thread.sleep(200);
                num_click(start_at);
                ADBAction.tap(ADBPoint.NUM_OK);
                Thread.sleep(200);
                ADBAction.tap(ADBPoint.GALAXY_SEARCH_SYSTEM);
                Thread.sleep(200);
                num_click(1);
                ADBAction.tap(ADBPoint.NUM_OK);
                Thread.sleep(200);
                ADBAction.tap(ADBPoint.GALAXY_SEARCH_SUBMIT);
                Thread.sleep(200);

                for (int galaxy = start_at; galaxy <= 12; galaxy++) {
                    for (int system = 1; system <= 400; system++) {
                        ADBAction.tap(ADBPoint.GALAXY_NEXT);
                    }
                }
            }
            break;
            case "com.next2fun.gc": {
                int start_at = 1;
                ADBAction.tap(ADBPoint.GALAXY_SEARCH);
                Thread.sleep(200);
                ADBAction.tap(ADBPoint.GALAXY_SEARCH_GALAXY);
                Thread.sleep(200);
                num_click(start_at);
                ADBAction.tap(ADBPoint.NUM_OK);
                Thread.sleep(200);
                ADBAction.tap(ADBPoint.GALAXY_SEARCH_SYSTEM);
                Thread.sleep(200);
                num_click(1);
                ADBAction.tap(ADBPoint.NUM_OK);
                Thread.sleep(200);
                ADBAction.tap(ADBPoint.GALAXY_SEARCH_SUBMIT);
                Thread.sleep(200);
                for (int galaxy = start_at; galaxy <= 12; galaxy++) {
                    for (int system = 1; system <= 400; system++) {
                        ADBAction.tap(ADBPoint.GALAXY_NEXT);
                    }
                }
            }
            break;
            case "com.next2fun.gc2": {
                int start_at= 1;
                ADBAction.tap(ADBPoint.GALAXY_SEARCH);
                Thread.sleep(200);
                ADBAction.tap(ADBPoint.GALAXY_SEARCH_GALAXY);
                Thread.sleep(200);
                num_click(start_at);
                ADBAction.tap(ADBPoint.NUM_OK);
                Thread.sleep(200);
                ADBAction.tap(ADBPoint.GALAXY_SEARCH_SYSTEM);
                Thread.sleep(200);
                num_click(1);
                ADBAction.tap(ADBPoint.NUM_OK);
                Thread.sleep(200);
                ADBAction.tap(ADBPoint.GALAXY_SEARCH_SUBMIT);
                Thread.sleep(200);

                for (int galaxy = start_at; galaxy <= 20; galaxy++) {
                    for (int system = 1; system <= 400; system++) {
                        ADBAction.tap(ADBPoint.GALAXY_NEXT);
                    }
                }
            }
            break;
        }
    }

    public static void num_click(int num) throws Exception {
        for (char digit : String.valueOf(num).toCharArray()) {
            ADBAction.tap(ADBClient.getInstance().getNum(Integer.valueOf("" + digit)));
        }
    }
}
