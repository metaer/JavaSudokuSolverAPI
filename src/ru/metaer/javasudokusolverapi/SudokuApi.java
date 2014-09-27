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
        try{
            solution = ss.getSolutionString(inputString);
            code = 1;
            message = "Successfully solved";
        }
        catch (SudokuSolverLibException e) {
            message = e.getMessage();
            if (e instanceof UserErrorException) {
                code = -1;
            } else if (e instanceof NoSolutionException) {
                code = 0;
            } else if (e instanceof InternalErrorException) {
                code = -2;
            }
        }
        Map result = new HashMap<String,Object>();
        result.put("code", code);
        result.put("message", message);
        if (solution != null) {
            result.put("solution", solution);
        }
        JSONObject json = new JSONObject(result);
        return json.toString();
    }
}