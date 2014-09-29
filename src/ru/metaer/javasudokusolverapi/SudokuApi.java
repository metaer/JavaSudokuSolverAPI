package ru.metaer.javasudokusolverapi;

import org.json.JSONObject;
import ru.metaer.javasudokusolver.*;

import java.util.HashMap;
import java.util.Map;

public class SudokuApi {
    public static String getSolution(String inputString) {
        SudokuSolver ss = SudokuSolver.getInstance();
        Integer code = null;
        String message;
        String solution = null;
        LanguageManager.getInstance().setCurrentLocale("ru");
        Map result = new HashMap<String,Object>();
        try{
            solution = ss.getSolutionString(inputString);
            result.put("solution", solution);
            result.put("code", 1);
            result.put("message", "Задача успешно решена");
            result.put("type", "solved");
        }
        catch (SudokuSolverLibException e) {
            result.put("message", e.getMessage());
            if (e instanceof NoSolutionException) {
                result.put("code", 0);
                result.put("type", "nosolution");
            } else if (e instanceof UserErrorException) {
                result.put("code", -1);
                result.put("type", "usererror");
                if (e instanceof WrongSudokuConditionException || e instanceof TaskIsAlreadySolvedException) {
                    Map extra = new HashMap<String, Object>();
                    result.put("extra", extra);
                    if (e instanceof RepeatInColumnException) {
                        extra.put("subtype", "repeat_in_col");
                        extra.put("number", ((RepeatInColumnException) e).getCol());
                    } else if (e instanceof RepeatInRowException) {
                        extra.put("subtype", "repeat_in_row");
                        extra.put("number", ((RepeatInRowException) e).getRow());
                    } else if (e instanceof RepeatInSquareException) {
                        extra.put("subtype", "repeat_in_square");
                        extra.put("number", ((RepeatInSquareException) e).getSquareNumber());
                    } else if (e instanceof TaskIsAlreadySolvedException) {
                        extra.put("subtype", "alreadysolved");
                    }
                }
            } else if (e instanceof InternalErrorException) {
                result.put("code", -2);
                result.put("type", "internalerror");
            }
        }

        JSONObject json = new JSONObject(result);
        return json.toString();
    }
}