package com.travel.Utils;

import java.util.Stack;

public class ScreenHistoryManager {
    private static Stack<Integer> screenHistory = new Stack<>();
    public static void addToHistory(int screenId) {
        screenHistory.push(screenId);
    }
    public static int getPreviousScreen() {
        if (!screenHistory.isEmpty() && screenHistory.size() > 1) {
            screenHistory.pop();
            return screenHistory.peek();
        }
        return -1;
    }
}
