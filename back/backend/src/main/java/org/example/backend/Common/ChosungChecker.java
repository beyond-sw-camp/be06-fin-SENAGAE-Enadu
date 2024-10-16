package org.example.backend.Common;

public class ChosungChecker {

    // 초성 유니코드 범위 (U+1100 ~ U+1112)
    private static final char CHOSUNG_START = 0x3131; // 'ᄀ'의 시작점
    private static final char CHOSUNG_END = 0x314E;   // 'ᄒ'의 끝점

    public static boolean isChosungOnly(String input) {
        for (char ch : input.toCharArray()) {
            // 문자가 한글 초성 범위 내에 있는지 확인
            if (ch < CHOSUNG_START || ch > CHOSUNG_END) {
                return false;
            }
        }
        return true;
    }
}