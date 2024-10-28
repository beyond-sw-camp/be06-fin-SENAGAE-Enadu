package org.example.backend.Common;

import java.util.HashMap;
import java.util.Map;

public class UserGradeIconManager {
    private static Map<String, String> gradeIconMap = new HashMap<>();

    static {
        gradeIconMap.put("뉴비","https://enadu.s3.ap-northeast-2.amazonaws.com/gradeicon/newbie.svg");
        gradeIconMap.put("견습", "https://enadu.s3.ap-northeast-2.amazonaws.com/gradeicon/intern.svg");
        gradeIconMap.put("프로", "https://enadu.s3.ap-northeast-2.amazonaws.com/gradeicon/pro.svg");
        gradeIconMap.put("마스터", "https://enadu.s3.ap-northeast-2.amazonaws.com/gradeicon/master.svg");
        gradeIconMap.put("갓", "https://enadu.s3.ap-northeast-2.amazonaws.com/gradeicon/god.svg");
    }

    public static String getGradeIcon(String grade) {
        return gradeIconMap.get(grade);
    }
}
